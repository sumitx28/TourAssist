import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import { Card, CardContent, Typography, CardActions, Button } from '@mui/material';

const PastBookings = () => {
  const [bookingDetails, setBookingDetails] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const API_URL = process.env.API_URL;
  const authToken = localStorage.getItem("authToken");
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

  const hasBookings = bookingDetails && Array.isArray(bookingDetails) && bookingDetails.length > 0;

  useEffect(() => {
    const fetchBookingDetails = async () => {
      setLoading(true);
      try {
        const response = await axios.get('http://localhost:8080/api/v1/booking/past-booking/1', {
          headers: {
            'Authorization': `Bearer ${authToken}`
          }
        });
        setBookingDetails(response.data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchBookingDetails();
  }, []);

  if (loading) return <Typography>Loading...</Typography>;
  if (error) return <Typography>Error: {error}</Typography>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        {hasBookings ? (
          bookingDetails.map((booking) => (
            <Card key={booking.id} sx={{ maxWidth: 345, m: 2 }}>
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
                  Booking Date: {new Date(booking.bookingDate).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Total Price: {booking.totalPrice}
                </Typography>
              </CardContent>
              <CardActions>
              </CardActions>
            </Card>
          ))
        ) : (
          <Typography>No past booking details found.</Typography>
        )}
      </div>
    </div>
  );
}

export default PastBookings;
