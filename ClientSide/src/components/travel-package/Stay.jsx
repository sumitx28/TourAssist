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

const Stay = ({ stay, handleSwitchChange }) => {
  return (
    <DetailsContainer>
      <Typography variant="h6" gutterBottom>
        <strong>Stay</strong>
      </Typography>
      <ActivityItem>
        <ColoredDay>{stay.resortMaster.resortName}</ColoredDay>
        <Typography>
          {stay.suiteMaster.suiteType} ${stay.price}
        </Typography>
        {stay.isCustomizable && (
          <Switch
            color="primary"
            onChange={() => handleSwitchChange("stay")}
            defaultChecked
          />
        )}
      </ActivityItem>
    </DetailsContainer>
  );
};

export default Stay;
