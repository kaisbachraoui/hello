# Hello Web Application

This repository contains a minimal full stack example with a React
frontend, a Spring Boot backend (Java 17) and a MySQL database.  All
components can be started using Docker.

## Quick start

```bash
# Build and run the stack
docker-compose up --build
```

The React app will be available on <http://localhost:3000> and displays a
blue text "hello page" with font size 20px. The backend runs on
<http://localhost:8080> and exposes `/api/hello`.
