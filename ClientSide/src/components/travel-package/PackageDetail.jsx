import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import {
  Card,
  CardContent,
  Container,
  Typography,
  Grid,
  Button,
  Paper,
} from "@mui/material";
import travelPackage from "./travelPackage.json";
import TripDetails from "./TripDetails";
import ActivityDetail from "./ActivityDetail";
import TravelMode from "./TravelMode";
import Stay from "./Stay";
import TourGuide from "./TourGuide";

const PackageDetail = () => {
  const { id } = useParams();
  // Fetch from backend using id param.
  const packageData = travelPackage;

  const [finalPackage, setFinalPackage] = useState({
    pricePerPerson: 0,
    deselectedActivities: [],
    packageData: {},
    deselectedTravelMode: false,
    deselectedStay: false,
  });

  const handleBookClick = () => {
    alert("Book");
    console.log(finalPackage);
  };

  const handleSwitchChange = (id) => {
    if (id === "travelMode") {
      setFinalPackage({
        ...finalPackage,
        deselectedTravelMode: !finalPackage.deselectedTravelMode,
      });
    } else if (id === "stay") {
      setFinalPackage({
        ...finalPackage,
        deselectedStay: !finalPackage.deselectedStay,
      });
    } else if (id === "tourGuide") {
      setFinalPackage({
        ...finalPackage,
        deselectedTourGuide: !finalPackage.deselectedTourGuide,
      });
    } else {
      const index = finalPackage.deselectedActivities.indexOf(id);

      if (index === -1) {
        setFinalPackage({
          ...finalPackage,
          deselectedActivities: [...finalPackage.deselectedActivities, id],
        });
      } else {
        const updatedDeselectedActivities = [
          ...finalPackage.deselectedActivities,
        ];
        updatedDeselectedActivities.splice(index, 1);

        setFinalPackage({
          ...finalPackage,
          deselectedActivities: updatedDeselectedActivities,
        });
      }
    }
  };

  useEffect(() => {
    let cost = 0;

    if (packageData) {
      const travelCost = packageData.transportationDetails
        ? finalPackage.deselectedTravelMode
          ? 0
          : packageData.transportationDetails.price
        : 0;

      const activitiesCost = packageData.activities.reduce(
        (total, activity) => {
          if (
            !finalPackage.deselectedActivities.includes(
              activity.activityMasterId
            )
          ) {
            total += activity.price;
          }
          return total;
        },
        0
      );

      const stayCost = packageData.stay
        ? finalPackage.deselectedStay
          ? 0
          : packageData.stay.price
        : 0;

      const tourGuideCost = packageData.tourGuide
        ? finalPackage.deselectedTourGuide
          ? 0
          : packageData.tourGuide.tourGuideMaster.price
        : 0;

      cost = travelCost + activitiesCost + stayCost + tourGuideCost;
    }

    setFinalPackage({
      ...finalPackage,
      pricePerPerson: cost,
    });
  }, [
    packageData,
    finalPackage.deselectedActivities,
    finalPackage.deselectedTravelMode,
    finalPackage.deselectedStay,
    finalPackage.deselectedTourGuide,
  ]);

  return (
    <Container maxWidth="md" sx={{ marginTop: 10 }}>
      <Grid container justifyContent="center" spacing={2}>
        <Grid item xs={12} md={6}>
          <Card sx={{ width: "100%" }}>
            <CardContent>
              {packageData && (
                <div>
                  <Typography variant="h6" gutterBottom>
                    <img src="https://picsum.photos/420/200" alt="" />
                  </Typography>

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
                </div>
              )}
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={6}>
          <Card sx={{ width: "100%" }}>
            <CardContent>
              <Typography variant="h4" gutterBottom>
                {packageData.packageName}
              </Typography>
              {packageData && (
                <div>
                  <Typography variant="h6" gutterBottom>
                    Offered By: {packageData.agentDetails.companyName}
                  </Typography>

                  <TripDetails
                    source={packageData.sourceDetails}
                    destination={packageData.destinationDetails}
                    startDate={packageData.tripStartDate}
                    endDate={packageData.tripEndDate}
                  />

                  <ActivityDetail
                    activities={packageData.activities}
                    handleSwitchChange={handleSwitchChange}
                  />

                  <Paper elevation={3} sx={{ padding: 2, marginBottom: 2 }}>
                    <Typography variant="h6" gutterBottom>
                      Total Travel Cost:
                    </Typography>
                    <Typography variant="h5">
                      {" "}
                      <strong>${finalPackage.pricePerPerson}</strong>/Person*
                    </Typography>
                  </Paper>

                  <Button
                    variant="contained"
                    color="primary"
                    onClick={handleBookClick}
                  >
                    Book
                  </Button>
                </div>
              )}
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Container>
  );
};

export default PackageDetail;
