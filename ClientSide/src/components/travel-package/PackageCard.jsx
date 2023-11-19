import React, { useState } from "react";
import {
  Card,
  CardContent,
  Grid,
  Typography,
  Button,
  CircularProgress,
} from "@mui/material";
import TripDetails from "./TripDetails";
import ActivityDetail from "./ActivityDetail";
import TravelMode from "./TravelMode";
import Stay from "./Stay";
import TourGuide from "./TourGuide";

const PackageCard = ({
  packageData,
  finalPackage,
  handleSwitchChange,
  handleBookClick,
}) => {
  const [isBooking, setBooking] = useState(false);

  const handleButtonClick = async () => {
    // Add any additional logic you need before booking
    setBooking(true);

    // Simulate asynchronous action (e.g., API call)
    await new Promise((resolve) => setTimeout(resolve, 1000));

    // Call handleBookClick function
    handleBookClick();

    // Reset the booking state after the action is completed
    setBooking(false);
  };

  return (
    <Card sx={{ width: "100%", borderRadius: "15px" }}>
      <CardContent>
        {packageData && (
          <Grid container spacing={2}>
            {/* Left side of the screen */}
            <Grid
              item
              xs={12}
              md={6}
              container
              direction="column"
              justifyContent="center"
            >
              <img
                src={packageData.mediaPath[0].media}
                alt=""
                style={{
                  width: "100%",
                  height: "250px",
                  objectFit: "cover",
                  borderRadius: "10px",
                }}
              />

              <Typography
                variant="h4"
                gutterBottom
                style={{ color: "#3f51b5", marginTop: 15 }}
              >
                {packageData.packageName}
              </Typography>

              <Typography variant="h6" gutterBottom>
                By: {packageData.agentDetails.companyName}
              </Typography>

              <TripDetails
                source={packageData.sourceDetails}
                destination={packageData.destinationDetails}
                startDate={packageData.tripStartDate}
                endDate={packageData.tripEndDate}
              />
              <Grid
                item
                container
                direction="column"
                justifyContent="center"
                alignItems="center"
              >
                <Typography variant="h5">
                  <strong>${finalPackage.pricePerPerson}</strong>/Person*
                </Typography>
              </Grid>
            </Grid>

            {/* Right side of the screen */}
            <Grid item xs={12} md={6}>
              <ActivityDetail
                activities={packageData.activity}
                handleSwitchChange={handleSwitchChange}
              />

              <TravelMode
                travelMode={packageData.transportationDetails}
                handleSwitchChange={handleSwitchChange}
              />

              <Stay
                stay={packageData.stay}
                handleSwitchChange={handleSwitchChange}
              />

              <TourGuide
                tourGuide={packageData.tourGuide}
                handleSwitchChange={handleSwitchChange}
              />

              <Button
                variant="contained"
                color="primary"
                onClick={handleButtonClick}
                style={{ marginTop: 15, width: "100%" }}
                disabled={isBooking}
              >
                {isBooking ? (
                  <CircularProgress size={24} color="inherit" />
                ) : (
                  "Book Now"
                )}
              </Button>
            </Grid>
          </Grid>
        )}
      </CardContent>
    </Card>
  );
};

export default PackageCard;
