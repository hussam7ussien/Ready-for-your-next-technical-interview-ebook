# Using this guide

## Prepare with evidence

Review core ideas, explain a small example, then state what can fail. Practise answering aloud before reading the model answer.

## Candidate routine

Clarify inputs and assumptions. Show the approach, check boundaries, and give time and space costs. Prepare one design decision and one incident story.

## Interviewer routine

Choose role skills and expected depth first. Follow the reasoning, record hints, and assess evidence rather than memorized wording.

## Coding focus

Explain correctness and test edge cases. A working example alone is not proof that an algorithm works for all valid inputs.

## Design focus

Start with requirements, traffic, correctness, and constraints. Draw the flow, then discuss failures, operations, and cost.

## Use this reference

Read the chapter, try its questions, then use this page for recall. Use companion examples and templates for hands-on practice.

# Java essentials

## Arrays and lists

Arrays have fixed length and use length. Collections use size(). List<Integer> stores references; int[] stores primitive values. Arrays.asList(int[]) produces a list containing that array.

## List creation

List.of rejects null and is unmodifiable. Arrays.asList is fixed-size, permits replacement, and shares storage with its reference array. new ArrayList<>(...) makes an editable list copy.

## Strings

String is immutable: keep the result of concat. For content use equals; for nullable references use Objects.equals. Object == checks identity. StringBuilder supports repeated text building.

## Generics and wrappers

T is a type placeholder. Box<String>.get() returns String. Integer and Long wrap int and long; ranges stay the same. Unboxing null fails. Generics use reference types.

## Numbers

int is 32-bit; long is 64-bit. BigInteger supports larger whole numbers. BigDecimal supports precise decimal work. Use string constructors, retain returned results, and choose explicit rounding when needed.

## Exceptions

Checked exceptions must be caught or declared. throw raises an exception now; throws declares possible propagation. RuntimeException is unchecked. A throws declaration does not handle failure or change the return type.

# Collections

## Interfaces

List and Set extend Collection. Map is separate within the framework. Declare an interface when useful; select an implementation for its behavior and operation costs.

## List and Set

List preserves sequence and permits duplicates. get and set use indexes. remove(1) removes by index; remove(Integer.valueOf(1)) removes a value. Set.add returns false for an existing equal value.

## Map operations

put adds or replaces. putIfAbsent adds if the key is missing or mapped to null. containsKey checks keys; containsValue searches values. HashMap key lookup is expected constant time, values search linear.

## Hashing

Colliding keys can share a bucket; equality still distinguishes them. Keep fields used by equals and hashCode stable while stored. HashSet does not promise iteration order.

## Stack and queue

ArrayDeque supports LIFO push/pop and FIFO offer/poll. peek reads without removal. pop returns the removed element and throws when empty. peek and poll return null when empty; ArrayDeque rejects null.

## Iteration and cost

Iterator moves forward. ListIterator also moves backward, sets the last returned value, and inserts at its cursor. Mutation rules depend on the collection. LinkedList edits are cheap at an already positioned iterator; indexed access is linear.

# Streams

## Pipeline

filter, map, distinct, sorted, and flatMap are intermediate operations. Terminal operations drive processing. A consumed stream cannot be reused; create another from the source.

## Choose the operation

filter keeps values; map transforms each one; distinct removes duplicates; sorted orders; flatMap flattens nested streams. Keep required business side effects out of intermediate stages.

## Predicate

Predicate<T>.test returns boolean. Reuse conditions in filter, match operations, or removeIf. and and or short-circuit; negate reverses a condition. removeIf requires supported mutation and changes the collection.

## Terminal results

count returns long; match operations return boolean; find and min/max return Optional. forEach returns void. reduce combines values. Handle empty results explicitly. forEachOrdered preserves encounter order when one exists.

## Lists and numbers

toList returns an unmodifiable list. Collectors.toList does not guarantee mutability. Use toCollection(ArrayList::new) for an editable result. mapToInt provides sum, average, and summaryStatistics.

