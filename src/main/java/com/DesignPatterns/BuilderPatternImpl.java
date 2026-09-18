package DesignPatterns;

public class BuilderPatternImpl {

    static class Burger {

        private String bun;
        private String cheese;
        private String patty;
        private String sauce;
        private boolean onion;

        private Burger(Builder builder) {
            this.bun = builder.bun;
            this.cheese = builder.cheese;
            this.patty = builder.patty;
            this.sauce = builder.sauce;
            this.onion = builder.onion;
        }

        @Override
        public String toString() {
            return "Burger {" +
                    "bun='" + bun + '\'' +
                    ", cheese='" + cheese + '\'' +
                    ", patty='" + patty + '\'' +
                    ", sauce='" + sauce + '\'' +
                    ", onion=" + onion +
                    '}';
        }


        // =========================
        // BUILDER
        // =========================

        static class Builder {

            private String bun;
            private String cheese;
            private String patty;
            private String sauce;
            private boolean onion;

            public Builder bun(String bun) {
                this.bun = bun;
                return this;
            }

            public Builder cheese(String cheese) {
                this.cheese = cheese;
                return this;
            }

            public Builder patty(String patty) {
                this.patty = patty;
                return this;
            }

            public Builder sauce(String sauce) {
                this.sauce = sauce;
                return this;
            }

            public Builder onion(boolean onion) {
                this.onion = onion;
                return this;
            }

            public Burger build() {
                return new Burger(this);
            }
        }
    }


    public static void main(String[] args) {

        Burger burger = new Burger.Builder()
                .bun("Sesame")
                .cheese("Cheddar")
                .patty("Chicken")
                .sauce("Mayo")
                .onion(true)
                .build();

        System.out.println(burger);
    }
}
