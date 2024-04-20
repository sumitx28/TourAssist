import React, { useState, useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import {
  Card,
  CardContent,
  CardMedia,
  Typography,
  Container,
  CardActions,
  Button,
} from "@mui/material";
import { Link } from "react-router-dom";

const UpcomingAgentBookings = () => {
  const [upcomingBookings, setUpcomingBookings] = useState([]);
  const API_URL = process.env.API_URL;
  const authToken = localStorage.getItem("authToken");

  useEffect(() => {
    if (!authToken) {
      console.error("Authentication token is missing.");
      return;
    }

    let user;
    try {
      user = jwtDecode(authToken);
    } catch (e) {
      console.error("Authentication token is invalid.");
      return;
    }

    const fetchUpcomingBookings = async () => {
      try {
        const response = await axios.get(
          `${API_URL}/api/v1/booking/booking-details`,
          {
            params: { appUserId: user?.appUserId },
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${authToken}`,
            },
          }
        );
        setUpcomingBookings(response.data.bookingDetailsList);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUpcomingBookings();
  }, [authToken]);

  const getImageUrlByBookingId = async (bookingId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/package/${bookingId}`
      );

      if (
        response.data &&
        response.data.mediaPath &&
        response.data.mediaPath[0] &&
        response.data.mediaPath[0].media
      ) {
        return response.data.mediaPath[0].media;
      } else {
        console.error("Image URL not found in the API response.");
        return null;
      }
    } catch (error) {
      console.error("Error fetching image URL:", error);
      return null;
    }
  };

  const hasUpcomingBookings =
    upcomingBookings &&
    Array.isArray(upcomingBookings) &&
    upcomingBookings.length > 0;

  return (
    <Container maxWidth="md" className="mt-4">
      <div className="flex flex-wrap justify-center">
        {hasUpcomingBookings ? (
          upcomingBookings.map((booking) => (
            <Link
              key={booking.packageId}
              to={`/package/${booking.packageId}`}
              style={{ textDecoration: "none" }}
            >
              <Card
                className="m-2"
                style={{
                  backgroundColor: "black",
                  color: "white",
                  height: "270px",
                  width: "300px",
                }}
              >
                <CardMedia
                  component="img"
                  style={{ height: "150px" }}
                  image="https://media.istockphoto.com/id/155439315/photo/passenger-airplane-flying-above-clouds-during-sunset.jpg?s=612x612&w=0&k=20&c=LJWadbs3B-jSGJBVy9s0f8gZMHi2NvWFXa3VJ2lFcL0="
                  alt={booking.packageName}
                />
                <CardContent>
                  <Typography gutterBottom variant="h5" component="div">
                    {booking.packageName}
                  </Typography>
                  <Typography variant="body2">
                    Booking Date:{" "}
                    {new Date(booking.bookingDate).toLocaleDateString()}
                  </Typography>
                  <Typography variant="body2">
                    Total Price: {booking.totalPrice}
                  </Typography>
                </CardContent>
                <CardActions>
                  <Button variant="outlined" color="primary">
                    Learn More
                  </Button>
                </CardActions>
              </Card>
            </Link>
          ))
        ) : (
          <Typography>No upcoming bookings found.</Typography>
        )}
      </div>
    </Container>
  );
};

export default UpcomingAgentBookings;
