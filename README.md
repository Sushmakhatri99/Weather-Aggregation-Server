# Weather Aggregation Server

## Overview
This project implements a distributed system that aggregates and distributes weather data using a RESTful API. The system consists of an `AggregationServer`, `Client`, and `ContentServer`. The server handles multiple `GET` and `PUT` requests concurrently, supports data expiration, and uses Lamport clocks for synchronization.

## Project Structure
- **AggregationServer.java**: Main server that handles `GET` and `PUT` requests for weather data.
- **Client.java**: A client that retrieves weather data from the server using a `GET` request.
- **ContentServer.java**: A content server that sends weather data to the aggregation server using a `PUT` request.
- **LamportClock.java**: Implements a Lamport Clock for synchronization.
- **WeatherData.java**: Represents the weather data with attributes like `id`, `name`, `air_temp`, and `lastUpdated`.

## How to Run the System

### 1. Prerequisites
- Make sure Java 11 (or later) is installed on your system.
- Ensure that the `JUnit` library is added to the classpath for unit testing.
- You will need `junit-4.12.jar` and `hamcrest-core-1.3.jar` for testing.

### 2. Compilation
Open your terminal and navigate to the project directory. Then compile the Java files:

```bash
javac -d bin src/main/java/*.java
# Step 3: Start the Aggregation Server
java -cp bin main.java.AggregationServer

# Step 4: Run the Content Server to Send a PUT Request
java -cp bin main.java.ContentServer

# Step 5: Run the Client to Send a GET Request
java -cp bin main.java.Client
# Step 6: Add README.md to Git, Commit, and Push
git add README.md
git commit -m "Added README file"
git push origin main

# Step 7: Test Expiry of Data (manually)
# - Run the Content Server to add data.
# - Wait 30 seconds.
# - Run the Client to verify that the data has expired.