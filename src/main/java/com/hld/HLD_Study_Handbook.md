# 📚 HIGH-LEVEL DESIGN (HLD) COMPLETE STUDY HANDBOOK
## For Java/Senior Software Engineer Interviews

---

# TABLE OF CONTENTS

1. [Network Fundamentals & Protocols](#1-network-fundamentals--protocols)
2. [Client-Server Architecture](#2-client-server-architecture)
3. [Proxy Servers](#3-proxy-servers)
4. [CAP Theorem](#4-cap-theorem)
5. [Database Properties: ACID vs BASE](#5-database-properties-acid-vs-base)
6. [SQL vs NoSQL Databases](#6-sql-vs-nosql-databases)
7. [Microservices Architecture](#7-microservices-architecture)
8. [Decomposition Patterns](#8-decomposition-patterns)
9. [Strangler Fig Pattern](#9-strangler-fig-pattern)
10. [SAGA Pattern](#10-saga-pattern)
11. [Scaling Strategies](#11-scaling-strategies)
12. [Caching Strategies](#12-caching-strategies)
13. [Content Delivery Network (CDN)](#13-content-delivery-network-cdn)
14. [Database Replication](#14-database-replication)
15. [Message Queues & Event-Driven Architecture](#15-message-queues--event-driven-architecture)
16. [Sharding & Partitioning](#16-sharding--partitioning)
17. [Consistent Hashing](#17-consistent-hashing)
18. [URL Shortener System Design](#18-url-shortener-system-design)
19. [Capacity Estimation](#19-capacity-estimation)
20. [Database Types Deep Dive](#20-database-types-deep-dive)
21. [Chat System Design](#21-chat-system-design)
22. [Data Versioning & Vector Clocks](#22-data-versioning--vector-clocks)
23. [Fault-Tolerant Microservices](#23-fault-tolerant-microservices)
24. [Rate Limiting](#24-rate-limiting)
25. [Service Discovery](#25-service-discovery)
26. [Client-Side Load Balancing](#26-client-side-load-balancing)
27. [Bulkhead Pattern](#27-bulkhead-pattern)
28. [Time Limiter](#28-time-limiter)
29. [Interview Quick Reference](#29-interview-quick-reference)

---

# 1. NETWORK FUNDAMENTALS & PROTOCOLS

## 1.1 OSI Model Layers

The OSI (Open Systems Interconnection) model is a conceptual framework with **7 layers**:

| Layer # | Layer Name | Function | Protocols/Examples |
|---------|------------|----------|-------------------|
| 7 | Application | User interface, network services | HTTP, FTP, SMTP, DNS |
| 6 | Presentation | Data formatting, encryption | SSL/TLS, JPEG, ASCII |
| 5 | Session | Session management | NetBIOS, RPC |
| 4 | Transport | End-to-end communication | TCP, UDP |
| 3 | Network | Routing, logical addressing | IP, ICMP, ARP |
| 2 | Data Link | Frame transmission | Ethernet, MAC |
| 1 | Physical | Bit transmission | Cables, Hubs |

### Interview Point
> **Q: Why is understanding OSI model important in system design?**
> A: It helps identify at which layer a problem occurs and what protocols are involved. For example, load balancers operate at Layer 4 (TCP) or Layer 7 (HTTP).

---

## 1.2 Network Protocols

### Why Network Protocols Are Important

Network protocols ensure efficient data transmission by defining standards for:
- **Interoperability**: Provides a common way to share information between different systems
- **Reliability**: Ensures data integrity during transmission
- **Efficiency**: Optimizes data transmission by minimizing latency and packet loss
- **Security**: SSL/TLS encrypts data

---

## 1.3 TCP (Transmission Control Protocol)

### Definition
TCP is a **connection-oriented**, **reliable** protocol that operates at the Transport Layer of OSI model.

### TCP 3-Way Handshake

```
Client                          Server
   |                               |
   |-------- SYN (seq=x) -------->|
   |                               |
   |<---- SYN+ACK (seq=y, ack=x+1)|
   |                               |
   |-------- ACK (ack=y+1) ------>|
   |                               |
   |     Connection Established    |
```

### Key Characteristics
| Feature | Description |
|---------|-------------|
| Connection-Oriented | Establishes connection before data transfer |
| Reliable | Guarantees packet delivery |
| Ordered | Maintains packet sequence |
| Error-Checked | Retransmits lost packets |
| Flow Control | Prevents overwhelming receiver |

### Use Cases
- Email (SMTP, IMAP, POP3)
- Web browsing (HTTP/HTTPS)
- File transfer (FTP)
- Any application requiring data integrity

---

## 1.4 UDP (User Datagram Protocol)

### Definition
UDP is a **connectionless**, **unreliable** protocol that provides minimal transport services.

### Key Characteristics
| Feature | Description |
|---------|-------------|
| Connectionless | No handshake required |
| Unreliable | No guarantee of delivery |
| Unordered | No packet sequencing |
| Fast | Lower latency than TCP |
| Lightweight | Minimal overhead |

### Use Cases
- Video streaming
- Online gaming
- VoIP (Voice over IP)
- DNS queries
- Live broadcasts

### TCP vs UDP Comparison

| Aspect | TCP | UDP |
|--------|-----|-----|
| Connection | Connection-oriented | Connectionless |
| Reliability | Guaranteed delivery | Best effort |
| Ordering | Ordered | Unordered |
| Speed | Slower | Faster |
| Header Size | 20-60 bytes | 8 bytes |
| Flow Control | Yes | No |
| Use Case | Web, Email, File Transfer | Streaming, Gaming, DNS |

---

## 1.5 HTTP and HTTPS

### HTTP (HyperText Transfer Protocol)
- Application layer protocol for web communication
- Request-Response model between client and server
- Stateless protocol

### HTTPS (HTTP Secure)
- HTTP + SSL/TLS encryption
- Provides:
  - **Data encryption**: Prevents eavesdropping
  - **Data integrity**: Detects tampering
  - **Authentication**: Verifies server identity

### HTTP Methods (Interview Essential)

| Method | Purpose | Idempotent | Safe |
|--------|---------|------------|------|
| GET | Retrieve resource | Yes | Yes |
| POST | Create resource | No | No |
| PUT | Update/Replace resource | Yes | No |
| PATCH | Partial update | No | No |
| DELETE | Remove resource | Yes | No |
| HEAD | Get headers only | Yes | Yes |
| OPTIONS | Get supported methods | Yes | Yes |

### HTTP Status Codes

| Range | Category | Examples |
|-------|----------|----------|
| 1xx | Informational | 100 Continue |
| 2xx | Success | 200 OK, 201 Created, 204 No Content |
| 3xx | Redirection | 301 Moved, 302 Found, 304 Not Modified |
| 4xx | Client Error | 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found |
| 5xx | Server Error | 500 Internal Error, 502 Bad Gateway, 503 Service Unavailable |

---

## 1.6 FTP (File Transfer Protocol)

### Definition
Protocol for transferring files between client and server.

### Connection Types
1. **Control Connection**: For commands (port 21) - persistent
2. **Data Connection**: For file transfer (port 20) - temporary

---

# 2. CLIENT-SERVER ARCHITECTURE

## 2.1 Evolution of Architecture

### Single Server Architecture
```
[Client] <---> [Application + Database on Single Server]
```

**Limitations:**
- No separation of concerns
- Single point of failure
- Cannot scale

### Application & DB Separation
```
[Client] <---> [Application Server] <---> [Database Server]
```

**Benefits:**
- Independent scaling
- Better security (DB in private network)
- Easier maintenance

### Multi-Tier Architecture with Load Balancer
```
                    ┌─── [App Server 1] ───┐
[Client] ─> [LB] ──>├─── [App Server 2] ───├──> [Database]
                    └─── [App Server 3] ───┘
```

**Benefits:**
- High availability
- Horizontal scaling
- Fault tolerance

---

## 2.2 N-Tier Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION TIER                        │
│                  (Web/Mobile Clients)                       │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                      LOGIC TIER                             │
│                (Application Servers)                        │
│            Business Logic & Processing                      │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                      DATA TIER                              │
│                    (Databases)                              │
└─────────────────────────────────────────────────────────────┘
```

### Interview Point
> **Q: Why separate application logic and database?**
> A: 
> 1. **Security**: DB can be in private subnet
> 2. **Scaling**: Each tier scales independently
> 3. **Maintenance**: Changes in one tier don't affect others
> 4. **Performance**: Specialized hardware for each tier

---

# 3. PROXY SERVERS

## 3.1 Definition
A proxy server acts as an **intermediary** between clients and servers.

## 3.2 Types of Proxies

### Forward Proxy
```
[Client] ──> [Forward Proxy] ──> [Internet] ──> [Server]
```

**Use Cases:**
- Hide client IP address
- Access control (block certain websites)
- Caching for performance
- Bypass geo-restrictions

### Reverse Proxy
```
[Client] ──> [Internet] ──> [Reverse Proxy] ──> [Backend Servers]
```

**Use Cases:**
- **Load Balancing**: Distribute traffic across servers
- **SSL Termination**: Handle encryption/decryption
- **Caching**: Cache static content
- **Security**: Hide backend server details
- **Compression**: Reduce response size

### Examples
| Type | Examples |
|------|----------|
| Forward Proxy | Squid, CCProxy |
| Reverse Proxy | Nginx, HAProxy, Apache |

## 3.3 Advantages of Proxies

| Advantage | Description |
|-----------|-------------|
| Enhanced Security | Hides internal network structure |
| Improved Performance | Caching reduces latency |
| Content Control | Filter/modify content |
| Load Balancing | Distribute traffic evenly |

## 3.4 Disadvantages

| Disadvantage | Description |
|--------------|-------------|
| Single Point of Failure | Proxy down = service down |
| Added Latency | Extra hop in network |
| Complexity | Additional infrastructure to manage |
| Cost | Hardware and maintenance |

---

# 4. CAP THEOREM

## 4.1 Definition

In a **distributed system**, it is impossible to simultaneously guarantee all three of the following:

| Property | Definition |
|----------|------------|
| **C**onsistency | Every read receives the most recent write |
| **A**vailability | Every request receives a response (success or failure) |
| **P**artition Tolerance | System continues to operate despite network partitions |

## 4.2 Visual Representation

```
                    Consistency (C)
                         /\
                        /  \
                       /    \
                      /  CA  \
                     /________\
                    /\        /\
                   /  \  CP  /  \
                  / AP \    /    \
                 /______\  /______\
        Availability (A)    Partition Tolerance (P)
```

## 4.3 The Trade-offs

### When Network Partition Occurs:

You must choose between:
- **CP (Consistency + Partition Tolerance)**: System may become unavailable but data remains consistent
- **AP (Availability + Partition Tolerance)**: System remains available but data may be inconsistent

### Why CA is Impractical in Distributed Systems

In a distributed system, **network partitions WILL happen**. Therefore:
- You MUST choose P (Partition Tolerance)
- The real choice is between C and A when partition occurs

## 4.4 Database Examples by CAP

| Type | Examples | Trade-off |
|------|----------|-----------|
| **CP** | MongoDB, HBase, Redis | Sacrifices availability during partitions |
| **AP** | Cassandra, DynamoDB, CouchDB | Sacrifices consistency during partitions |
| **CA** | Traditional RDBMS (single node) | Not truly distributed |

## 4.5 Interview Scenarios

### Scenario 1: Banking Application
**Choice**: CP (Consistency + Partition Tolerance)
**Reason**: Account balance must be accurate. Better to be unavailable than show wrong balance.

### Scenario 2: Social Media Feed
**Choice**: AP (Availability + Partition Tolerance)
**Reason**: Users prefer seeing slightly stale posts over seeing nothing.

### Scenario 3: E-commerce Inventory
**Choice**: Depends on business requirement
- **CP**: If overselling is unacceptable
- **AP**: If occasional overselling is okay (can cancel orders)

## 4.6 Original Notes Reference

From your handwritten notes:
- "In distributed system, if nodes can't communicate, it causes partition"
- "If partitioned, then either we should forget about consistency OR availability"
- "A write on node B, then B will sync changes to all other places in one shot"

⚠️ **CORRECTION**:
- **Original Note**: "Changes must be done at each place in one shot"
- **What's Technically Correct**: Changes don't happen instantaneously. In CP systems, the system blocks until all replicas are updated. In AP systems, changes propagate eventually (eventual consistency).
- **Interview Relevance**: Understanding synchronous vs asynchronous replication is crucial.

---

# 5. DATABASE PROPERTIES: ACID vs BASE

## 5.1 ACID Properties (Traditional RDBMS)

| Property | Definition | Example |
|----------|------------|---------|
| **A**tomicity | Transaction is all-or-nothing | Bank transfer: debit AND credit both succeed or both fail |
| **C**onsistency | Database moves from one valid state to another | Constraints (FK, Check) are always satisfied |
| **I**solation | Concurrent transactions don't interfere | Two users updating same row don't see partial changes |
| **D**urability | Committed transactions survive failures | Data persists after power failure |

### ACID-Compliant Databases
- PostgreSQL
- MySQL (with InnoDB)
- Oracle
- SQL Server

## 5.2 BASE Properties (NoSQL)

| Property | Definition |
|----------|------------|
| **B**asically **A**vailable | System guarantees availability |
| **S**oft State | State may change over time without input |
| **E**ventually Consistent | System will become consistent given enough time |

### BASE-Following Databases
- Cassandra
- DynamoDB
- CouchDB
- Riak

## 5.3 ACID vs BASE Comparison

| Aspect | ACID | BASE |
|--------|------|------|
| Consistency | Strong (immediate) | Eventual |
| Availability | May sacrifice for consistency | High availability |
| Scaling | Vertical (scale up) | Horizontal (scale out) |
| Use Case | Banking, transactions | Social media, analytics |
| Complexity | Simpler to reason about | More complex application logic |

## 5.4 Interview Point

> **Q: When would you choose ACID over BASE?**
> A: 
> - **ACID**: When data accuracy is critical (financial transactions, inventory management, healthcare records)
> - **BASE**: When availability and scalability are more important than immediate consistency (social feeds, analytics, logging)

---

# 6. SQL vs NoSQL DATABASES

## 6.1 SQL (Relational) Databases

### Characteristics
- **Structured data** with predefined schema
- **ACID compliant**
- **Vertical scaling** (scale up)
- **Table-based** with rows and columns
- **Complex queries** with JOINs

### Examples
| Database | Best For |
|----------|----------|
| PostgreSQL | Complex queries, JSON support, ACID |
| MySQL | Web applications, read-heavy workloads |
| Oracle | Enterprise, large-scale transactions |
| SQL Server | Microsoft ecosystem |

## 6.2 NoSQL Databases

### 6.2.1 Key-Value Stores

```
┌──────────────────────────────┐
│  Key          │    Value     │
├──────────────────────────────┤
│  user:1       │  {JSON}      │
│  session:abc  │  {data}      │
│  cart:123     │  {items}     │
└──────────────────────────────┘
```

**Characteristics:**
- Simple get/put operations
- Very fast lookups
- No complex queries
- Horizontally scalable

**Examples**: Redis, DynamoDB, Memcached

**Use Cases**: Caching, session storage, real-time data

### 6.2.2 Document Databases

```json
{
  "_id": "user123",
  "name": "John",
  "address": {
    "city": "NYC",
    "zip": "10001"
  },
  "orders": [
    {"id": 1, "amount": 100},
    {"id": 2, "amount": 200}
  ]
}
```

**Characteristics:**
- Store data as JSON/BSON documents
- Flexible schema
- Can query within documents
- Horizontally scalable

**Examples**: MongoDB, CouchDB

**Use Cases**: Content management, catalogs, user profiles

### 6.2.3 Column-Family (Wide-Column) Stores

```
Row Key    | Column Family: Personal    | Column Family: Contact
-----------+----------------------------+------------------------
user1      | name: John, age: 30        | email: john@x.com
user2      | name: Jane, age: 25        | phone: 123-456-7890
```

**Characteristics:**
- Data organized by columns, not rows
- Excellent for aggregations
- High write throughput
- Good for time-series data

**Examples**: Cassandra, HBase, ScyllaDB

**Use Cases**: Time-series data, IoT, analytics, logging

### 6.2.4 Graph Databases

```
    (Alice)──FRIENDS──>(Bob)
       │                 │
    WORKS_AT          WORKS_AT
       │                 │
       ▼                 ▼
   (Google)          (Amazon)
```

**Characteristics:**
- Nodes and relationships (edges)
- Excellent for connected data
- Fast traversal of relationships

**Examples**: Neo4j, Amazon Neptune, JanusGraph

**Use Cases**: Social networks, recommendation engines, fraud detection

## 6.3 Comparison Table

| Feature | SQL | NoSQL |
|---------|-----|-------|
| Schema | Fixed | Flexible |
| Scaling | Vertical | Horizontal |
| Consistency | Strong (ACID) | Eventual (BASE) |
| Joins | Supported | Generally not supported |
| Transactions | Multi-row ACID | Limited (per document/row) |
| Query Language | SQL | Varies by DB |

## 6.4 Decision Framework

```
                           Need Complex Joins?
                                  │
                    ┌─────────────┼─────────────┐
                    │ Yes         │             │ No
                    ▼             │             ▼
               Use SQL           │        What's the data shape?
                                 │             │
                                 │    ┌────────┼────────┬──────────┐
                                 │    │ K-V    │ Docs   │ Graph    │
                                 │    ▼        ▼        ▼          │
                                 │  Redis   MongoDB   Neo4j       │
                                 │                                 │
                                 │        Time Series?             │
                                 │             │                   │
                                 │         Cassandra               │
```

---

# 7. MICROSERVICES ARCHITECTURE

## 7.1 Monolithic vs Microservices

### Monolithic Architecture
```
┌─────────────────────────────────────────────┐
│              MONOLITHIC APP                 │
├─────────────────────────────────────────────┤
│  ┌─────────┐ ┌─────────┐ ┌─────────┐       │
│  │  User   │ │  Order  │ │ Payment │       │
│  │ Module  │ │ Module  │ │ Module  │       │
│  └─────────┘ └─────────┘ └─────────┘       │
│                                             │
│              Single Database                │
└─────────────────────────────────────────────┘
```

**Advantages:**
- Simple to develop initially
- Easy to test
- Easy to deploy (single artifact)

**Disadvantages:**
- Hard to scale specific components
- Tight coupling
- Single point of failure
- Difficult to adopt new technologies
- Long deployment cycles
- Overloaded IDE (from your notes)

### Microservices Architecture
```
┌───────────┐   ┌───────────┐   ┌───────────┐
│   User    │   │   Order   │   │  Payment  │
│  Service  │   │  Service  │   │  Service  │
│    DB     │   │    DB     │   │    DB     │
└─────┬─────┘   └─────┬─────┘   └─────┬─────┘
      │               │               │
      └───────────────┼───────────────┘
                      │
              [API Gateway]
                      │
                  [Client]
```

**Advantages:**
- Independent deployment
- Technology diversity
- Fault isolation
- Easy to scale specific services
- Smaller, focused teams

**Disadvantages:**
- Increased latency (network calls)
- Distributed system complexity
- Transaction management challenges
- Service coordination required
- Data consistency issues

## 7.2 When to Use Microservices

| Use Microservices When | Stick with Monolith When |
|------------------------|-------------------------|
| Large team (multiple teams) | Small team |
| Need independent scaling | Simple application |
| Different tech stacks needed | Tight deadline |
| Frequent deployments needed | Budget constraints |
| Complex domain | Proof of concept |

## 7.3 Key Microservices Principles

1. **Single Responsibility**: Each service does one thing well
2. **Self-Contained**: Each service has its own database
3. **Independently Deployable**: No coordination needed for deployment
4. **Decentralized**: No central point of control
5. **Technology Agnostic**: Services can use different tech stacks

## 7.4 From Your Original Notes

> "If partition services not made correctly and services are connected with each other - Latency will increase"

This is a key insight about **service coupling**. Poorly designed service boundaries lead to:
- Excessive inter-service communication
- Chatty interfaces
- Performance degradation

---

# 8. DECOMPOSITION PATTERNS

## 8.1 Decompose by Business Capability

### Definition
Divide services based on what the business does.

### Example: Online Order Application

```
┌──────────────────────────────────────────────────────────────┐
│                    ONLINE ORDER APPLICATION                   │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │   Order     │  │   Account   │  │   Login     │          │
│  │ Management  │  │ Management  │  │ Management  │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
│                                                              │
│  ┌─────────────┐  ┌─────────────┐                           │
│  │   Billing   │  │   Payment   │                           │
│  │ Management  │  │ Management  │                           │
│  └─────────────┘  └─────────────┘                           │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

### How to Identify Business Capabilities
1. Identify core business functions
2. Each capability becomes a service
3. Services own their data

## 8.2 Decompose by Subdomain (DDD)

### Definition
Use Domain-Driven Design (DDD) to identify bounded contexts.

### Example: Order Management Domain

```
┌─────────────────────────────────────────────────────┐
│              ORDER MANAGEMENT DOMAIN                │
├─────────────────────────────────────────────────────┤
│                                                     │
│  ┌───────────────┐  ┌───────────────┐              │
│  │ Order Placing │  │ Order Tracking│              │
│  │  Subdomain    │  │   Subdomain   │              │
│  └───────────────┘  └───────────────┘              │
│                                                     │
└─────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────┐
│                PAYMENT DOMAIN                       │
├─────────────────────────────────────────────────────┤
│                                                     │
│  ┌───────────────┐  ┌───────────────┐              │
│  │Forward Payment│  │Reverse Payment│              │
│  │  (Charges)    │  │  (Refunds)    │              │
│  └───────────────┘  └───────────────┘              │
│                                                     │
└─────────────────────────────────────────────────────┘
```

### DDD Terminology

| Term | Definition |
|------|------------|
| **Domain** | The problem space |
| **Subdomain** | A subset of the domain |
| **Bounded Context** | A boundary within which a model is defined |
| **Ubiquitous Language** | Common vocabulary shared by team and code |
| **Aggregate** | A cluster of domain objects treated as a unit |

## 8.3 Database per Service

### Pattern
Each microservice has its own dedicated database.

```
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│ Order Service │    │ User Service  │    │Payment Service│
└───────┬───────┘    └───────┬───────┘    └───────┬───────┘
        │                    │                    │
        ▼                    ▼                    ▼
   ┌─────────┐          ┌─────────┐          ┌─────────┐
   │Order DB │          │ User DB │          │Payment DB│
   │ (MySQL) │          │(MongoDB)│          │(Postgres)│
   └─────────┘          └─────────┘          └─────────┘
```

### Benefits
- Services are loosely coupled
- Each service can use the best database for its needs
- Independent scaling of databases

### Challenges
- Cross-service queries are complex
- Distributed transactions
- Data consistency

---

# 9. STRANGLER FIG PATTERN

## 9.1 Definition

An architectural pattern for **gradually migrating** a legacy monolithic application to microservices.

Named after the strangler fig tree that gradually grows around and eventually replaces its host tree.

## 9.2 How It Works

```
PHASE 1: Initial State
┌─────────────────────────────────────┐
│         MONOLITH                    │
│  [Feature A] [Feature B] [Feature C]│
└─────────────────────────────────────┘

PHASE 2: Extract First Feature
┌─────────────────────────────────────┐
│ [Router/Proxy]                      │
├─────────────────────────────────────┤
│         │                           │
│         ▼                           │
│    ┌─────────┐                      │
│    │Feature A│ ← New Microservice   │
│    │ (New)   │                      │
│    └─────────┘                      │
│         │                           │
│         ▼                           │
│  ┌─────────────────────────────┐   │
│  │  MONOLITH (Legacy)          │   │
│  │  [Feature B] [Feature C]    │   │
│  └─────────────────────────────┘   │
└─────────────────────────────────────┘

PHASE 3: Continue Migration
┌─────────────────────────────────────┐
│ [Router/Proxy]                      │
├─────────────────────────────────────┤
│    ┌─────────┐  ┌─────────┐        │
│    │Feature A│  │Feature B│        │
│    │ (New)   │  │ (New)   │        │
│    └─────────┘  └─────────┘        │
│         │                           │
│  ┌─────────────────────────────┐   │
│  │  MONOLITH (Shrinking)       │   │
│  │  [Feature C]                │   │
│  └─────────────────────────────┘   │
└─────────────────────────────────────┘

PHASE 4: Complete Migration
┌─────────────────────────────────────┐
│ [Router/Proxy/API Gateway]          │
├─────────────────────────────────────┤
│  ┌─────────┐ ┌─────────┐ ┌─────────┐│
│  │Feature A│ │Feature B│ │Feature C││
│  │ (New)   │ │ (New)   │ │ (New)   ││
│  └─────────┘ └─────────┘ └─────────┘│
└─────────────────────────────────────┘
```

## 9.3 Key Principles

1. **Coexistence**: Old and new systems run simultaneously
2. **Gradual Migration**: Migrate feature by feature
3. **Route Traffic**: Proxy/Router directs requests to appropriate system
4. **Transform**: Incrementally develop new microservices

## 9.4 Benefits

| Benefit | Description |
|---------|-------------|
| Low Risk | No big-bang migration |
| Continuous Delivery | Ship features while migrating |
| Reversible | Can roll back if issues arise |
| Incremental Investment | Spread cost over time |

## 9.5 Interview Point

> **Q: How do you migrate a legacy monolith to microservices?**
> A: Use the Strangler Fig Pattern:
> 1. Identify service boundaries
> 2. Add a routing layer (API Gateway)
> 3. Extract one feature at a time
> 4. Route traffic to new service
> 5. Repeat until monolith is empty

---

# 10. SAGA PATTERN

## 10.1 The Problem

In microservices, each service has its own database. How do we handle transactions that span multiple services?

**Traditional Solution (Doesn't Work):**
- 2PC (Two-Phase Commit) is impractical in distributed systems
- Holding locks across services causes availability issues

## 10.2 Definition

SAGA is a sequence of **local transactions** where each transaction updates a database and publishes an event/message to trigger the next transaction. If any transaction fails, **compensating transactions** are executed to undo the changes.

## 10.3 SAGA Types

### 10.3.1 Choreography-Based SAGA

Each service knows what to do next based on events. No central coordinator.

```
┌──────────────────────────────────────────────────────────────────────┐
│                    CHOREOGRAPHY-BASED SAGA                           │
├──────────────────────────────────────────────────────────────────────┤
│                                                                      │
│   [Order Service]                                                    │
│        │                                                             │
│        │ 1. Create Order (PENDING)                                   │
│        │ 2. Publish "OrderCreated" event                             │
│        ▼                                                             │
│   ┌─────────────────────────┐                                       │
│   │    MESSAGE BROKER       │                                       │
│   │    (Kafka/RabbitMQ)     │                                       │
│   └─────────────────────────┘                                       │
│        │                                                             │
│        │ "OrderCreated" event                                        │
│        ▼                                                             │
│   [Payment Service]                                                  │
│        │                                                             │
│        │ 3. Process Payment                                          │
│        │ 4. Publish "PaymentCompleted" or "PaymentFailed"            │
│        ▼                                                             │
│   ┌─────────────────────────┐                                       │
│   │    MESSAGE BROKER       │                                       │
│   └─────────────────────────┘                                       │
│        │                                                             │
│        │ "PaymentCompleted"                                          │
│        ▼                                                             │
│   [Inventory Service]                                                │
│        │                                                             │
│        │ 5. Reserve Inventory                                        │
│        │ 6. Publish "InventoryReserved"                              │
│        ▼                                                             │
│   [Order Service]                                                    │
│        │                                                             │
│        │ 7. Update Order to CONFIRMED                                │
│                                                                      │
└──────────────────────────────────────────────────────────────────────┘
```

**Compensation Flow (On Failure):**
```
If Inventory fails:
  → Publish "InventoryFailed"
  → Payment Service receives event
  → Payment Service issues refund (compensating transaction)
  → Order Service receives "PaymentRefunded"
  → Order Service marks order as CANCELLED
```

**Pros:**
- Simple to implement for few services
- Loosely coupled
- No single point of failure

**Cons:**
- Hard to understand the overall flow
- Cyclic dependencies possible
- Difficult to maintain as services grow

### 10.3.2 Orchestration-Based SAGA

A central **Orchestrator** tells each service what to do.

```
┌──────────────────────────────────────────────────────────────────────┐
│                    ORCHESTRATION-BASED SAGA                          │
├──────────────────────────────────────────────────────────────────────┤
│                                                                      │
│                     ┌───────────────────┐                           │
│                     │  SAGA ORCHESTRATOR │                          │
│                     │  (Order Saga)      │                          │
│                     └─────────┬─────────┘                           │
│                               │                                      │
│         ┌─────────────────────┼─────────────────────┐               │
│         │                     │                     │               │
│         ▼                     ▼                     ▼               │
│  ┌─────────────┐      ┌─────────────┐      ┌─────────────┐        │
│  │   Order     │      │   Payment   │      │  Inventory  │        │
│  │   Service   │      │   Service   │      │   Service   │        │
│  └─────────────┘      └─────────────┘      └─────────────┘        │
│                                                                      │
│  Orchestrator Commands:                                             │
│  1. Send "CreateOrder" to Order Service                             │
│  2. Send "ProcessPayment" to Payment Service                        │
│  3. Send "ReserveInventory" to Inventory Service                    │
│  4. Send "ConfirmOrder" to Order Service                            │
│                                                                      │
│  On Failure (Compensating):                                         │
│  1. Send "CancelReservation" to Inventory                           │
│  2. Send "RefundPayment" to Payment                                 │
│  3. Send "CancelOrder" to Order                                     │
│                                                                      │
└──────────────────────────────────────────────────────────────────────┘
```

**Pros:**
- Easy to understand the flow
- Single place to see saga logic
- No cyclic dependencies

**Cons:**
- Single point of failure (orchestrator)
- More coupling to orchestrator
- Additional infrastructure

## 10.4 Comparison Table

| Aspect | Choreography | Orchestration |
|--------|--------------|---------------|
| Coordination | Event-driven | Command-driven |
| Coupling | Loose | Tighter (to orchestrator) |
| Complexity | Distributed | Centralized |
| Failure Point | None | Orchestrator |
| Best For | Simple sagas | Complex workflows |

## 10.5 Commands in SAGA

From your notes, SAGA operations can be categorized as:

| Operation | Type | Example |
|-----------|------|---------|
| CREATE | Command | CreateOrder, CreatePayment |
| UPDATE | Command | UpdateOrderStatus |
| DELETE | Compensating | CancelOrder, RefundPayment |

## 10.6 Interview Point

> **Q: How do you handle distributed transactions in microservices?**
> A: Use the SAGA pattern. Each service performs its local transaction and publishes an event. If any step fails, compensating transactions roll back the changes. Choose Choreography for simple flows or Orchestration for complex workflows.

---

# 11. SCALING STRATEGIES

## 11.1 Vertical Scaling (Scale Up)

### Definition
Add more resources (CPU, RAM, Storage) to existing server.

```
BEFORE                          AFTER
┌─────────────────┐            ┌─────────────────┐
│  Server         │            │  Server         │
│  4 CPU cores    │    →       │  16 CPU cores   │
│  8 GB RAM       │            │  64 GB RAM      │
│  500 GB SSD     │            │  2 TB SSD       │
└─────────────────┘            └─────────────────┘
```

### Pros
- Simple to implement
- No application changes needed
- No distributed system complexity

### Cons
- Hardware limits (can't scale infinitely)
- Single point of failure
- Expensive (diminishing returns)
- Downtime during upgrade

## 11.2 Horizontal Scaling (Scale Out)

### Definition
Add more servers/instances to handle load.

```
BEFORE                          AFTER
                               ┌─────────────────┐
                               │  Server 1       │
┌─────────────────┐            ├─────────────────┤
│  Server         │    →       │  Server 2       │
└─────────────────┘            ├─────────────────┤
                               │  Server 3       │
                               └─────────────────┘
                                       │
                               [Load Balancer]
```

### Pros
- Near-infinite scalability
- Fault tolerance
- Cost-effective (commodity hardware)
- No downtime for scaling

### Cons
- Application must support it (stateless)
- Increased complexity
- Data consistency challenges
- Load balancing required

## 11.3 Comparison

| Aspect | Vertical | Horizontal |
|--------|----------|------------|
| Complexity | Low | High |
| Cost | High (per unit) | Lower (per unit) |
| Limit | Hardware limits | Near unlimited |
| Downtime | Required | Not required |
| SPOF | Yes | No |

## 11.4 Database Scaling

### Vertical Database Scaling
- Add more CPU/RAM to database server
- Limited by hardware
- Good for moderate scale

### Horizontal Database Scaling

#### 11.4.1 Read Replicas
```
                    ┌─────────────────┐
     WRITES ──────> │  MASTER         │
                    │  (Primary)      │
                    └────────┬────────┘
                             │ Replication
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
        ┌──────────┐  ┌──────────┐  ┌──────────┐
READS → │  SLAVE   │  │  SLAVE   │  │  SLAVE   │
        │(Replica) │  │(Replica) │  │(Replica) │
        └──────────┘  └──────────┘  └──────────┘
```

#### 11.4.2 Sharding
See [Section 16: Sharding & Partitioning](#16-sharding--partitioning)

---

# 12. CACHING STRATEGIES

## 12.1 Why Caching?

- **Reduce latency**: Cache access is faster than database
- **Reduce database load**: Fewer queries to database
- **Improve throughput**: Handle more requests

## 12.2 Cache Architecture

```
┌────────┐     ┌─────────┐     ┌──────────┐
│ Client │────>│  Cache  │────>│ Database │
└────────┘     │ (Redis) │     │          │
               └─────────┘     └──────────┘
                    │
                    ▼
              ┌──────────┐
              │ Cache    │
              │ HIT/MISS │
              └──────────┘
```

## 12.3 Caching Strategies

### 12.3.1 Cache-Aside (Lazy Loading)

```
1. Application checks cache
2. If HIT → Return from cache
3. If MISS → Query database
4. Store result in cache
5. Return to client
```

**Pros:**
- Only requested data is cached
- Cache failures don't break the system

**Cons:**
- Cache miss penalty (3 round trips)
- Data can become stale

### 12.3.2 Write-Through

```
1. Application writes to cache
2. Cache synchronously writes to database
3. Return success to client
```

**Pros:**
- Cache is always consistent with DB
- No stale data

**Cons:**
- Write latency (2 writes)
- Cache may have unused data

### 12.3.3 Write-Behind (Write-Back)

```
1. Application writes to cache
2. Cache acknowledges immediately
3. Cache asynchronously writes to database (batched)
```

**Pros:**
- Lowest write latency
- Batch writes improve DB performance

**Cons:**
- Risk of data loss
- More complex

### 12.3.4 Read-Through

```
1. Application reads from cache
2. If MISS → Cache fetches from database
3. Cache stores and returns data
```

**Pros:**
- Simpler application code
- Consistent read logic

## 12.4 Cache Eviction Policies

| Policy | Description | Use Case |
|--------|-------------|----------|
| **LRU** (Least Recently Used) | Remove least recently accessed | General purpose |
| **LFU** (Least Frequently Used) | Remove least frequently accessed | Popular items |
| **FIFO** | Remove oldest entry | Simple queue |
| **TTL** (Time To Live) | Remove after expiration | Time-sensitive data |

## 12.5 TTL (Time To Live)

From your notes: "Data will be removed after TTL expires"

- Set appropriate TTL based on data freshness requirements
- Too short: High cache miss rate
- Too long: Stale data

## 12.6 Cache Technologies

| Technology | Type | Best For |
|------------|------|----------|
| **Redis** | In-memory, distributed | Sessions, real-time data |
| **Memcached** | In-memory, distributed | Simple caching |
| **Caffeine** | In-process (Java) | Local caching |
| **Ehcache** | In-process (Java) | Local/distributed |

## 12.7 Cache Patterns in Microservices

```
┌─────────┐    ┌──────────┐    ┌───────────┐
│ Client  │───>│   API    │───>│  Service  │
└─────────┘    │ Gateway  │    └─────┬─────┘
               └──────────┘          │
                                     ▼
                              ┌───────────┐
                              │   Redis   │
                              │  (Cache)  │
                              └─────┬─────┘
                                    │
                                    ▼
                              ┌───────────┐
                              │ Database  │
                              └───────────┘
```

---

# 13. CONTENT DELIVERY NETWORK (CDN)

## 13.1 Definition

A CDN is a geographically distributed network of servers that cache and deliver content to users from the nearest location.

## 13.2 How CDN Works

```
                            ┌─────────────────────┐
                            │    ORIGIN SERVER    │
                            │   (Main Database)   │
                            └──────────┬──────────┘
                                       │
            ┌──────────────────────────┼──────────────────────────┐
            │                          │                          │
            ▼                          ▼                          ▼
   ┌─────────────────┐      ┌─────────────────┐      ┌─────────────────┐
   │   CDN Edge      │      │   CDN Edge      │      │   CDN Edge      │
   │   (US East)     │      │   (Europe)      │      │   (Asia)        │
   └────────┬────────┘      └────────┬────────┘      └────────┬────────┘
            │                        │                        │
            ▼                        ▼                        ▼
       [US Users]              [EU Users]              [Asian Users]
```

## 13.3 CDN Benefits

| Benefit | Description |
|---------|-------------|
| **Reduced Latency** | Content served from nearest location |
| **High Availability** | Multiple edge servers provide redundancy |
| **Scalability** | Handle traffic spikes |
| **Security** | DDoS protection, WAF |
| **Cost Savings** | Reduce origin server bandwidth |

## 13.4 What to Cache on CDN

| Cache | Don't Cache |
|-------|-------------|
| Static images | User-specific data |
| CSS/JS files | Real-time data |
| Videos | API responses (usually) |
| Fonts | Authenticated content |
| Static HTML | |

## 13.5 CDN Providers

- Cloudflare
- AWS CloudFront
- Akamai
- Google Cloud CDN
- Fastly

## 13.6 From Your Notes

> "CDN stores static data at each specific CDN from main Database"
> "It also improves security"

This accurately captures CDN's role in:
1. Distributing static content geographically
2. Providing security through DDoS mitigation

---

# 14. DATABASE REPLICATION

## 14.1 Definition

Database replication is the process of copying data from one database server (master/primary) to one or more servers (slaves/replicas).

## 14.2 Master-Slave Architecture

```
                    ┌─────────────────┐
     WRITES ──────> │     MASTER      │
                    │    (Primary)    │
                    └────────┬────────┘
                             │
                    Replication (Sync/Async)
                             │
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
        ┌──────────┐  ┌──────────┐  ┌──────────┐
READS → │  SLAVE   │  │  SLAVE   │  │  SLAVE   │
        │(Replica) │  │(Replica) │  │(Replica) │
        └──────────┘  └──────────┘  └──────────┘
```

## 14.3 Replication Types

### Synchronous Replication
- Master waits for slave to acknowledge
- **Strong consistency**
- Higher write latency
- Data is never lost

### Asynchronous Replication
- Master doesn't wait for slave
- **Eventual consistency**
- Lower write latency
- Risk of data loss if master fails before replication

### Semi-Synchronous
- Wait for at least one slave to acknowledge
- Balance between consistency and performance

## 14.4 Benefits of Replication

| Benefit | Description |
|---------|-------------|
| **High Availability** | Failover to replica if master fails |
| **Read Scaling** | Distribute read load across replicas |
| **Geographic Distribution** | Replicas in different regions |
| **Backup** | Replicas serve as live backups |

## 14.5 Replication Lag

The delay between a write on master and its availability on slave.

**Causes:**
- Network latency
- Slave processing speed
- High write volume

**Mitigation:**
- Read-your-writes consistency
- Monotonic reads
- Synchronous replication for critical data

---

# 15. MESSAGE QUEUES & EVENT-DRIVEN ARCHITECTURE

## 15.1 Why Message Queues?

- **Decoupling**: Services don't need to know about each other
- **Asynchronous Processing**: Don't wait for response
- **Load Leveling**: Handle traffic spikes
- **Reliability**: Messages persist until processed

## 15.2 Basic Architecture

```
┌──────────┐    ┌─────────────────┐    ┌──────────┐
│ Producer │───>│  MESSAGE QUEUE  │───>│ Consumer │
└──────────┘    │   (Broker)      │    └──────────┘
                └─────────────────┘
```

## 15.3 Messaging Patterns

### 15.3.1 Point-to-Point (Queue)

```
┌──────────┐    ┌─────────────────┐    ┌──────────┐
│ Producer │───>│     QUEUE       │───>│ Consumer │
└──────────┘    └─────────────────┘    └──────────┘

Each message consumed by ONE consumer only
```

### 15.3.2 Publish-Subscribe (Topic)

```
┌──────────┐    ┌─────────────────┐    ┌──────────┐
│ Publisher│───>│     TOPIC       │───>│Subscriber│
└──────────┘    │   Topic 1       │    └──────────┘
                │                 │    ┌──────────┐
                │                 │───>│Subscriber│
                └─────────────────┘    └──────────┘

Each message delivered to ALL subscribers
```

## 15.4 Exchange Types (RabbitMQ)

From your notes:

| Exchange Type | Routing |
|---------------|---------|
| **Direct** | Route by exact routing key |
| **Fanout** | Broadcast to all queues |
| **Topic** | Route by pattern matching |
| **Headers** | Route by message headers |

```
┌──────────┐     ┌──────────────┐     ┌─────────┐
│ Producer │────>│   EXCHANGE   │────>│ Queue 1 │
└──────────┘     │              │     └─────────┘
                 │  (Fanout/    │     ┌─────────┐
                 │   Direct/    │────>│ Queue 2 │
                 │   Topic)     │     └─────────┘
                 └──────────────┘
```

## 15.5 Message Queue Technologies

| Technology | Best For | Features |
|------------|----------|----------|
| **Kafka** | Event streaming, high throughput | Distributed log, replay |
| **RabbitMQ** | Traditional messaging | Flexible routing, AMQP |
| **Amazon SQS** | Simple queuing | Managed, serverless |
| **Amazon SNS** | Pub/Sub | Fan-out, push |
| **Redis Pub/Sub** | Simple pub/sub | In-memory, fast |

## 15.6 Kafka Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      KAFKA CLUSTER                          │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│   Topic: orders                                             │
│   ┌──────────────────────────────────────────────────────┐ │
│   │ Partition 0 │ Partition 1 │ Partition 2              │ │
│   │   [0][1][2] │   [0][1]    │   [0][1][2][3]           │ │
│   └──────────────────────────────────────────────────────┘ │
│                                                             │
│   Consumer Group: order-processors                          │
│   ┌───────────┐  ┌───────────┐  ┌───────────┐             │
│   │Consumer 1 │  │Consumer 2 │  │Consumer 3 │             │
│   │(Part 0)   │  │(Part 1)   │  │(Part 2)   │             │
│   └───────────┘  └───────────┘  └───────────┘             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

# 16. SHARDING & PARTITIONING

## 16.1 Definition

**Sharding** (Horizontal Partitioning): Splitting data across multiple database instances, where each shard holds a subset of the data.

## 16.2 Types of Partitioning

### Horizontal Partitioning (Sharding)
Divide rows across multiple tables/databases.

```
BEFORE: Single Users Table
┌────────────────────────────┐
│ id │ name    │ country     │
├────────────────────────────┤
│ 1  │ Alice   │ USA         │
│ 2  │ Bob     │ UK          │
│ 3  │ Charlie │ India       │
│ 4  │ David   │ USA         │
└────────────────────────────┘

AFTER: Sharded by Country
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│ Shard: USA      │  │ Shard: UK       │  │ Shard: India    │
├─────────────────┤  ├─────────────────┤  ├─────────────────┤
│ 1 │ Alice │ USA │  │ 2 │ Bob │ UK    │  │ 3 │Charlie│India│
│ 4 │ David │ USA │  └─────────────────┘  └─────────────────┘
└─────────────────┘
```

### Vertical Partitioning
Split columns across tables.

```
BEFORE: Single Users Table
┌───────────────────────────────────────────────────┐
│ id │ name    │ email          │ bio (large text)  │
└───────────────────────────────────────────────────┘

AFTER: Vertically Partitioned
┌─────────────────────────────┐  ┌────────────────────────┐
│ Users_Main                  │  │ Users_Profile          │
├─────────────────────────────┤  ├────────────────────────┤
│ id │ name    │ email        │  │ id │ bio (large text)  │
└─────────────────────────────┘  └────────────────────────┘
```

## 16.3 Sharding Strategies

### 16.3.1 Range-Based Sharding

```
Shard by User ID ranges:
Shard 1: users 1-1000
Shard 2: users 1001-2000
Shard 3: users 2001-3000
```

**Pros:** Simple, range queries efficient
**Cons:** Hotspots (newest users on one shard)

### 16.3.2 Hash-Based Sharding

```
shard_id = hash(user_id) % num_shards

Example:
hash(user_123) % 3 = 1  → Shard 1
hash(user_456) % 3 = 2  → Shard 2
```

**Pros:** Even distribution
**Cons:** Range queries across all shards, resharding is complex

### 16.3.3 Directory-Based Sharding

```
┌─────────────────────────────┐
│     LOOKUP SERVICE          │
│  user_id  │  shard_id       │
├─────────────────────────────┤
│  user_1   │  shard_2        │
│  user_2   │  shard_1        │
└─────────────────────────────┘
```

**Pros:** Flexible placement
**Cons:** Lookup service = SPOF, extra hop

### 16.3.4 Geographic Sharding

```
Shard by region:
US-East Shard: US East Coast users
EU Shard: European users
Asia Shard: Asian users
```

**Pros:** Low latency for users
**Cons:** Complex for global queries

## 16.4 Challenges of Sharding

| Challenge | Description | Solution |
|-----------|-------------|----------|
| **Joins** | Can't join across shards | Denormalization, application-level joins |
| **Resharding** | Adding/removing shards | Consistent hashing |
| **Hotspots** | Uneven data distribution | Better shard key selection |
| **Transactions** | Cross-shard transactions | SAGA pattern, eventual consistency |

## 16.5 From Your Notes

> "To solve joining problem, make table Normalized"

⚠️ **CORRECTION**:
- **Original Note**: "Normalize to solve join problem"
- **What's Technically Correct**: Actually, **denormalization** helps solve cross-shard join problems. By storing redundant data, you reduce the need for joins across shards.
- **Interview Relevance**: In distributed systems, denormalization is often preferred over normalization for performance reasons.

---

# 17. CONSISTENT HASHING

## 17.1 The Problem

With regular hash-based sharding:
```
shard_id = hash(key) % num_shards
```

When you add or remove a shard, **almost all keys need to be remapped**.

Example:
```
3 shards: hash(key) % 3 = 1  → Shard 1
4 shards: hash(key) % 4 = 2  → Shard 2 (different!)
```

This causes massive data movement = **rebalancing problem**.

## 17.2 Solution: Consistent Hashing

Place both servers and keys on a **hash ring** (0 to 2^32-1).

```
                    0
                    │
           S3 ──────┼────── S1
                   / \
                  /   \
                 /     \
                K2     K1
               /         \
              /           \
             S2 ──────────── 
```

**Rule**: Each key is assigned to the first server found clockwise.

## 17.3 How It Works

1. Hash servers to positions on ring: `hash(server_ip) → position`
2. Hash keys to positions on ring: `hash(key) → position`
3. Walk clockwise from key position to find responsible server

## 17.4 Adding/Removing Servers

**Adding Server S4:**
- Only keys between S3 and S4 need to be moved
- All other keys remain on same server

**Removing Server S2:**
- Only keys that were on S2 move to next server (S3)
- Minimal data movement

## 17.5 Virtual Nodes (VNodes)

**Problem**: With few physical servers, load distribution is uneven.

**Solution**: Each physical server has multiple virtual nodes on the ring.

```
Physical Server A → Virtual: A1, A2, A3, A4, A5
Physical Server B → Virtual: B1, B2, B3, B4, B5
Physical Server C → Virtual: C1, C2, C3, C4, C5
```

This ensures more uniform distribution.

## 17.6 Use Cases

| Use Case | Examples |
|----------|----------|
| **Distributed Caches** | Memcached, Redis Cluster |
| **Distributed Databases** | Cassandra, DynamoDB, Riak |
| **Load Balancers** | Consistent hash load balancing |
| **CDN** | Content distribution |

## 17.7 Interview Point

> **Q: How do you handle adding servers to a distributed cache without invalidating all cache?**
> A: Use Consistent Hashing. Place servers and keys on a hash ring. When a server is added, only keys between the new server and its predecessor need to move. Use virtual nodes for better load distribution.

---

# 18. URL SHORTENER SYSTEM DESIGN

## 18.1 Requirements

### Functional Requirements
- Given a long URL, generate a short URL
- Given a short URL, redirect to original URL
- Custom short URLs (optional)
- URL expiration (optional)

### Non-Functional Requirements
- High availability
- Low latency
- Short URL should be as short as possible

## 18.2 Capacity Estimation

From your notes:

**Assumptions:**
- 100M URLs per day
- 365 days = 36.5 billion URLs per year
- Read:Write ratio = 10:1

**Storage:**
- Each URL mapping: ~500 bytes
- 10 years: 36.5B × 10 × 500 bytes = **~180 TB**

## 18.3 Short URL Generation

### Approach 1: Hash Function (MD5/SHA)

```
Long URL → MD5 hash → 128 bits → Base62 encoding → Short URL

Problem: MD5 produces 128 bits (22+ characters in Base62)
         Too long for short URL
```

**Solution**: Take first 6-7 characters of hash

**Collision handling**: Check DB, if exists, generate new hash

### Approach 2: Base62 Encoding

Characters available: `[0-9, a-z, A-Z]` = 62 characters

| Length | Combinations |
|--------|--------------|
| 6 chars | 62^6 = 56.8 billion |
| 7 chars | 62^7 = 3.5 trillion |

For your notes' estimate of 36.5B URLs, **7 characters** is sufficient.

### Approach 3: Counter-Based ID

```
1. Get unique ID from ID generator
2. Convert ID to Base62
3. Store mapping

ID: 123456789
Base62: 8m0Kx
```

**Challenge**: Need distributed unique ID generator

## 18.4 ID Generation Strategies

### UUID
- 128 bits, globally unique
- Too long for short URL

### Database Auto-Increment
- Simple, sequential
- Single point of failure
- Doesn't scale

### Snowflake ID (Twitter)
```
┌─────────────────────────────────────────────────────────────────┐
│ 1 bit │    41 bits       │  10 bits     │    12 bits           │
│ Sign  │   Timestamp      │  Machine ID  │  Sequence Number     │
└─────────────────────────────────────────────────────────────────┘
```

### Zookeeper-Based Counter
From your notes: Use Zookeeper as distributed coordinator for unique ranges.

```
┌───────────────────────────────────────────────────────────────────┐
│                       ZOOKEEPER                                   │
│  Maintains ranges for each app server                            │
│  Server 1: 1-1000                                                │
│  Server 2: 1001-2000                                             │
│  Server 3: 2001-3000                                             │
└───────────────────────────────────────────────────────────────────┘
```

## 18.5 System Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                        URL SHORTENER SYSTEM                         │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌─────────┐     ┌──────────────┐     ┌────────────────────────┐  │
│  │ Client  │────>│ Load Balancer│────>│ Application Servers    │  │
│  └─────────┘     └──────────────┘     │ (URL Shortening Logic) │  │
│                                       └───────────┬────────────┘  │
│                                                   │               │
│                    ┌──────────────────────────────┼────────────┐ │
│                    │                              │            │ │
│                    ▼                              ▼            │ │
│              ┌──────────┐                  ┌───────────┐      │ │
│              │  Cache   │                  │ ID Gen    │      │ │
│              │ (Redis)  │                  │(Zookeeper)│      │ │
│              └────┬─────┘                  └───────────┘      │ │
│                   │                                            │ │
│                   ▼                                            │ │
│              ┌───────────────────────────────────────────────┐ │ │
│              │              DATABASE                         │ │ │
│              │  short_url (PK) │ long_url │ created_at │ TTL│ │ │
│              └───────────────────────────────────────────────┘ │ │
│                                                                 │ │
└─────────────────────────────────────────────────────────────────────┘
```

## 18.6 Database Schema

```sql
CREATE TABLE url_mapping (
    short_url VARCHAR(7) PRIMARY KEY,
    long_url VARCHAR(2048) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    user_id BIGINT,
    click_count INT DEFAULT 0
);

-- Index for expiration cleanup
CREATE INDEX idx_expires_at ON url_mapping(expires_at);
```

## 18.7 API Design

```
POST /api/shorten
Request: { "long_url": "https://example.com/very/long/path" }
Response: { "short_url": "https://short.url/abc123" }

GET /{short_code}
Response: 301 Redirect to long_url
```

---

# 19. CAPACITY ESTIMATION

## 19.1 Back-of-the-Envelope Estimation

From your notes: "Don't spend too much time (2-3 min)"

## 19.2 Number Scale Reference

| Prefix | Value | Example |
|--------|-------|---------|
| Thousand (K) | 10^3 | 1,000 |
| Million (M) | 10^6 | 1,000,000 |
| Billion (B) | 10^9 | 1,000,000,000 |
| Trillion (T) | 10^12 | 1,000,000,000,000 |
| Quadrillion (Q) | 10^15 | |

## 19.3 Storage Scale Reference

| Unit | Size |
|------|------|
| Byte | 1 character (ASCII) |
| KB (Kilobyte) | 10^3 bytes |
| MB (Megabyte) | 10^6 bytes |
| GB (Gigabyte) | 10^9 bytes |
| TB (Terabyte) | 10^12 bytes |
| PB (Petabyte) | 10^15 bytes |

## 19.4 Useful Numbers

| Resource | Value |
|----------|-------|
| 1 second | 1,000 ms |
| 1 day | 86,400 seconds ≈ 100K seconds |
| 1 month | 2.5M seconds |
| 1 year | 30M seconds |

## 19.5 Example: Facebook Estimation

From your notes:

**Traffic Estimation:**
- DAU (Daily Active Users): Assume 1 billion
- Posts per user per day: 5
- Total posts per day: 5 billion

**Storage Estimation:**
- Average post size: 500 bytes
- Daily storage: 5B × 500 bytes = 2.5 TB/day
- Yearly storage: 2.5 TB × 365 = ~900 TB/year

**RAM Estimation:**
- Cache 20% of daily posts
- Posts to cache: 1B (20% of 5B)
- Cache size: 1B × 500 bytes = 500 GB
- With 64GB per server: ~8 cache servers needed

## 19.6 Estimation Template

```
1. TRAFFIC ESTIMATION
   - DAU/MAU: ___
   - Actions per user per day: ___
   - Total requests per day: ___
   - QPS (Queries Per Second): Total / 86400

2. STORAGE ESTIMATION
   - Data per action: ___ bytes
   - Daily new data: ___
   - Yearly data: ___
   - 5-10 year projection: ___

3. BANDWIDTH ESTIMATION
   - Incoming: ___ KB/s
   - Outgoing: ___ KB/s

4. MEMORY ESTIMATION
   - Cache ratio: 20% (80-20 rule)
   - Cache size: ___
   - Servers needed: ___
```

---

# 20. DATABASE TYPES DEEP DIVE

## 20.1 Relational Databases

### Properties
- Structured schema (tables, rows, columns)
- ACID compliant
- SQL query language
- Relationships via foreign keys
- Complex queries with JOINs

### When to Use
- Complex transactions
- Need for strong consistency
- Well-defined schema
- Complex queries required

### Examples: PostgreSQL, MySQL, Oracle

## 20.2 Key-Value Stores

From your notes: "Data stored in Key-Value form"

### Properties
- Simple get/set operations
- No complex queries
- Very fast lookups
- Horizontally scalable
- Distributed by nature

### When to Use
- Session storage
- Caching
- Real-time data
- Shopping cart

### Examples: Redis, DynamoDB, Memcached

## 20.3 Document Databases

From your notes: "You can query through value"

### Properties
- Stores JSON/BSON documents
- Flexible schema
- Can query within documents
- Supports indexing

### When to Use
- Content management
- User profiles
- Catalog data
- Mobile app backends

### Examples: MongoDB, CouchDB

## 20.4 Column-Family Stores

From your notes: "Column-Value format, ordering built-in"

### Properties
- Data organized by columns
- Excellent for aggregations
- High write throughput
- Time-ordered data

### When to Use
- Time-series data
- IoT data
- Analytics
- Event logging

### Examples: Cassandra, HBase, ScyllaDB

## 20.5 Graph Databases

From your notes: "Node and Relationships"

### Properties
- Nodes and edges (relationships)
- Traverse relationships efficiently
- Pattern matching queries

### When to Use
- Social networks
- Recommendation engines
- Fraud detection
- Knowledge graphs

### Examples: Neo4j, Amazon Neptune

## 20.6 Decision Matrix

| Need | Recommended |
|------|-------------|
| Strong consistency + complex queries | PostgreSQL, MySQL |
| High-speed caching | Redis |
| Flexible schema + rich queries | MongoDB |
| Time-series + high writes | Cassandra |
| Connected data | Neo4j |
| Simple key lookup | DynamoDB |

---

# 21. CHAT SYSTEM DESIGN

## 21.1 Requirements

### Functional Requirements
- One-on-one messaging
- Group messaging
- Online/offline status
- Read receipts
- Last seen
- Media sharing

### Non-Functional Requirements
- Low latency
- High availability
- Message ordering
- At-least-once delivery

## 21.2 Protocols for Real-Time Communication

From your notes:

### HTTP Polling
```
Client ───[Request]───> Server
Client <──[Response]─── Server
(Repeat every few seconds)
```
**Cons**: High latency, wasteful of resources

### Long Polling
```
Client ───[Request]───> Server
                        (Server holds connection)
Client <──[Response]─── Server (when data available)
```
**Cons**: Server resource consumption, timeouts

### WebSockets
```
Client <────[Full Duplex]────> Server
(Persistent bidirectional connection)
```
**Pros**: Real-time, low latency, efficient
**Used by**: WhatsApp, Slack, Discord

## 21.3 System Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                        CHAT SYSTEM ARCHITECTURE                     │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│   ┌─────────┐    ┌──────────────────────────────────────────────┐  │
│   │ Client  │◄──►│              API GATEWAY                     │  │
│   │ (Mobile)│    │  - Authentication                            │  │
│   └─────────┘    │  - Rate Limiting                             │  │
│                  └───────────────────┬──────────────────────────┘  │
│                                      │                              │
│          ┌───────────────────────────┼───────────────────────────┐ │
│          │                           │                           │ │
│          ▼                           ▼                           ▼ │
│   ┌─────────────┐          ┌─────────────────┐          ┌────────┐│
│   │ User Service│          │ WebSocket Server│          │ Media  ││
│   │             │          │ (Chat Servers)  │          │ Service││
│   └──────┬──────┘          └────────┬────────┘          └───┬────┘│
│          │                          │                       │     │
│          ▼                          ▼                       ▼     │
│   ┌─────────────┐          ┌─────────────────┐          ┌────────┐│
│   │ User DB     │          │ Message Queue   │          │  S3    ││
│   │ (Postgres)  │          │ (Kafka)         │          │        ││
│   └─────────────┘          └────────┬────────┘          └────────┘│
│                                     │                              │
│                                     ▼                              │
│                            ┌─────────────────┐                    │
│                            │ Message Storage │                    │
│                            │ (Cassandra)     │                    │
│                            └─────────────────┘                    │
│                                                                     │
│   ┌─────────────────────────────────────────────────────────────┐ │
│   │              USER-SERVER MAPPING SERVICE                    │ │
│   │  (Which WebSocket server is user connected to?)            │ │
│   │  User A → WS Server 1                                      │ │
│   │  User B → WS Server 3                                      │ │
│   └─────────────────────────────────────────────────────────────┘ │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

## 21.4 Message Flow

From your notes: "Send message from sender, to message type"

### One-on-One Message Flow

```
1. User A connects to WebSocket Server 1
2. User A sends message to User B
3. Server 1 looks up: Where is User B connected?
4. If User B on Server 3:
   - Push message to Server 3 via Message Queue
   - Server 3 delivers to User B
5. Store message in Cassandra
6. Send ACK to User A
```

### Group Message Flow

```
1. User sends message to Group
2. Lookup all group members
3. For each member:
   - Find their WebSocket server
   - Route message through queue
4. Store message once with group_id
```

## 21.5 Database Schema

From your notes: "Partition table based on unique IDs"

### Messages Table (Cassandra)

```
CREATE TABLE messages (
    chat_id UUID,
    message_id TIMEUUID,  -- Time-based for ordering
    sender_id UUID,
    content TEXT,
    message_type TEXT,    -- text, image, video
    created_at TIMESTAMP,
    PRIMARY KEY (chat_id, message_id)
) WITH CLUSTERING ORDER BY (message_id DESC);
```

**Partitioning Strategy:**
- Partition by `chat_id` (conversation ID)
- Messages within partition ordered by `message_id` (time-based)

### User-Server Mapping (Redis)

```
user:123:server -> "ws-server-5"
user:123:status -> "online"
user:123:last_seen -> "2024-01-15T10:30:00"
```

## 21.6 Online/Offline Status

From your notes: "Last Seen, Online/Offline"

### Heartbeat Mechanism
1. Client sends heartbeat every 30 seconds
2. Server updates Redis: `user:123:last_heartbeat = now()`
3. If no heartbeat for 60 seconds → mark offline
4. Push status change to friends

## 21.7 Message Ordering

From your notes: "Timestamp ordering built-in"

- Use TIMEUUID (time-based UUID) for message IDs
- Cassandra clustering order: `DESC` for newest first
- Vector clocks for conflict resolution in distributed systems

---

# 22. DATA VERSIONING & VECTOR CLOCKS

## 22.1 The Problem

In distributed systems with multiple replicas:
- Same data can be modified on different nodes
- How do we track versions and detect conflicts?

From your notes: "Data Versioning - because DB, as it can go on any server"

## 22.2 Vector Clocks

### Definition
A vector clock is a list of `(node, counter)` pairs that tracks the causal history of data.

### How It Works

```
Initial: [A:0, B:0, C:0]

Node A writes:  [A:1, B:0, C:0]
Node B reads from A, then writes: [A:1, B:1, C:0]
Node A writes again: [A:2, B:0, C:0]

Now we have:
  Version 1: [A:1, B:1, C:0]  -- from B
  Version 2: [A:2, B:0, C:0]  -- from A

These are CONCURRENT (conflict) - neither dominates the other
```

### From Your Notes

```
Original: [us-cart]-1
After writes:
  [us-cart]-1 → Node A update → [us-cart]-2
  [us-cart]-1 → Node B update → [us-cart]-2

Conflict detected: same version updated independently
```

### Resolution Strategies

1. **Last Write Wins (LWW)**: Use timestamp to pick winner
2. **Application-Level**: Return both versions, let application resolve
3. **CRDTs**: Use conflict-free replicated data types

## 22.3 Interview Point

> **Q: How do you handle conflicting writes in a distributed database?**
> A: Use vector clocks to track causality. Each update increments the node's counter. Compare vectors to determine if updates are:
> - **Causally related**: One version dominates
> - **Concurrent**: Conflict - need resolution strategy

---

# 23. FAULT-TOLERANT MICROSERVICES

## 23.1 Definition

From your notes:
> "Fault Tolerant Microservice is a service which continues to work even when downstream system fails. Instead of crashing or Cascading failures, it handles the failure gracefully."

## 23.2 Why It's Required

In microservices:
- Services communicate over the network
- One service's unavailability can bring down the whole system
- This is called **CASCADING FAILURE**

### Example Scenario

```
Product Service becomes slow (60 seconds to respond)
↓
Order Service calls Product Service → Thread blocked for 60s
↓
Burst of requests → All Order Service threads blocked
↓
Order Service clients also get blocked
↓
Services run out of threads → Start rejecting requests
↓
CASCADING FAILURE
```

## 23.3 Fault Tolerance Patterns

Apply in this order (from your notes):

```
RateLimiter → Bulkhead → TimeLimiter → CircuitBreaker → Retry
```

**Why this order?**
- Apply RateLimiter before Retry
- Retry logic shouldn't overwhelm already limited traffic

## 23.4 Overview Table

| Pattern | Purpose | Protects From |
|---------|---------|---------------|
| **Rate Limiter** | Limit requests in time window | Traffic spikes, DDoS |
| **Bulkhead** | Limit concurrent calls | Resource exhaustion |
| **Time Limiter** | Set timeout on calls | Hanging requests |
| **Circuit Breaker** | Stop calls to failing service | Cascading failures |
| **Retry** | Retry failed calls | Transient failures |

---

# 24. RATE LIMITING

## 24.1 Definition

Controls the number of requests allowed to a microservice in a given time window. Protects system from traffic spikes (like DDoS attacks).

## 24.2 Algorithms

### 24.2.1 Fixed Window Counter

```
┌─────────────────────────────────────────────────────────────┐
│ Window 1 (0-10sec)          │ Window 2 (10-20sec)          │
│ ████░░░░░░                  │ ░░░░░░░░░░                   │
│ Count: 4/5                  │ Count: 0/5                   │
└─────────────────────────────────────────────────────────────┘
```

**How it works:**
1. Count requests in fixed time window
2. If count exceeds limit, reject
3. Counter resets at window boundary

**Problem (from your notes):**
> "Traffic at the edge (end of window 1, start of window 2) can cause spike"

```
Window 1: ....[5 requests]
Window 2: [5 requests]....
          ↑
          10 requests in 1 second!
```

### 24.2.2 Sliding Log

```
┌─────────────────────────────────────────────────────────────┐
│ Timestamps: [1.2s, 3.5s, 5.1s, 7.8s, 9.2s]                 │
│ Window: 10 seconds                                          │
│ Limit: 5                                                    │
│                                                             │
│ New request at 11.5s:                                       │
│ Remove timestamps older than 11.5-10=1.5s                   │
│ Remaining: [3.5s, 5.1s, 7.8s, 9.2s] = 4 requests           │
│ 4 < 5, so ALLOWED                                          │
└─────────────────────────────────────────────────────────────┘
```

**Pros:** Accurate, no edge case issues
**Cons:** Memory (store all timestamps), cleanup overhead

### 24.2.3 Sliding Window Counter (Sub-Windows)

```
┌─────────────────────────────────────────────────────────────┐
│ Main Window: 10sec, Sub-Window: 2sec, Limit: 5              │
│                                                             │
│ SW1(0-2) │ SW2(2-4) │ SW3(4-6) │ SW4(6-8) │ SW5(8-10)     │
│    1     │    1     │    1     │    1     │    0          │
│                                                             │
│ Request at 9th sec:                                         │
│ Sum = 1+1+1+1+0 = 4 < 5, ALLOWED                           │
└─────────────────────────────────────────────────────────────┘
```

**Pros:** Good balance of accuracy and memory
**Cons:** More complex than fixed window

### 24.2.4 Sliding Window Counter (Weighted)

```
┌─────────────────────────────────────────────────────────────┐
│ Previous Window (0-10sec): 5 requests                       │
│ Current Window (10-20sec): 0 requests                       │
│                                                             │
│ Request at 11th second:                                     │
│ - 10% into current window                                   │
│ - Count = Current + (90% × Previous)                        │
│ - Count = 0 + (0.9 × 5) = 4.5 ≈ 5                          │
│ - Limit reached, REJECT                                     │
└─────────────────────────────────────────────────────────────┘
```

**Pros:** Simple, low memory
**Cons:** Assumes uniform distribution

### 24.2.5 Token Bucket

```
┌─────────────────────────────────────────────────────────────┐
│                    TOKEN BUCKET                              │
│                                                             │
│    ┌─────────┐                                              │
│    │ Refiller│ → Adds tokens at fixed rate                  │
│    └────┬────┘                                              │
│         ▼                                                    │
│    ┌─────────┐                                              │
│    │ Bucket  │ ← Capacity = 10 tokens                       │
│    │ ████░░░ │   Current = 4 tokens                         │
│    └────┬────┘                                              │
│         │                                                    │
│         ▼                                                    │
│    [Request] → Takes 1 token → If no token, REJECT          │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Pros:** Allows controlled bursts
**Cons:** Burst possible if bucket is full

### 24.2.6 Leaky Bucket

```
┌─────────────────────────────────────────────────────────────┐
│                    LEAKY BUCKET                              │
│                                                             │
│    [Requests] → Queue (fixed size)                          │
│                      │                                       │
│                      ▼                                       │
│                 ┌─────────┐                                 │
│                 │ Bucket  │                                 │
│                 │ ████░░░ │                                 │
│                 └────┬────┘                                 │
│                      │ Leak at constant rate                │
│                      ▼                                       │
│                 [Processed]                                 │
│                                                             │
│ If bucket full → REJECT (overflow)                          │
└─────────────────────────────────────────────────────────────┘
```

**Pros:** Smooths out bursts
**Cons:** Latency (requests wait in queue)

## 24.3 Comparison Table

| Algorithm | Memory | Accuracy | Burst Handling |
|-----------|--------|----------|----------------|
| Fixed Window | Low | Low | Poor |
| Sliding Log | High | High | Good |
| Sliding Window (Sub) | Medium | Medium | Good |
| Sliding Window (Weighted) | Low | Medium | Fair |
| Token Bucket | Low | Medium | Allows controlled burst |
| Leaky Bucket | Medium | High | Smooths bursts |

## 24.4 Implementation with Resilience4j

From your notes:

```java
// Resilience4j uses Token Bucket by default

@Component
public class OrderService {
    
    @Autowired
    ProductClient productClient;
    
    @RateLimiter(name = "productRateLimiter", 
                 fallbackMethod = "rateLimitedFallback")
    public void invokeProductAPI(String id) {
        String response = productClient.getProductById(id);
        System.out.println("Response: " + response);
    }
    
    public void rateLimitedFallback(String id, Throwable t) {
        System.out.println("Rate limit exceeded. Try later");
    }
}
```

**application.properties:**
```properties
# 2 tokens refilled every 10 seconds
# Wait 1 second before rejecting
resilience4j.ratelimiter.instances.productRateLimiter.limitForPeriod=2
resilience4j.ratelimiter.instances.productRateLimiter.limitRefreshPeriod=10s
resilience4j.ratelimiter.instances.productRateLimiter.timeoutDuration=1s
```

## 24.5 Custom Rate Limiter with AOP

From your notes:

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRateLimiter {
    int limit();
    int windowInSeconds();
}

@Aspect
@Component
public class RateLimiterAspect {
    
    @Around("@annotation(customRateLimiter)")
    public Object rateLimit(ProceedingJoinPoint pjp,
                           CustomRateLimiter customRateLimiter) 
                           throws Throwable {
        // Custom rate limiting logic
        return pjp.proceed();
    }
}

// Usage
@CustomRateLimiter(limit = 5, windowInSeconds = 60)
public String getProducts() {
    return "List of products";
}
```

---

# 25. SERVICE DISCOVERY

## 25.1 The Problem

From your notes:
- Can't hardcode URLs of service instances
- Not scalable or feasible
- Problems with hardcoding:
  1. **Single Point of Failure**: If instance goes down, no failover
  2. **No Load Balancing**: One instance overloaded
  3. **Tight Coupling**: Need code change to update URL
  4. **Testing Difficulty**: Different URLs per environment

## 25.2 Solution: Service Discovery (Eureka)

### Eureka Server
Acts like a **phonebook**. Stores info about all registered services:
- Service name
- Instance ID
- IP address
- Port number
- Health status

### Eureka Client
- **Registers** itself with server
- **Discovers** other services via server

## 25.3 Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                      EUREKA SERVER                                  │
│                      (Port 8761)                                    │
│                                                                     │
│  Registry:                                                          │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │ Service        │ Instance ID            │ IP:Port │ Status  │   │
│  ├─────────────────────────────────────────────────────────────┤   │
│  │ product-service│ product-service-1      │ x.x.x.1 │ UP      │   │
│  │ product-service│ product-service-2      │ x.x.x.2 │ UP      │   │
│  │ order-service  │ order-service-1        │ x.x.x.3 │ UP      │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                     │
└──────────────────────────────┬──────────────────────────────────────┘
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
        ▼                      ▼                      ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│ Product       │    │ Product       │    │ Order         │
│ Service #1    │    │ Service #2    │    │ Service       │
│ (Port 8082)   │    │ (Port 8083)   │    │ (Port 8081)   │
└───────────────┘    └───────────────┘    └───────────────┘
```

## 25.4 Setup Eureka Server

**pom.xml:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

**Main Application:**
```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

**application.properties:**
```properties
spring.application.name=eureka-server
server.port=8761

# Server doesn't register itself
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

## 25.5 Setup Eureka Client

**pom.xml:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

**application.properties:**
```properties
server.port=8082
spring.application.name=product-service

eureka.client.service-url.defaultZone=http://localhost:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
```

## 25.6 Service Invocation with Discovery

### Using RestTemplate:
```java
@Autowired
DiscoveryClient discoveryClient;

public void callProductAPI(String id) {
    List<ServiceInstance> instances = 
        discoveryClient.getInstances("product-service");
    
    URI uri = instances.get(0).getUri();
    
    RestTemplate restTemplate = new RestTemplate();
    String response = restTemplate.getForObject(
        uri + "/products/" + id, String.class);
}
```

### Using FeignClient:
```java
@FeignClient(name = "product-service")
public interface ProductClient {
    
    @GetMapping("/products/{id}")
    String getProductById(@PathVariable("id") String id);
}
```

## 25.7 How Eureka Knows Service Status

### 1. De-registration Request
- Client sends de-registration on graceful shutdown
- Server marks client as DOWN

### 2. Heartbeat
```
Client ──[heartbeat]──> Server (every 30 seconds by default)
```

**Configuration:**
```properties
# Client sends heartbeat every 60 seconds
eureka.instance.lease-renewal-interval-in-seconds=60

# Server waits this long before evicting
eureka.instance.lease-expiration-duration-in-seconds=90
```

**Server configuration:**
```properties
# Allow server to remove clients when heartbeat missed
eureka.server.enable-self-preservation=false

# How often eviction runs
eureka.server.eviction-interval-timer-in-ms=6000
```

## 25.8 Data Storage

Eureka stores data **in-memory**:
```
Map<String, Lease<InstanceInfo>>

Key: appName/instanceId
     e.g., PRODUCT-SERVICE/192.157.2.27:product-service:8082

Value: InstanceInfo object
       - Instance ID
       - App name
       - IP, hostname, port
       - Status (UP, DOWN)
       - Last renewed timestamp
       - Lease duration
```

## 25.9 High Availability (Cluster)

From your notes: "Single Eureka Server is Single Point of Failure"

**Solution: 3-node cluster**

```
Eureka-1 (8761) ←→ Eureka-2 (8762)
       ↘       ↙
      Eureka-3 (8763)
```

**Each server's application.properties:**
```properties
# Eureka-1
spring.application.name=eureka-server
server.port=8761
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.defaultZone=\
    http://localhost:8762/eureka/,http://localhost:8763/eureka/
```

**Client configuration:**
```properties
eureka.client.service-url.defaultZone=\
    http://eureka-1:8761/eureka,\
    http://eureka-2:8762/eureka,\
    http://eureka-3:8763/eureka
```

## 25.10 Caching

From your notes: "Eureka Server does not get called for every request"

- Client fetches registry at startup
- Caches it locally
- Refreshes periodically (default 30 seconds)

```properties
eureka.client.registry-fetch-interval-seconds=30
```

**Trade-off**: Local cache can be stale → May call dead instance

---

# 26. CLIENT-SIDE LOAD BALANCING

## 26.1 Types of Load Balancers

| Type | Description | Examples |
|------|-------------|----------|
| **Server-Side** | Centralized, separate service | Nginx, ELB, HAProxy |
| **Client-Side** | Built into client, uses library | Spring Cloud LoadBalancer, Ribbon |

## 26.2 Client-Side Load Balancing with RestTemplate

**Before (Manual):**
```java
@Autowired
DiscoveryClient discoveryClient;

public void callProductAPI(String id) {
    List<ServiceInstance> instances = 
        discoveryClient.getInstances("product-service");
    
    // Manual selection
    URI uri = instances.get(0).getUri();
    
    String response = restTemplate.getForObject(
        uri + "/products/" + id, String.class);
}
```

**With Spring Cloud LoadBalancer:**
```java
@Configuration
public class Config {
    
    @Bean
    @LoadBalanced  // Enables client-side load balancing
    public RestTemplate restTemplateObj() {
        return new RestTemplate();
    }
}

@RestController
public class OrderController {
    
    @Autowired
    RestTemplate restTemplate;
    
    @Value("${product.service.baseurl}")
    String productBaseURL;
    
    @GetMapping("/{id}")
    public void callProductAPI(@PathVariable String id) {
        // URL uses service name, not hostname
        String response = restTemplate.getForObject(
            productBaseURL + "/products/" + id, String.class);
    }
}
```

**application.properties:**
```properties
product.service.baseurl=http://product-service
```

## 26.3 Load Balancing Algorithms

### Built-in Algorithms
| Algorithm | Description |
|-----------|-------------|
| **RoundRobin** (default) | Rotate through instances sequentially |
| **Random** | Random instance selection |

### Custom Configuration

```java
@SpringBootApplication
@EnableFeignClients
@LoadBalancerClient(
    name = "product-service",
    configuration = LoadBalancerProductClientConfig.class
)
public class OrderServiceApplication { }

@Configuration
public class LoadBalancerProductClientConfig {
    
    @Bean
    public ReactorLoadBalancer<ServiceInstance> productClientLoadBalancer(
            LoadBalancerClientFactory factory) {
        
        return new RandomLoadBalancer(
            factory.getLazyProvider(
                "product-service",
                ServiceInstanceListSupplier.class
            ),
            "product-service"
        );
    }
}
```

### Different Algorithms for Different Services

```java
@LoadBalancerClients(
    defaultConfiguration = LoadBalancerGlobalConfig.class,
    value = {
        @LoadBalancerClient(
            name = "product-service",
            configuration = LoadBalancerProductClientConfig.class
        )
    }
)
public class OrderServiceApplication { }
```

### Custom Load Balancer Implementation

```java
public class MyCustomLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    
    private final ObjectProvider<ServiceInstanceListSupplier> suppliers;
    private final String serviceId;
    
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        return suppliers
            .getIfAvailable()
            .get()
            .next()
            .map(instances -> {
                if (instances.isEmpty()) {
                    return new EmptyResponse();
                }
                // Custom selection logic
                return new DefaultResponse(instances.get(0));
            });
    }
}
```

## 26.4 FeignClient Load Balancing

FeignClient handles load balancing automatically:

```java
@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/products/{id}")
    String getProductById(@PathVariable("id") String id);
}
```

Need load balancer dependency:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

---

# 27. BULKHEAD PATTERN

## 27.1 Difference: Rate Limiter vs Bulkhead

From your notes:

| Aspect | Rate Limiter | Bulkhead |
|--------|--------------|----------|
| **Protects from** | Incoming traffic | Downstream services |
| **Controls** | Requests per time window | Concurrent calls |
| **Purpose** | Limit clients | Limit resource allocation |

## 27.2 Use Cases

### Use Case 1: Lightweight Downstream Service

Product Service can only handle 3 concurrent requests:
```
Order Service ──[max 3 concurrent]──> Product Service
```

**Solution**: Semaphore Bulkhead

### Use Case 2: Slow Downstream Service

Two APIs in Order Service:
- API 1 → Product Service (fast, 100ms)
- API 2 → Payment Service (slow, 5 seconds)

Problem: Spike in API 2 traffic → All threads blocked by Payment Service → API 1 also unavailable

**Solution**: Thread Pool Bulkhead

## 27.3 Semaphore Bulkhead

Limits concurrent calls using a counter (semaphore).

```java
@Component
public class OrderService {
    
    @Autowired
    ProductClient productClient;
    
    @Bulkhead(
        name = "productService",
        type = Bulkhead.Type.SEMAPHORE,
        fallbackMethod = "productFallback"
    )
    public void invokeProductAPI(String id) {
        String response = productClient.getProductById(id);
    }
    
    public void productFallback(String id, Throwable t) {
        System.out.println("Too many concurrent requests");
    }
}
```

**application.properties:**
```properties
# Only 2 concurrent calls allowed
resilience4j.bulkhead.instances.productService.maxConcurrentCalls=2

# Don't wait, reject immediately if no slots
resilience4j.bulkhead.instances.productService.maxWaitDuration=0
```

## 27.4 Thread Pool Bulkhead

Assigns a **dedicated thread pool** for each downstream service.

```java
@Component
public class OrderService {
    
    @Bulkhead(
        name = "productService",
        type = Bulkhead.Type.THREADPOOL,
        fallbackMethod = "productFallback"
    )
    public CompletableFuture<String> invokeProductAPI(String id) {
        return CompletableFuture.completedFuture(
            productClient.getProductById(id)
        );
    }
    
    public CompletableFuture<String> productFallback(String id, Throwable t) {
        return CompletableFuture.completedFuture("Product Service is busy");
    }
}
```

**application.properties:**
```properties
# Max 3 threads in pool
resilience4j.thread-pool-bulkhead.instances.productService.coreThreadPoolSize=3
resilience4j.thread-pool-bulkhead.instances.productService.maxThreadPoolSize=3

# Queue capacity for additional requests
resilience4j.thread-pool-bulkhead.instances.productService.queueCapacity=2
```

**Behavior:**
- Threads: 3
- Queue: 2
- Total capacity: 5 requests
- 6th request onwards: rejected

## 27.5 Comparison

| Aspect | Semaphore | Thread Pool |
|--------|-----------|-------------|
| **Isolation** | Shared threads | Dedicated pool |
| **Use Case** | Limit concurrency | Isolate slow services |
| **Return Type** | Normal | CompletableFuture |
| **Blocking** | Yes | Async |

---

# 28. TIME LIMITER

## 28.1 Purpose

Prevents async calls from hanging indefinitely.

From your notes:
> "Time Limiter is non-blocking in Resilience4j. Mainly designed for asynchronous operations (Mono, Flux, CompletableFuture)."

## 28.2 For Blocking Calls

Use FeignClient/RestTemplate timeout configuration:

```yaml
feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000
```

## 28.3 For Reactive Calls

```java
@TimeLimiter(name = "productService", fallbackMethod = "timeoutFallback")
public Mono<String> getProduct(String id) {
    return webClient.get()
        .uri("/products/" + id)
        .retrieve()
        .bodyToMono(String.class);
}
```

---

# 29. INTERVIEW QUICK REFERENCE

## 29.1 Common Interview Questions

### Scaling
1. **Q**: How would you scale a database?
   **A**: Read replicas for read scaling, sharding for write scaling, caching layer to reduce load

2. **Q**: Horizontal vs Vertical scaling?
   **A**: Vertical = more resources to one server (limited), Horizontal = more servers (scalable, complex)

### CAP Theorem
3. **Q**: Can you have all three CAP properties?
   **A**: No, during network partition you must choose between C and A

4. **Q**: When to choose AP vs CP?
   **A**: AP for social media (availability), CP for banking (consistency)

### Microservices
5. **Q**: How to handle distributed transactions?
   **A**: SAGA pattern - either Choreography (event-driven) or Orchestration (central coordinator)

6. **Q**: How to migrate monolith to microservices?
   **A**: Strangler Fig Pattern - gradual migration with coexistence

### Caching
7. **Q**: What caching strategy would you use?
   **A**: Cache-aside for read-heavy, Write-through for consistency, Write-behind for write-heavy

8. **Q**: How to handle cache invalidation?
   **A**: TTL, event-based invalidation, write-through cache

### Load Balancing
9. **Q**: L4 vs L7 load balancing?
   **A**: L4 = TCP level (faster, less intelligent), L7 = HTTP level (content-aware routing)

### Fault Tolerance
10. **Q**: How to prevent cascading failures?
    **A**: Circuit breaker, bulkhead isolation, timeouts, retry with backoff

## 29.2 Numbers to Remember

| Item | Value |
|------|-------|
| SSD IOPS | ~100K |
| HDD IOPS | ~100-200 |
| Memory read | ~100 ns |
| SSD read | ~100 μs |
| HDD read | ~10 ms |
| Network round trip (same DC) | ~0.5 ms |
| Network round trip (cross-continent) | ~100 ms |

## 29.3 Common Trade-offs

| Trade-off | When to favor left | When to favor right |
|-----------|-------------------|---------------------|
| Consistency vs Availability | Financial transactions | Social media |
| Latency vs Throughput | Real-time gaming | Batch processing |
| Scalability vs Simplicity | Large scale | Small team/app |
| SQL vs NoSQL | Complex queries | Flexibility/scale |

## 29.4 Design Checklist

For any system design question, cover:

1. **Requirements** (Functional & Non-functional)
2. **Capacity Estimation** (Traffic, Storage, Bandwidth)
3. **High-Level Design** (Components, Data flow)
4. **Database Design** (Schema, Sharding strategy)
5. **API Design** (Endpoints, Request/Response)
6. **Deep Dive** (Critical components)
7. **Scaling** (How to handle 10x, 100x growth)
8. **Trade-offs** (What you're sacrificing)

---

# APPENDIX: CORRECTIONS FROM ORIGINAL NOTES

## Correction 1: Normalization vs Denormalization

**Original Note**: "To solve joining problem, make table Normalized"

⚠️ **CORRECTION**:
- **Wrong**: Normalization helps with joins
- **Correct**: In distributed systems, **denormalization** reduces cross-shard joins by storing redundant data
- **Why it matters**: Interviewers expect you to know that traditional normalization doesn't work well in distributed databases

## Correction 2: CAP Theorem - Changes Propagation

**Original Note**: "Changes must be done at each place in one shot"

⚠️ **CORRECTION**:
- **Wrong**: Changes happen instantaneously everywhere
- **Correct**: 
  - In **CP systems**: System blocks until replication completes (synchronous)
  - In **AP systems**: Changes propagate eventually (asynchronous)
- **Why it matters**: Understanding sync vs async replication is crucial for designing consistent systems

## Correction 3: BASE Soft State

**Original Note**: Limited explanation of "Soft State"

⚠️ **CLARIFICATION**:
- **Soft State** means the state of the system may change over time even without input, due to eventual consistency propagation
- Example: A read may return stale data that later updates itself

---

# END OF HANDBOOK

**Total Topics Covered**: 28 major sections with detailed sub-topics
**Source**: Handwritten HLD notes PDF (185 pages)
**Purpose**: Java/Senior Software Engineer HLD Interview Preparation

---

*Generated from your handwritten notes with corrections and enhancements for interview preparation.*