## Locale and parallel work

Locale.ROOT gives language-neutral case conversion; default locale can change results. Parallel streams add coordination costs. Avoid shared mutable results and measure whether the workload benefits.

# Searching and sorting

## Search

Linear search works on unsorted data in O(n). Binary search needs a sorted or monotonic search space and takes O(log n). Keep bounds consistent and ensure each iteration makes progress.

## Simple sorts

Bubble, selection, and insertion are generally O(n²). Early-exit bubble and insertion can benefit from ordered input. Insertion suits small or nearly sorted data.

## Merge sort

Predictable O(n log n) time, O(n) extra space, and stable ordering in the teaching version. Splitting uses recursion; merging needs a buffer.

## Quick sort

Expected O(n log n) with suitable randomized partitioning; worst O(n²). Recursion uses stack space: expected O(log n), worst O(n) in the simple version. Not stable.

## Heap sort

O(n log n) worst-case time and O(1) extra space with iterative sift operations. It is not stable. Low memory does not guarantee the best measured performance.

## Explain the tradeoff

Stable means equal keys keep their original relative order. State whether space includes output, temporary storage, and recursion. Prefer a suitable library sort in ordinary application code.

# Coding challenges

## Array practice

Two Sum; Contains Duplicate; Best Time to Buy and Sell Stock; Maximum Subarray; Move Zeroes; Product of Array Except Self; Kth Largest Element in an Array.

## String practice

Valid Anagram; Valid Palindrome; Valid Parentheses; Longest Substring Without Repeating Characters; Group Anagrams.

## Linked list practice

Merge Two Sorted Lists; Reverse Linked List; Linked List Cycle.

## Search and intervals

Binary Search; Merge Intervals.

## Trees grids and steps

Maximum Depth of Binary Tree; Number of Islands; Climbing Stairs.

## Practice independently

Use the problem statements and examples in this chapter. Write your own solution, explain why it works, and check its behavior. No solutions are provided here.

# SOLID and design patterns

## SOLID

SRP groups code that changes for the same reason. OCP supports a known variation through extension. LSP preserves the contract. ISP narrows client interfaces. DIP makes policy depend on abstractions.

## Injection and composition

DI supplies dependencies; DIP describes dependency direction. Prefer composition for replaceable behavior. A Spring singleton is per bean definition per container, not one instance across all replicas.

## Creation patterns

Simple Factory centralizes selection. Factory Method lets subclasses choose a product. Abstract Factory creates compatible families. Builder handles optional construction steps. Prototype copies an object.

## Wrapping patterns

Adapter translates an interface. Decorator adds behavior around the same contract. Proxy controls access or invocation. Facade simplifies access to a subsystem. Similar structure can serve different intent.

## Behavior patterns

Strategy selects a policy. State changes behavior with state transitions. Observer notifies listeners. Template Method fixes a workflow while subclasses fill steps. In-memory notification does not guarantee durable delivery.

## Other names and judgment

Bridge separates independently varying abstractions and implementations. Composite treats leaves and groups alike. Flyweight shares reusable state. Explain the actual problem, variation, cost, and contract before naming a pattern.

# Spring Boot and dependency injection

## Boot basics

Spring manages beans and dependencies. Boot adds starters, auto-configuration, and defaults. SpringBootApplication combines configuration, auto-configuration, and scanning. Main package placement affects discovery.

## Register and select

@Service registers a scanned class. @Bean registers a factory method result. @Qualifier narrows injection candidates; @Primary marks a preferred candidate. A new object outside registration is not automatically managed.

## Scopes and lifecycle

Singleton is per definition per container. Prototype creates a new instance when requested from the container. A prototype injected once into a singleton is normally reused; ObjectProvider supports later lookup. Scope is not thread safety.

## Profiles

Base settings remain unless overridden. In the same location group, later active profiles win for conflicting properties. prod has no special rank. Activating two bean profiles can enable both beans.

## Property priority

