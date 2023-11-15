import React from "react";
import { Typography, styled, Switch } from "@mui/material";

const DetailsContainer = styled("div")(({ theme }) => ({
  backgroundColor: theme.palette.background.paper,
  padding: theme.spacing(2),
  borderRadius: theme.spacing(1),
  boxShadow: theme.shadows[3],
  marginBottom: theme.spacing(2),
}));

const ActivityItem = styled("div")({
  marginBottom: 10,
  display: "flex",
  alignItems: "center",
});

const ColoredDay = styled("strong")({
  color: "blue",
  marginRight: 10,
});

const TourGuide = ({ tourGuide, handleSwitchChange }) => {
  return (
    <DetailsContainer>
      <Typography variant="h6" gutterBottom>
        <strong>Tour Guide</strong>
      </Typography>
      <ActivityItem>
        <ColoredDay>{tourGuide.tourGuideMaster.tourGuideName}</ColoredDay>
        <Typography>
          {tourGuide.isCustomizable
            ? `$${tourGuide.tourGuideMaster.price}`
            : `$${tourGuide.tourGuideMaster.price}`}
        </Typography>
        {tourGuide.isCustomizable && (
          <Switch
            color="primary"
            onChange={() => handleSwitchChange("tourGuide")}
            defaultChecked
          />
        )}
      </ActivityItem>
    </DetailsContainer>
  );
};

export default TourGuide;
