import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import { Card, CardContent, Typography } from '@mui/material';

const BookingDetails = () => {
  const [bookingDetails, setBookingDetails] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const authToken = localStorage.getItem("authToken");

  useEffect(() => {
    if (!authToken) {
      setError("Authentication token is missing.");
      return;
    }
    let user;
    try {
      user = jwtDecode(authToken);
    } catch (e) {
      setError("Authentication token is invalid.");
      return;
    }

    const fetchBookingDetails = async () => {
      setLoading(true);
      try {
        const response = await axios.get('http://localhost:8080/api/v1/booking/booking-details', {
          params: { appUserId: user?.appUserId },
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${authToken}`
          }
        });
        setBookingDetails(response.data.bookingDetailsList);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    if (user && user.appUserId) {
      fetchBookingDetails();
    }
  }, [authToken]);

  if (loading) return <Typography>Loading...</Typography>;
  if (error) return <Typography>Error: {error}</Typography>;

  const hasBookings = bookingDetails && Array.isArray(bookingDetails) && bookingDetails.length > 0;

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        {hasBookings ? (
          bookingDetails.map((booking) => (
            <Card key={booking.packageId} sx={{ maxWidth: 345, m: 2 }}>
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  Booking ID: {booking.packageId}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  User ID: {booking.customerId}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Package Name: {booking.packageName}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Booking Date: {new Date(booking.bookingDate).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Total Price: {booking.totalPrice}
                </Typography>
              </CardContent>
              {/* Add CardActions if needed */}
            </Card>
          ))
        ) : (
          <Typography>No booking details found.</Typography>
        )}
      </div>
    </div>
  );
}

export default BookingDetails;
