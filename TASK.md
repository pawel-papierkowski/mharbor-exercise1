# Mid Java Developer – Technical Exercise

Please complete the following exercise using **Java 21 and Spring Boot**.

The aim of this exercise is to assess code quality, automated testing, simplicity, and your approach to
changing business requirements.

Please use Git and make a separate commit after each step.

## Technical Requirements
Please use:
- Java 21
- Spring Boot
- Maven or Gradle
- JUnit
- Git

No database, authentication, or frontend is required.

Keep the solution simple and avoid unnecessary complexity.

Publish the completed solution in a Git repository and send us the link.

You may be asked to explain or make a small change to your solution during the technical interview.

## Step 1 – Journey Cost Calculator

Create a simple Spring Boot application with a REST endpoint that calculates the cost of a vehicle
journey.

The request should contain:
- Distance in kilometres
- Cost per kilometre

The following pricing rule applies:
- The first 100 km are charged at the standard rate.
- Any distance above 100 km receives a 10% discount.

Example

For a journey of 200 km at €0.25 per km:
```
First 100 km: 100 × €0.25 = €25.00
Remaining 100 km: 100 × €0.25 = €25.00
10% discount = €2.50
Discounted cost = €22.50
Total journey cost = €47.50
```

Please include automated tests covering the pricing rules and relevant boundary cases.

Make reasonable assumptions where necessary.

**Commit your solution before continuing to Step 2.**

## Step 2 – TransX Customer Discount

The business has introduced a special pricing agreement for a customer called **TransX**.

TransX receives an additional **5% discount on the final journey cost**.

Update or add automated tests to demonstrate the new behaviour.

