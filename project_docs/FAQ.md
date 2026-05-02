# Quicklink FAQ

## How do I create a quicklink?
Open `gencode/ui/index.html` in a browser, go to the Home page, enter a unique quicklink name and a target URL, then click Create Quicklink.

## Where is the backend API?
The backend runs at `http://localhost:8080` and exposes:
- `GET /api/quicklinks`
- `POST /api/quicklinks`
- `PUT /api/quicklinks/{quicklink}`
- `DELETE /api/quicklinks/{quicklink}`
- `GET /api/quicklinks/audit`

## Which database does the app use?
The app uses MongoDB and the database is named `quicklink_db`.

## Which collections are available?
- `quicklink`
- `audit`

## How do I connect to MongoDB?
Use `mongodb://localhost:27017` if Docker mapped the container port to the host. Inside Docker, use the MongoDB container hostname, for example `quicklink_db`.

## Can I see audit events?
Yes. The Console page displays audit events and supports filtering by event type and quicklink name.

## What happens when I delete a quicklink?
The backend removes the quicklink document from the `quicklink` collection and writes a DELETE event into the `audit` collection.

## How do I block or unblock a quicklink?
Use the Console page manage panel. Click the Block/Unblock button for the quicklink.

## What should I do if a quicklink already exists?
The backend rejects duplicate quicklink names. Choose a new unique name and try again.