For common sources, command-line arguments outrank JVM properties, then environment variables, then configuration data. Other specialized sources can add rules. ConfigurationProperties groups typed settings.

## Secrets

Priority follows the loading mechanism: config import, environment injection, or direct SDK call. A secret does not always win. Rotation needs a reload or restart plan and updated consumers. Keep credentials out of code and logs.

# HTTP APIs and validation

## Boundary

Controllers handle HTTP input and output. Services own business decisions. Validate request shape at the API boundary and use database constraints for rules that must hold across writers.

## Validation

@Valid activates validation. NotNull rejects null; NotBlank also rejects empty or whitespace text. Nested inputs need suitable validation. Valid syntax does not prove ownership or stock availability.

## Status codes

200/204 success; 201 created; 202 accepted and pending. 400 invalid input; 401 missing or invalid authentication; 403 denied; 404 missing; 409 conflict; 429 rate limit; 500 unexpected failure.

## Errors

RestControllerAdvice maps selected controller-layer exceptions. Use stable error codes and trace IDs; hide sensitive internals. Filter failures and background errors may need separate handling.

## Responses

Return DTOs with only the needed fields. For pending work, return an ID or URL for checking status. Do not report accepted work as completed.

## Lists and authorization

Bound page sizes and use stable ordering. Cursor pagination avoids expensive deep offsets for suitable paths. Derive identity and tenant scope from trusted data and verify resource ownership.

# JPA and database access

## Managed entities

The persistence context tracks entities. Dirty checking can persist a managed change inside a transaction without another save. save, flush, and commit are different; flushed SQL can still roll back.

## Fetch defaults

ManyToOne and OneToOne default to EAGER; OneToMany and ManyToMany to LAZY. LAZY is a hint. EAGER does not promise one JOIN or prevent N+1.

## Lazy boundaries

An unloaded relationship can fail after its session closes. A null check does not reopen it. Fetch needed data or map to a DTO inside the boundary. OSIV can hide SQL during response creation.

## Fetch Join and Graph

join fetch explicitly loads a relationship through the query. EntityGraph declares required relationships for a method; inspect provider SQL. Collection fetches with pagination need care because rows can multiply.

## Batch fetching

Hibernate can group pending lazy loads in one session. With 100 distinct customers and full batches of 20, one orders query plus about five customer queries is an illustration, not a guarantee.

## Projection and proof

DTO projection selects needed values and is not a managed entity. Choose the result shape first, inspect SQL, count round trips, and test realistic data. Annotation names alone do not prove performance.

# Transactions and rollback

## Boundary

A transaction groups related database work. Spring usually applies it through a proxy. The interceptor starts or joins, invokes the target, then decides completion. Self-invocation normally bypasses that interception.

## Rollback rules

RuntimeException and Error trigger default rollback; checked exceptions need a matching rule such as rollbackFor. A string argument to @Transactional selects a transaction manager, not a rollback policy.

## Caught errors

Catching your own error can let the proxy see success. An inner intercepted REQUIRED call may already mark the shared transaction rollback-only; an outer catch does not clear that state.

## Propagation

REQUIRED joins or starts. REQUIRES_NEW uses an independent transaction and often another connection. NESTED uses savepoints where supported. Separate intercepted scopes can share one physical transaction.

## Read-only

readOnly expresses read intent and may reduce persistence work. It is not a universal write lock and does not automatically select a replica. A managed write still needs a suitable transaction boundary.

## External work

Database rollback cannot undo an email or remote payment. Persist intent and use a recoverable workflow. Verify interception, rollback, and commit behavior with integration tests, not plain mocks alone.

# Concurrency

## Find shared state

Singleton requests may share fields. count++ reads, adds, and writes; two calls can lose an update. A local int is not inherently a concurrency bug. Protect the actual shared operation.

## Visibility and atomicity

volatile makes writes visible but does not make count++ atomic. AtomicInteger increment operations are atomic for one value. Multi-field decisions still need suitable coordination.

## Parallel results

