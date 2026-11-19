# Technical Design Document

Title:
Status:
Owner:
Reviewers:
Date:

## Problem and goal

What happens today? Who is affected? What should change for the user?

## Requirements and constraints

List required behavior, correctness rules, traffic, latency, recovery targets, budget, team capacity, and launch scope. Mark assumptions that need measurement.

## Proposed design

Show the main request and background flows. Name each component and its owner. State where important data lives.

## Data and API changes

Describe schemas, keys, constraints, APIs, errors, events, and compatibility. Define duplicate-request handling and ordering where needed.

## Failure and recovery

Walk through a timeout, duplicate request, service outage, and process crash. What state survives? How does work resume? Who handles failed work?

## Security

Explain authentication, authorization, tenant checks, credentials, and sensitive data handling.

## Capacity cost and monitoring

State traffic assumptions and the headroom definition. Check CPU, memory, disk, connections, and downstream limits. List cost inputs and useful metrics, logs, traces, and alerts.

## Alternatives and decision

Compare realistic options against the same requirements. Explain the chosen tradeoff and what would make you revisit it.

## Validation and rollout

List checks that show the design works. Describe deployment order, schema migration, backfill, rollout gates, and rollback or forward recovery.

## Open questions and actions

For each item, name the decision or action, one owner, and a due date. Link related issues, references, and architecture decisions.
