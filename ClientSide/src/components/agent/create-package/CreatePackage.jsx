import React, { useState } from "react";
import {
  TextField,
  Select,
  MenuItem,
  Button,
  FormControlLabel,
  Checkbox,
} from "@mui/material";

function TravelForm() {
  const [packageName, setPackageName] = useState("");
  const [tripStartDate, setTripStartDate] = useState("");
  const [tripEndDate, setTripEndDate] = useState("");
  const [tripSource, setTripSource] = useState("");
  const [tripDestination, setTripDestination] = useState("");
  const [selectedActivities, setSelectedActivities] = useState([]);
  const [selectedResort, setSelectedResort] = useState("");
  const [selectedRoomType, setSelectedRoomType] = useState("");
  const [selectedTourGuide, setSelectedTourGuide] = useState("");
  const [selectedTransport, setSelectedTransport] = useState("");
  const [isTransportCustomizable, setIsTransportCustomizable] = useState(false);
  const [customTransportPrice, setCustomTransportPrice] = useState("");
  const [isTourGuideCustomizable, setIsTourGuideCustomizable] = useState(false);
  const [customTourGuideName, setCustomTourGuideName] = useState("");
  const [isResortCustomizable, setIsResortCustomizable] = useState(false); // New state for resort customizability
  const [customResortName, setCustomResortName] = useState(""); // New state for custom resort name
  const [isRoomTypeCustomizable, setIsRoomTypeCustomizable] = useState(false); // New state for room type customizability
  const [customRoomTypeName, setCustomRoomTypeName] = useState(""); // New state for custom room type name

  const activities = [
    "Hiking",
    "Sightseeing",
    "Swimming",
    "Cycling",
    "Boating",
  ];

  const transportModes = [
    { id: 1, name: "Flight" },
    { id: 2, name: "Cruise" },
    { id: 3, name: "Train" },
  ];
  const sources = [
    { id: 1, name: "Helsinki" },
    { id: 2, name: "New York" },
    { id: 3, name: "Chicago" },
  ];
  const destinations = [
    { id: 4, name: "Dallas" },
    { id: 5, name: "Toronto" },
    { id: 6, name: "Halifax" },
  ];

  const resorts = [
    { id: 1, name: "Resort A" },
    { id: 2, name: "Resort B" },
    { id: 3, name: "Resort C" },
  ];

  const roomTypes = [
    { id: 1, name: "Standard" },
    { id: 2, name: "Deluxe" },
    { id: 3, name: "Suite" },
  ];

  const tourGuides = [
    { id: 1, name: "Guide 1" },
    { id: 2, name: "Guide 2" },
    { id: 3, name: "Guide 3" },
  ];

  const handleActivityChange = (e) => {
    setSelectedActivities(e.target.value);
  };

  const handleCustomizableChange = (
    e,
    stateUpdater,
    customValueStateUpdater
  ) => {
    stateUpdater(e.target.checked);
    customValueStateUpdater(""); // Clear the custom value when changing customizability.
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Is Transport Customizable: ", isTransportCustomizable);
    console.log("Custom Transport Price: ", customTransportPrice);
    console.log("Selected Transport: ", selectedTransport);

    console.log("Is Tour Guide Customizable: ", isTourGuideCustomizable);
    console.log("Custom Tour Guide Name: ", customTourGuideName);
    console.log("Selected Tour Guide: ", selectedTourGuide);

    console.log("Is Resort Customizable: ", isResortCustomizable);
    console.log("Custom Resort Name: ", customResortName);
    console.log("Selected Resort: ", selectedResort);

    console.log("Is Room Type Customizable: ", isRoomTypeCustomizable);
    console.log("Custom Room Type Name: ", customRoomTypeName);
    console.log("Selected Room Type: ", selectedRoomType);
  };

  return (
    <div className="px-6 py-24 max-w-screen-lg mx-auto">
      <div className="mx-auto text-center">
        <h2 className="text-3xl font-bold text-gray-900">New Travel Package</h2>
        <p className="mt-2 text-lg text-gray-600">Let's offer our best ones!</p>
      </div>
      <form action="#" method="POST" className="mx-auto mt-8 max-w-xl">
        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
          <div>
            <label
              htmlFor="package-name"
              className="block text-sm font-semibold text-gray-900"
            >
              Package Name
            </label>
            <div className="mt-2.5">
              <TextField
                type="text"
                name="package-name"
                id="package-name"
                variant="outlined"
                fullWidth
                value={packageName}
                onChange={(e) => setPackageName(e.target.value)}
              />
            </div>
          </div>
          <div>
            <label
              htmlFor="selected-activities"
              className="block text-sm font-semibold text-gray-900"
            >
              Activities
            </label>
            <div className="mt-2.5">
              <Select
                multiple
                name="selected-activities"
                id="selected-activities"
                variant="outlined"
                fullWidth
                value={selectedActivities}
                onChange={handleActivityChange}
              >
                {activities.map((activity) => (
                  <MenuItem key={activity} value={activity}>
                    {activity}
                  </MenuItem>
                ))}
              </Select>
            </div>
          </div>
          <div>
            <label
              htmlFor="trip-start-date"
              className="block text-sm font-semibold text-gray-900"
            >
              Trip Start Date
            </label>
            <div className="mt-2.5">
              <TextField
                type="date"
                name="trip-start-date"
                id="trip-start-date"
                variant="outlined"
                fullWidth
                value={tripStartDate}
                onChange={(e) => setTripStartDate(e.target.value)}
              />
            </div>
          </div>
          <div>
            <label
              htmlFor="trip-end-date"
              className="block text-sm font-semibold text-gray-900"
            >
              Trip End Date
            </label>
            <div className="mt-2.5">
              <TextField
                type="date"
                name="trip-end-date"
                id="trip-end-date"
                variant="outlined"
                fullWidth
                value={tripEndDate}
                onChange={(e) => setTripEndDate(e.target.value)}
              />
            </div>
          </div>
          <div>
            <label
              htmlFor="trip-source"
              className="block text-sm font-semibold text-gray-900"
            >
              Trip Source
            </label>
            <div className="mt-2.5">
              <Select
                name="trip-source"
                id="trip-source"
                variant="outlined"
                fullWidth
                value={tripSource}
                onChange={(e) => setTripSource(e.target.value)}
              >
                {sources.map((location) => (
                  <MenuItem value={location.id} key={location.id}>
                    {location.name}
                  </MenuItem>
                ))}
              </Select>
            </div>
          </div>
          <div>
            <label
              htmlFor="trip-destination"
              className="block text-sm font-semibold text-gray-900"
            >
              Trip Destination
            </label>
            <div className="mt-2.5">
              <Select
                name="trip-destination"
                id="trip-destination"
                variant="outlined"
                fullWidth
                value={tripDestination}
                onChange={(e) => setTripDestination(e.target.value)}
              >
                {destinations.map((location) => (
                  <MenuItem value={location.id} key={location.id}>
                    {location.name}
                  </MenuItem>
                ))}
              </Select>
            </div>
          </div>
          <div>
            <label
              htmlFor="selected-tour-guide"
              className="block text-sm font-semibold text-gray-900"
            >
              Tour Guide
            </label>
            <div className="mt-2.5">
              <Select
                name="selected-tour-guide"
                id="selected-tour-guide"
                variant="outlined"
                fullWidth
                value={selectedTourGuide}
                onChange={(e) => setSelectedTourGuide(e.target.value)}
              >
                {tourGuides.map((guide) => (
                  <MenuItem value={guide.id} key={guide.id}>
                    {guide.name}
                  </MenuItem>
                ))}
              </Select>
              <FormControlLabel
                control={
                  <Checkbox
                    checked={isTourGuideCustomizable}
                    onChange={(e) =>
                      handleCustomizableChange(
                        e,
                        setIsTourGuideCustomizable,
                        setCustomTourGuideName
                      )
                    }
                    name="isTourGuideCustomizable"
                  />
                }
                label="Is Customizable?"
              />
              {isTourGuideCustomizable && (
                <TextField
                  type="text"
                  name="custom-tour-guide-name"
                  id="custom-tour-guide-name"
                  variant="outlined"
                  fullWidth
                  label="Price"
                  value={customTourGuideName}
                  onChange={(e) => setCustomTourGuideName(e.target.value)}
                />
              )}
            </div>
          </div>
          <div>
            <label
              htmlFor="selected-transport"
              className="block text-sm font-semibold text-gray-900"
            >
              Mode of Transport
            </label>
            <div className="mt-2.5">
              <Select
                name="selected-transport"
                id="selected-transport"
                variant="outlined"
                fullWidth
                value={selectedTransport}
                onChange={(e) => setSelectedTransport(e.target.value)}
              >
                {transportModes.map((mode) => (
                  <MenuItem value={mode.id} key={mode.id}>
                    {mode.name}
                  </MenuItem>
                ))}
              </Select>
              <FormControlLabel
                control={
                  <Checkbox
                    checked={isTransportCustomizable}
                    onChange={(e) =>
                      handleCustomizableChange(
                        e,
                        setIsTransportCustomizable,
                        setCustomTransportPrice
                      )
                    }
                    name="isTransportCustomizable"
                  />
                }
                label="Is Customizable"
              />
              {isTransportCustomizable && (
                <TextField
                  type="number"
                  name="custom-transport-price"
                  id="custom-transport-price"
                  variant="outlined"
                  fullWidth
                  label="Price"
                  value={customTransportPrice}
                  onChange={(e) => setCustomTransportPrice(e.target.value)}
                />
              )}
            </div>
          </div>
          <div>
            <label
              htmlFor="selected-resort"
              className="block text-sm font-semibold text-gray-900"
            >
              Resort
            </label>
            <div className="mt-2.5">
              <Select
                name="selected-resort"
                id="selected-resort"
                variant="outlined"
                fullWidth
                value={selectedResort}
                onChange={(e) => setSelectedResort(e.target.value)}
              >
                {resorts.map((resort) => (
                  <MenuItem value={resort.id} key={resort.id}>
                    {resort.name}
                  </MenuItem>
                ))}
              </Select>
              <FormControlLabel
                control={
                  <Checkbox
                    checked={isResortCustomizable}
                    onChange={(e) =>
                      handleCustomizableChange(
                        e,
                        setIsResortCustomizable,
                        setCustomResortName
                      )
                    }
                    name="isResortCustomizable"
                  />
                }
                label="Is Customizable"
              />
              {isResortCustomizable && (
                <TextField
                  type="text"
                  name="custom-resort-name"
                  id="custom-resort-name"
                  variant="outlined"
                  fullWidth
                  label="Price"
                  value={customResortName}
                  onChange={(e) => setCustomResortName(e.target.value)}
                />
              )}
            </div>
          </div>
          <div>
            <label
              htmlFor="selected-room-type"
              className="block text-sm font-semibold text-gray-900"
            >
              Type of Room
            </label>
            <div className="mt-2.5">
              <Select
                name="selected-room-type"
                id="selected-room-type"
                variant="outlined"
                fullWidth
                value={selectedRoomType}
                onChange={(e) => setSelectedRoomType(e.target.value)}
              >
                {roomTypes.map((type) => (
                  <MenuItem value={type.id} key={type.id}>
                    {type.name}
                  </MenuItem>
                ))}
              </Select>
              <FormControlLabel
                control={
                  <Checkbox
                    checked={isRoomTypeCustomizable}
                    onChange={(e) =>
                      handleCustomizableChange(
                        e,
                        setIsRoomTypeCustomizable,
                        setCustomRoomTypeName
                      )
                    }
                    name="isRoomTypeCustomizable"
                  />
                }
                label="Is Customizable"
              />
              {isRoomTypeCustomizable && (
                <TextField
                  type="text"
                  name="custom-room-type-name"
                  id="custom-room-type-name"
                  variant="outlined"
                  fullWidth
                  label="Price"
                  value={customRoomTypeName}
                  onChange={(e) => setCustomRoomTypeName(e.target.value)}
                />
              )}
            </div>
          </div>
        </div>
        <div className="mt-8">
          <Button variant="contained" onClick={handleSubmit}>
            Create
          </Button>
        </div>
      </form>
    </div>
  );
}

export default TravelForm;
