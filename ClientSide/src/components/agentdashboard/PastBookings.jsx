import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';

const PastBookings = () => {
  const [bookingDetails, setBookingDetails] = useState([]);
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
    return; // Stop execution if the token is invalid
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

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        <div className="w-full sm:w-1/2 md:w-3/4 p-2">
          <div className="bg-white shadow rounded-lg p-4">
            <h3 className="text-lg font-semibold mb-4">Past Booking Details</h3>
            <div className="space-y-3"> 
              {hasBookings ? (
                <ul>
                  {bookingDetails.map((booking) => (
                    <li key={booking.id}>
                      <p>Booking ID: {booking.id}</p>
                      <p>User: {booking.customer.firstName} {booking.customer.lastName}</p>
                      <p>Package Name: {booking.packageD.packageName}</p>
                      <p>Booking Date: {new Date(booking.bookingDate).toLocaleDateString()}</p>
                      <p>Total Price: {booking.totalPrice}</p>
                    </li>
                  ))}
                </ul>
              ) : (
                <p>No past booking details found.</p>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PastBookings;
