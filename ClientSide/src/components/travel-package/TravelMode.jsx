import { Typography, styled, useTheme, Switch } from "@mui/material";

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

const TravelMode = ({ travelMode, handleSwitchChange }) => {
  const theme = useTheme();
  return (
    <DetailsContainer>
      <Typography variant="h6" gutterBottom>
        <strong>Travel Mode</strong>
      </Typography>
      <ActivityItem>
        <ColoredDay>{travelMode.mode}</ColoredDay>
        <Typography>
          {travelMode.isCustomizable
            ? `$${travelMode.price}`
            : `$${travelMode.price}`}
        </Typography>
        {travelMode.isCustomizable && (
          <Switch
            color="primary"
            onChange={() => handleSwitchChange("travelMode")}
            defaultChecked
          />
        )}
      </ActivityItem>
    </DetailsContainer>
  );
};

export default TravelMode;
