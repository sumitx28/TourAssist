import React from "react";
import { Typography, styled, useTheme } from "@mui/material";

const DetailsContainer = styled("div")(({ theme }) => ({
  backgroundColor: theme.palette.background.paper,
  padding: theme.spacing(2),
  borderRadius: theme.spacing(1),
  boxShadow: theme.shadows[3],
  marginBottom: theme.spacing(2),
}));

const SourceDestination = styled("strong")(({ theme }) => ({
  color: "blue",
}));

const TripDetails = ({ source, destination, startDate, endDate }) => {
  const theme = useTheme();

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString();
  };

  const getTripDuration = () => {
    if (startDate && endDate) {
      const start = new Date(startDate);
      const end = new Date(endDate);

      const durationInDays = Math.ceil((end - start) / (1000 * 60 * 60 * 24));

      return `${durationInDays}D/${durationInDays + 1}N`;
    }

    return "N/A";
  };

  return (
    <DetailsContainer>
      <Typography variant="body1" gutterBottom>
        <SourceDestination>From:</SourceDestination> {source.city},{" "}
        {source.country}
      </Typography>
      <Typography variant="body1" gutterBottom>
        <SourceDestination>To:</SourceDestination> {destination.city},{" "}
        {destination.country}
      </Typography>
      <Typography variant="body1" gutterBottom>
        <SourceDestination>Trip Duration:</SourceDestination>{" "}
        {getTripDuration()}
      </Typography>
      <Typography variant="body1" gutterBottom>
        <SourceDestination>Trip Start Date:</SourceDestination>{" "}
        {formatDate(startDate)}
      </Typography>
      <Typography variant="body1" gutterBottom>
        <SourceDestination>Trip End Date:</SourceDestination>{" "}
        {formatDate(endDate)}
      </Typography>
    </DetailsContainer>
  );
};

export default TripDetails;
