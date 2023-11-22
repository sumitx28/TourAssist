import React, { useState } from "react";
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
} from "@mui/material";
import SearchResults from "./SearchResults";
import "./Search.css";

const Search = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [sourceCity, setSourceCity] = useState("");
  const [destinationCity, setDestinationCity] = useState("");
  const [packageStartDate, setPackageStartDate] = useState("");
  const [packageEndDate, setPackageEndDate] = useState("");
  const [numberOfGuest, setNumberOfGuest] = useState("");
  const [priceRange, setPriceRange] = useState([0, 1000]);
  const [isCustomizable, setIsCustomizable] = useState("");
  const [packageName, setPackageName] = useState("");
  const [packageRating, setPackageRating] = useState("");
  const [sortBy, setSortBy] = useState("priceSort:ASC");

  const fetchTravelPackages = async () => {
    setLoading(true);
    setError("");

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
        setResults(response.data.travelPackages);
      } else {
        setError(
          "The response from the API does not have the expected structure."
        );
      }
    } catch (e) {
      setError(`Error: ${e.response ? e.response.data.message : e.message}`);
    } finally {
      setLoading(false);
    }
  };

  const handleSourceCityChange = (e) => setSourceCity(e.target.value);
  const handleDestinationCityChange = (e) => setDestinationCity(e.target.value);
  const handlePackageStartDateChange = (e) =>
    setPackageStartDate(e.target.value);
  const handlePackageEndDateChange = (e) => setPackageEndDate(e.target.value);
  const handleNumberOfGuestChange = (e) => setNumberOfGuest(e.target.value);
  const handlePriceRangeChange = (e, newValue) => setPriceRange(newValue);
  const handleIsCustomizableChange = (e) => setIsCustomizable(e.target.value);
  const handlePackageRatingChange = (e) => setPackageRating(e.target.value);
  const handleSortByChange = (e) => setSortBy(e.target.value);

  return (
    <div className="search-container">
      <div className="search-box">
        {/* Search section */}
        <div className="search-fields">
          <TextField
            label="Source City"
            variant="outlined"
            value={sourceCity}
            onChange={handleSourceCityChange}
            className="search-input"
          />
          <TextField
            label="Destination City"
            variant="outlined"
            value={destinationCity}
            onChange={handleDestinationCityChange}
            className="search-input"
          />
          <TextField
            label="Start Date"
            type="date"
            variant="outlined"
            value={packageStartDate}
            onChange={handlePackageStartDateChange}
            InputLabelProps={{
              shrink: true,
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
      <SearchResults results={results} />
    </div>
  );
};

export default Search;
