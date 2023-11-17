# TourAssist

A web-app letting tour agents to list their customizable tour packages and customer to compare those packages and customize it as per their needs.

## DEPLOYMENT

Please ensure you are connected to Dal VPN

URL: http://172.17.0.148:8074

# Overview

[Understand the project requirements.](https://dalu.sharepoint.com/:p:/r/teams/CSCI5308_Fall2023/_layouts/15/Doc.aspx?sourcedoc=%7BB2F1C7A0-286C-491A-B9E0-598ADE2064E0%7D&file=Group_9_TourAssist.pptx&action=edit&mobileredirect=true "Project description")

# Backend Setup - Local

1. Java 17
2. Spring boot: 3.1.4
3. [Steps to use Dal VPN](https://dalu.sharepoint.com/sites/its/SitePages/vpn.aspx?xsdata=MDV8MDF8fDExMTQ0MjBhYjNkOTRlZGJkOGNmMDhkYmJmOTRmZGM3fDYwYjgxOTk5MGI3ZjQxMmQ5MmEzZTE3ZDhhZTllM2UwfDB8MHw2MzgzMTQ0MTg4MzY4NTQ5OTF8VW5rbm93bnxWR1ZoYlhOVFpXTjFjbWwwZVZObGNuWnBZMlY4ZXlKV0lqb2lNQzR3TGpBd01EQWlMQ0pRSWpvaVYybHVNeklpTENKQlRpSTZJazkwYUdWeUlpd2lWMVFpT2pFeGZRPT18MXxMM1JsWVcxekx6RTVPbVpKTUc5R1NEWk5RelZUUnpVdGRVeHVabE42UTJWbll6UjRVelZNUWpOSWJXTXRPSG94VWs5aWRYTXhRSFJvY21WaFpDNTBZV04yTWk5amFHRnVibVZzY3k4eE9UbzBZV1UwTkRGak0yVTFZVEUwWW1FNE9HRTVNMlZtTkROa016VmtOVFZqTkVCMGFISmxZV1F1ZEdGamRqSXZiV1Z6YzJGblpYTXZNVFk1TlRnME5UQTRNalF5TXc9PXwxMzZlZTlmMmI0OTc0NzgxNzY2OTA4ZGJiZjk0ZmRjNXxkMmZjMDcxM2M3YTg0ZGEyYmIyMDZkMDUyYTA4ZjZhMg%3D%3D&sdata=OFdmNHhPL1NXcE9zSGM3c28wWUV5Q1pnVnJ1eWJSZmp5em9lT2NVYWdycz0%3D)
4. Env for dev : spring.profiles.active=dev (VPN needs to be connected to access hosted DB), remove this profile from IntelliJ for local DB.

5. Backend should be running on http://localhost:8080

## Frontend Setup - Local

TourAssist requires [Node.js](https://nodejs.org/) v18+ to run.

Install the dependencies and devDependencies and start the server.

```sh
cd ClientSide
npm install
npm run dev
```

Frontend should be running on https://localhost:5173
