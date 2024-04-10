# Quiz Application

The Quiz Application is developed in conjunction with a voluntary project for the Fullstack Development course IDATT2105 at NTNU by a team of three students. This platform serves as a quiz bank, offering an intuitive interface for both creating and participating in quizzes. 

Our project was made using Java and Spring Boot for backend development, paired with Vue for the frontend. The design of the application ensures a seamless experience across both PC and mobile devices. 

Additionally, the project features mock data that includes three pre-created accounts, each populated with sample data to showcase the application's capabilities and facilitate immediate user engagement.

#### User account 1
- username: user1
- password: password

#### User account 2
- username: user2
- password: password

#### User account 3
- username: user3
- password: password

## Content
**1**. [App Features](#app-features)

**2**. [Installation](#installation)

**3**. [Documentation](#documentation)

**4**. [CI/CD](#CI/CD)

**5**. [Contributors](#contributors)

## App Features
- Create new user
- Login with existing user
- Create new quiz and add questions to the quiz
- Questions can be of the type multiple choice, true or false, or fill in the blank
- Play a quiz and get the results 
- Get an overview of all the quizzes you have made
- Search on quiz name 

## Installation

### Docker 
The project integrates Docker to encapsulate the different environments. This integration simplifies the setup process, allowing users to quickly and efficiently launch the database, the backend, and the frontend of the system with minimal effort.

### Database Setup with Mock Data
Our application comes with a pre-populated data.sql script that contains insert statements for mock data, which includes pre-made users and sample data entries. You need to inject this mock data into your database. Here is how you can do it:

1. Locate the data.sql script in the project repository
2. Open a database management tool
3. Connect to the database
4. Open a new SQL query tab and load the data.sql script content into it
5. Execute the script to populate the database with the mock data

### Backend
In order to run the backend you need to build and start the docker container:
```bash
docker compose up --build
```

To run the tests:
```bash
mvn test
```

### Frontend
Build and start the docker container to run the frontend:
```bash
docker compose up --build
```

To run vitest unit tests:
```bash
npm run test:unit
```

To run vitest with coverage:
```bash
npm run test:coverage
```

To run cypress tests:
```bash
npx cypress open
```

## Documentation
### Backend
The backend is developed using Java v17 and Spring boot framework v3 including a set of dependencies that have been important for the development.

Some of the most important are:
- **spring-boot-starter**: provides default configurations for application functionality
- **spring-boot-starter-test**: integrates essential testing libraries enabling unit and integration tests
- **lombok**: provides annotations to eliminate boilerplate code, such as constructors and getters/setters
- **jackson**: handles JSON parsing and generating for API communication
- **spring-security-test**: offers testing support, allowing the simulation of authenticated users in tests
- **h2**: an in-memory database, ideal for development and testing phases
- **java-jwt**: allows generating, parsing and and validation JSON Web Tokens, essential for implementing secure authentication and authorization
- **springdoc**: simplifies API documentation with OpenAPI 3, automatically generating a web-based UI for testing API endpoints
- **mockito**: enables mocking for unit tests
- **mysql-connector-j**: ensures connectivity and operations with MySQL databases 

### Frontend
The frontend is developed using Vuejs v3 complemented by a set of dependencies and libraries to enhance usability and performance.

Some of the most important are:
- **Axios**: gives ability to make API calls
- **Pinia**: for state management
- **Vue Router**: used for defining routes, enabling navigation within the application
- **Vitest**: provides a framework for unit testing 
- **Font awesome**: a library of icons, improving the user interface with visually appealing elements

### Database

![ER-diagram](/ER-diagram.pdf)

For testing purposes we use a H2 database as this is ideal for testing phases due to its simplicity in setup and tear down. 
For production purposes, a MySQL database is used. 


### API Documentation
Endpoints are documented using Swagger. Documentation can be accessed here:

[Swagger](http://localhost:8080/swagger-ui/index.html)

## CI/CD
Our project implements CI/CD practices to facilitate a smooth and efficient development workflow.

The Continuous Integration (CI) pipeline for our backend is automated to handle Maven builds, code linting, Docker image creation, and test execution upon commits and pull requests. For the frontend, similar triggers initiate jobs that set up the Node.js environment, manage dependencies, and conduct lint checks and unit testing. The workflows validate code integrity and maintain our commitment to quality with each update.

Continuous Deployment is currently set up to work locally. When changes are successfully integrated through our CI pipeline, the updated Docker images are created and can be run on any developer's machine using Docker Compose. This local deployment process ensures that all team members can work with the application in a consistent environment, mirroring production settings.

We adopt a feature branch workflow, where new features and fixes are developed in separate branches, derived from our main development branch (dev). This approach allows our team members to work on various aspects of the project simultaneously without interfering with the stable version of the application.

Development Branch (dev): Serves as the primary branch where the latest developments are merged. This branch is continuously integrated and tested through our CI pipeline.

Master Branch: Reserved for deployment-ready code. Once features and updates are thoroughly tested and validated in the dev branch, they are merged into the master branch. The code in the master branch represents the version of our application that is ready for production deployment.

By implementing these CI/CD practices, we ensure that our development process is efficient, our team can collaborate effectively, and our application remains robust and reliable at all times.

## Contributors

- Eline Evje
- Ina Martini
- Malin H. Høli
