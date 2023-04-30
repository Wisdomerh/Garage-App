# Garage-App

## Overview
Kotlin Garage App is a console application with a user-friendly menu system that allows users to manage their garage inventory, track expenses, and schedule maintenance tasks. The application is built using the Kotlin programming language and comes with various features such as data persistence, logging capabilities, and JUnit tests.

## Features
The application comes with the following features:

## Inventory Management 
Users can add, edit, and delete cars from their garage inventory, view detailed information about each car, such as make, model, and year.
Expense Tracking: Users can track their expenses related to their cars, including fuel, repairs, and other costs.
Maintenance Scheduling: Users can schedule maintenance tasks such as oil changes, tire rotations, and more.
Data Persistence: User data is persisted to disk using SQLite, allowing users to access their garage information across multiple sessions.
Logging: The application includes logging capabilities through the SLF4J library, enabling easy debugging and troubleshooting.
JUnit Tests: The application includes JUnit tests for each major feature, ensuring that the app is working as expected.

# Releases
## Version 1.0
The initial version of the application included basic menu items for inventory management, expense tracking, and maintenance scheduling. The application did not have any persistence or logging features.

## Version 2.0
Version 2.0 of the application introduced SQLite database persistence for user data, as well as logging capabilities through SLF4J. The application also included more detailed information for each car in the inventory and added support for tracking expenses related to each car.

## Version 3.0
Version 3.0 of the application introduced advanced filtering options for the inventory, allowing users to filter by make, model, year, and more. The application also included new menu items for managing maintenance tasks and added support for scheduling recurring maintenance events.

# Installation
To install the application, follow these steps:

Clone this repository: https://github.com/Wisdomerh/Garage-App.git
Open the project in your preferred IDE, such as IntelliJ IDEA.
Build and run the application from the command line using Gradle: ./gradlew run

#Usage
Once the application is running, use the main menu to navigate to the desired feature (inventory management, expense tracking, or maintenance scheduling).
Follow the prompts to add, edit, or delete information related to your garage.
Use the advanced filtering options to search for specific cars or maintenance events.
