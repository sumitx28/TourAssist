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
