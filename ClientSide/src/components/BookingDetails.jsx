import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';

const BookingDetails = () => {
  const [bookingDetails, setBookingDetails] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const authToken = localStorage.getItem("authToken");
  const user = jwtDecode(authToken);
  const hasBookings = bookingDetails && Array.isArray(bookingDetails) && bookingDetails.length > 0;

  useEffect(() => {
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
    } else {
      setError("Authentication token is invalid or missing.");
    }
  }, [user]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex flex-wrap justify-center">
        <div className="w-full sm:w-1/2 md:w-3/4 p-2">
          <div className="bg-white shadow rounded-lg p-4">
            <h3 className="text-lg font-semibold mb-4">Booking Details</h3>
                <div className="space-y-3">
                    {hasBookings ? (
                    <ul>
                    {bookingDetails.map((booking) => (
                        <li key={booking.packageId}>
                        <p>Booking ID: {booking.packageId}</p>
                        <p>User ID: {booking.customerId}</p>
                        <p>Package Name: {booking.packageName}</p>
                        <p>Booking Date: {booking.bookingDate}</p>
                        <p>Total Price: {booking.totalPrice}</p>
                        </li>
                    ))}
                    </ul>
                ) : (
                    <p>No booking details found.</p>
                )}
                </div>
            </div>
        </div>
      </div>
    </div>
  );
}

export default BookingDetails;
