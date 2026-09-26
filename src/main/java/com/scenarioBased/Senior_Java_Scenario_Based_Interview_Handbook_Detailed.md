# Senior Java Scenario-Based Interview Handbook
## Spring Boot • Microservices • Kafka • Database • Behavioural

> **Target:** Senior Software Engineer / Java Developer (~5 years experience)  
> **Purpose:** Prepare for scenario-based interviews where the interviewer evaluates design thinking, production experience, troubleshooting ability, trade-offs, scalability, and ownership.

---

# Table of Contents

1. [How to Answer Scenario Questions](#1-how-to-answer-scenario-questions)
2. [Spring Boot Scenarios](#2-spring-boot-scenarios)
3. [Microservices Scenarios](#3-microservices-scenarios)
4. [Kafka Scenarios](#4-kafka-scenarios)
5. [Database Scenarios](#5-database-scenarios)
6. [Cross-Technology System Scenarios](#6-cross-technology-system-scenarios)
7. [Behavioural / Situational Scenarios](#7-behavioural--situational-scenarios)
8. [Production Troubleshooting Playbook](#8-production-troubleshooting-playbook)
9. [Senior-Level Trade-off Cheat Sheet](#9-senior-level-trade-off-cheat-sheet)
10. [Rapid Revision Checklist](#10-rapid-revision-checklist)

---

# 1. How to Answer Scenario Questions

A senior developer should not immediately jump to a technology.

Use this framework:

```text
                INTERVIEWER SCENARIO
                       |
                       v
              1. Clarify Requirements
                       |
                       v
              2. Identify Constraints
                       |
                       v
              3. Identify Failure Points
                       |
                       v
              4. Propose Design
                       |
                       v
              5. Explain Data / Flow
                       |
                       v
              6. Handle Failures
                       |
                       v
              7. Discuss Scalability
                       |
                       v
              8. Discuss Observability
                       |
                       v
              9. Explain Trade-offs
                       |
                       v
             10. Give Real Example
```

## 1.1 Senior Answer Structure

For almost every scenario, answer in this order:

### Step 1 — Clarify

Ask or state assumptions:

- Expected traffic?
- Read-heavy or write-heavy?
- Synchronous or asynchronous requirement?
- Strong consistency or eventual consistency?
- SLA/latency requirement?
- Can duplicate processing happen?
- What happens if a dependency is unavailable?
- Is data loss acceptable?

### Step 2 — Start with the simplest correct design

Do not introduce Kafka, Redis, Kubernetes, distributed locks, or sharding simply because they are available.

Say:

> "I would first understand the consistency and latency requirements. Then I would choose the simplest architecture that satisfies them."

### Step 3 — Explain failure handling

Senior engineers are expected to think about:

- Timeout
- Retry
- Duplicate request
- Duplicate event
- Partial failure
- Network failure
- Database failure
- Dependency failure
- Deployment failure
- Message loss
- Message duplication

### Step 4 — Explain scalability

Discuss:

- Horizontal scaling
- Stateless services
- Load balancing
- Caching
- Database indexing
- Read replicas
- Partitioning
- Kafka partitions
- Consumer scaling
- Backpressure

### Step 5 — Explain observability

Mention:

- Structured logs
- Correlation ID
- Metrics
- Distributed tracing
- Alerts
- Dashboards
- Kafka consumer lag
- DB latency
- Error rate
- P95/P99 latency

### Step 6 — Explain trade-offs

A senior answer should contain:

> "This approach solves X, but the trade-off is Y."

---

# 2. Spring Boot Scenarios

---

## SB-01. API Suddenly Takes 5 Seconds Instead of 200 ms

### Scenario

An API normally responds in 200 ms. Suddenly P95 latency becomes 5 seconds.

### Senior Approach

Do not immediately increase server resources.

First determine where the time is spent.

```text
Client
  |
  v
Load Balancer
  |
  v
API Gateway
  |
  v
Spring Boot
  |
  +----> Database
  |
  +----> Redis
  |
  +----> External API
  |
  +----> Kafka
```

Add timing/metrics around each dependency.

### Investigation Sequence

```text
High Latency
    |
    +--> CPU high?
    |
    +--> Memory / GC?
    |
    +--> Thread pool exhausted?
    |
    +--> DB latency?
    |
    +--> Connection pool exhausted?
    |
    +--> External API slow?
    |
    +--> Lock contention?
    |
    +--> Kafka operation blocking?
```

Check:

- P50/P95/P99 latency
- CPU
- Heap
- GC pauses
- Thread count
- DB query duration
- DB connection pool
- HTTP client connection pool
- External service latency
- Recent deployment/configuration changes

### Design Improvements

If DB is slow:

- Check execution plan
- Add/modify indexes
- Reduce unnecessary columns
- Pagination
- Avoid N+1 queries
- Use appropriate fetch strategy
- Cache stable data

If external service is slow:

- Set timeout
- Circuit breaker
- Retry only transient failures
- Bulkhead isolation
- Async processing if business permits

### Trade-off

Caching improves latency but introduces:

- stale data
- cache invalidation complexity
- memory cost

### Interview Answer

> "I would first isolate the latency rather than blindly scaling the application. I would use distributed tracing and metrics to determine whether the delay is in the application, database, network, or downstream service. Once the bottleneck is identified, I would optimize that layer and add resilience such as timeouts or circuit breakers where appropriate."

---

## SB-02. API Receives 10x Traffic

### Scenario

Traffic increases from 1,000 requests/minute to 10,000 requests/minute.

### Architecture

```text
                 Users
                   |
                   v
              Load Balancer
                   |
             +-----+-----+
             |     |     |
             v     v     v
           App1  App2  App3
             |     |     |
             +-----+-----+
                   |
          +--------+--------+
          |                 |
        Redis              DB
                            |
                       Read Replica
```

### Approach

1. Ensure service is stateless.
2. Scale horizontally.
3. Add load balancing.
4. Cache frequently read data.
5. Rate-limit abusive clients.
6. Protect database.
7. Move non-critical work asynchronously to Kafka.
8. Monitor saturation.

### Important

Do not use cache for data requiring strict real-time consistency unless stale data is acceptable.

### Trade-offs

Horizontal scaling:

- Pros: easy capacity increase, fault tolerance
- Cons: infrastructure cost, DB can become bottleneck

Caching:

- Pros: lower DB load
- Cons: stale data/invalidation

Kafka:

- Pros: decouples producers/consumers
- Cons: eventual consistency and operational complexity

---

## SB-03. API Timeout Between Services

### Scenario

Service A calls Service B. B sometimes takes 30 seconds.

### Bad Design

```text
A ---- HTTP ----> B
                   |
                   | waits 30 sec
                   v
                timeout
```

### Better Design

```text
A
 |
 +--> timeout
 |
 +--> retry transient failures
 |
 +--> circuit breaker
 |
 +--> fallback where meaningful
```

### Rules

Retry only when:

- Failure is transient
- Operation is safe/idempotent
- Retry count is bounded

Avoid retrying:

- Validation failures
- Authentication failures
- Permanent business errors

Use exponential backoff with jitter.

```text
Attempt 1 -> fail
       |
       +-- wait
       |
Attempt 2 -> fail
       |
       +-- longer wait
       |
Attempt 3 -> fail
       |
       +--> fallback / error
```

### Trade-off

Retries improve resilience against temporary failures but can amplify load during an outage.

---

## SB-04. Intermittent HTTP 500

### Investigation

```text
500
 |
 +--> Application exception?
 +--> DB failure?
 +--> Timeout?
 +--> Null/data issue?
 +--> Dependency failure?
 +--> Resource exhaustion?
 +--> Recent deployment?
```

Use:

- Correlation ID
- Centralized logs
- Stack traces
- Metrics
- Distributed tracing
- Request payload metadata
- Error rate by endpoint

Do not log:

- Passwords
- Tokens
- Credit card information
- Sensitive PII

### Senior Answer

> "I would first correlate the failing requests using a request/correlation ID, identify the exception class and failing dependency, then reproduce or inspect the same path using traces. I would also check whether failures correlate with a specific instance, input, database record, or deployment."

---

## SB-05. Global Exception Handling

Use `@RestControllerAdvice` and `@ExceptionHandler`.

### Recommended Error Contract

```json
{
  "timestamp": "2026-09-22T12:00:00Z",
  "status": 400,
  "code": "INVALID_ORDER",
  "message": "Order is invalid",
  "path": "/orders",
  "correlationId": "abc-123"
}
```

### Benefits

- Consistent API contract
- Centralized mapping
- Easier monitoring
- Better client handling

### Important

Do not expose internal stack traces to clients.

---

## SB-06. API Called Twice

### Scenario

Client sends the same payment request twice.

### Solution: Idempotency

```text
Client
  |
  | Idempotency-Key: abc123
  v
Payment Service
  |
  v
DB
  |
  +--> key already exists?
          |
       yes -> return previous result
       no  -> process
```

Store:

```text
idempotency_key
request_hash
status
response
created_at
```

Use a unique constraint on `idempotency_key`.

### Trade-off

Idempotency storage adds DB/storage overhead but protects against duplicate financial/business operations.

---

## SB-07. Request Validation

Use Bean Validation:

```java
@NotBlank
@Email
@Size
@Positive
@Valid
```

Validate at API boundary.

Separate:

- Syntactic validation
- Business validation

Example:

```text
Amount <= 0
```

is validation.

```text
Account has insufficient balance
```

is business validation.

---

## SB-08. JWT Valid But API Returns 403

### Difference

- `401 Unauthorized`: authentication missing/invalid
- `403 Forbidden`: authenticated but not authorized

### Debug Flow

```text
JWT
 |
 +--> Signature valid?
 +--> Expired?
 +--> Issuer valid?
 +--> Audience valid?
 +--> Required scope?
 +--> Required role?
 +--> Authority mapping correct?
```

Common issue:

JWT contains:

```json
"roles": ["ADMIN"]
```

but Spring Security expects:

```text
ROLE_ADMIN
```

Authority mapping must be configured consistently.

---

## SB-09. Two Implementations of Same Interface

Example:

```java
interface PaymentProcessor {}

@Service
class CardPaymentProcessor implements PaymentProcessor {}

@Service
class UpiPaymentProcessor implements PaymentProcessor {}
```

Solutions:

### `@Primary`

Use when one implementation should be the default.

### `@Qualifier`

Use when selection is explicit.

```java
@Qualifier("upiPaymentProcessor")
```

### Senior consideration

If implementations are selected dynamically based on business type, consider a strategy/factory approach instead of spreading `if/else` conditions throughout services.

---

## SB-10. Circular Dependency

Example:

```text
A -> B
B -> A
```

Prefer redesigning dependencies.

Potential solutions:

- Extract shared responsibility
- Introduce another abstraction
- Move logic to a third service/component

`@Lazy` can sometimes break initialization cycles but should not be the default architectural solution.

---

## SB-11. Application Memory Continuously Increases

### Investigation

```text
Memory Growth
     |
     +--> Heap usage
     +--> GC frequency
     +--> Old generation
     +--> Thread count
     +--> Cache size
     +--> Static collections
     +--> ThreadLocal
     +--> Unclosed resources
```

Take heap dump and inspect retained objects.

Common causes:

- Unbounded cache
- Static collection
- Listener registration leak
- ThreadLocal misuse
- Large objects retained unexpectedly

### Do not say

> "Increase heap size."

That may hide the problem temporarily.

---

## SB-12. CPU 100%

Check:

1. Which process/thread uses CPU?
2. Thread dump
3. Hot methods
4. Infinite loops
5. Excessive serialization
6. Regex problems
7. High GC
8. Excessive logging
9. Busy polling
10. Traffic spike

Use profiling/thread dumps before optimizing.

---

## SB-13. OutOfMemoryError

### Flow

```text
OOM
 |
 +--> Heap OOM?
 +--> Metaspace?
 +--> Direct memory?
 +--> Container memory?
```

Collect:

- Heap dump
- GC logs
- JVM metrics
- Container memory metrics

Fix root cause first.

---

## SB-14. Thread Pool Exhaustion

Possible causes:

- Slow DB
- Slow external API
- Too many blocking operations
- Long transactions
- Deadlock
- Insufficient pool size

Increasing the pool can make the DB or downstream system even worse.

### Senior Insight

> "Thread pool size is not an independent tuning parameter. It must be considered with CPU, blocking time, DB connection pool, downstream capacity, and request rate."

---

## SB-15. DB Connection Pool Exhaustion

Typical flow:

```text
HTTP Requests
     |
     v
Thread Pool
     |
     v
Connection Pool
     |
     v
Database
```

Investigate:

- Active connections
- Idle connections
- Connection wait time
- Long-running queries
- Transactions not closing
- Connection leak
- Pool size
- DB max connections

---

## SB-16. Graceful Shutdown

During deployment:

```text
New Requests
     X
     |
Load Balancer stops routing
     |
Existing requests finish
     |
Consumers stop safely
     |
DB transactions complete
     |
Application exits
```

Important for:

- Kubernetes
- Rolling deployments
- Kafka consumers

Avoid killing processes while work is in progress.

---

# 3. Microservices Scenarios

---

## MS-01. Service A -> B -> C and C Is Down

### Failure

```text
A ---> B ---> C
             X
```

Without protection:

```text
C down
 |
B waits
 |
B threads exhausted
 |
A waits
 |
A threads exhausted
 |
System-wide outage
```

### Resilience

Use:

- Timeout
- Circuit breaker
- Bulkhead
- Bounded retries
- Fallback
- Async processing where possible

```text
A ---> B ---> C
      |
      +--> timeout
      +--> circuit breaker
      +--> fallback
```

---

## MS-02. Cascading Failure

A cascading failure occurs when failure in one component causes dependent components to fail.

### Prevention

### Timeout

Never wait indefinitely.

### Circuit Breaker

States:

```text
CLOSED
  |
 failures exceed threshold
  v
OPEN
  |
 wait
  v
HALF_OPEN
  |
 test request
  |
 +---- success -> CLOSED
 |
 +---- failure -> OPEN
```

### Bulkhead

Separate resources for independent workloads.

```text
Application
 |
 +--> Payment Pool
 |
 +--> Reporting Pool
 |
 +--> Notification Pool
```

If reporting is slow, it should not consume payment resources.

---

## MS-03. REST vs Kafka

### REST

Use when:

- Immediate response required
- Request/response semantics
- Stronger synchronous workflow
- Client needs result now

### Kafka

Use when:

- Asynchronous processing
- Event-driven architecture
- Decoupling
- High throughput
- Multiple consumers
- Event history/replay is useful

### Decision

```text
Does caller need immediate result?
       |
      yes ----------------> REST
       |
      no
       |
Do multiple consumers need event?
       |
      yes ----------------> Kafka
       |
      no -----------------> Consider async REST/message queue
```

### Trade-off

REST:

- Simple
- Easy debugging
- Tighter coupling

Kafka:

- Loose coupling
- Scalable
- Eventual consistency
- More operational complexity

---

## MS-04. Timeout + Retry + Circuit Breaker

Recommended order:

```text
Request
  |
Timeout
  |
Retry transient failure
  |
Circuit Breaker
  |
Fallback
```

Actually, circuit breaker is often wrapped around the outbound call including retry policy, depending on framework/design.

### Important

Do not retry indefinitely.

Use:

```text
maxAttempts = 3
backoff = exponential
jitter = enabled
```

---

## MS-05. Partial Failure

Example:

```text
Order created
     |
     +--> Payment SUCCESS
     |
     +--> Inventory FAILED
```

Do not rely on a single ACID transaction across independent services.

Use Saga.

---

## MS-06. Saga Pattern

### Choreography

Services react to events.

```text
Order
 |
OrderCreated
 v
Payment
 |
PaymentCompleted
 v
Inventory
 |
InventoryReserved
 v
Notification
```

### Orchestration

Central orchestrator controls workflow.

```text
        Order Saga
            |
     +------+------+
     |      |      |
   Order  Payment Inventory
```

### Compensation

If inventory fails:

```text
Payment SUCCESS
      |
Inventory FAILED
      |
Refund Payment
```

### Trade-offs

Choreography:

- Less central coordination
- Can become difficult to understand as workflow grows
- Event coupling can increase

Orchestration:

- Central visibility
- Easier workflow management
- Orchestrator becomes important infrastructure

---

## MS-07. Payment Succeeds but Order Update Fails

Do not assume rollback across services.

Possible design:

```text
Payment Service
      |
PaymentCompleted
      |
      v
Kafka
      |
      v
Order Service
      |
 DB failure
      |
 retry / DLQ
```

If payment is already successful, the event should remain recoverable.

Use:

- Durable event
- Retry
- DLQ
- Idempotent consumer
- Reconciliation job

---

## MS-08. API Gateway Goes Down

Gateway can be a critical dependency.

Mitigation:

```text
Internet
   |
Load Balancer
   |
+--+--+
|     |
GW1  GW2
```

Use multiple gateway instances and health checks.

### Trade-off

Central gateway simplifies:

- Authentication
- Routing
- Rate limiting

But becomes a critical infrastructure layer.

---

## MS-09. Service Discovery

Example with Eureka:

```text
Service A ---> Eureka
Service B ---> Eureka
Service C ---> Eureka

A asks:
"Where is B?"

Eureka:
"B = 10.0.1.20:8080"
```

Services register and send heartbeats.

When an instance disappears, service discovery removes/unmarks it depending on configuration and failure detection.

---

## MS-10. Distributed Tracing

Use a trace ID propagated across requests.

```text
Client
 |
traceId=abc
 v
Gateway
 |
traceId=abc
 v
Order
 |
traceId=abc
 v
Payment
 |
traceId=abc
 v
Inventory
```

Useful fields:

- trace ID
- span ID
- service
- endpoint
- duration
- status
- error

---

## MS-11. Rate Limiting

Common algorithms:

### Fixed Window

Simple but boundary burst problem.

### Sliding Window

More accurate but more expensive.

### Token Bucket

Allows controlled bursts.

```text
Bucket
[●●●●●]
   |
requests consume tokens
   |
new tokens added over time
```

For distributed systems, Redis is often used for shared rate-limit state.

---

## MS-12. Duplicate Requests

Use:

- Idempotency key
- Unique business key
- DB unique constraint
- Idempotent state transition

Example:

```text
Order status:
CREATED -> PAID -> SHIPPED

PAID -> PAID
```

Repeated event should not perform payment again.

---

## MS-13. Schema Changes

Avoid breaking all consumers at once.

### Backward-compatible migration

```text
Old producer ---> Old consumer
      |
      +--> New optional field
              |
              v
          New consumer
```

For DB:

```text
1. Add nullable column
2. Deploy code supporting old + new
3. Backfill
4. Start writing new field
5. Switch reads
6. Remove old field later
```

---

# 4. Kafka Scenarios

---

## K-01. Consumer Slower Than Producer

### Scenario

Producer:

```text
10,000 msg/sec
```

Consumer:

```text
5,000 msg/sec
```

Lag increases.

```text
Producer ---> Kafka ---> Consumer
 10k/sec                 5k/sec
                |
                v
              LAG ↑
```

### Solutions

1. Increase consumer instances, subject to partition count.
2. Increase partitions if appropriate.
3. Optimize processing.
4. Batch DB writes.
5. Remove unnecessary blocking.
6. Improve downstream DB performance.
7. Increase parallelism carefully.

### Critical Rule

Consumer parallelism is constrained by partitions.

If:

```text
3 partitions
10 consumers
```

only up to 3 consumers can actively consume partitions in that consumer group.

---

## K-02. Consumer Crashes Before Offset Commit

Suppose:

```text
1. Consume message
2. Process successfully
3. DB commit succeeds
4. Consumer crashes
5. Offset not committed
```

After restart, Kafka delivers the message again.

This is **at-least-once processing**.

### Solution

Make processing idempotent.

```text
Message ID
    |
already processed?
  /       \
yes       no
 |         |
skip     process
           |
           v
        mark done
```

---

## K-03. Consumer Commits Before Processing

```text
1. Consume
2. Commit offset
3. Processing
4. Crash
```

The message may not be reprocessed.

This risks message loss.

Therefore commit timing matters.

---

## K-04. Duplicate Message

Kafka delivery can result in duplicate processing depending on configuration/failures.

Use:

- Unique event ID
- Idempotent consumer
- DB unique constraint
- Processed-event table

Example:

```text
processed_events
----------------
event_id UNIQUE
processed_at
```

If event already exists, skip.

---

## K-05. Poison Message

A poison message always fails processing.

Bad approach:

```text
consume
  |
fail
  |
retry
  |
fail
  |
retry forever
```

This can block progress.

Better:

```text
Topic
 |
Consumer
 |
retry with bounded attempts
 |
 +---- success --> continue
 |
 +---- failure --> DLQ
```

DLQ record should include:

- original payload
- event ID
- error reason
- retry count
- timestamp
- source topic/partition/offset

---

## K-06. Ordering Requirement

Kafka guarantees ordering within a partition, not globally across a topic.

If customer events must be ordered:

```text
partitionKey = customerId
```

Then:

```text
Customer A -> Partition 1
Customer B -> Partition 2
Customer C -> Partition 3
```

All events for the same customer go to the same partition.

### Trade-off

Ordering by key can create hot partitions if one key produces disproportionately high traffic.

---

## K-07. 10 Consumers, 3 Partitions

```text
Topic
P0 P1 P2

Consumer Group
C1 C2 C3 C4 C5 C6 C7 C8 C9
```

Only 3 consumers can own partitions at a time.

Extra consumers remain idle.

### Interview Point

To scale consumer parallelism, partition count must support it.

---

## K-08. 3 Consumers, 10 Partitions

Each consumer receives multiple partitions.

```text
C1 -> P0 P1 P2 P3
C2 -> P4 P5 P6
C3 -> P7 P8 P9
```

This is valid.

---

## K-09. Consumer Rebalancing

Rebalancing occurs when group membership or partition assignment changes.

Causes:

- Consumer joins
- Consumer leaves
- Consumer crashes
- Partition count changes
- Consumer misses heartbeats/poll expectations

Too much rebalancing can reduce throughput.

Check:

- Consumer processing duration
- `max.poll.interval.ms`
- `max.poll.records`
- heartbeat/session configuration
- long blocking work

---

## K-10. Kafka Lag Increasing

### Investigation

```text
Lag
 |
 +--> Producer rate increased?
 +--> Consumer throughput decreased?
 +--> DB slower?
 +--> External API slower?
 +--> Consumer errors?
 +--> Retries?
 +--> Rebalances?
 +--> Not enough partitions?
 +--> Consumer instances unavailable?
```

### Key Metric

Consumer lag = latest produced offset - consumer committed offset.

---

## K-11. At-Most-Once vs At-Least-Once vs Exactly-Once

### At-most-once

Message can be lost, but not normally processed twice.

### At-least-once

Message is not intentionally lost but may be processed more than once.

### Exactly-once

Processing semantics can avoid duplicate effects under supported transactional boundaries, but it should not be described as "the whole distributed system magically becomes exactly once."

### Senior Answer

> "For most business systems I focus on idempotent processing and reliable delivery rather than assuming exactly-once solves external side effects."

---

## K-12. Kafka + DB Consistency

### Problem

```text
Kafka message
    |
    v
DB transaction
    |
    +--> success
    |
    v
commit offset
```

If DB commits but offset commit fails, message can be processed again.

Solution: idempotency.

---

# Outbox Pattern

## Problem

Application must update DB and publish an event.

Naive approach:

```text
DB transaction
   |
   +--> update DB
   |
   +--> publish Kafka
```

Failure between these operations creates inconsistency.

### Outbox

```text
Application
    |
    v
DB Transaction
    |
    +--> Business Table
    |
    +--> Outbox Table
              |
              v
        Outbox Publisher
              |
              v
            Kafka
```

Both business update and outbox insert happen in one DB transaction.

Publisher later sends outbox events to Kafka.

### Outbox Table

```text
id
aggregate_id
event_type
payload
status
created_at
published_at
```

### Trade-offs

Pros:

- DB and event intent committed atomically
- Reliable event publishing

Cons:

- Additional table
- Publisher required
- Duplicate publishing still needs idempotency
- Cleanup/retention needed

---

# 5. Database Scenarios

---

## DB-01. Query on 100 Million Rows Is Slow

### Investigation

```text
Slow Query
 |
 +--> EXPLAIN / execution plan
 +--> Index?
 +--> Full table scan?
 +--> Cardinality?
 +--> Join order?
 +--> Sort?
 +--> Large result set?
 +--> Lock wait?
```

### Improvements

- Correct indexes
- Composite indexes
- Query rewrite
- Pagination
- Avoid `SELECT *`
- Partition large tables where appropriate
- Archive old data
- Read replicas for read-heavy workloads

---

## DB-02. Composite Index

Suppose:

```sql
CREATE INDEX idx_order
ON orders(customer_id, status, created_at);
```

Good candidates:

```sql
WHERE customer_id = ?
WHERE customer_id = ? AND status = ?
WHERE customer_id = ? AND status = ? AND created_at > ?
```

The leftmost-prefix concept matters.

The exact behavior depends on the database optimizer and query.

### Interview Tip

Never say:

> "An index always makes queries faster."

Indexes have storage and write/update cost.

---

## DB-03. Index Makes Write Performance Worse

Every insert/update may require index maintenance.

Too many indexes:

- Increase storage
- Increase write cost
- Increase maintenance cost

Therefore index based on actual query patterns.

---

## DB-04. Deadlock

Example:

```text
Transaction A:
lock Row 1
wait Row 2

Transaction B:
lock Row 2
wait Row 1
```

```text
A ---> Row1 ---> waits Row2
                    ^
                    |
B ---> Row2 ---> waits Row1
```

### Prevention

- Consistent lock ordering
- Short transactions
- Appropriate indexes
- Avoid unnecessary locks
- Retry deadlock victims carefully

### Important

A deadlock retry must be safe and idempotent.

---

## DB-05. Double Booking

One seat remains, two users request it.

Bad:

```text
SELECT available = true
       |
both requests see true
       |
both INSERT booking
```

### Option 1 — Optimistic Locking

Use version:

```text
seat_id
available
version
```

Update:

```sql
UPDATE seat
SET available = false,
    version = version + 1
WHERE seat_id = ?
  AND available = true
  AND version = ?;
```

If affected rows = 0, another transaction won.

### Option 2 — Pessimistic Lock

```sql
SELECT ...
FOR UPDATE;
```

Locks row until transaction completes.

### Trade-off

Optimistic:

- Better concurrency when conflicts are rare
- Requires conflict handling

Pessimistic:

- Stronger serialization
- Can reduce concurrency
- Can increase lock contention

---

## DB-06. Optimistic vs Pessimistic Locking

### Optimistic

Assume conflict is rare.

Good for:

- User profile updates
- Low-contention records

### Pessimistic

Lock while operating.

Good for:

- Highly contested inventory/seat/account operations

---

## DB-07. Read Replicas

Architecture:

```text
                Application
                 /       \
                /         \
             Write       Read
               |           |
            Primary     Replica
                           |
                       Replica 2
```

Benefits:

- Scale reads
- Reduce primary load

Problem:

Replication lag.

A user may write data and immediately read from replica and not see it.

### Solution

Route consistency-sensitive read to primary or use an appropriate consistency strategy.

---

## DB-08. Database Is Bottleneck

Investigate:

1. Slow queries
2. Missing indexes
3. Connection pool
4. Lock contention
5. Too many queries/request
6. N+1
7. Large transactions
8. Read/write ratio
9. Cache opportunity

Then consider:

- Read replicas
- Caching
- Partitioning
- Archival
- Sharding only when necessary

---

## DB-09. Sharding

Split data across database nodes.

```text
                 Application
                     |
               Sharding Layer
              /       |       \
             v        v        v
          DB-1      DB-2      DB-3
```

Shard key example:

```text
customer_id
```

### Good shard key

- High cardinality
- Even distribution
- Frequently used for lookup
- Stable

### Problems

- Cross-shard joins
- Cross-shard transactions
- Rebalancing
- Hot shards
- Operational complexity

Do not introduce sharding before simpler scaling techniques are exhausted.

---

## DB-10. Large Schema Migration

Bad:

```text
ALTER huge_table ... 
```

during peak traffic.

Safer approach:

```text
1. Add compatible schema
2. Deploy backward-compatible code
3. Backfill gradually
4. Start dual read/write if required
5. Validate
6. Switch traffic
7. Remove old schema later
```

Use online migration features supported by the database.

---

# 6. Cross-Technology System Scenarios

---

# Scenario 1 — Order Management System

## Requirement

Customer places an order.

Flow:

```text
Customer
   |
   v
API Gateway
   |
   v
Order Service
   |
   +----> DB
   |
   +----> Kafka: OrderCreated
                 |
          +------+------+
          |             |
          v             v
       Payment       Inventory
          |             |
          v             v
       PaymentDone   InventoryReserved
          |             |
          +------+------+
                 |
                 v
            Notification
```

## Recommended Approach

Order creation should be fast.

```text
POST /orders
      |
      v
Order Service
      |
      v
Persist Order
      |
      v
Publish OrderCreated
      |
      v
return 202/201 depending on business contract
```

Payment and inventory can be asynchronous if the business allows it.

## Saga

```text
OrderCreated
    |
    v
Payment
    |
 success
    v
Inventory
    |
 failure
    v
Refund Payment
    |
    v
Cancel Order
```

## Important States

```text
PENDING_PAYMENT
PAYMENT_COMPLETED
INVENTORY_RESERVED
CONFIRMED
CANCELLED
REFUND_PENDING
REFUNDED
```

Use explicit state transitions rather than scattered booleans.

---

# Scenario 2 — Payment DB Commit but Kafka Publish Fails

## Problem

```text
Payment DB = SUCCESS
Kafka event = NOT PUBLISHED
```

### Outbox Solution

```text
Payment Service
      |
      v
DB Transaction
  |          |
  v          v
Payment    Outbox
SUCCESS    Event
              |
              v
        Publisher
              |
              v
            Kafka
```

The publisher retries.

### Additional Requirement

Publisher can itself publish duplicates.

Therefore consumers must be idempotent.

---

# Scenario 3 — Kafka Consumer DB Commit Before Offset

```text
Kafka
  |
  v
Consumer
  |
  v
DB commit SUCCESS
  |
  X crash
  |
offset not committed
```

After restart:

```text
same event
   |
   v
consumer
   |
   +--> event already processed?
             |
            yes
             |
            skip
```

Use:

- Event ID
- Unique constraint
- Processed-event table
- Idempotent business operation

---

# Scenario 4 — High-Traffic API

## Architecture

```text
                 Internet
                    |
                    v
              Load Balancer
                    |
                    v
               API Gateway
             /      |      \
            v       v       v
          App1     App2     App3
            |       |       |
            +-------+-------+
                    |
        +-----------+-----------+
        |                       |
      Redis                   Kafka
        |                       |
        v                       v
      Cache                 Consumers
                                |
                                v
                               DB
```

## Techniques

### Scale application

Horizontal scaling.

### Protect DB

- Cache
- Connection pool
- Query optimization
- Async writes where appropriate

### Rate limit

Use token bucket/sliding window.

### Async processing

Move non-critical operations to Kafka.

Examples:

- Email
- Analytics
- Audit events
- Notifications

---

# Scenario 5 — Slow Microservice

```text
Service A
   |
   v
Service B
   |
   v
Service C
   |
  slow
```

Use:

- Timeout
- Circuit breaker
- Retry with backoff
- Bulkhead
- Distributed tracing

Trace:

```text
traceId=abc

A: 50ms
B: 80ms
C: 9,800ms  <-- bottleneck
```

Fix C instead of randomly scaling A.

---

# Scenario 6 — Duplicate Payment

## Design

```text
Client
 |
 | idempotency-key = XYZ
 v
Payment API
 |
 v
Idempotency Store
 |
 +--> exists --> return previous result
 |
 +--> absent --> process
                  |
                  v
                DB
```

Use unique constraint:

```text
UNIQUE(idempotency_key)
```

Do not rely only on an in-memory map because multiple application instances exist.

---

# Scenario 7 — Booking System

## Requirement

One seat, 100 users.

### Preferred database-centric approach

```text
100 Requests
     |
     v
Booking Service
     |
     v
Atomic DB operation
     |
     +---- success -> booked
     |
     +---- 0 rows -> sold out
```

Example:

```sql
UPDATE seat
SET available = false
WHERE seat_id = ?
AND available = true;
```

Check affected rows.

If `1`:

```text
Booking successful
```

If `0`:

```text
Someone else booked it
```

This can be simpler and safer than introducing Redis locks.

---

# Scenario 8 — Kafka Lag

## Diagnostic Tree

```text
Kafka Lag
   |
   +--> Producer rate increased?
   |
   +--> Consumer processing slower?
   |       |
   |       +--> DB?
   |       +--> External API?
   |       +--> CPU?
   |       +--> GC?
   |
   +--> Consumer errors?
   |
   +--> Retries?
   |
   +--> Rebalances?
   |
   +--> Insufficient partitions?
```

### Actions

- Increase consumer instances
- Ensure enough partitions
- Optimize consumer
- Batch DB writes
- Remove unnecessary synchronous calls
- Increase throughput
- Fix downstream bottleneck

---

# 7. Behavioural / Situational Scenarios

For behavioural questions, use **STAR**:

```text
S = Situation
T = Task
A = Action
R = Result
```

But at senior level, add:

```text
What did I learn?
What did I change afterward?
```

---

## B-01. Tell Me About a Production Issue You Owned

### Structure

**Situation**

> "We had a production issue where..."

**Task**

> "I was responsible for identifying the root cause and restoring service."

**Action**

Explain:

1. How you detected it
2. How you isolated the problem
3. What logs/metrics you used
4. Immediate mitigation
5. Permanent fix
6. Testing

**Result**

Use measurable outcome if available.

**Learning**

> "After the incident, I added monitoring/alerting/test coverage so the same class of issue would be detected earlier."

---

## B-02. Tell Me About a Mistake

Do not choose a catastrophic mistake.

Use:

```text
Mistake
  |
Impact
  |
Ownership
  |
Fix
  |
Prevention
```

Avoid blaming:

- Manager
- QA
- DevOps
- Product
- Another developer

### Senior answer pattern

> "I initially underestimated X. This caused Y. Once I identified it, I took ownership, fixed it, and introduced Z to prevent recurrence."

---

## B-03. Disagreement With Senior Developer

Good answer:

```text
Understand their concern
        |
Present evidence
        |
Compare options
        |
Agree on decision
        |
Commit to decision
```

Do not make the answer:

> "I proved that I was right."

Instead:

> "The objective was to reach the best technical decision for the system."

---

## B-04. Product Wants Unrealistic Delivery

Use:

```text
Requirement
   |
Break into work
   |
Estimate
   |
Identify must-have
   |
Identify optional
   |
Communicate risk
   |
Agree on scope
```

Say:

> "I would avoid committing to an unrealistic date without explaining the technical risk. I would propose an MVP or phased delivery."

---

## B-05. Production Down at 2 AM

### Immediate response

```text
Detect
  |
Acknowledge
  |
Assess impact
  |
Mitigate
  |
Restore
  |
RCA
  |
Prevent recurrence
```

Priority:

1. Restore service
2. Preserve evidence
3. Communicate clearly
4. Root cause
5. Permanent prevention

Do not spend 2 hours finding the perfect root cause while customers are still impacted if a safe rollback is available.

---

## B-06. You Introduced Production Bug

Good structure:

> "I would immediately acknowledge the issue, help mitigate it, communicate the impact, and participate in the root-cause analysis. I would avoid hiding the mistake because delayed communication increases the impact."

Then explain:

- rollback/hotfix
- testing
- monitoring
- prevention

---

## B-07. Mentoring Junior Developer

Mention:

- Explain reasoning, not just code
- Pair programming
- Code review
- Gradually increase ownership
- Give constructive feedback
- Let them solve problems instead of doing everything for them

---

## B-08. Code Review

Look for:

### Correctness

- Business logic
- Edge cases

### Design

- SOLID
- Coupling
- Separation of concerns

### Reliability

- Error handling
- Retry
- Timeout

### Performance

- DB calls
- N+1
- Unnecessary loops

### Security

- Authentication
- Authorization
- Sensitive logging

### Maintainability

- Naming
- Tests
- Simplicity

---

# 8. Production Troubleshooting Playbook

## 8.1 API Slow

```text
Latency
 |
 +--> Traffic?
 +--> CPU?
 +--> GC?
 +--> Threads?
 +--> DB?
 +--> External API?
 +--> Lock?
 +--> Network?
```

---

## 8.2 API 500

```text
500
 |
 +--> Logs
 +--> Stack trace
 +--> Correlation ID
 +--> Recent deployment
 +--> Dependency
 +--> DB
 +--> Specific input?
 +--> Specific instance?
```

---

## 8.3 Kafka Lag

```text
Lag
 |
 +--> Producer throughput
 +--> Consumer throughput
 +--> Partitions
 +--> Consumer count
 +--> Processing time
 +--> DB
 +--> External API
 +--> Errors
 +--> Rebalance
```

---

## 8.4 DB Slow

```text
DB latency
 |
 +--> Execution plan
 +--> Index
 +--> Lock
 +--> Connection pool
 +--> Query volume
 +--> N+1
 +--> Large transaction
 +--> Hardware saturation
```

---

## 8.5 High CPU

```text
CPU
 |
 +--> Thread dump
 +--> Hot method
 +--> Infinite loop
 +--> GC
 +--> Serialization
 +--> Logging
 +--> Traffic spike
```

---

## 8.6 High Memory

```text
Memory
 |
 +--> Heap
 +--> Old Gen
 +--> GC
 +--> Cache
 +--> Static objects
 +--> ThreadLocal
 +--> Direct memory
 +--> Container limit
```

---

# 9. Senior-Level Trade-off Cheat Sheet

## REST vs Kafka

| REST | Kafka |
|---|---|
| Synchronous | Asynchronous |
| Immediate response | Eventual processing |
| Simpler | More operational complexity |
| Tighter coupling | Loose coupling |
| Good for queries/commands | Good for events |

---

## Retry vs Circuit Breaker

| Retry | Circuit Breaker |
|---|---|
| Handles temporary failure | Prevents repeated calls to failing dependency |
| Can increase load | Protects system |
| Needs backoff | Has state transitions |
| Good for transient errors | Good during sustained failure |

---

## Optimistic vs Pessimistic Locking

| Optimistic | Pessimistic |
|---|---|
| Assumes low conflict | Assumes contention |
| Better concurrency | More serialization |
| Detects conflict | Prevents concurrent modification |
| Good for low contention | Good for highly contested resources |

---

## Cache vs Database

Cache:

- Faster
- Reduces DB load
- Potentially stale
- Invalidation problem

DB:

- Source of truth
- Stronger consistency
- More expensive per request at scale

---

## Kafka vs Direct REST

Choose Kafka when the business does not require immediate synchronous completion and you benefit from decoupling, buffering, replay, or multiple consumers.

Choose REST when the caller needs an immediate response and the operation naturally has request/response semantics.

---

## Saga Choreography vs Orchestration

### Choreography

```text
A -> event -> B -> event -> C
```

Good for simple event-driven workflows.

### Orchestration

```text
        Orchestrator
       /     |      \
      A      B       C
```

Good when workflow is complex and central coordination improves visibility.

---

# 10. Rapid Revision Checklist

## Spring Boot

- [ ] Dependency Injection
- [ ] Bean lifecycle
- [ ] `@Primary` vs `@Qualifier`
- [ ] Global exception handling
- [ ] Validation
- [ ] JWT/OAuth2
- [ ] Timeout
- [ ] Retry
- [ ] Circuit breaker
- [ ] Thread pool
- [ ] Connection pool
- [ ] Memory leak
- [ ] CPU troubleshooting
- [ ] Graceful shutdown
- [ ] Observability

## Microservices

- [ ] REST vs Kafka
- [ ] API Gateway
- [ ] Service discovery
- [ ] Load balancing
- [ ] Circuit breaker
- [ ] Retry
- [ ] Timeout
- [ ] Bulkhead
- [ ] Saga
- [ ] Idempotency
- [ ] Distributed tracing
- [ ] Correlation ID
- [ ] Eventual consistency
- [ ] Schema evolution

## Kafka

- [ ] Topic
- [ ] Partition
- [ ] Consumer group
- [ ] Offset
- [ ] Consumer lag
- [ ] Ordering
- [ ] Rebalancing
- [ ] At-most-once
- [ ] At-least-once
- [ ] Exactly-once
- [ ] Idempotent consumer
- [ ] Retry topic
- [ ] DLQ
- [ ] Outbox
- [ ] Partition key
- [ ] Consumer scaling

## Database

- [ ] Index
- [ ] Composite index
- [ ] Execution plan
- [ ] Transactions
- [ ] Isolation levels
- [ ] Deadlock
- [ ] Optimistic locking
- [ ] Pessimistic locking
- [ ] Read replica
- [ ] Partitioning
- [ ] Sharding
- [ ] Connection pool
- [ ] N+1
- [ ] Schema migration

## Behavioural

- [ ] Production incident
- [ ] Technical disagreement
- [ ] Mistake
- [ ] Conflict
- [ ] Deadline pressure
- [ ] Mentoring
- [ ] Code review
- [ ] Ownership
- [ ] Failure
- [ ] Stakeholder communication
- [ ] RCA
- [ ] Process improvement

---

# Final Senior Interview Mindset

A senior answer should sound like this:

> "First I would clarify the requirement and understand the consistency, latency and availability expectations. Then I would choose the simplest architecture that satisfies those requirements. I would identify the failure points, define timeout/retry/idempotency behaviour, and explain how the system behaves during partial failure. After that I would discuss scalability and observability. Finally, I would explain the trade-offs of my approach and why I would choose it over the alternatives."

## Avoid These Interview Mistakes

### ❌ "I will use Kafka because it is scalable."

Instead:

### ✅

> "I would use Kafka if the operation can be asynchronous and we benefit from decoupling, buffering, or multiple consumers. If the caller needs an immediate response, I would keep that part synchronous."

---

### ❌ "I will use Redis for everything."

Instead:

### ✅

> "I would introduce Redis when caching or distributed coordination provides measurable value. I would first identify the consistency requirements and cache invalidation strategy."

---

### ❌ "I will use microservices."

Instead:

### ✅

> "I would split services around business capabilities and ownership boundaries. I would avoid creating a distributed system without a clear reason because microservices introduce network, consistency, deployment and observability complexity."

---

### ❌ "Exactly once will solve duplicates."

Instead:

### ✅

> "I would design the business operation to be idempotent because retries, crashes and external side effects can still produce duplicate attempts."

---

### ❌ "Increase the thread pool."

Instead:

### ✅

> "I would first determine why threads are blocked. Increasing the pool without addressing a slow database or downstream service could increase contention and make the incident worse."

---

# 30 High-Value Scenarios to Practice First

If interview time is limited, practice these first:

1. API suddenly becomes slow
2. API returns intermittent 500
3. API receives 10x traffic
4. API timeout between microservices
5. Cascading failure
6. Circuit breaker design
7. REST vs Kafka
8. Saga for order/payment/inventory
9. Payment succeeds but order fails
10. Duplicate payment
11. Kafka consumer crashes after DB commit
12. Kafka consumer slower than producer
13. Kafka consumer lag
14. Partition key selection
15. Kafka ordering
16. Consumer rebalancing
17. Poison message/DLQ
18. Kafka + DB consistency
19. Outbox pattern
20. DB query on 100M rows
21. Composite indexes
22. DB deadlock
23. Optimistic vs pessimistic locking
24. Double booking
25. Read replica and replication lag
26. Database bottleneck
27. Production outage
28. Technical disagreement
29. Mistake and learning
30. Ownership/mentoring

---

# Interview Answer Template

Use this template mentally for any unfamiliar scenario:

```text
1. Requirement
   "First I would clarify..."

2. Assumptions
   "Assuming traffic/latency/consistency is..."

3. Design
   "I would design it as..."

4. Flow
   "The request/event would flow from A -> B -> C..."

5. Failure
   "If B fails, I would..."

6. Data consistency
   "For consistency, I would..."

7. Idempotency
   "Because retries can happen, I would..."

8. Scalability
   "At higher traffic, I would..."

9. Observability
   "I would monitor..."

10. Trade-off
    "The downside of this approach is..."

11. Alternative
    "If the requirement changed to X, I would consider Y."

12. Real experience
    "In a similar situation, I have worked with..."
```

**This structure demonstrates senior-level thinking because it shows not only what technology you know, but why, when, and how you would use it in a production system.**

---

# DETAILED INTERVIEW-SPEAKING LAYER

The purpose of this section is to make every technical point answerable as a senior developer. For every technology, explain **what it does, why it is needed, how it works, when to use it, failure behaviour, and trade-offs**.

## 1. Spring Boot: Production Reasoning

### Timeout

**What:** A maximum wait time for a dependency.

**Why:** Without a timeout, slow downstream services consume request threads until the application itself becomes unavailable.

**How:** Configure connection/read/request timeouts at the HTTP-client layer and choose values from the service SLA rather than arbitrary numbers.

**When:** Almost every synchronous network dependency should have an explicit timeout.

**Trade-off:** Too short causes false failures; too long causes resource exhaustion.

**Interview:** "I treat timeout as a resource-protection mechanism, not merely a user-experience setting."

### Retry

**What:** Reattempt a failed operation.

**Why:** Temporary network failures and short-lived dependency failures can recover.

**How:** Use bounded attempts, exponential backoff and jitter. Retry only transient failures and only when the operation is safe to repeat.

**When:** Network failures, selected 5xx responses and transient infrastructure errors.

**Do not retry:** Validation, authentication, authorization and permanent business failures.

**Trade-off:** Retry can create a retry storm and increase pressure on an already unhealthy dependency.

### Circuit Breaker

**What:** A state machine that stops calls to a repeatedly failing dependency.

**Why:** Prevents cascading failure and protects threads/connections.

**How:** CLOSED allows calls; repeated failures open the circuit; HALF_OPEN allows a limited test; success closes it again.

```text
CLOSED --failures--> OPEN --wait--> HALF_OPEN
   ^                                  |
   |----------- success --------------|
   |
   +------------- failure ------------> OPEN
```

**Trade-off:** Requests can fail fast while the dependency is recovering, so fallback behaviour must be meaningful.

### Bulkhead

**What:** Separate resource pools for independent workloads.

**Why:** Reporting should not consume all threads needed by payment.

**How:** Separate thread pools, connection pools or concurrency limits.

```text
Application
 |-- Payment pool
 |-- Reporting pool
 `-- Notification pool
```

**Trade-off:** More configuration and potentially lower overall utilization.

### Idempotency

**What:** Repeating the same logical operation does not create an additional business effect.

**Why:** Requests can be duplicated because of client retries, network ambiguity and service retries.

**How:** Use idempotency keys, event IDs, business keys and database unique constraints.

**Example:** A payment request with key `ABC` is processed once; another request with `ABC` returns the stored result.

**Trade-off:** Requires persistent state and cleanup/retention rules.

---

# 2. Microservices: Production Reasoning

## REST vs Kafka

### REST

**Why:** The caller needs an immediate response.

**How:** Request -> service -> response.

**Trade-off:** Strong temporal coupling: caller depends on downstream availability and latency.

### Kafka

**Why:** Work can be asynchronous, multiple consumers need the event, or buffering/replay is valuable.

**How:** Producer -> topic -> consumer group.

**Trade-off:** Loose coupling and buffering come with eventual consistency, retries, partitioning and operational complexity.

**Senior decision:** "I would not select Kafka simply because it is scalable. I would first determine whether the business operation can be asynchronous."

## Saga

**What:** A sequence of local transactions with compensating business actions.

**Why:** Order, payment and inventory usually own separate databases and cannot be rolled back by one local ACID transaction.

```text
OrderCreated
    |
 Payment success
    |
 Inventory reserve
    |
 Confirm
```

Failure:

```text
Payment success
      |
Inventory failure
      |
Refund payment
      |
Cancel order
```

**Important:** Compensation is not a database rollback. A refund is a new business operation.

**Trade-off:** Better distributed consistency but more intermediate states and recovery logic.

## API Gateway

**Why:** Centralizes routing, authentication, rate limiting and cross-cutting concerns.

**How:** Put multiple gateway instances behind a load balancer.

**Trade-off:** It simplifies clients but becomes important infrastructure and must not be a single point of failure.

## Distributed Tracing

**Why:** Logs from separate services need to be connected to one user request.

**How:** Propagate trace/correlation ID across every hop.

```text
trace=ABC
Gateway -> Order -> Payment -> Inventory
```

**Use:** Identify latency bottlenecks and failure location.

---

# 3. Kafka: Production Reasoning

## Partition

**What:** An ordered log segment inside a topic.

**Why:** Partitions provide parallelism and distribution.

**How:** Consumers in a group receive partitions; one partition is assigned to one consumer in that group at a time.

**Trade-off:** More partitions improve possible parallelism but increase operational overhead.

## Partition Key

**Why:** Business ordering normally requires a stable key.

**How:** Use `customerId`, `orderId` or another entity identifier when all events for that entity must remain ordered.

```text
customer A -> P1
customer B -> P2
customer A -> P1
```

**Trade-off:** A high-volume key can create a hot partition.

## Consumer Group

**Why:** Allows multiple instances to process a topic in parallel.

**Important:** Three partitions cannot provide more than three active partition consumers in one group at the same time.

## Offset

**What:** Consumer progress marker.

**Why:** Kafka needs to know how far a consumer has successfully progressed.

**Safe business pattern:** Process -> commit offset.

If the consumer crashes after processing but before committing, the event can be delivered again. Therefore processing must be idempotent.

## Consumer Lag

**What:** Difference between the latest produced position and consumer progress.

**Why:** Growing lag means processing capacity is below incoming workload.

**How to troubleshoot:**

```text
Lag
 |
 +-- producer traffic increased?
 +-- consumer processing slower?
 +-- DB slow?
 +-- external API slow?
 +-- retries?
 +-- rebalance?
 +-- insufficient partitions?
 +-- CPU/GC?
```

**Senior point:** Do not automatically add consumers; they may simply overload a slow database.

## Poison Message / DLQ

**Why:** A permanently failing message should not block healthy work indefinitely.

**How:** Bounded retries -> DLQ -> alert -> investigate -> replay after correction.

**DLQ should retain:** event ID, original payload, error, retry count, timestamp, source topic/partition/offset.

**Trade-off:** DLQ improves availability but requires operational ownership and replay procedures.

## At-Least-Once

**Why:** Prioritize reliable delivery.

**Risk:** Duplicate processing.

**Required design:** Idempotent consumer.

## Exactly-Once

Kafka can provide transactional semantics for supported Kafka workflows, but external side effects such as payment providers still require idempotency.

**Senior answer:** "Exactly-once is not a substitute for business-level idempotency."

## Outbox

**Problem:**

```text
DB commit -> success
Kafka publish -> failure
```

**Solution:**

```text
One DB transaction
 |-- business table
 `-- outbox table
          |
          v
      publisher -> Kafka
```

**Why:** Business state and event intent are committed together.

**Trade-off:** Extra table, publisher, cleanup and duplicate handling.

---

# 4. Database: Production Reasoning

## Index

**Why:** Avoid unnecessary full scans and locate matching rows efficiently.

**How:** Create indexes based on actual query patterns and execution plans.

**Trade-off:** Indexes consume storage and increase insert/update/delete cost.

## Composite Index

For `(customer_id, status, created_at)`, queries using the leading columns generally benefit most.

**Why column order matters:** The physical/index access structure begins with the first indexed column.

**Senior point:** Never say "an index always makes a query faster." Verify with the execution plan and workload.

## Deadlock

```text
A locks Row1 -> waits Row2
B locks Row2 -> waits Row1
```

**Why:** Circular lock dependency.

**How to reduce:** Consistent lock ordering, short transactions, appropriate indexes and safe retries.

**Trade-off:** Retrying a deadlocked transaction is only safe when the operation can be repeated without an incorrect business effect.

## Optimistic Locking

**Why:** Good when concurrent conflicts are relatively rare.

**How:** Store a version and update only when the version still matches.

```sql
UPDATE seat
SET available=false, version=version+1
WHERE id=? AND available=true AND version=?;
```

**Trade-off:** Conflicts fail and need retry/recovery.

## Pessimistic Locking

**Why:** Useful when contention is high and serial access is acceptable.

**How:** Lock the row, for example with `SELECT ... FOR UPDATE`, then perform the transaction.

**Trade-off:** More lock contention and potential deadlocks.

## Read Replica

**Why:** Scale read traffic away from the primary.

**Problem:** Replication lag can produce stale reads.

**Senior answer:** "I would route read-after-write or consistency-sensitive queries appropriately rather than assuming replicas are immediately consistent."

## Sharding

**Why:** Scale storage/throughput beyond a single database.

**How:** Route records using a shard key.

**Good shard key:** Stable, high-cardinality, evenly distributed and aligned with access patterns.

**Trade-off:** Cross-shard joins, transactions, rebalancing and debugging become more difficult.

---

# 5. Cross-Technology Scenario: Order System

## Design

```text
Client
 |
Gateway
 |
Order Service
 |
Order DB
 |
Outbox
 |
Kafka
 +------ Payment
 +------ Inventory
 +------ Notification
 +------ Analytics
```

## Why each layer?

**Gateway:** routing/authentication/rate limiting.

**Order Service:** owns order business rules.

**Order DB:** durable source of order state.

**Outbox:** ensures the DB update and event intent are committed together.

**Kafka:** decouples asynchronous consumers and absorbs bursts.

**Payment:** owns payment state.

**Inventory:** owns reservation state.

**Notification:** can be asynchronous because an email usually should not block order creation.

## State Machine

```text
PENDING_PAYMENT
      |
PAYMENT_COMPLETED
      |
INVENTORY_RESERVED
      |
CONFIRMED
```

Failure states can include `CANCELLED`, `REFUND_PENDING` and `REFUNDED`.

**Why a state machine?** Explicit states prevent contradictory boolean combinations and make recovery rules easier to implement.

---

# 6. Cross-Technology Scenario: Duplicate Payment

```text
Client
 |
Idempotency-Key
 |
Payment API
 |
DB unique constraint
 |
Payment state
 |
Outbox
 |
Kafka
```

**Why idempotency key?** Protects against repeated client requests.

**Why DB constraint?** Prevents two application instances from both winning a check-then-insert race.

**Why state machine?** Prevents illegal transitions such as charging an already refunded payment.

**Why outbox?** Prevents the payment DB update from succeeding while event publication is silently lost.

**Why consumer idempotency?** Kafka delivery can be repeated.

---

# 7. Cross-Technology Scenario: Booking the Last Seat

```text
100 requests
     |
Booking Service
     |
Atomic DB update
     |
 +---+---+
 |       |
1 row   0 rows
 |       |
booked  sold out
```

**Why:** The database can enforce the invariant atomically.

**Why not immediately use Redis locks?** A distributed lock adds expiry, failure and ownership complexity. If an atomic DB update is sufficient, it is usually simpler.

**When optimistic locking helps:** When contention is manageable and conflicts can be retried.

**When pessimistic locking helps:** When contention is very high and serial access is acceptable.

---

# 8. Behavioural Questions: Senior Answer Method

## Production Incident

Use:

```text
Situation
  -> Impact
  -> Responsibility
  -> Investigation
  -> Mitigation
  -> Fix
  -> Prevention
```

**Why this structure works:** It demonstrates ownership, technical reasoning, communication and learning.

## Technical Conflict

```text
Understand other view
        |
Gather evidence
        |
Compare trade-offs
        |
Agree decision
        |
Execute
```

**Why:** Senior engineers should optimize for the system and business outcome, not for winning an argument.

## Unrealistic Deadline

```text
Requirement
 -> Break down
 -> Estimate
 -> Identify critical path
 -> Must-have vs optional
 -> Communicate risk
 -> Phase delivery
```

**Why:** You protect engineering quality while still helping the business achieve the highest-value outcome.

## Production Bug You Introduced

```text
Acknowledge
 -> Contain
 -> Communicate
 -> Rollback/Hotfix
 -> RCA
 -> Prevention
```

**Senior signal:** Never make the answer about blaming QA, DevOps or another developer. Explain what you personally did and what process/technical improvement followed.

---

# 9. Follow-up Questions Interviewers Commonly Ask

For every design, prepare answers to:

### Why this technology?
Explain the requirement it solves.

### Why not the alternative?
Explain the trade-off rather than saying the alternative is "bad."

### What happens if it fails?
Explain the failure path explicitly.

### What happens at 10x traffic?
Explain horizontal scaling, bottlenecks and backpressure.

### What if the same message arrives twice?
Explain idempotency.

### What if the database is down?
Explain timeout, retry boundaries, queueing and recovery.

### What if Kafka is unavailable?
Explain producer retry and how the outbox protects event intent.

### What if Redis is unavailable?
Explain whether the application can safely fall back to the database and how to avoid turning a cache outage into a DB overload.

### How do you monitor it?
Mention metrics, logs, traces, alerts and dashboards.

### What is the trade-off?
Always state one. Senior engineers understand that every design has a cost.

---

# 10. Golden Senior-Level Answer Pattern

Use this sentence pattern:

> **"I would choose X because of Y. I would implement it using Z. If A fails, I would handle it using B. For duplicate processing I would use C. At higher scale I would use D. The trade-off is E."**

For unfamiliar scenarios:

```text
Requirement
    |
Consistency + latency
    |
Traffic + scale
    |
Sync/async decision
    |
Failure modes
    |
Idempotency
    |
Data design
    |
Observability
    |
Trade-off
```

The objective is not to mention the maximum number of technologies. The objective is to demonstrate **engineering judgement: why a solution is appropriate, how it works, how it fails, and what it costs.**
