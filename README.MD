# TourAssist: Streamlining Travel Experiences

TourAssist is a web application designed to empower tour agents to enhance their tour packages and enable customers to compare and personalize these packages according to their preferences.

## Project Overview

[Detailed Project Requirements](https://dalu.sharepoint.com/:p:/r/teams/CSCI5308_Fall2023/_layouts/15/Doc.aspx?sourcedoc=%7BB2F1C7A0-286C-491A-B9E0-598ADE2064E0%7D&file=Group_9_TourAssist.pptx&action=edit&mobileredirect=true "Project description")

### Backend Dependencies

1. 🚀 **Install [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html):** 
2. 🛠️ **Install [Maven (v3.1.4)](https://formulae.brew.sh/formula/maven):**
   - `brew install maven`
3. 🗃️ **Setup [MySQL Locally](https://www.mysql.com/downloads/):** 
- [Run setup SQL script](https://git.cs.dal.ca/courses/2023-fall/csci-5308/Group15/-/blob/main/ServerSide/TourAssist/sql/db-structure.sql)
4. 🐳 **Install [Docker](https://www.docker.com/products/docker-desktop/):**

### Backend Setup and Deployment

1. Run tests:

   `mvn clean test`

2. Package Jar:

   `mvn clean package`

3. Build docker image

   `docker build -t backend-app-image TourAssist-backend/`

4. Run docker container

   `docker run -d -p 8080:8080 --name tour-assist-be backend-app-image`

Backend API should be accessible at http://localhost:8080

## Frontend Setup and Deployment

TourAssist requires [Node.js](https://nodejs.org/) v18+ to run. 🚀

Check your node & npm version using:

`node -v`

`npm -v`

## Run Locally

1. Navigate to TourAssist-frontend directory

   `cd TourAssist-frontend `

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

   `docker build -t frontend-app-image TourAssist-frontend/`

2. Run docker container

   `docker run -d -p 5173:5173 -e ENV=development --name tour-assist-fe frontend-app-image`

Frontend should be accessible at http://localhost:5173

# Use Cases

| Use Case | Description |
|----------|-------------|
| Customer and Agent Signup | This feature allows both agents and customers to sign up with their basic details. Passwords are encrypted and stored securely in the database. Details captured include: <br> 1. Name <br> 2. Email <br> 3. Password <br> 4. Mobile <br> 5. Verification Document (For Agents) |
| Login and Logout | This use case employs JWT-based stateless implementation. Users can log in using their email and password. Upon login, a unique JWT token is generated. Users can also logout, which invalidates the current JWT for that user. |
| Create Package | Agents can create unique, customizable tour packages. Each item in the package has an associated price. Details include: <br> 1. Package Name <br> 2. Package Media <br> 3. Trip Duration <br> 4. Trip Source and Destination <br> 5. Resort <br> 6. Activities <br> 7. Travel <br> 8. Tour Guide <br> 9. Package Media (Images) |
| Booking and Payment | Customers can customize packages and proceed with booking. Payment flow is initiated and dummy payment interface is provided for capturing payment details. Booking and payment status are updated upon completion. |
| Search and Filter | Users can search for travel packages based on destination and dates. Sorting options include: <br> 1. Sort by Name <br> 2. Sort by Price (Highest/Lowest) <br> 3. Sort by User Rating <br> Filter options include: <br> 1. Filter by User Rating <br> 2. Filter by Price Range <br> 3. Filter by Agent/Company <br> 4. Filter by Customization Availability |
| View and Edit Customer Profile | Users can view and edit their profiles, updating information such as email and contact number. |
| Booking Details | Users can view their booking history, including past and upcoming bookings. |
| Forgot Password | Users can reset their password by providing their email. A reset password link is sent to the email address provided. |
| Details Page of Travel Package | Details of travel packages include source, destination, dates, activities, transport details, stay amenities, and agent contacts. Users can customize packages and proceed to book seamlessly. |
| Agent Dashboard | Agents have access to a comprehensive dashboard showing past and upcoming bookings, customer details, and earnings analysis. |