Avoid parallel forEach adding to one ArrayList. Return mapped results and let the stream collect them. This removes shared list mutation, not every side effect inside the mapper.

## Collections

Use ConcurrentHashMap, suitable concurrent queues, or coordinated wrappers for the workload. CopyOnWriteArrayList suits many reads and few writes. Thread-safe calls do not automatically make a sequence atomic.

## Map loading

putIfAbsent(key, load()) evaluates load first. computeIfAbsent defers computation; HashMap still lacks concurrent safety. ConcurrentHashMap provides atomic per-key behavior, not a durable once-only business guarantee.

## Database decisions

For stock use a conditional update, optimistic version, or suitable lock. Keep related writes in one transaction. Several app replicas need shared authoritative coordination, not only a process-local counter.

# Caching

## Abstraction

Spring supplies cache annotations; the provider stores values and CacheManager selects caches. Cacheable can skip a target on a hit; configure a provider and proxy interception.

## Writes

CachePut runs and stores its returned value. CacheEvict removes entries. A void method supplies no updated value to CachePut. Coordinate changes with successful database commit.

## Keys and lifetime

Include every input affecting the answer, especially trusted tenant scope. Define TTL, size, and freshness. Use the same key rule for reads, updates, and evictions.

## Cache-aside

Look up the cache, load the database on a miss, populate, then return. A cache is a second copy; define what stale data means for the operation.

## Stampede and failure

Many misses on one key can overload the database. Consider single flight, bounded loading, jitter, or a suitable lock. A cache outage needs a controlled fallback or degraded response.

## Limits

Shared Redis aligns replicas but does not fix invalid keys or races. An older reader can repopulate stale data after eviction. Validate authoritative price and stock before a purchase.

# Security and identity

## Identity and access

Authentication establishes who the caller is; authorization decides what they may do. A valid login does not prove resource ownership. Enforce tenant boundaries in queries, caches, jobs, and exports.

## OAuth and tokens

OAuth 2 defines authorization flows. JWT is a token format. OpenID Connect adds identity. Access tokens reach resource APIs; refresh tokens obtain new access tokens from the authorization server.

## JWT checks

Decoding reads content; it does not prove trust. Verify signature, allowed algorithm, issuer, audience, expiry, and required claims. A signed JWT is not necessarily encrypted.

## State models

Sessions and opaque tokens commonly need server-side lookup. JWTs can be checked locally; immediate revocation needs another design. Choose from required behavior, not a universal preference.

## Spring boundaries

Request rules protect routes. PreAuthorize protects methods when method security and interception apply. Use trusted identity data; an arbitrary tenant header is not enough.

## Browser defenses

CORS controls browser access to cross-origin responses; it is not authentication. CSRF concerns unwanted actions with automatically sent credentials. Protect cookies with appropriate Secure, HttpOnly, and SameSite settings.

# Async work and application events

## Caller and worker

@Async submits through a proxy to an Executor. The caller can continue sooner, but work still takes time. Call an injected service; self-invocation normally bypasses interception.

## Executor selection

@Async("reportExecutor") names a Spring Executor bean. Bound worker count and queue size, choose rejection behavior, and size from the workload. More threads can increase contention.

## Result

CompletableFuture lets the caller observe completion and failure. whenComplete can record the outcome. join waits and can expose CompletionException; waiting still blocks the caller.

## Exceptions

A later failure in a void async task cannot be caught by the caller's try/catch. Use AsyncUncaughtExceptionHandler. Submission rejection can still fail synchronously.

## Transactions

The caller's thread-bound transaction does not automatically move to the worker. A worker can open a separate transaction and commit independently of a later caller rollback.

## Reliable events

AFTER_COMMIT delays a listener until successful completion, but in-memory work can disappear on a crash. Use durable intent, an Outbox, a queue, and safe retries when recovery matters.

# Testing

## Choose evidence

Unit tests isolate business logic. Integration tests cross real boundaries. E2E tests verify user journeys. Test behavior that could fail rather than mirroring implementation details.

## Mockito

