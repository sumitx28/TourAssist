import * as React from "react";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import DashboardIcon from "@mui/icons-material/Dashboard";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import EventNoteIcon from "@mui/icons-material/EventNote";
import UpcomingIcon from "@mui/icons-material/EventAvailable";
import SearchIcon from "@mui/icons-material/Search";
import PeopleIcon from "@mui/icons-material/People";
import MonetizationOnIcon from "@mui/icons-material/MonetizationOn";
import { Link } from "react-router-dom";
import getUserRole from "../../utility/getUserRole";

const MainListItems = () => {
  const [role, setRole] = React.useState("");
  React.useEffect(() => {
    const role = getUserRole();
    setRole(role);
    console.log(role);
  }, []);

  return (
    <React.Fragment>
      <ListItemButton component={Link} to="/search">
        <ListItemIcon>
          <SearchIcon />
        </ListItemIcon>
        <ListItemText primary="Search" />
      </ListItemButton>
      {role == "AGENT" && (
        <ListItemButton>
          <Link to={"/create-package"}>
            <ListItemIcon>
              <ShoppingCartIcon />
            </ListItemIcon>
          </Link>
          <Link to={"/create-package"}>
            <ListItemText primary="Create Package" />
          </Link>
        </ListItemButton>
      )}

      <ListItemButton component={Link} to="/upcomingbookings">
        <ListItemIcon>
          <UpcomingIcon />
        </ListItemIcon>
        <ListItemText primary="Upcoming Bookings" />
      </ListItemButton>

      {role == "AGENT" && (
        <ListItemButton component={Link} to="/pastbookings">
          <ListItemIcon>
            <EventNoteIcon />
          </ListItemIcon>
          <ListItemText primary="Past Bookings" />
        </ListItemButton>
      )}
      {role == "AGENT" && (
        <ListItemButton component={Link} to="/customerdetails">
          <ListItemIcon>
            <PeopleIcon />
          </ListItemIcon>
          <ListItemText primary="Customer Details" />
        </ListItemButton>
      )}
      {role == "AGENT" && (
        <ListItemButton component={Link} to="/amountearned">
          <ListItemIcon>
            <MonetizationOnIcon />
          </ListItemIcon>
          <ListItemText primary="Amount Earned" />
        </ListItemButton>
      )}
      {/* {role == "CUSTOMER" && (
        <ListItemButton component={Link} to="/userprofile">
          <ListItemIcon>
            <PeopleIcon />
          </ListItemIcon>
          <ListItemText primary="User Profile" />
        </ListItemButton>
      )} */}
    </React.Fragment>
  );
};

export default MainListItems;
