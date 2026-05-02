# quicklink

# Project Description
Quicklink is a web based application that allows users to create a short code for a various urls. Using the short code, all users of the application can use the short code to redirect to the target url. This application is very similar to popupar tinyurl.

## Project plan ##
- Store generated project plan in 'project_plan.md' under project_docs folder
- Create Epics and stores for UI, Backend code, tests, and other project tasks. Each task should not be more than 3 points
- for each epic, provide
    - functional objective of the epic
    - short summary of the acceptance criteria
    - total story points of all the tasks under that epic
- for each task, provide
    - specific description of the tasks in the title
    - short summary of the task in the task description
    - story points
- create a section for functional and technical risks in completing the project

## Architecture and file structure ##
- File structure
    - All the generated code should be created under 'gencode' folder
    - don't generate anything in root folder

/project_docs
        - solution_document.md: contains solution document with 
            - implementation solution including data model, control flow
            - platform, tools and dependencies needed to run this application
            - list of templates, frameworks, platforms used in the application
            - developer guide including various project components, installation and execution instructions
        - FAQ: FAQ of how to the quicklink app
        - README for contributors: how to contribute to project and major epics pending
/gencode
    /ui
    /backend
    /database_model
    /api
        - API file for test the various apis and the file should be postman-compatible

- Coding standards
    - create unit tests for all the generate code
    - Use mockito when generating the tests to mock dependencies
    - Modern clean code inspired by Google keep, tinyurl apps
    - Desktop first but mobile responsive
    - create a summary document of the unit tests created, and test results as test_result.md in project_docs folder

- functional specs
    - application should accept quicklink name, target_url and tags
    - application should not allow duplicate quicklink names
    - whenever there is a CRUD operation on quicklink, there should be audit event generated with corresponding event type in the audit collection

### User Interface ###
GOALS
    - generate code under 'ui' folder
    - use html/css/js only for creating the app
    - proper folder structure with seprate pages, styles, and scripts
    - Smooth UX: fast, minimal clicks, clear typography and simple spacing
    - Accesible UI (labels, focus states, keyboard navigation, semantic html)
    - When generating UI code, incorporate open-source templates: Download minimaxing from https://html5up.net/, and ensure CSS/JS paths are updated. Adapt forms and scripts to call /api/quicklinks endpoints.

PAGES/ROUTES (No framework)
Use a simply multi-page framework OR a single page with hash routing. Choose pages:
1. Home
    - Form should accept quicklink name, target_url and tags. Don't autogenerate the quicklink name
2. Console
    - Admin page that has functions to delete, block quicklink
    - Paginated list of 25 audit events should be displayed in the console page, sorted by descending audit creation date
    - the audit events table should be filterable by event type and quicklink name 
3. Help
    Contains FAQ
    Contact information for help: test@gmail.com

### Backend code ###
    - generate the code under 'backend' folder
    - use springboot for generating the code
    - create APIs for each of the CRUD operations
    - use gradle build file
    - connect to the mongodb database named 'quicklink_db' deployed in local docker container
    - Change bootJar to use the actual artifact name or explicitly configure it. In Docketfile, use the actual artifact name
    - store the gradlw wrapper in a subfolder for frequent use

### Database ###
- store any files in 'db' folder
- Deploy the model to local mongodb database 'quicklink_db' deployed in a local docker container
- generate a schema file for the database model and store in quicklink_db.json file

Database model
{
    "quicklink": {
        "quicklink": string,
        "target_url": string,
        "creation_date": "YYYY-MM-DD HH:mm:SSS",
        "update_date": "YYYY-MM-DD HH:mm:SSS",
        "tags": string[],
        "usage_count": number,
        "blocked": boolean
    },
    "audit": {
            "event_date": "YYYY-MM-DD HH:mm:SSS",
            "event_desc": string,
            "quicklink" : string,
            "event_type": string
    }
}

## Build and Test ##
- create a Dockerfile for frontend and backend with necessary parameters
- start the docker container 'quicklink_db'
- seed the database with initial values: 
    [
        {"quicklink":"google", "target_url":"https://www.google.com"},
        {"quicklink":"ddg", "target_url":"https://www.duckduckgo.com"},
    ]
- run the backend project in a docker container
- backend code
    - build the code using gradle 
    - create an executable jar file
    - run all the unit tests to verify that the code is successful
    
### Deploy locally

### Prompt to build the complete website

1. build the project
2. run the project
3. open the browser with the UI homepage