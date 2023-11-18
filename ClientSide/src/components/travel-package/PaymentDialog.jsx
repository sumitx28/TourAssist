import React from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
} from "@mui/material";
import API_URL from "../../../config/config";
import axios from "axios";

const PaymentDialog = ({ open, onClose, bookingData }) => {
  const [cardNumber, setCardNumber] = React.useState("");
  const [cvv, setCVV] = React.useState("");
  const [expiryDate, setExpiryDate] = React.useState("");
  const [paymentType, setPaymentType] = React.useState("Credit Card");

  const isValidCardNumber = (cardNumber) => {
    return /^\d{16}$/.test(cardNumber);
  };

  const isValidCVV = (cvv) => {
    return /^\d{3}$/.test(cvv);
  };

  const isValidExpiryDate = (expiryDate) => {
    return /^\d{2}\/\d{4}$/.test(expiryDate);
  };

  const handlePay = async () => {
    if (
      !isValidCardNumber(cardNumber) ||
      !isValidCVV(cvv) ||
      !isValidExpiryDate(expiryDate)
    ) {
      alert("Invalid card details. Please check your card information.");
      return;
    }

    alert("Processing payment...");

    const authToken = localStorage.getItem("authToken");

    // Dummy successful and failed card details
    const successfulCard = {
      number: "4111111111111111",
      cvv: "123",
      expiryDate: "12/2024",
    };

    const failedCard = {
      number: "4000123412341234",
      cvv: "999",
      expiryDate: "06/2022",
    };

    const isPaymentSuccessful =
      cardNumber === successfulCard.number &&
      cvv === successfulCard.cvv &&
      expiryDate === successfulCard.expiryDate;

    const paymentData = {
      customerEmail: bookingData.user.sub,
      transactionId: Date.now(),
      bookingId: bookingData.bookingId,
      paymentType: paymentType,
      price: bookingData.totalPrice,
    };

    console.log(paymentData);

    if (isPaymentSuccessful) {
      paymentData.transactionStatus = "SUCCESS";
      alert("Payment Successful");
    } else {
      paymentData.transactionStatus = "FAILED";
      alert("Payment Failed. Please use valid card details.");
    }

    const response = await axios.post(
      `${API_URL}/api/v1/payment/payment-transaction`,
      paymentData,
      {
        headers: {
          Authorization: `Bearer ${authToken}`,
          "Content-Type": "application/json",
        },
      }
    );

    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Payment</DialogTitle>
      <DialogContent>
        <FormControl fullWidth variant="outlined" margin="normal">
          <InputLabel id="payment-type-label">Payment Type</InputLabel>
          <Select
            label="Payment Type"
            value={paymentType}
            onChange={(e) => setPaymentType(e.target.value)}
            labelId="payment-type-label"
          >
            <MenuItem value="Credit Card">Credit Card</MenuItem>
            <MenuItem value="Debit Card">Debit Card</MenuItem>
          </Select>
        </FormControl>
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
