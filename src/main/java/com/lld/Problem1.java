package com.lld;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*
Problem statement
Design a system where a user can cancel a confirmed booking (hotel or flight).
The refund amount is calculated based on a configurable cancellation policy.
Rules: 100% refund if cancelled 7+ days before travel, 50% refund if 3–7 days, 0% refund within 24 hours.
System must support multiple booking types without changing core logic.

Functional requirements

User can cancel a CONFIRMED booking
Refund amount calculated based on days remaining before travel
Policy is config-driven (rules should be changeable without code change)
System supports HotelBooking and FlightBooking (extensible to more)
Booking status transitions: CONFIRMED → CANCELLED → REFUND_PROCESSED
Notification sent to user after cancellation

Non-functional requirements
No third-party libraries
Open for extension — new booking types must not change CancellationService
 */

    enum BookingType{
        HOTEL,
        FLIGHT
    }

    enum BookingStatus{
        CONFIRMED,
        CANCELLED,
        REFUND_PROCESSED
    }

    abstract class Booking {
        private final String bookingId;
        private final String userId;
        private final LocalDate travelDate;
        private final double totalAmount;
        private BookingStatus bookingStatus;

        public Booking(String bookingId, String userId, LocalDate travelDate, double totalAmount) {
            this.bookingId = bookingId;
            this.userId = userId;
            this.travelDate = travelDate;
            this.totalAmount = totalAmount;
            this.bookingStatus = BookingStatus.CONFIRMED;
        }

        protected abstract BookingType getBookingType();

        public String getBookingId() {return bookingId;}
        public String getUserId() {return userId;}
        public LocalDate getTravelDate() {return travelDate;}
        public double getTotalAmount() {return totalAmount;}
        public BookingStatus getBookingStatus() {return bookingStatus;}

        public void setBookingStatus(BookingStatus status) {
            this.bookingStatus = status;
        }
    }

    class HotelBooking extends Booking {

        private final String hotelName;
        private final String roomType;

        public HotelBooking(Builder builder) {
            super(builder.bookingId, builder.userId, builder.travelDate, builder.totalAmount);
            this.hotelName = builder.hotelName;
            this.roomType = builder.roomType;
        }

        @Override
        public BookingType getBookingType() {
            return BookingType.HOTEL;
        }

        public static class Builder {
            private String bookingId;
            private String userId;
            private LocalDate travelDate;
            private double totalAmount;
            private String hotelName;
            private String roomType;

            public Builder bookingId(String bookingId) {
                this.bookingId = bookingId;
                return this;
            }

            public Builder userId(String userId) {
                this.userId = userId;
                return this;
            }

            public Builder travelDate(LocalDate travelDate) {
                this.travelDate = travelDate;
                return this;
            }

            public Builder totalAmount(double amount) {
                this.totalAmount = amount;
                return this;
            }

            public Builder hotelName(String hotelName) {
                this.hotelName = hotelName;
                return this;
            }

            public Builder roomType(String roomType) {
                this.roomType = roomType;
                return this;
            }

            public HotelBooking build() {
                Objects.requireNonNull(bookingId,   "bookingId must not be null");
                Objects.requireNonNull(userId,      "userId must not be null");
                Objects.requireNonNull(travelDate,  "travelDate must not be null");
                Objects.requireNonNull(hotelName,   "hotelName must not be null");
                if (totalAmount <= 0) throw new IllegalArgumentException("totalAmount must be positive");
                return new HotelBooking(this);
            }
        }
    }

    class FlightBooking extends Booking{

        private String flightNumber;
        private String origin;
        private String destination;

        public FlightBooking(Builder builder) {
            super(builder.bookingId, builder.userId, builder.travelDate, builder.totalAmount);
            this.flightNumber = builder.flightNumber;
            this.origin = builder.origin;
            this.destination = builder.destination;
        }

        @Override
        protected BookingType getBookingType() {
            return BookingType.FLIGHT;
        }

        static class Builder{
            private String bookingId;
            private String userId;
            private LocalDate travelDate;
            private double totalAmount;
            private String flightNumber;
            private String origin;
            private String destination;

            public Builder bookingId(String bookingId) {
                this.bookingId = bookingId;
                return this;
            }

            public Builder userId(String userId) {
                this.userId = userId;
                return this;
            }

            public Builder travelDate(LocalDate travelDate) {
                this.travelDate = travelDate;
                return this;
            }

            public Builder totalAmount(double amount) {
                this.totalAmount = amount;
                return this;
            }

            public Builder flightNumber(String flightNumber){
                this.flightNumber  = flightNumber;
                return this;
            }

            public Builder origin(String origin) {
                this.origin = origin;
                return this;
            }

            public Builder destination(String destination) {
                this.destination = destination;
                return this;
            }
        }
    }

    interface  CancellationPolicy {
        double calculateRefund(Booking booking);
        boolean isApplicable(Long daysToTravel);
    }

    class FullRefundPolicy implements CancellationPolicy{

        private static final int FULL_REFUND_THRESHOLD = 7;
        private static final double REFUND_PERCENTAGE = 1.0;

        @Override
        public double calculateRefund(Booking booking) {
            return booking.getTotalAmount() * REFUND_PERCENTAGE;
        }

        @Override
        public boolean isApplicable(Long daysToTravel) {
            return daysToTravel >= FULL_REFUND_THRESHOLD;
        }
    }

    class PartialRefundPolicy implements CancellationPolicy{

        private static final int MIN_PARTIAL_REFUND_THRESHOLD = 3;
        private static final int MAX_PARTIAL_REFUND_THRESHOLD = 7;
        private static final double REFUND_PERCENTAGE = 0.5;

        @Override
        public double calculateRefund(Booking booking) {
            return booking.getTotalAmount() * REFUND_PERCENTAGE;
        }

        @Override
        public boolean isApplicable(Long daysToTravel) {
            return daysToTravel < MAX_PARTIAL_REFUND_THRESHOLD && daysToTravel > MIN_PARTIAL_REFUND_THRESHOLD;
        }
    }

