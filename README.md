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

## Demo Flow

The application currently demonstrates the main scholarship application flow:

1. Open the Smart Scholarship Tracker application.
2. Enter student details in the Application tab.
3. Select a scholarship type.
4. Submit the application.
5. View the eligibility result screen.
6. Open the Notifications tab to view the status-change notification.
7. Open the University API tab to fetch university data from an external REST API.

## External API

The project uses the public Universities API:

```text
http://universities.hipolabs.com/search?country=Malaysia
```

The API is used to demonstrate REST API integration and JSON parsing. If the API is unavailable, the application displays a user-friendly fallback message instead of showing a Java stack trace.

## Current Scholarship Rules

```text
Merit Scholarship:
GPA >= 3.70

Need-Based Scholarship:
GPA >= 3.00
Household income <= 4000

Balanced Scholarship:
GPA >= 3.30
Household income <= 6000
```

## MVC Structure

```text
Model:
Student, Scholarship, ScholarshipApplication, EligibilityResult

View:
ApplicationFormView, ResultView, NotificationView, UniversityLookupView, MainFrame

Controller:
ApplicationController

Service:
ApplicationService, EligibilityService, ApplicationValidator, UniversityLookupService
```
