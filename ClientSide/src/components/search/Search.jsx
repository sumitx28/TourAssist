import React, { useEffect, useState } from "react";
import axios from "axios";
import API_URL from "../../../config/config";
import {
  TextField,
  Button,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Slider,
  Typography,
  CircularProgress,
  Snackbar,
  SnackbarContent,
} from "@mui/material";
import { styled } from "@mui/material/styles";
import { green, red } from "@mui/material/colors";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import ErrorIcon from "@mui/icons-material/Error";
import fetchData from "../../utility/request";

import SearchResults from "./SearchResults";
import "./Search.css";

const StyledSnackbar = styled(Snackbar)({
  bottom: "20px",
  left: "20px",
});

const StyledSnackbarContent = styled(SnackbarContent)(({ theme, variant }) => ({
  backgroundColor: variant === "success" ? green[600] : red[600],
  color: theme.palette.getContrastText(
    variant === "success" ? green[600] : red[600]
  ),
  display: "flex",
  alignItems: "center",
}));

const Search = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [sourceCity, setSourceCity] = useState("");
  const [destinationCity, setDestinationCity] = useState("");
  const [packageStartDate, setPackageStartDate] = useState(
    new Date().toISOString().split("T")[0]
  );
  const [packageEndDate, setPackageEndDate] = useState("");
  const [numberOfGuest, setNumberOfGuest] = useState("");
  const [priceRange, setPriceRange] = useState([0, 1000]);
  const [isCustomizable, setIsCustomizable] = useState("");
  const [packageName, setPackageName] = useState("");
  const [packageRating, setPackageRating] = useState("");
  const [sortBy, setSortBy] = useState("priceSort:ASC");
  const [showSpinner, setShowSpinner] = useState(false);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarVariant, setSnackbarVariant] = useState("success");
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [locations, setLocations] = useState([]);

  const showSnackbar = (variant, message) => {
    setSnackbarVariant(variant);
    setSnackbarMessage(message);
    setSnackbarOpen(true);

    setTimeout(() => {
      setSnackbarOpen(false);
    }, 2000);
  };

  const fetchTravelPackages = async () => {
    if (
      packageEndDate &&
      new Date(packageEndDate) < new Date(packageStartDate)
    ) {
      showSnackbar("error", "End Date cannot be before Start Date");
      return;
    }

    setLoading(true);
    setError("");
    setShowSpinner(true);

    const dataPayload = {
      sourceCity,
      destinationCity,
      packageStartDate: packageStartDate
        ? new Date(packageStartDate).toISOString()
        : "",
      packageEndDate: packageEndDate
        ? new Date(packageEndDate).toISOString()
        : "",
      numberOfGuest,
    };

    const queryParams = new URLSearchParams();
    if (sortBy) queryParams.append("sortBy", sortBy);
    if (priceRange)
      queryParams.append("filterBy", `price:${priceRange.join("#")}`);
    if (isCustomizable)
      queryParams.append("filterBy", `isCustomizable:${isCustomizable}`);
    if (packageName)
      queryParams.append("filterBy", `packageName:${packageName}`);
    if (packageRating)
      queryParams.append("filterBy", `packageRating:${packageRating}`);

    const authToken = localStorage.getItem("authToken");
    const headers = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${authToken}`,
    };

    const url = `${API_URL}/api/v1/search/travel-packages?${queryParams.toString()}`;

    try {
      const response = await axios.post(url, dataPayload, { headers });
      if (response.data && response.data.travelPackages) {
        setTimeout(() => {
          setShowSpinner(false);
          setResults(response.data.travelPackages);
          if (response.data.travelPackages.length == 0) {
            showSnackbar("error", "No Packages Found");
          }
        }, 2000);
      } else {
        setError(
          "The response from the API does not have the expected structure."
        );
        setShowSpinner(false);
      }
    } catch (e) {
      setError(`Error: ${e.response ? e.response.data.message : e.message}`);
      setShowSpinner(false);
    } finally {
      setLoading(false);
    }
  };

  const handleSourceCityChange = (e) => setSourceCity(e.target.value);
  const handleDestinationCityChange = (e) => setDestinationCity(e.target.value);
  const handlePackageStartDateChange = (e) =>
    setPackageStartDate(e.target.value);
  const handlePackageEndDateChange = (e) => setPackageEndDate(e.target.value);
  const handlePriceRangeChange = (e, newValue) => setPriceRange(newValue);
  const handleSortByChange = (e) => setSortBy(e.target.value);

  useEffect(() => {
    const getData = async () => {
      const locationsData = await fetchData("/api/v1/locations");
      setLocations(locationsData);
    };

    getData();

    console.log(locations);
  }, []);

  return (
    <div className="search-container">
      <div className="search-box">
        {/* Search section */}
        <div className="search-fields">
          {/* Source City */}
          <FormControl variant="outlined" className="search-input">
            <InputLabel>Source City</InputLabel>
            <Select
              value={sourceCity}
              onChange={handleSourceCityChange}
              label="Source City"
            >
              {locations.map((location) => (
                <MenuItem key={location.id} value={location.city}>
                  {location.city}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          {/* Destination City */}
          <FormControl variant="outlined" className="search-input">
            <InputLabel>Destination City</InputLabel>
            <Select
              value={destinationCity}
              onChange={handleDestinationCityChange}
              label="Destination City"
            >
              {locations.map((location) => (
                <MenuItem key={location.id} value={location.city}>
                  {location.city}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <TextField
            label="Start Date"
            type="date"
            variant="outlined"
            value={packageStartDate}
            onChange={handlePackageStartDateChange}
            InputLabelProps={{
              shrink: true,
            }}
            inputProps={{
              min: new Date().toISOString().split("T")[0],
            }}
            className="search-input"
          />
          <TextField
            label="End Date"
            type="date"
            variant="outlined"
            value={packageEndDate}
            onChange={handlePackageEndDateChange}
            InputLabelProps={{
              shrink: true,
            }}
            className="search-input"
          />
          <Typography id="price-range-slider" gutterBottom>
            Price Range
          </Typography>
          <Slider
            value={priceRange}
            onChange={handlePriceRangeChange}
            valueLabelDisplay="auto"
            aria-labelledby="price-range-slider"
            min={0}
            max={1000}
            className="price-slider"
          />
          <FormControl variant="outlined" className="search-input">
            <InputLabel>Sort By</InputLabel>
            <Select
              value={sortBy}
              onChange={handleSortByChange}
              label="Sort By"
            >
              <MenuItem value="priceSort:ASC">Price - Low to High</MenuItem>
              <MenuItem value="priceSort:DESC">Price - High to Low</MenuItem>
            </Select>
          </FormControl>
          <Button
            variant="contained"
            color="primary"
            onClick={fetchTravelPackages}
            className="search-button"
          >
            Search
          </Button>
        </div>
      </div>
      {/* Results section */}
      {showSpinner && <CircularProgress size={24} style={{ marginLeft: 10 }} />}
      {!showSpinner && <SearchResults results={results} />}
      <StyledSnackbar
        open={snackbarOpen}
        autoHideDuration={2000}
        onClose={() => setSnackbarOpen(false)}
      >
        <StyledSnackbarContent
          variant={snackbarVariant}
          message={
            <span>
              {snackbarVariant === "success" ? (
                <CheckCircleIcon style={{ marginRight: "8px" }} />
              ) : (
                <ErrorIcon style={{ marginRight: "8px" }} />
              )}
              {snackbarMessage}
            </span>
          }
        />
      </StyledSnackbar>
    </div>
  );
};

export default Search;