@Mock supplies a test double; @InjectMocks creates the target and injects mocks. MockitoExtension initializes Mockito support. A plain Mockito test does not create Spring transaction proxies.

## Spring context

MockitoBean supplies or replaces a bean in a Spring test context. WebMvcTest focuses on MVC. DataJpaTest focuses on persistence. SpringBootTest loads application integration; use an appropriate web environment for real HTTP.

## Database truth

Use PostgreSQL-backed checks for PostgreSQL behavior. Testcontainers can provide real dependencies. Verify commit and proxy boundaries; a test transaction may hide a later commit failure or keep lazy data open.

## Useful failures

Test duplicate requests, races, timeouts, retries, denied access, tenant isolation, N+1 counts, and worker crashes after external effects. Verify business outcomes as well as status codes.

## AI and review

Ask AI for missing cases, then inspect assertions and fixtures. State what ran, what was mocked, and what was skipped. Passing generated tests do not establish untested behavior.

# PostgreSQL and SQL plans

## Commands

EXPLAIN shows estimates. EXPLAIN ANALYZE executes and adds observations. BUFFERS adds block-access evidence. ANALYZE also executes writes; use a suitable test environment for them.

## Read the fields

cost uses planner units, not milliseconds. actual time uses milliseconds. rows and timing are per-loop averages when loops repeat. Parent time includes child time, so do not sum every node.

## Scans

Seq Scan can be correct for small tables or broad reads. Index Scan finds entries and fetches rows. Index Only Scan still depends on visibility. Bitmap Heap Scan groups table-page access through a bitmap.

## Joins and sorts

Nested Loop repeats inner work for outer rows. Hash Join builds and probes. Merge Join uses ordered input. Inspect loops, sort methods, disk spills, and large filtering work.

## Statistics and indexes

Compare estimates with actual rows and inspect where they diverge. ANALYZE updates statistics. Match indexes to filters and ordering; every extra index costs storage and writes.

## Measure the whole path

A good SQL plan can still sit behind connection waits, locks, N+1 calls, or slow remote dependencies. Validate tuning on representative data and check results, API latency, and write cost.

# System requirements and capacity

## Start with scope

Identify users, operations, correctness rules, expected traffic, latency, availability, budget, and team constraints. Separate launch needs from future possibilities.

## Service boundaries

Design API outcomes honestly: created, pending, failed, or unknown. A modular monolith can simplify delivery; microservices add independent deployment and ownership with network and data complexity.

## Estimate traffic

500 orders per minute is about 8.33 orders per second. A 10:1 read-to-write request ratio gives about 91.7 total requests per second in the worked model. Request rate is not SQL or disk IOPS.

## Headroom

Capacity 25 percent above 500 means 625 per minute. Keeping 25 percent of capacity unused means about 667. State the definition, peak assumptions, and failure allowance.

## Resources and cost

Estimate CPU time per request, memory, connections, disk IOPS, throughput, storage growth, and replication. Include infrastructure, traffic, backups, monitoring, and engineering cost. Orders per second alone cannot select an instance.

## Find and test limits

Look at CPU, pool waits, lock contention, disk, query counts, and dependency latency. JMeter tests should cover steady load, spikes, slow dependencies, business success, p95/p99, and errors.

# Data modeling

## SQL or NoSQL

Choose from relationships, access patterns, and correctness. SQL suits related business data and joins. NoSQL includes documents, key-value, and graph models. Both capabilities and scaling depend on the specific database.

## Practical choices

PostgreSQL is a useful order-data default. Document storage can fit varying product attributes read together. Redis suits suitable cache or session lookups. PostgreSQL also supports JSON; flexible fields alone do not require another store.

## Normal forms

1NF avoids repeating groups. 2NF removes dependence on part of a composite candidate key. 3NF removes unsuitable dependencies through non-key attributes. Keep the formal detail proportional to the design.

## Anomalies

Update anomaly: copies disagree. Insertion anomaly: one fact cannot be stored independently of another. Deletion anomaly: deleting one record accidentally removes another fact. Split responsibilities into related tables.