class NoRefundPolicy implements CancellationPolicy{

    private static final int NO_REFUND_THRESHOLD = 3;
    private static final double REFUND_PERCENTAGE = 0.0;

    @Override
    public double calculateRefund(Booking booking) {
        return booking.getTotalAmount() * REFUND_PERCENTAGE;
    }

    @Override
    public boolean isApplicable(Long daysToTravel) {
        return daysToTravel <= NO_REFUND_THRESHOLD;
    }
}

class CancellationPolicyFactory {

    private static final List<CancellationPolicy> cancellationPolicyList = Arrays.asList(
            new FullRefundPolicy(),
            new PartialRefundPolicy(),
            new NoRefundPolicy()
    );

    public CancellationPolicy getCancellationPolicyBean(long daysToTravel) {
        return cancellationPolicyList.stream()
                .filter(cancellationPolicy -> cancellationPolicy.isApplicable(daysToTravel))
                .findFirst()
                .orElseThrow(() ->  new IllegalStateException("Travel Date is Past Dated"));
    }
}

class CancellationService {
    private final CancellationPolicyFactory cancellationPolicyFactory;
    private final CompositeNotificationService notificationService;


    CancellationService(CancellationPolicyFactory policyFactory, CompositeNotificationService notificationService) {
        this.cancellationPolicyFactory = policyFactory;
        this.notificationService = notificationService;
    }

    public void cancelBooking(Booking booking) {
        double refundedAmount = 0.0;
        long daysToTravel  = ChronoUnit.DAYS.between(LocalDate.now(), booking.getTravelDate());
        CancellationPolicy cancellationPolicy = cancellationPolicyFactory.getCancellationPolicyBean(daysToTravel);
        if(cancellationPolicy.isApplicable(daysToTravel)) {
            refundedAmount = cancellationPolicy.calculateRefund(booking);
        }

        if(refundedAmount < 0.0) {
            throw new IllegalStateException("Refund is Not Processed, amount cannot be negative");
        }
        System.out.println("Total Refund Amount is :: "+refundedAmount);
        booking.setBookingStatus(BookingStatus.REFUND_PROCESSED);

        String message = notificationService.buildNotificationMessage(booking, refundedAmount, daysToTravel);
        notificationService.notifySubscriber(booking.getUserId(), message);
    }
}


interface NotificationService {
    void notifySubscriber(String userId, String message);

    default String buildNotificationMessage(Booking booking, double refundAmount, long daysToTravel) {
        return String.format(
                "Your %s booking %s has been cancelled. Days to travel: %d. Refund: %.2f",
                booking.getBookingType(),
                booking.getBookingId(),
                daysToTravel,
                refundAmount);
    }
}

class SmsNotification implements NotificationService{

    @Override
    public void notifySubscriber(String userId, String message) {
        System.out.println("SMS Notification to "+userId+" for "+message);
    }
}

class EmailNotification implements NotificationService {
    @Override
    public void notifySubscriber(String userId, String message) {
        System.out.println("Email Notification to "+userId+" for "+message);
    }
}

class CompositeNotificationService implements NotificationService  {

    private final List<NotificationService> notificationServiceList = new ArrayList<>();

    public void addNotifier(NotificationService notificationService) {
        notificationServiceList.add(notificationService);
    }

    @Override
    public void notifySubscriber(String userId, String message) {
        notificationServiceList.forEach(notificationService -> notificationService.notifySubscriber(userId, message));
    }
}

public class Problem1 {

    public static void main(String[] args) {

        CompositeNotificationService compositeNotificationService = new CompositeNotificationService();
        compositeNotificationService.addNotifier(new EmailNotification());
        compositeNotificationService.addNotifier(new SmsNotification());


        CancellationPolicyFactory  cancellationPolicyFactory = new CancellationPolicyFactory();
        CancellationService cancellationService = new CancellationService(cancellationPolicyFactory, compositeNotificationService);

        Booking hotelBooking = new HotelBooking.Builder()
                .bookingId("Id-100")
                .userId("Vivek-123")
                .hotelName("Taj Land")
                .travelDate(LocalDate.now().plusDays(5))
                .roomType("Deluxe")
                .totalAmount(25000)
                .build();

        cancellationService.cancelBooking(hotelBooking);
    }
}






