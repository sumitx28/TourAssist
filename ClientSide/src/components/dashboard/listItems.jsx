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

export const mainListItems = (
  <React.Fragment>
    <ListItemButton component={Link} to="/search">
      <ListItemIcon>
        <SearchIcon />
      </ListItemIcon>
      <ListItemText primary="Search" />
    </ListItemButton>
    <ListItemButton>
      <Link to={"/agent-dashboard"}>
        <ListItemIcon>
          <DashboardIcon />
        </ListItemIcon>
      </Link>
      <Link to={"/agent-dashboard"}>
        <ListItemText primary="Dashboard" />
      </Link>
    </ListItemButton>
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
    <ListItemButton component={Link} to="/upcoming-bookings">
      <ListItemIcon>
        <UpcomingIcon />
      </ListItemIcon>
      <ListItemText primary="Upcoming Bookings" />
    </ListItemButton>
    <ListItemButton component={Link} to="/past-bookings">
      <ListItemIcon>
        <EventNoteIcon />
      </ListItemIcon>
      <ListItemText primary="Past Bookings" />
    </ListItemButton>
    <ListItemButton component={Link} to="/customer-details">
      <ListItemIcon>
        <PeopleIcon />
      </ListItemIcon>
      <ListItemText primary="Customer Details" />
    </ListItemButton>
    <ListItemButton component={Link} to="/amount-earned">
      <ListItemIcon>
        <MonetizationOnIcon />
      </ListItemIcon>
      <ListItemText primary="Amount Earned" />
    </ListItemButton>
  </React.Fragment>
);
