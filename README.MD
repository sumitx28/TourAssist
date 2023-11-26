# TourAssist

A web-app letting tour agents to list their customizable tour packages and customer to compare those packages and customize it as per their needs.

# Overview

[Understand the project requirements.](https://dalu.sharepoint.com/:p:/r/teams/CSCI5308_Fall2023/_layouts/15/Doc.aspx?sourcedoc=%7BB2F1C7A0-286C-491A-B9E0-598ADE2064E0%7D&file=Group_9_TourAssist.pptx&action=edit&mobileredirect=true "Project description")

## LIVE DEPOLYED APPLICATION

Please ensure you are connected to [Dal VPN](https://dalu.sharepoint.com/sites/its/SitePages/vpn.aspx?xsdata=MDV8MDF8fDExMTQ0MjBhYjNkOTRlZGJkOGNmMDhkYmJmOTRmZGM3fDYwYjgxOTk5MGI3ZjQxMmQ5MmEzZTE3ZDhhZTllM2UwfDB8MHw2MzgzMTQ0MTg4MzY4NTQ5OTF8VW5rbm93bnxWR1ZoYlhOVFpXTjFjbWwwZVZObGNuWnBZMlY4ZXlKV0lqb2lNQzR3TGpBd01EQWlMQ0pRSWpvaVYybHVNeklpTENKQlRpSTZJazkwYUdWeUlpd2lWMVFpT2pFeGZRPT18MXxMM1JsWVcxekx6RTVPbVpKTUc5R1NEWk5RelZUUnpVdGRVeHVabE42UTJWbll6UjRVelZNUWpOSWJXTXRPSG94VWs5aWRYTXhRSFJvY21WaFpDNTBZV04yTWk5amFHRnVibVZzY3k4eE9UbzBZV1UwTkRGak0yVTFZVEUwWW1FNE9HRTVNMlZtTkROa016VmtOVFZqTkVCMGFISmxZV1F1ZEdGamRqSXZiV1Z6YzJGblpYTXZNVFk1TlRnME5UQTRNalF5TXc9PXwxMzZlZTlmMmI0OTc0NzgxNzY2OTA4ZGJiZjk0ZmRjNXxkMmZjMDcxM2M3YTg0ZGEyYmIyMDZkMDUyYTA4ZjZhMg%3D%3D&sdata=OFdmNHhPL1NXcE9zSGM3c28wWUV5Q1pnVnJ1eWJSZmp5em9lT2NVYWdycz0%3D):

URL: http://172.17.0.148:8074

## BACKEND - DEPENDENCIES

1. [Install Java 17 or higher](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. [Install Maven](https://formulae.brew.sh/formula/maven) - version: 3.1.4
   - brew install maven
3. [Setup MySQL Locally](https://www.mysql.com/downloads/)
   - [Run setup SQL script](https://git.cs.dal.ca/courses/2023-fall/csci-5308/Group15/-/blob/main/ServerSide/TourAssist/sql/db-structure.sql)
4. [Docker](https://www.docker.com/products/docker-desktop/)

## BACKEND - BUILD/DEPLOYMENT INSTRUCTIONS

1. Run tests:

   `mvn clean test`

2. Package Jar:

   `mvn clean package -Dspring.profiles.active=prod -DskipTests`

3. Build docker image

   `docker build -t backend-app-image ServerSide/TourAssist/`

4. Run docker container

   `docker run -d -p 8080:8080 --name tour-assist-be backend-app-image`

Backend API should be accessible at http://localhost:8080

## BACKEND - DATABASE DESIGN

- [Wiki Link](https://git.cs.dal.ca/courses/2023-fall/csci-5308/Group15/-/wikis/Database-Design)

## INITIAL CODE SMELLS

- [Link](https://git.cs.dal.ca/courses/2023-fall/csci-5308/Group15/-/wikis/Implementation-and-Design-Smell-and-Architecture-Designite-Reports)

## CODE SMELLS POST REFACTORING

- [TODO]

## Frontend

TourAssist requires [Node.js](https://nodejs.org/) v18+ to run.

Check your node & npm version using:

`node -v`

`npm -v`

<<<<<<< HEAD
## Run Locally

1. Navigate to ClientSide directory

   `cd ClientSide `

2. Install Node Dependencies

   `npm install`

3. Set Environment variable to development

   - For Mac/Linux: `export ENV=development`
   - For Windows: `$Env:ENV = 'development'`

4. Run React App

   `npm run dev`

Frontend should be accessible at http://localhost:5173

## Run Using Docker

1. Build docker image

   `docker build -t frontend-app-image ClientSide/`

2. Run docker container

   `docker run -d -p 5173:5173 -e ENV=development --name tour-assist-fe frontend-app-image`

Frontend should be accessible at http://localhost:5173
=======

# Use Cases:


### Customer and Agent Signup Use Case
This feature lets both Agent and Customer to signup with their basic details to use the application. Password gets encrypted and stored in the database in this Use Case.
Below are the major details captured for signup:
1. Name
2. Email
3. Password
4. Mobile
5. Verification Document (For Agents)

### Login  and Logout Use Case 

This use cases uses the JWT based stateless implementation. The user can login by providing their email and password.
Upon login, a valid JWT token is generated in the authentication response, which will be unique to the user.
Likewise, the user can also logout from the application which invalidate the current JWT for that user via API call.

### Create Package Use Case
This feature builds the core database structure of the entire application from technical perspective. With this use case, agent can create a unique package which are customisable. Each item has a price associated with it. Below are the package items which can be offered:
1. Package Name
2. Package Media - Which gets stored on Cloudinary database.
3. Trip duration details
4. Trip source and destination
5. Resort - Customizable
6. Activities - Customizable
7. Travel - Customizable
8. Tour Guide - Customizable
9. Package Media - Images of the package


### Booking and Payment Use Case
This feature deals with the package booking and payment flow by customer. Here, the Customer can do all the customizations for all the customizable items, and the package created accordingly. After, the booking is done, payment flow initiates and dummy payment interface is built on front-end to capture the payment details.
Post the payment, booking and payment status are changed to CONFIRM and SUCCESS respectively.


### Search and Filter Use Case:

A user can search for travel packages for the required destination and the from and to dates so that the 
appropriate list of travel packages can be displayed.

The API also supports multiple sort functionalities listed below:
Sort Functionality:

1. Sort by name
2. Sort by price (highest first)
3. Sort by price (lowest first)
4. Sort by user rating

### Filter Functionality Use Case:

Filter by user rating
1. Filter by price range
2. Filter by agent/company
3. Filter by customization availability

The API takes in parameters such as sortBy and FilterBy (which can be configured in multiple combinations) to return 
the list of travel packages as per the criteria 
specified in the request.

## View and Edit Customer Profile Use Case:

This feature allows the app user to view/edit their profile, so that the information can be up to date in the application

1. User can view their profile and edit their information such as email, contact number.
The API takes the appUserId and the email or the phone number to be updated in the request body and returns the status
with the field names updated.

### Booking Details Use Case:

A user can view their booking history which includes both past and upcoming bookings of the customer. This feature 
allows the user to keep track of their past and future bookings in order to have a better user experience.

The API takes the appUserId has a request parameter and fetches the required booking details.

### Forgot Password Use Case:
Here a user can enter the email Id and they will receive an email with reset password link. 
Once they navigate to that URL, with the help of Base64 encoded token, the user is verified and an option to reset the password is provided. 

### Details page of travel Package Use Case:
Here input source, destination, proposed dates, day-by-day activities, transport details, stay amenities, and agent contacts are showcased. 
One can also customize packages; default includes all. It also includes images of agent and company name. 'Book' button seamlessly redirects users to the payment page for a hassle-free experience. 

### Agent Dashboard Use Case: 
This dashboard support agents with a comprehensive dashboard that includes, view past and upcoming bookings, access customer details for deeper insights, analyse earnings monthly, yearly, etc.
It provides a robust overview for effective business analysis.
>>>>>>> 13f8cd7883a88424571fcab3c1f8e82199d742e2