## Constraints

Use primary and foreign keys, uniqueness, NOT NULL, and CHECK rules where appropriate. Application validation alone does not protect against competing writers. Tenant-owned references must not cross tenant boundaries accidentally.

## Indexes and snapshots

B-tree suits equality, ranges, and matching order. Hash supports equality. GIN can support arrays or jsonb containment. Purchased price is an intentional historical fact; denormalization needs an update and freshness policy.

# Transactions and distributed data

## ACID

Atomicity groups participating changes. Consistency preserves enforced rules. Isolation controls concurrent effects. Durability describes committed data survival under stated guarantees. One local transaction does not include every remote service.

## PostgreSQL isolation

Read Committed is the default. Read Uncommitted behaves as Read Committed. Repeatable Read gives a stable snapshot and prevents phantoms in PostgreSQL but can permit write skew. Serializable can require whole-transaction retries.

## Locks and deadlocks

Optimistic locking rejects a version conflict. Pessimistic locking makes competitors wait. Opposite lock order can create a cycle. Keep transactions short, avoid remote waits under locks, and use consistent lock ordering.

## Connections

Pools reuse connections; pool waiting is not the same as a slow query. Budget across all replicas, workers, and clients. Adding replicas can exhaust the database connection budget.

## Replication and partitioning

Read replicas can lag; use an authoritative source for critical confirmation. Partitioning splits data, often within one database. Sharding distributes ownership and adds routing and cross-shard complexity.

## Consistency and CAP

Define strong consistency for the operation. Eventual consistency converges with progress when updates stop. During a partition, CAP constrains consistency and availability under its model; it is not simply choose any two features.

# Messaging and consistency

## Queue and fanout

Queue workers share tasks. Publish/subscribe gives independent subscribers separate delivery paths. SNS can fan out to SQS queues. Each consumer owns retry and recovery behavior.

## Kafka

A topic contains partitions. An offset is a position within one partition, not across the topic. Consumer groups and partition assignment control work sharing; ordering scope must be explicit.

## Delivery

At most once can lose work. At least once can repeat. Exactly once applies within a defined scope; a broker claim does not automatically include an external payment or email effect.

## Idempotency

Identify one logical operation across retries. Store request identity, state, and result; enforce uniqueness and reject conflicting payloads. Use provider-supported keys for remote side effects.

## Outbox and Inbox

Outbox stores business change plus sending intent in one local transaction. Inbox stores unique receipt plus the protected receiving effect. Publishing can repeat; both sides need appropriate duplicate handling.

## Saga and ordering

Saga uses local commits and compensating actions; compensation can fail. Choreography distributes reactions; orchestration coordinates the flow. Define event keys, versions, legal transitions, dead letters, and recovery ownership.

# Resilience and traffic spikes

## Timeouts

Bound waiting. A payment timeout means uncertainty, not proof of failure. Reconcile the same logical operation before starting another charge.

## Retries

Retry selected temporary failures only when safe or protected by idempotency. Bound attempts and total time. Exponential backoff adds delay; jitter spreads clients. Nested retries can multiply downstream calls.

## Circuit breaker

Closed allows calls and observes outcomes. Open rejects quickly. Half-open permits limited recovery trials. A fallback must report an honest business state.

## Rate and burst

Rate limits control traffic over time. A full bucket of 20 tokens with refill 10 per second permits a short initial burst, then about 10 per second steadily. HTTP 429 commonly reports a rate limit.

## Capacity and overload

Warm capacity absorbs a spike while scaling reacts. Load shedding rejects some excess work to preserve useful service. Concurrency limits bound in-flight work; backpressure slows producers.

## Queues and testing

Durable queues buffer temporary mismatch, but need capacity and age limits. Test recovery, dependency slowdown, and duplicate effects, not only the initial spike. More API capacity does not remove downstream limits.

# Orders payments inventory and notifications

## Business rules

