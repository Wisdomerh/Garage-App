# Garage-App
<img src="https://camo.githubusercontent.com/2092bf04f4f09eaf8abc1b971bc9c9e151866592b37958752a74899a0f6a0ce0/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f496e74656c6c694a253230494445412d3030303030302e7376673f7374796c653d666f722d7468652d6261646765266c6f676f3d496e74656c6c694a2d49444541266c6f676f436f6c6f723d7768697465" alt="Development" data-canonical-src="https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=for-the-badge&amp;logo=IntelliJ-IDEA&amp;logoColor=white" style="max-width: 100%;">  <a href="https://kotlinlang.org/" rel="nofollow"><img src="https://camo.githubusercontent.com/9d7c5c1024bd88ccedf534a897c618fd381363534c74fa16c681130c359683b7/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4b6f746c696e2d3746353246462e7376673f7374796c653d666f722d7468652d6261646765266c6f676f3d4b6f746c696e266c6f676f436f6c6f723d7768697465" alt="Language" data-canonical-src="https://img.shields.io/badge/Kotlin-7F52FF.svg?style=for-the-badge&amp;logo=Kotlin&amp;logoColor=white" style="max-width: 100%;"></a>

## Overview
Kotlin Garage App is a console application with a user-friendly menu system that allows users to manage their garage inventory, track expenses, and schedule maintenance tasks. The application is built using the Kotlin programming language and comes with various features such as data persistence, logging capabilities, and JUnit tests.

## Features
The application comes with the following features:

## Main Menu:
         > ----------------------------------
         > |         GARAGE APP             |
         > ----------------------------------
         > |    MENU                        |
         > |   1) Add Car or Part           |
         > |   2) List Car or Part          |
         > |   3) Update Car or Part        |
         > |   4) Remove Car or Part        |
         > |   5) Search Cars Or Parts      |
         > |   6) Calculate price of parts  |
         > |   21) Save                     |
         > |   22) Load                     |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>>
         
## Add menu:
            > ----------------------------------
            > |           ADD MENU             |
            > ----------------------------------
            > |    1) Add new car              |
            > |    2) Add new part             |
            > |    3) Add part to existing car |
            > |    0) Return to Main Menu      |
            > ----------------------------------
            
## Remove Menu:
            > ----------------------------------
            > |         REMOVE MENU            |
            > ----------------------------------
            > |   1) Remove Car                |
            > |   2) Remove Part               |
            > |   0) Return to Main Menu       |
            > ----------------------------------
            > 
            
            ### More features withtin the app!!

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

Clone this repository: `git clone https://github.com/Wisdomerh/Garage-App.git`
Open the project in your preferred IDE, such as IntelliJ IDEA.
Build and run the application from the command line using Gradle: ./gradlew run

#Usage
Once the application is running, use the main menu to navigate to the desired feature (inventory management, expense tracking, or maintenance scheduling).
Follow the prompts to add, edit, or delete information related to your garage.
Use the advanced filtering options to search for specific cars or maintenance events.
