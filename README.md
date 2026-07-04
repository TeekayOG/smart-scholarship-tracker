# Smart Scholarship Tracker

Smart Scholarship Tracker is a Java application developed for the Software Construction group assignment.

## Scenario

Scenario 3: Smart Scholarship Tracker

The system manages scholarship applications, checks student eligibility using GPA and household income criteria, categorises application outcomes, and displays status notifications.

## Main Features

- Student scholarship application form
- Scholarship eligibility checking
- Multiple scholarship types
- Status tracking
- Notification system
- Java Swing GUI
- External REST API integration
- JUnit 5 testing
- JaCoCo coverage reporting
- GitHub Actions CI

## Technologies

- Java 17
- Maven
- Java Swing
- JUnit 5
- JaCoCo
- Gson
- GitHub Actions

## How to Run

```bash
mvn clean package
java -jar target/smart-scholarship-tracker-1.0-SNAPSHOT.jar
```

## How to Test

```bash
mvn test
```

## Project Structure

```text
src/main/java/com/scholarshiptracker/
  model/
  strategy/
  observer/
  service/
  controller/
  view/
  api/
  exception/
```

The structure separates the application into model, service, controller, view, API, strategy, observer, and exception layers.
