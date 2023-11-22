import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Container, Grid, Dialog } from "@mui/material";
import PaymentDialog from "./PaymentDialog";
import axios from "axios";
import NavBar from "../commons/NavBar";
import { jwtDecode } from "jwt-decode";
import UserDetailsForm from "./UserDetailsForm";
import PackageCard from "./PackageCard";
import ErrorDialog from "./ErrorDialog";

const PackageDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const API_URL = process.env.API_URL;

  const [finalPackage, setFinalPackage] = useState({
    pricePerPerson: 0,
    deselectedActivities: [],
    packageData: {},
    deselectedTravelMode: false,
    deselectedStay: false,
    bookingData: {},
  });

  const [errorDialog, setErrorDialog] = useState({
    open: false,
    message: "",
  });

  const [openPaymentDialog, setOpenPaymentDialog] = useState(false);

  const [packageData, setPackageData] = useState(null);

  const [openDialog, setOpenDialog] = useState(false);
  const [userDetailsArray, setUserDetailsArray] = useState([
    { firstName: "", lastName: "", dateOfBirth: "" },
  ]);

  const openErrorDialog = (message) => {
    setErrorDialog({ open: true, message });
  };

  const closeErrorDialog = () => {
    setErrorDialog({ open: false, message: "" });
  };

  const openDialogHandler = () => setOpenDialog(true);
  const closeDialogHandler = () => setOpenDialog(false);

  const openPaymentDialogHandler = () => setOpenPaymentDialog(true);
  const closePaymentDialogHandler = () => setOpenPaymentDialog(false);

  const handleUserDetailsChange = (index, field, value) => {
    const updatedUserDetailsArray = [...userDetailsArray];
    updatedUserDetailsArray[index] = {
      ...updatedUserDetailsArray[index],
      [field]: value,
    };
    setUserDetailsArray(updatedUserDetailsArray);
  };

  const handleBookClick = () => openDialogHandler();

  const handleConfirmBooking = async () => {
    if (!packageData) {
      console.error("Package data is not available.");
      return;
    }

    const bookingItemRequests = [];

    packageData.activity.forEach((activity) => {
      if (
        !finalPackage.deselectedActivities.includes(activity.activityMaster.id)
      ) {
        bookingItemRequests.push({
          itemName: "ACTIVITY",
          itemId: activity.id,
        });
      }
    });

    if (!finalPackage.deselectedTourGuide && packageData.tourGuide) {
      bookingItemRequests.push({
        itemName: "GUIDE",
        itemId: packageData.tourGuide.id,
      });
    }

    if (!finalPackage.deselectedStay && packageData.stay) {
      bookingItemRequests.push({
        itemName: "RESORT",
        itemId: packageData.stay.id,
      });
    }

    if (
      !finalPackage.deselectedTravelMode &&
      packageData.transportationDetails
    ) {
      bookingItemRequests.push({
        itemName: "TRANSPORTATION",
        itemId: packageData.transportationDetails.id,
      });
    }

    const authToken = localStorage.getItem("authToken");
    const user = jwtDecode(authToken);

    const response = await axios.get(
      `${API_URL}/api/v1/customer/${user.appUserId}`,
      {
        headers: {
          Authorization: `Bearer ${authToken}`,
          "Content-Type": "application/json",
        },
      }
    );

    const bookingData = {
      packageId: id,
      customerId: response.data.id,
      agentId: packageData.agentDetails.agentId,
      bookingItemRequests,
      guests: userDetailsArray.map((user) => ({
        firstName: user.firstName,
        lastName: user.lastName,
        dateOfBirth: new Date(user.dateOfBirth).toISOString(),
      })),
    };

    try {
      const response = await axios.post(
        `${API_URL}/api/v1/booking/create-booking`,
        bookingData,
        {
          headers: {
            Authorization: `Bearer ${authToken}`,
            "Content-Type": "application/json",
          },
        }
      );

      bookingData.bookingId = response.data;
      bookingData.totalPrice =
        finalPackage.pricePerPerson * userDetailsArray.length;
      bookingData.user = user;

      setFinalPackage({ ...finalPackage, bookingData });

      closeDialogHandler();
      openPaymentDialogHandler();
    } catch (e) {
      alert("Error booking your package. Please try again later.");
    }
  };

  const handleAddTraveler = () =>
    setUserDetailsArray([
      ...userDetailsArray,
      { firstName: "", lastName: "", dateOfBirth: "" },
    ]);

  const handleRemoveTraveler = (index) => {
    const updatedUserDetailsArray = [...userDetailsArray];
    updatedUserDetailsArray.splice(index, 1);
    setUserDetailsArray(updatedUserDetailsArray);
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
        console.log(error);
        openErrorDialog("Package does not exist!");
      }
    };

    fetchData();
  }, [id, navigate]);

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

    setFinalPackage({ ...finalPackage, pricePerPerson: cost });
  }, [
    packageData,
    finalPackage.deselectedActivities,
    finalPackage.deselectedTravelMode,
    finalPackage.deselectedStay,
    finalPackage.deselectedTourGuide,
  ]);

  return (
    <div>
      <NavBar />
      <Container maxWidth="lg" sx={{ marginTop: 2 }}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <PackageCard
              packageData={packageData}
              finalPackage={finalPackage}
              handleSwitchChange={handleSwitchChange}
              handleBookClick={handleBookClick}
            />
          </Grid>
        </Grid>
      </Container>

      <ErrorDialog
        open={errorDialog.open}
        message={errorDialog.message}
        onClose={closeErrorDialog}
      />

      <Dialog open={openDialog} onClose={closeDialogHandler}>
        <UserDetailsForm
          userDetailsArray={userDetailsArray}
          handleUserDetailsChange={handleUserDetailsChange}
          handleAddTraveler={handleAddTraveler}
          handleConfirmBooking={handleConfirmBooking}
          handleRemoveTraveler={handleRemoveTraveler}
        />
      </Dialog>

      <PaymentDialog
        open={openPaymentDialog}
        onClose={closePaymentDialogHandler}
        bookingData={finalPackage.bookingData}
      />
    </div>
  );
};

export default PackageDetail;