Authenticate ownership and tenant scope. Quantities are positive, stock cannot go negative, retries must not double-charge, and confirmation follows verified payment and reservation rules.

## Separate states

Order, payment, and reservation states change independently. Pending or unknown is a valid state. A refund can remain pending after order cancellation.

## Create and reserve

Validate input and price; put the order, conditional local reservation, and required Outbox intent in one database transaction where they share that database. Require exactly the intended affected row.

## Payment recovery

Persist attempt identity before calling the provider. Reuse its supported key. On timeout, reconcile status or a verified webhook. A browser redirect is not sufficient payment evidence.

## Races and expiry

Conditionally transition reservations before returning stock. Payment confirmation can race with expiry; define reserve-again, refund, or review policy. Keep workers recoverable after crashes.

## Notifications

Persist notification intent after the right business state. Retry safely and monitor delivery. Distinguish provider acceptance from receipt or reading. Email failure should not undo a valid order through an unsafe remote rollback assumption.

# Docker and Kubernetes

## Units

An image packages software; a container runs it. A Pod groups cooperating containers sharing network and configured volumes. A cluster has a control plane and worker nodes. EKS and GKE manage control-plane responsibilities.

## Control plane

API server accepts requests; etcd stores cluster state; scheduler selects nodes; controllers reconcile desired state. Kubelet manages Pods on a node; the runtime runs containers; CNI provides Pod networking.

## Workloads

Deployment runs replicas. StatefulSet provides stable identity and storage links, not automatic database replication. DaemonSet runs on eligible nodes. Job completes a task; CronJob schedules Jobs. Repeated effects must be safe.

## Resources and storage

Requests guide placement; CPU limits can throttle and memory limits can lead to OOMKilled. 500m is half a CPU unit. Java needs memory beyond heap. A Pod must fit one eligible node. Persistent storage is not a backup.

## Traffic and health

Service provides stable backend access. Ingress requires a controller. API gateways add product-specific API policies. Readiness controls traffic, liveness can restart, startup delays other probes. Do not restart every API because the database is down.

## Scaling and recovery

HPA changes Pod count; node autoscaling adds node capacity. Self-healing restores desired operation; autoscaling responds to demand. Check events, readiness, logs, previous exits, and downstream capacity.

# Deployment observability and recovery

## Signals

Logs describe events; metrics show patterns; traces connect request time. Measure latency, throughput, errors, and resource saturation. Protect sensitive data in all telemetry.

## Objectives

SLI is the measured behavior; SLO is its target; SLA is the customer agreement and consequences. A percentage is not the only possible SLI. State the eligible population and time window.

## Tail latency

p95 and p99 expose slow requests hidden by averages. Compare matching routes, outcomes, and windows. Do not average replica percentiles to claim a fleet percentile.

## Safe rollout

Use identifiable versions, readiness, graceful shutdown, and business checks. Expand compatible schema, migrate and verify data, then contract old structures. Rolling back code does not necessarily undo a migration.

## Recovery

RTO is target restoration time; RPO is acceptable data-loss window. Replicas can copy deletion. Retained backups and logs support point-in-time recovery; rehearse restoration and application checks.

## Incidents

Assign coordination, investigation, and communication. Restore useful service, reconcile uncertain effects, then write a postmortem with evidence and owned actions. A successful process restart is not proof of business recovery.

# AWS and GCP

## Core service map

EC2 ↔ Compute Engine; EKS ↔ GKE; S3 ↔ Cloud Storage; RDS PostgreSQL ↔ Cloud SQL; ElastiCache ↔ Memorystore. Similar roles do not mean identical interfaces, limits, or operations.

## Compute choice

ECS orchestrates; Fargate supplies managed compute. Cloud Run services manage container execution and routing for suitable workloads. Compare background work, networking, scaling, and control. Kubernetes is a separate operational choice.

## Networks

AWS VPCs are regional, with zone subnets. Standard GCP VPC networks are global, with regional subnets. Scope is not automatic resilience. Routing, addresses, firewall rules, and placement determine reachability.

