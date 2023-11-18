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
  Typography,
  CircularProgress,
} from "@mui/material";
import API_URL from "../../../config/config";
import axios from "axios";

const PaymentDialog = ({ open, onClose, bookingData }) => {
  const formControlStyle = {
    marginBottom: "16px",
    marginTop: "16px",
  };

  const totalAmountStyle = {
    marginTop: "16px",
    marginBottom: "8px",
  };

  const [cardNumber, setCardNumber] = React.useState("");
  const [cvv, setCVV] = React.useState("");
  const [expiryDate, setExpiryDate] = React.useState("");
  const [paymentType, setPaymentType] = React.useState("Credit Card");
  const [isProcessingPayment, setProcessingPayment] = React.useState(false);
  const [paymentResult, setPaymentResult] = React.useState(null);

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

    setProcessingPayment(true);

    const authToken = localStorage.getItem("authToken");

    // Dummy successful and failed card details
    const successfulCard = {
      number: "4111111111111111",
      cvv: "123",
      expiryDate: "12/2024",
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
    } else {
      paymentData.transactionStatus = "FAILED";
    }

    try {
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

      setPaymentResult(isPaymentSuccessful ? "success" : "failed");
    } catch (error) {
      console.error("Error processing payment:", error);
      setPaymentResult("failed");
    } finally {
      setProcessingPayment(false);
    }
  };

  const handleClose = () => {
    setPaymentResult(null);
    onClose();
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Payment</DialogTitle>
      <DialogContent>
        <FormControl fullWidth variant="outlined" style={formControlStyle}>
          <InputLabel id="payment-type-label" shrink>
            Payment Type
          </InputLabel>
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
        <Typography variant="h6" color="primary" style={totalAmountStyle}>
          Total Payable Amount: ${bookingData.totalPrice}
        </Typography>
      </DialogContent>
      <DialogActions>
        {paymentResult === "success" && (
          <Typography variant="body1" style={{ color: "green" }}>
            Payment Successful
          </Typography>
        )}
        {paymentResult === "failed" && (
          <Typography variant="body1" style={{ color: "red" }}>
            Payment Failed. Please try again.
          </Typography>
        )}
        <Button onClick={handleClose} color="primary">
          Close
        </Button>
        <Button
          onClick={handlePay}
          color="primary"
          variant="contained"
          disabled={isProcessingPayment}
          style={{
            backgroundColor: paymentResult === "success" ? "green" : "red",
          }}
        >
          {isProcessingPayment ? (
            <CircularProgress size={24} color="primary" />
          ) : (
            "Pay"
          )}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default PaymentDialog;
