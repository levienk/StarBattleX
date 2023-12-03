# Star Battle

This project includes both client and server code for the Star Battle game.

## Prerequisites
Before you begin, make sure you have installed:
- Docker
- Java Development Kit (JDK) 17
- Gradle

## Getting Started with Star Battle

### Step 1: Start the Database and Services
1. **Start Docker**:
    - Ensure the Docker service is active on your machine.
2. **Launch Services**:
    - Open a terminal and navigate to the project's root directory.
    - Run `docker compose up` to start all services, including MongoDB.
    - Wait for confirmation that all services are up and running.

### Step 2: Build and Run the Server
1. **Build the Project**:
    - In a new terminal window, navigate to the project's root directory.
    - Execute `./gradlew build` to build the project.
    - Verify the build process completes successfully.
2. **Run the Server**:
    - Start the server by running `./gradlew bootRun`.
    - Ensure there are no errors and the server is running by checking the terminal output.

### Step 3: Run the Client Application
1. **Start the Java Client**:
    - In a new command line window, within the project directory, execute `./gradlew run`.
    - This will launch the JavaFX client application.
    - Confirm the client starts and is able to connect to the server.

## Additional Information

### Server
- The server can also be started within IntelliJ by navigating to Tasks -> application -> bootRun in the IntelliJ Gradle window.

### Client
- The client code is located at `src/main/java/starbattle/client`.
- Alternatively, use Tasks -> Application -> Run in the IntelliJ Gradle window to start the Java client.

### Tests
- Test code is housed within `src/test/java`.
- To run tests in IntelliJ, use the green arrow next to the test files.
- To execute tests using Gradle, run `./gradlew test` in the terminal.

