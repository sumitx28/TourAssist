// ActivityDetail.jsx
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

const ActivityDetail = ({ activities, handleSwitchChange }) => {
  return (
    <DetailsContainer>
      <Typography variant="h6" gutterBottom>
        <strong>Activities</strong>
      </Typography>
      {activities.map((activity, index) => (
        <ActivityItem key={index}>
          <ColoredDay>Day {index + 1}</ColoredDay>
          <Typography>
            {activity.activityMaster.activityName} ${activity.price}
          </Typography>
          {activity.isCustomizable && (
            <Switch
              color="primary"
              onChange={() => handleSwitchChange(activity.activityMaster.id)}
              defaultChecked
            />
          )}
        </ActivityItem>
      ))}
    </DetailsContainer>
  );
};

export default ActivityDetail;
