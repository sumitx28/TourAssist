import * as React from "react";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import DashboardIcon from "@mui/icons-material/Dashboard";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { Link } from "react-router-dom";

export const mainListItems = (
  <React.Fragment>
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
  </React.Fragment>
);
