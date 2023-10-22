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
  const [formData, setFormData] = useState({
    packageName: "",
    tripStartDate: "",
    tripEndDate: "",
    tripSource: "",
    tripDestination: "",
    selectedActivities: [],
    selectedResort: "",
    selectedRoomType: "",
    selectedTourGuide: "",
    selectedTransport: "",
    isTransportCustomizable: false,
    customTransportPrice: "",
    isTourGuideCustomizable: false,
    customTourGuidePrice: "",
    isRoomTypeCustomizable: false,
    customRoomTypePrice: "",
  });

  const activities = [
    { id: 1, name: "Hiking" },
    { id: 2, name: "Sightseeing" },
    { id: 3, name: "Swimming" },
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
    setFormData({ ...formData, selectedActivities: e.target.value });
  };

  const handleCustomizableChange = (e, stateKey, customValueKey) => {
    setFormData({
      ...formData,
      [stateKey]: e.target.checked,
      [customValueKey]: e.target.checked ? "" : formData[customValueKey],
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // console.log(
    //   "Is Transport Customizable: ",
    //   formData.isTransportCustomizable
    // );
    // console.log("Custom Transport Price: ", formData.customTransportPrice);
    // console.log("Selected Transport: ", formData.selectedTransport);

    // console.log(
    //   "Is Tour Guide Customizable: ",
    //   formData.isTourGuideCustomizable
    // );
    // console.log("Custom Tour Guide Name: ", formData.customTourGuideName);
    // console.log("Selected Tour Guide: ", formData.selectedTourGuide);

    // console.log("Is Resort Customizable: ", formData.isResortCustomizable);
    // console.log("Custom Resort Name: ", formData.customResortName);
    // console.log("Selected Resort: ", formData.selectedResort);

    // console.log("Is Room Type Customizable: ", formData.isRoomTypeCustomizable);
    // console.log("Custom Room Type Name: ", formData.customRoomTypeName);
    // console.log("Selected Room Type: ", formData.selectedRoomType);

    // console.log("Selected Activities: ", formData.selectedActivities);

    const postData = {
      packageName: formData.packageName,
      agentId: 123,
      tripStartDate: formData.tripStartDate,
      tripEndDate: formData.tripEndDate,
      sourceId: formData.tripSource,
      destinationId: formData.tripDestination,
      isCustomizable:
        formData.isResortCustomizable ||
        formData.isRoomTypeCustomizable ||
        formData.isTourGuideCustomizable ||
        formData.isTransportCustomizable,
      stayRequest: {
        resortId: formData.selectedResort,
        suiteId: formData.selectedRoomType,
        price: Number(formData.customRoomTypePrice),
        isCustomizable: formData.isRoomTypeCustomizable,
      },
      tourGuideRequest: {
        guideId: formData.selectedTourGuide,
        price: Number(formData.customTourGuidePrice),
        isCustomizable: formData.isTourGuideCustomizable,
      },
      transportationRequest: {
        modeId: formData.selectedTransport,
        price: Number(formData.customTransportPrice),
        isCustomizable: formData.isTransportCustomizable,
      },
      activities: formData.selectedActivities.map((value) => {
        return {
          activityId: value,
          activityDate: new Date().toJSON().slice(0, 10),
          price: 0,
          isCustomizable: false,
        };
      }),
    };

    console.log(postData);
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
                value={formData.packageName}
                onChange={(e) =>
                  setFormData({ ...formData, packageName: e.target.value })
                }
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
                value={formData.selectedActivities}
                onChange={handleActivityChange}
              >
                {activities.map((activity) => (
                  <MenuItem key={activity.id} value={activity.id}>
                    {activity.name}
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
                value={formData.tripStartDate}
                onChange={(e) =>
                  setFormData({ ...formData, tripStartDate: e.target.value })
                }
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
                value={formData.tripEndDate}
                onChange={(e) =>
                  setFormData({ ...formData, tripEndDate: e.target.value })
                }
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
                value={formData.tripSource}
                onChange={(e) =>
                  setFormData({ ...formData, tripSource: e.target.value })
                }
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
                value={formData.tripDestination}
                onChange={(e) =>
                  setFormData({ ...formData, tripDestination: e.target.value })
                }
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
                value={formData.selectedTourGuide}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    selectedTourGuide: e.target.value,
                  })
                }
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
                    checked={formData.isTourGuideCustomizable}
                    onChange={(e) =>
                      handleCustomizableChange(
                        e,
                        "isTourGuideCustomizable",
                        "customTourGuidePrice"
                      )
                    }
                    name="isTourGuideCustomizable"
                  />
                }
                label="Is Customizable?"
              />
              {formData.isTourGuideCustomizable && (
                <TextField
                  type="text"
                  name="custom-tour-guide-name"
                  id="custom-tour-guide-name"
                  variant="outlined"
                  fullWidth
                  label="Price"
                  value={formData.customTourGuidePrice}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      customTourGuidePrice: e.target.value,
                    })
                  }
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
                value={formData.selectedTransport}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    selectedTransport: e.target.value,
                  })
                }
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
                    checked={formData.isTransportCustomizable}
                    onChange={(e) =>
                      handleCustomizableChange(
                        e,
                        "isTransportCustomizable",
                        "customTransportPrice"
                      )
                    }
                    name="isTransportCustomizable"
                  />
                }
                label="Is Customizable"
              />
              {formData.isTransportCustomizable && (
                <TextField
                  type="number"
                  name="custom-transport-price"
                  id="custom-transport-price"
                  variant="outlined"
                  fullWidth
                  label="Price"
                  value={formData.customTransportPrice}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      customTransportPrice: e.target.value,
                    })
                  }
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
                value={formData.selectedResort}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    selectedResort: e.target.value,
                  })
                }
              >
                {resorts.map((resort) => (
                  <MenuItem value={resort.id} key={resort.id}>
                    {resort.name}
                  </MenuItem>
                ))}
              </Select>
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
                value={formData.selectedRoomType}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    selectedRoomType: e.target.value,
                  })
                }
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
                    checked={formData.isRoomTypeCustomizable}
                    onChange={(e) =>
                      handleCustomizableChange(
                        e,
                        "isRoomTypeCustomizable",
                        "customRoomTypePrice"
                      )
                    }
                    name="isRoomTypeCustomizable"
                  />
                }
                label="Is Customizable"
              />
              {formData.isRoomTypeCustomizable && (
                <TextField
                  type="text"
                  name="custom-room-type-name"
                  id="custom-room-type-name"
                  variant="outlined"
                  fullWidth
                  label="Price"
                  value={formData.customRoomTypePrice}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      customRoomTypePrice: e.target.value,
                    })
                  }
                />
              )}
            </div>
          </div>
        </div>
        <div className="mt-8">
          <Button
            variant="contained"
            onClick={handleSubmit}
            style={{ width: "100%" }}
          >
            Create
          </Button>
        </div>
      </form>
    </div>
  );
}

export default TravelForm;
