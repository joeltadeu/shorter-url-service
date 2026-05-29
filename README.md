![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

# Shorter URL Microservice

A URL shortener service built with Java 25 and Spring Boot, deployed with Docker Compose.

## 📋 Overview

The primary goal of this project is to study and understand how a URL shortener service works — from generating a compact hash ID for a long URL, storing it with a TTL in a key-value store, and resolving it back to the original destination on redirect. The service is deployed using [Docker Compose](https://docs.docker.com/compose/) for local development and containerised environments.

Key Technical Features:

- **Database**: Shortened URLs are persisted in [Redis](https://redis.io/docs/latest/), a fast in-memory key-value store. Each entry is stored with a one-year TTL, and the short ID is produced by an Adler-32 hash of the original URL using [Guava](https://github.com/google/guava).
- **Observability**: Centralised logging with [Loki](https://grafana.com/docs/loki/latest/), metrics collection with [Prometheus](https://prometheus.io/), dashboards with [Grafana](https://grafana.com/docs/grafana/latest/), and container resource metrics via [cAdvisor](https://github.com/google/cadvisor).
- **API Documentation**: Interactive documentation is provided via [springdoc-openapi](https://springdoc.org/) (Swagger UI and Scalar UI).
- **Containerisation**: The service is packaged as a [Distroless](https://github.com/GoogleContainerTools/distroless) container image for a minimal and secure runtime.

---

## 🏗️ Architecture

This section presents the **high-level architecture** of the system.

![Alt text](__assets/shorterurl_architecture.png?raw=true "Shorter URL Service Architecture")

---

## 🛠️ Technology Stack

- **Language**: Java 25
- **Framework**: Spring Boot 4.0.6
- **Data**: Spring Data Redis
- **Hash Generation**: Guava 33.6.0 (Adler-32)
- **Validation**: Hibernate Validator, Bean Validation (Jakarta)
- **API Documentation**: springdoc-openapi 3.0.1 (Swagger UI + Scalar UI)
- **Containerisation**: Docker, Docker Compose, Distroless base image
- **Logging**: Loki, Loki4j Logback appender 1.5.2
- **Monitoring**: Prometheus, Grafana, cAdvisor
- **Metrics**: Micrometer, micrometer-registry-prometheus

---

## 📚 API Endpoints

### Shorten a URL
- **Endpoint:** `POST /v1/urls`
- **Description:** Accepts a long URL in a JSON body, persists the mapping in Redis with a one-year TTL, and returns the shortened URL details.
- **Response:** `201 Created` with a JSON object and a `Location` header pointing to the redirect endpoint.

Request Example:
```bash
curl --location 'http://localhost:8081/v1/urls' \
--header 'Content-Type: application/json' \
--data '{
    "url": "https://www.youtube.com/watch?v=MarSC2dFA9g"
}'
```

Response Example:
```json
{
  "id": "tN72u2j",
  "originalUrl": "https://www.youtube.com/watch?v=MarSC2dFA9g",
  "shortenedUrl": "http://localhost:8081/v1/urls/tN72u2j",
  "expiresAt": "2027-05-29 14:30:00"
}
```

| Status | Description |
|--------|-------------|
| `201 Created` | Shortened URL created — `Location` header contains the redirect URL |
| `400 Bad Request` | URL is missing or invalid |

---

### Redirect to Original URL
- **Endpoint:** `GET /v1/urls/{id}`
- **Description:** Resolves the short URL hash ID and issues an HTTP 302 redirect to the original long URL.
- **Response:** `302 Found` with a `Location` header pointing to the original URL.

Request Example:
```bash
curl -v 'http://localhost:8081/v1/urls/tN72u2j'
```

| Status | Description |
|--------|-------------|
| `302 Found` | Redirect to the original URL |
| `404 Not Found` | No URL found for the given short ID |

---

## 📘 Documentation & Testing

### OpenAPI / Swagger

Once the service is running, you can access the interactive API documentation:

- Swagger UI: http://localhost:8081/swagger-ui/index.html
- Raw OpenAPI spec: http://localhost:8081/api-docs

![Alt text](__assets/swagger.png?raw=true "Swagger UI")

### Scalar UI

An alternative modern API UI is also available at:

- http://localhost:8081/scalar-ui.html

![Alt text](__assets/scalar_ui.png?raw=true "Scalar UI")

### Postman Collection

A **Postman collection** is provided to test all APIs.
- Location: `/documentation/postman/ShorterURL.postman_collection.json`

---

## 🔍 Centralised Logging

All logs are shipped to a central **Loki** instance and visualised through **Grafana**. Each log line is tagged with the application name, trace ID, and span ID, making it straightforward to correlate logs across requests.

### Loki

[Grafana Loki](https://grafana.com/docs/loki/latest/) is a horizontally scalable log aggregation system. Unlike traditional logging stacks, Loki indexes only the metadata labels attached to each log line (such as `app`, `level`, and `traceID`) rather than the full log content. This makes it lightweight and cost-effective while still enabling powerful label-based filtering in Grafana.

The service uses the `loki4j` Logback appender to push logs directly to Loki. The configuration is defined in `logback-spring.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

  <springProperty scope="context" name="appName" source="spring.application.name"/>

  <property name="LOG_PATTERN"
            value="%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [${appName},%X{traceId:-},%X{spanId:-}] [%thread] %logger{36} : %msg%n"/>

  <appender name="SHORTERURL_CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>${LOG_PATTERN}</pattern>
      <charset>UTF-8</charset>
    </encoder>
  </appender>

  <appender name="LOKI" class="com.github.loki4j.logback.Loki4jAppender">
    <http>
      <url>http://loki:3100/loki/api/v1/push</url>
    </http>
    <format>
      <label>
        <pattern>app=${appName},host=${HOSTNAME},traceID=%X{traceId:-NONE},level=%level</pattern>
        <readMarkers>true</readMarkers>
      </label>
      <message>
        <pattern>${LOG_PATTERN}</pattern>
      </message>
    </format>
  </appender>

  <springProfile name="docker">
    <root level="INFO">
      <appender-ref ref="SHORTERURL_CONSOLE"/>
      <appender-ref ref="LOKI"/>
    </root>
  </springProfile>

  <springProfile name="!docker">
    <root level="INFO">
      <appender-ref ref="SHORTERURL_CONSOLE"/>
    </root>
  </springProfile>

</configuration>
```

The `docker` Spring profile activates the Loki appender. When running locally (without the `docker` profile), only the console appender is used.

- Loki is accessible at: **http://localhost:3100**
- Logs are queried through the Grafana Explore view using LogQL.

![Alt text](__assets/loki.png?raw=true "Loki Log Explorer in Grafana")

---

## 📊 Monitoring

Monitoring ensures the health and performance of the service.

### Prometheus

**Prometheus** is an open-source systems monitoring and alerting toolkit. It scrapes and stores time-series metrics. The service exposes a `/actuator/prometheus` endpoint, and `prometheus.yml` defines the scrape targets — including the application itself, cAdvisor, and the Redis exporter.

The service exposes metrics using the following configuration in `application.yaml`:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: metrics, info, health, prometheus
  endpoint:
    metrics:
      access: read_only
    prometheus:
      access: read_only
  metrics:
    tags:
      application: ${spring.application.name}
    distribution:
      percentiles-histogram:
        http.server.requests: true
      percentiles:
        http.server.requests: 0.5, 0.9, 0.95, 0.99, 0.999
      service-level-objectives:
        http.server.requests: 500ms, 2000ms
```

Access Prometheus at **http://localhost:9090**

![Alt text](__assets/prometheus.png?raw=true "Prometheus Query Example")

---

### Grafana

**Grafana** is an open-source analytics and interactive visualisation platform. It connects to both Prometheus (for metrics) and Loki (for logs) to provide unified dashboards for the entire service — including HTTP traffic, JVM internals, Redis health, and live log streams.

Two dashboards are provisioned automatically on startup:

- **Microservices Observability** — JVM metrics, HTTP traffic, Tomcat, GC, buffer pools, Redis, and Loki log panels.
- **Logs, Traces & Metrics** — Trace explorer, log stream, log volume, latency percentiles, and error rate.

- Access: **http://localhost:3000**
- Username: `admin`
- Password: `admin`

![Alt text](__assets/grafana.png?raw=true "Grafana Dashboard")

---

### cAdvisor

[cAdvisor](https://github.com/google/cadvisor) (Container Advisor) analyses and exposes resource usage and performance data for running containers. It provides per-container CPU, memory, network, and filesystem metrics, which are scraped by Prometheus and displayed in Grafana.

- Access: **http://localhost:8080**

![Alt text](__assets/cAdvisor.png?raw=true "cAdvisor Container Metrics")

---

## 🧪 Tests

The project includes unit tests covering the controller and service layers.

### 🧩 Unit tests

Execute the unit tests using the command below:

```bash
mvn test
```

Tests use **Mockito** (`@ExtendWith(MockitoExtension.class)`) and **MockMvc** (`MockMvcBuilders.standaloneSetup(...)`) — no Spring context is loaded, keeping them fast and isolated.

---

## 🚀 Deployment

### 📦 Docker Compose

**Docker Compose** is a tool for defining and running multi-container Docker applications. It is the deployment strategy used by this project for local development and containerised environments.

The `docker-compose.yml` at the root of the project defines and orchestrates the full system. It includes configuration for:

- **shorterurl** — the Spring Boot application (port 8081)
- **db** — Redis key-value store (port 6379)
- **redis-exporter** — Prometheus exporter for Redis metrics (port 9121)
- **prometheus** — Metrics scraping and storage (port 9090)
- **grafana** — Dashboards and visualisation (port 3000)
- **loki** — Centralised log aggregation (port 3100)
- **cadvisor** — Container resource usage metrics (port 8080)

![Alt text](__assets/docker_compose.png?raw=true "Docker Compose Services")

Commands:

```bash
# Start all services
docker compose up -d

# Start all services and rebuild the application image
docker compose up -d --build

# View logs for a specific service
docker compose logs -f shorterurl

# Stop and remove containers
docker compose down
```

---

## 🔨 Build Project & Running Locally

To build the project using Maven:

```bash
mvn clean package
```

### Running Locally

Before starting the application, make sure Redis is running. You can spin up a standalone instance with:

```bash
docker run --name redis -p 6379:6379 -d redis:alpine
```

Then start the application with the `local` profile (connects to `localhost:6379`):

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Running as a Container

The service includes a **Dockerfile** to build a lightweight, secure container image using [Distroless](https://github.com/GoogleContainerTools/distroless). The Distroless image contains only the JRE and the application itself — no shell, no package manager, and a significantly smaller attack surface.

```dockerfile
FROM gcr.io/distroless/java25-debian13

COPY target/shorter-url-service.jar app.jar

EXPOSE 8081

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run the container image:

```bash
# Build the image
docker build -t shorterurl-service .

# Run the container (requires Redis to be running and reachable)
docker run -e SPRING_PROFILES_ACTIVE=docker -p 8081:8081 shorterurl-service
```

### Running with Docker Compose

Start the full stack (application, Redis, Redis Exporter, Prometheus, Grafana, Loki, cAdvisor):

```bash
docker compose up -d
```

```bash
docker compose down
```

---

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](https://www.apache.org/licenses/LICENSE-2.0) file for details.

---
