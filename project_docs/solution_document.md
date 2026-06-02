# Quicklink Solution Document

## Overview
Quicklink is a lightweight URL redirect service that lets users register a short code (`quicklink`) for a target URL. Users can then use the quicklink name to redirect to the destination URL. The application also records audit events for create, update, and delete operations.

## Architecture
- **Frontend**: Static HTML/CSS/JavaScript served from `gencode/ui`
- **Backend**: Java Spring Boot application in `gencode/backend`
- **Database**: MongoDB database named `quicklink_db`
- **Containers**: Docker is used to run MongoDB and the backend service

## Control Flow
1. User submits the quicklink creation form in the frontend.
2. Frontend sends a `POST /api/quicklinks` request to the backend.
3. Backend validates input and stores the record in MongoDB.
4. Backend writes an audit event to the `audit` collection.
5. Frontend refreshes the quicklink list and shows feedback.
6. Console page retrieves audit events from `GET /api/quicklinks/audit` and quicklinks from `GET /api/quicklinks`.

## Data Model
### `quicklink` collection
- `quicklink`: string
- `target_url`: string
- `creation_date`: string / datetime
- `update_date`: string / datetime
- `tags`: string[]
- `usage_count`: number
- `blocked`: boolean

### `audit` collection
- `event_date`: string / datetime
- `event_desc`: string
- `quicklink`: string
- `event_type`: string

## Platform, Tools and Dependencies
- Java 17
- Spring Boot 3.2.x
- Gradle 8.5 for build
- MongoDB 6.x or compatible
- Docker for local container management
- HTML/CSS/Vanilla JavaScript for frontend

## Developer Guide
### Setup
1. Ensure Docker is running.
2. Start the MongoDB container with `docker run -d --name quicklink_db -p 27017:27017 mongo:latest`.
3. Build the backend with Gradle via Docker:
   - `docker run --rm -v d:/code/quicklink/gencode/backend:/home/gradle/project -w /home/gradle/project gradle:8.5-jdk17 gradle build --no-daemon`
4. Run the backend:
   - `docker run -d --name quicklink_backend --network gencode_quicklink-net -p 8080:8080 -e MONGO_URI=mongodb://quicklink_db:27017/quicklink_db -v d:/code/quicklink/gencode/backend/build/libs:/app -w /app eclipse-temurin:17-jre java -jar quicklink-backend.jar`

### Local Development
- Frontend files are in `gencode/ui`. Open `gencode/ui/index.html` in a browser and point it to `http://localhost:8080`.
- Backend source is in `gencode/backend/src/main/java`.
- Application config is in `gencode/backend/src/main/resources/application.yml`.

### Deployment Notes
- The backend uses `MONGO_URI` environment variable when available.
- Docker network connectivity is required when using a MongoDB container.

## Testing
- No unit tests are implemented yet.
- The backend build currently passes with `gradle build`.
- Manual API validation has been performed for `GET /api/quicklinks`.
