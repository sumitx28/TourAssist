import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import {
  Card,
  CardContent,
  Typography,
  CardActions,
  CircularProgress,
  Container,
  CardMedia,
} from "@mui/material";
import fetchUserDetails from "../../utility/requestUserDetails";

const PastBookings = () => {
  const [bookingDetails, setBookingDetails] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const API_URL = process.env.API_URL;
  const authToken = localStorage.getItem("authToken");

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (!authToken) {
          throw new Error("Authentication token is missing.");
        }

        let user;
        try {
          user = jwtDecode(authToken);
        } catch (e) {
          throw new Error("Authentication token is invalid.");
        }

        const userDetails = await fetchUserDetails("agent");

        const response = await axios.get(
          `${API_URL}/api/v1/booking/past-booking/${userDetails.id}`,
          {
            headers: {
              Authorization: `Bearer ${authToken}`,
            },
          }
        );

        const filteredData = response.data.filter(
          (data) => data.bookingStatus === "CONFIRM"
        );

        setBookingDetails(filteredData);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [authToken, API_URL]);

  if (loading) return <CircularProgress />;
  if (error) return <Typography>Error: {error}</Typography>;

  return (
    <Container maxWidth="md" className="mt-4">
      <div className="flex flex-wrap justify-center">
        {bookingDetails.length > 0 ? (
          bookingDetails.map((booking) => (
            <Card key={booking.id} sx={{ maxWidth: 345, m: 2 }}>
              <CardMedia
                component="img"
                height="150"
                image="https://media.istockphoto.com/id/155439315/photo/passenger-airplane-flying-above-clouds-during-sunset.jpg?s=612x612&w=0&k=20&c=LJWadbs3B-jSGJBVy9s0f8gZMHi2NvWFXa3VJ2lFcL0="
                alt={booking.packageName}
              />
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  Booking ID: {booking.id}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  User: {booking.customer.firstName} {booking.customer.lastName}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Package Name: {booking.packageD.packageName}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Booking Date:{" "}
                  {new Date(booking.bookingDate).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Total Price: {booking.totalPrice}
                </Typography>
              </CardContent>
              <CardActions></CardActions>
            </Card>
          ))
        ) : (
          <Typography>No past booking details found.</Typography>
        )}
      </div>
    </Container>
  );
};

export default PastBookings;
