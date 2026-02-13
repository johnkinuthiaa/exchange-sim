# ExchangeSim: Simulated High-Frequency Trading Platform

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-Event%20Streaming-orange)](https://kafka.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Description

**ExchangeSim** is a project simulating a high-frequency trading (HFT) stock exchange platform, inspired by systems like Robinhood or professional exchanges. Built entirely with **Java and Spring Boot**, it focuses on low-latency order matching, event-driven architecture, distributed resilience, and real-world financial domain challenges.

This is a **simulation only** — no real money, no live exchanges. It generates mock market data, processes thousands of orders per second (in a local setup), and demonstrates production-grade patterns like CQRS, Sagas, in-memory order books, and high-throughput event streaming.
Using this project to learn Microservices and distributed systems
## Key Features


- **Order Types**: Market, Limit, Stop (with partial fills, cancellations)
- **Matching Engine**: Price-time priority FIFO, in-memory order books (ConcurrentSkipListMap + optional Disruptor)
- **Market Data Simulation**: Random walk ticks + WebSocket broadcasting
- **Real-time Updates**: Executions, quotes, and P&L via WebSockets
- **Risk Checks**: Pre-trade margin/position limits
- **Event-Driven**: Kafka for orders, trades, market events
- **Resilience**: Circuit breakers (Resilience4j), retries, bulkheads
- **Observability**: Distributed tracing (OpenTelemetry), metrics (Prometheus/Grafana)

## Architecture Overview

Clients → API Gateway → Core Services → Kafka Event Bus → Downstream Processing








### Order Matching Engine Focus

The core matching engine maintains per-symbol order books and processes orders with minimal latency.








## Microservices

Each is a separate Spring Boot application:

1. **api-gateway** – Spring Cloud Gateway (routing, auth, rate limiting, WebSocket proxy)
2. **auth-service** – User registration, JWT auth (Spring Security + OAuth2)
3. **market-data-service** – Simulates and broadcasts price ticks
4. **order-service** – Validates and publishes orders to Kafka
5. **matching-engine** – Core high-performance matcher (in-memory, optional Disruptor)
6. **portfolio-service** – Updates positions, P&L (CQRS/event-sourced views)
7. **risk-service** – Pre-trade checks
8. **notification-service** – Real-time pushes (WebSocket/email)
9. **analytics-service** – Trade history, reporting
10. **news service** -get news from finnhub

## Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.x, Spring Cloud
- **Messaging**: Apache Kafka
- **Real-time**: WebSocket
- **Data**: PostgreSQL (persistence), Redis (caching/order book snapshots)
- **High Performance**: LMAX Disruptor , Agrona buffers
- **Resilience**: Resilience4j
- **Observability**: Micrometer, Prometheus, Grafana, OpenTelemetry
- **Deployment**: Docker, Docker Compose (local), Kubernetes (production-like)
- **Testing**: Locust/Gatling for load simulation

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Kafka (via Docker)
- (Optional) Minikube/Kind for Kubernetes

### Local Setup (Docker Compose)

1. Clone the repo:
   ```bash
   git clone https://github.com/johnkinuthiaa/exchangesim.git
   cd exchangesim
   ```

2. Start infrastructure:
   ```bash
   docker-compose up -d kafka redis postgres prometheus grafana
   ```

3. Build and run services:
   ```bash
   mvn clean install
   # Run each service individually or use docker-compose for all
   docker-compose up --build
   ```

4. Access:
    - API Gateway: `http://localhost:8080`
    - WebSocket feeds: `/ws/market-data`
    - Grafana: `http://localhost:3000`

### Testing High Load

Use the `market-simulator` service or tools like Gatling to flood the system with orders and measure matching throughput.

## Challenges & Extensions

- Implement Saga orchestration for settlement
- Add gRPC for ultra-low-latency inter-service calls
- Integrate service mesh (Istio) for mTLS and traffic control
- Chaos engineering with simulated failures
- ML-based anomaly detection in trades

## References & Inspirations

- LMAX Exchange Architecture
- "Microservices Patterns" by Chris Richardson
- Open-source matching engines on GitHub (e.g., Java order book implementations)

Contributions welcome!