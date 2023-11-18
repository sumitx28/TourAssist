import React from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Typography,
} from "@mui/material";
import API_URL from "../../../config/config";
import axios from "axios";

const PaymentDialog = ({ open, onClose, bookingData }) => {
  const [cardNumber, setCardNumber] = React.useState("");
  const [cvv, setCVV] = React.useState("");
  const [expiryDate, setExpiryDate] = React.useState("");

  const handlePay = async () => {
    console.log(bookingData);

    alert("Processing payment...");

    const authToken = localStorage.getItem("authToken");

    const paymentData = {
      customerEmail: bookingData.user.sub,
      transactionId: Date.now(),
      bookingId: bookingData.bookingId,
      paymentType: "Credit Card",
      price: bookingData.totalPrice,
    };

    console.log(paymentData);

    // const response = await axios.post(
    //   `${API_URL}/api/v1/payment/payment-transaction`,
    //   bookingData,
    //   {
    //     headers: {
    //       Authorization: `Bearer ${authToken}`,
    //       "Content-Type": "application/json",
    //     },
    //   }
    // );

    alert("Payment Successful");

    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Payment</DialogTitle>
      <DialogContent>
        <TextField
          label="Card Number"
          fullWidth
          variant="outlined"
          margin="normal"
          value={cardNumber}
          onChange={(e) => setCardNumber(e.target.value)}
        />
        <TextField
          label="CVV"
          fullWidth
          variant="outlined"
          margin="normal"
          value={cvv}
          onChange={(e) => setCVV(e.target.value)}
        />
        <TextField
          label="Expiry Date (MM/YYYY)"
          fullWidth
          variant="outlined"
          margin="normal"
          value={expiryDate}
          onChange={(e) => setExpiryDate(e.target.value)}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="primary">
          Close
        </Button>
        <Button onClick={handlePay} color="primary" variant="contained">
          Pay
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default PaymentDialog;
