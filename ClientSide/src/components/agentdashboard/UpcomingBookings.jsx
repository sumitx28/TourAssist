import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import { Card, CardContent, Typography } from '@mui/material';

const UpcomingAgentBookings = () => {
  const [upcomingBookings, setUpcomingBookings] = useState([]);
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

    const fetchUpcomingBookings = async () => {
      setLoading(true);
      try {
        const response = await axios.get('http://localhost:8080/api/v1/booking/upcoming-booking/1', {
          headers: {
            'Authorization': `Bearer ${authToken}`
          }
        });
        setUpcomingBookings(response.data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUpcomingBookings();
  }, [authToken]);

  if (loading) return <Typography>Loading...</Typography>;
  if (error) return <Typography>Error: {error}</Typography>;

  const hasUpcomingBookings = upcomingBookings && Array.isArray(upcomingBookings) && upcomingBookings.length > 0;

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        {hasUpcomingBookings ? (
          upcomingBookings.map((booking) => (
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
                  Start Date: {new Date(booking.packageD.tripStartDate).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  End Date: {new Date(booking.packageD.tripEndDate).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Total Price: {booking.totalPrice}
                </Typography>
              </CardContent>
              {/* Add CardActions if needed */}
            </Card>
          ))
        ) : (
          <Typography>No upcoming bookings found.</Typography>
        )}
      </div>
    </div>
  );
}

export default UpcomingAgentBookings;
