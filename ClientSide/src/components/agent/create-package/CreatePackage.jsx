import React, { useEffect, useState } from "react";
import {
  TextField,
  Select,
  MenuItem,
  Button,
  FormControlLabel,
  Checkbox,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
} from "@mui/material";
import fetchData from "../../../utility/request";
import axios from "axios";
import API_URL from "../../../../config/config";
import NavBar from "../../commons/NavBar";

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

  const [dialogOpen, setDialogOpen] = useState(false);
  const [sources, setSources] = useState([]); // Use state to store sources and destinations
  const [destinations, setDestinations] = useState([]);
  const [activities, setActivities] = useState([]);
  const [transportModes, setTransportModes] = useState([]);
  const [tourGuides, setTourGuides] = useState([]);
  const [roomTypes, setRoomTypes] = useState([]);
  const [resorts, setResorts] = useState([]);

  useEffect(() => {
    const getData = async () => {
      const sourcesData = await fetchData("/api/v1/auth/locations");
      const destinationsData = await fetchData("/api/v1/auth/locations");
      const activitiesData = await fetchData("/api/v1/auth/activities");
      const transportModeData = await fetchData("/api/v1/auth/travel-modes");
      const roomTypesData = await fetchData("/api/v1/auth/suites");

      setSources(sourcesData);
      setDestinations(destinationsData);
      setActivities(activitiesData);
      setTransportModes(transportModeData);
      setRoomTypes(roomTypesData);
    };

    getData();
  }, []);

  const fetchTourGuides = async (destinationId) => {
    const tourGuideData = await fetchData(
      `/api/v1/auth/guides/${destinationId}`
    );
    setTourGuides(tourGuideData);
  };

  const fetchResorts = async (destinationId) => {
    const resortsData = await fetchData(
      `/api/v1/auth/resorts/${destinationId}`
    );
    setResorts(resortsData);
  };

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

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log(formData);

    const requiredFields = [
      "packageName",
      "tripStartDate",
      "tripEndDate",
      "tripSource",
      "tripDestination",
      "selectedTransport",
      "selectedResort",
      "selectedRoomType",
    ];

    const hasEmptyFields = requiredFields.some((field) => !formData[field]);

    if (hasEmptyFields) {
      setDialogOpen(true);
    } else {
      const postData = {
        packageName: formData.packageName,
        agentId: 1,
        tripStartDate: formData.tripStartDate,
        tripEndDate: formData.tripEndDate + "T10:00:00Z",
        sourceId: formData.tripSource + "T10:00:00Z",
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

      const authToken = localStorage.getItem("authToken");

      try {
        const response = await axios.post(
          `${API_URL}/api/v1/auth/create-package`,
          postData,
          {
            headers: {
              Authorization: `Bearer ${authToken}`,
              "Content-Type": "application/json",
            },
          }
        );
      } catch (e) {
        alert("Package Successfully Created");
      }
    }
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
  };

  return (
    <>
      <NavBar />
      <div className="px-6 py-4 max-w-screen-lg mx-auto">
        <div className="mx-auto text-center">
          <h2 className="text-3xl font-bold text-gray-900">
            New Travel Package
          </h2>
          <p className="mt-2 text-lg text-gray-600">
            Let's offer our best ones!
          </p>
        </div>
        <form action="#" method="POST" className="mx-auto mt-6 max-w-xl">
          <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
            <div>
              <label
                htmlFor="package-name"
                className="block text-sm font-semibold text-gray-900"
              >
                Package Name*
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
                      {activity.activityName}
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
                Trip Start Date*
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
                Trip End Date*
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
                Trip Source*
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
                      {location.city}
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
                Trip Destination*
              </label>
              <div className="mt-2.5">
                <Select
                  name="trip-destination"
                  id="trip-destination"
                  variant="outlined"
                  fullWidth
                  value={formData.tripDestination}
                  onChange={(e) => {
                    fetchTourGuides(e.target.value);
                    fetchResorts(e.target.value);
                    setFormData({
                      ...formData,
                      tripDestination: e.target.value,
                    });
                  }}
                >
                  {destinations.map((location) => (
                    <MenuItem value={location.id} key={location.id}>
                      {location.city}
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
                      {guide.guideName}
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
                <TextField
                  type="text"
                  name="custom-tour-guide-name"
                  id="custom-tour-guide-name"
                  variant="outlined"
                  fullWidth
                  label="Tour Guide Price"
                  value={formData.customTourGuidePrice}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      customTourGuidePrice: e.target.value,
                    })
                  }
                />
              </div>
            </div>
            <div>
              <label
                htmlFor="selected-transport"
                className="block text-sm font-semibold text-gray-900"
              >
                Mode of Travel*
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
                      {mode.mode}
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
                <TextField
                  type="number"
                  name="custom-transport-price"
                  id="custom-transport-price"
                  variant="outlined"
                  fullWidth
                  label="Travel Price"
                  value={formData.customTransportPrice}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      customTransportPrice: e.target.value,
                    })
                  }
                />
              </div>
            </div>
            <div>
              <label
                htmlFor="selected-resort"
                className="block text-sm font-semibold text-gray-900"
              >
                Resort*
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
                      {resort.resortName}
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
                Type of Room*
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
                      {type.suiteType}
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
                <TextField
                  type="text"
                  name="custom-room-type-name"
                  id="custom-room-type-name"
                  variant="outlined"
                  fullWidth
                  label="Room Price"
                  value={formData.customRoomTypePrice}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      customRoomTypePrice: e.target.value,
                    })
                  }
                />
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
        <Dialog
          open={dialogOpen}
          onClose={handleCloseDialog}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">
            {"Fill All Required Fields"}
          </DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              Please fill in all the required fields before submitting the form.
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleCloseDialog} autoFocus>
              OK
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    </>
  );
}

export default TravelForm;