## HTTP layers

HTTP is Layer 7; TCP/UDP are Layer 4; IP routing is Layer 3. ALB routes HTTP; NLB handles transport flows; API gateways manage APIs. WAF or Cloud Armor inspect supported HTTP paths; network permission is not application authorization.

## Messages and identity

SNS plus SQS gives independent queues; Pub/Sub gives independent subscriptions. Cloud Tasks targets explicit work; Step Functions compares with Workflows. Use least-privilege workload identity and controlled secret reload on either cloud.

## Operations and tradeoffs

CloudWatch compares with Monitoring and Logging; CloudTrail with Audit Logs. Choose regions, managed-service fit, recovery, team ownership, and total cost. Include egress and engineering effort; multi-cloud adds operational work.

# AI system design

## RAG

Retrieve relevant source material and pass it to the model. It supplies external knowledge without retraining for every document update. Retrieval does not guarantee a correct answer.

## Index flow

Store trusted documents with tenant, permission, version, and update metadata. Chunk, embed, and index. Refresh or remove records when sources change or access is revoked.

## Answer flow

Derive trusted access filters, retrieve authorized evidence, and ask for an evidence-based answer. Keyword search and reranking can help. Keep context focused and handle missing evidence honestly.

## Live data

Use trusted APIs for current payment status or stock. A stale document is not an authoritative transactional fact. Enforce access in backend code for both retrieval and tool calls.

## Prompt injection

Retrieved text can contain misleading instructions. Treat it as data, limit tools and permissions, and keep business controls outside the model. Filtering the final answer cannot undo earlier tenant data exposure.

## Evaluation

Test retrieval, answer grounding, freshness, security, missing answers, latency, and cost separately. Streaming can improve first output time without reducing total work. Include indexing, retries, and operations in estimates.

# Agentic AI for coding

## Parts

The model reasons; the harness manages context and tools. Rules give persistent guidance, skills package procedures, MCP connects tools and data, and hooks run configured lifecycle actions.

## Workflow

Define observable acceptance criteria, inspect the existing flow, plan proportionally, implement a focused diff, then verify. Supply code conventions and relevant failure cases rather than an oversized context dump.

## Evidence

Match checks to the claim: unit for focused logic, integration for real boundaries, E2E for journeys, static checks for build quality. Review generated tests and report mocked, skipped, and unexecuted work.

## Playwright

Use user-visible locators, awaited assertions, controlled fixtures, and traces. Browser context isolation does not isolate server data. A retry-passed test remains flaky. Exploration is not a saved regression test.

## MCP and skills

MCP is optional when CLIs or built-in tools suffice. Pin tested server versions for teams. A SKILL.md defines a repeatable workflow; it does not grant permissions. Match setup paths to the installed tool version.

## Team responsibility

Use least access, branches or worktrees, meaningful CI, and human review. Treat untrusted content carefully. Measure accepted changes, rework, defects, flakiness, review effort, and cost. The engineer owns the result.

# Design documents postmortems and leadership

## Technical design

Explain the problem, constraints, target behavior, flows, API and data changes, alternatives, failures, rollout, validation, and owners. Match document length to the risk and decision.

## ADR

An architecture decision record captures one choice, its context, alternatives, consequences, status, and owner. Link it to the wider design document rather than repeating all implementation detail.

## Worked design

For reliable notification, store order confirmation and Outbox intent together. Publish to durable work, reuse stable keys, handle duplicate effects, and monitor recovery. Keep pending work through rollout or rollback.

## Postmortem

Record impact, timeline, evidence, contributing causes, and follow-up actions. Blameless means examining system conditions, not removing accountability for completing improvements.

## Action quality

Give each action an owner and proof of completion. Verify user recovery, not only infrastructure health. Distinguish a triggering traffic rise from underlying capacity or transaction problems.

## Leadership and AI

Explain business needs, options, tradeoffs, ownership, and results from your experience. Say what evidence would change your decision. AI can help structure writing; verify every incident fact and claimed outcome.