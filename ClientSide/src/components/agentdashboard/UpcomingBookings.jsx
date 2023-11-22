import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';

const UpcomingAgentBookings = () => {
  const [upcomingBookings, setUpcomingBookings] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

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

  const hasUpcomingBookings = upcomingBookings && Array.isArray(upcomingBookings) && upcomingBookings.length > 0;

  useEffect(() => {
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
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        <div className="w-full sm:w-1/2 md:w-3/4 p-2">
          <div className="bg-white shadow rounded-lg p-4">
            <h3 className="text-lg font-semibold mb-4">Upcoming Booking Details</h3>
            <div className="space-y-3"> 
              {hasUpcomingBookings ? (
                <ul>
                  {upcomingBookings.map((booking) => (
                    <li key={booking.id}>
                      <p>Booking ID: {booking.id}</p>
                      <p>User: {booking.customer.firstName} {booking.customer.lastName}</p>
                      <p>Package Name: {booking.packageD.packageName}</p>
                      <p>Start Date: {new Date(booking.packageD.tripStartDate).toLocaleDateString()}</p>
                      <p>End Date: {new Date(booking.packageD.tripEndDate).toLocaleDateString()}</p>
                      <p>Total Price: {booking.totalPrice}</p>
                    </li>
                  ))}
                </ul>
              ) : (
                <p>No upcoming bookings found.</p>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UpcomingAgentBookings;
