# README for Contributors

## Purpose
This document helps new contributors understand the Quicklink project structure, how to contribute, and which epics remain open.

## Project Structure
- `gencode/backend` - Spring Boot backend application
- `gencode/ui` - frontend static website
- `project_docs` - project documentation
- `product_design.md` - original design specification

## How to Contribute
1. Review `project_docs/solution_document.md` for architecture and setup.
2. Run the backend locally and verify the API at `http://localhost:8080`.
3. Open `gencode/ui/index.html` in a browser to verify the user interface.
4. Make changes in `gencode/backend` for backend logic or in `gencode/ui` for frontend UI.
5. Create incremental commits and describe changes clearly.

## Recommended Workflow
- Use feature branches for enhancements.
- Keep tasks small and focused.
- Add unit tests for backend changes whenever possible.
- Document new APIs or UI flows in `project_docs`.

## Pending Epics
### Epic: UI polish and UX
- Implement frontend pages and navigation
- Add form validation and user feedback
- Improve responsive layout and accessibility

### Epic: Backend enhancement
- Add unit tests for service and controller logic
- Implement usage count increment on redirect
- Add duplicate quicklink validation and improved error handling

### Epic: Data and audit quality
- Seed initial data for quicklinks and audit events
- Clean up audit event filtering and sorting
- Add MongoDB schema validation rules

## Notes
- The backend currently uses `MONGO_URI` to connect to MongoDB when provided.
- The frontend is static and expects the backend on `http://localhost:8080`.
