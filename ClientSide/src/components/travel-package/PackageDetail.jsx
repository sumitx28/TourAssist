import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  Card,
  CardContent,
  Container,
  Typography,
  Grid,
  Button,
  Paper,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
} from "@mui/material";
import TripDetails from "./TripDetails";
import ActivityDetail from "./ActivityDetail";
import TravelMode from "./TravelMode";
import Stay from "./Stay";
import TourGuide from "./TourGuide";
import axios from "axios";
import API_URL from "../../../config/config";
import NavBar from "../commons/NavBar";

const PackageDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [finalPackage, setFinalPackage] = useState({
    pricePerPerson: 0,
    deselectedActivities: [],
    packageData: {},
    deselectedTravelMode: false,
    deselectedStay: false,
  });

  const [packageData, setPackageData] = useState(null);

  const [openDialog, setOpenDialog] = useState(false);
  const [userDetailsArray, setUserDetailsArray] = useState([
    {
      firstName: "",
      lastName: "",
      dateOfBirth: "",
    },
  ]);

  const openDialogHandler = () => {
    setOpenDialog(true);
  };

  const closeDialogHandler = () => {
    setOpenDialog(false);
  };

  const handleUserDetailsChange = (index, field, value) => {
    const updatedUserDetailsArray = [...userDetailsArray];
    updatedUserDetailsArray[index] = {
      ...updatedUserDetailsArray[index],
      [field]: value,
    };
    setUserDetailsArray(updatedUserDetailsArray);
  };

  const handleBookClick = () => {
    openDialogHandler();
  };

  const handleConfirmBooking = () => {
    if (!packageData) {
      console.error("Package data is not available.");
      return;
    }

    const bookingItemRequests = [];

    // Filter and include only selected activities in bookingItemRequests
    packageData.activity.forEach((activity) => {
      if (
        !finalPackage.deselectedActivities.includes(activity.activityMaster.id)
      ) {
        bookingItemRequests.push({
          itemName: "ACTIVITY",
          itemId: activity.activityMaster.id,
        });
      }
    });

    // Include selected guide
    if (!finalPackage.deselectedTourGuide && packageData.tourGuide) {
      bookingItemRequests.push({
        itemName: "GUIDE",
        itemId: packageData.tourGuide.id,
      });
    }

    // Include selected resort
    if (!finalPackage.deselectedStay && packageData.stay) {
      bookingItemRequests.push({
        itemName: "RESORT",
        itemId: packageData.stay.id,
      });
    }

    // Include selected transportation
    if (
      !finalPackage.deselectedTravelMode &&
      packageData.transportationDetails
    ) {
      bookingItemRequests.push({
        itemName: "TRANSPORTATION",
        itemId: packageData.transportationDetails.id || 1,
      });
    }

    const bookingData = {
      packageId: id,
      customerId: 3, // Replace with your actual customerId
      agentId: packageData.agentDetails.agentId,
      bookingItemRequests,
      guests: userDetailsArray.map((user) => ({
        firstName: user.firstName,
        lastName: user.lastName,
        dateOfBirth: new Date(user.dateOfBirth).toISOString(),
      })),
    };

    // Log or send the 'bookingData' object to your server
    console.log(bookingData);

    closeDialogHandler();
  };

  const handleAddTraveler = () => {
    setUserDetailsArray([
      ...userDetailsArray,
      { firstName: "", lastName: "", dateOfBirth: "" },
    ]);
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
    const fetchData = async () => {
      try {
        const response = await axios.get(`${API_URL}/api/v1/package/${id}`);
        setPackageData(response.data);
      } catch (error) {
        alert("Package does not exist!");
        navigate("/dashboard");
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    let cost = 0;

    if (packageData) {
      const travelCost = packageData.transportationDetails
        ? finalPackage.deselectedTravelMode
          ? 0
          : packageData.transportationDetails.price
        : 0;

      const activitiesCost = packageData.activity.reduce((total, activity) => {
        if (
          !finalPackage.deselectedActivities.includes(
            activity.activityMaster.id
          )
        ) {
          total += activity.price;
        }
        return total;
      }, 0);

      const stayCost = packageData.stay
        ? finalPackage.deselectedStay
          ? 0
          : packageData.stay.price
        : 0;

      const tourGuideCost = packageData.tourGuide
        ? finalPackage.deselectedTourGuide
          ? 0
          : packageData.tourGuide.price
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

  const dialogContent = (
    <div>
      <DialogTitle>Enter User Details</DialogTitle>
      <DialogContent>
        {userDetailsArray.map((user, index) => (
          <Grid container spacing={2} key={index}>
            <Grid item xs={4}>
              <TextField
                label={`Traveler ${index + 1} - First Name`}
                value={user.firstName}
                onChange={(e) =>
                  handleUserDetailsChange(index, "firstName", e.target.value)
                }
                fullWidth
              />
            </Grid>
            <Grid item xs={4}>
              <TextField
                label={`Traveler ${index + 1} - Last Name`}
                value={user.lastName}
                onChange={(e) =>
                  handleUserDetailsChange(index, "lastName", e.target.value)
                }
                fullWidth
              />
            </Grid>
            <Grid item xs={4}>
              <TextField
                label={`Traveler ${index + 1} - Date of Birth`}
                type="date"
                value={user.dateOfBirth}
                onChange={(e) =>
                  handleUserDetailsChange(index, "dateOfBirth", e.target.value)
                }
                fullWidth
              />
            </Grid>
          </Grid>
        ))}
      </DialogContent>
      <DialogActions>
        <Button onClick={closeDialogHandler} color="primary">
          Cancel
        </Button>
        <Button onClick={handleConfirmBooking} color="primary">
          Confirm Booking
        </Button>
        <Button onClick={handleAddTraveler} color="primary">
          Add Traveler
        </Button>
      </DialogActions>
    </div>
  );

  return (
    <div>
      <NavBar />
      <Container maxWidth="md" sx={{ marginTop: 2 }}>
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
                  Tour Package
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
                      activities={packageData.activity}
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
      {/* Dialog component */}
      <Dialog open={openDialog} onClose={closeDialogHandler}>
        {dialogContent}
      </Dialog>
    </div>
  );
};

export default PackageDetail;
