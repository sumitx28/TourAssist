import * as React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const ImgMediaCard = ({ data, id }) => {
  const [imageURL, setImageURL] = React.useState("");
  const {
    packageName,
    totalPackagePrice,
    tripStartDate,
    tripEndDate,
    isPackageCustomizable,
    agentDetails,
  } = data;
  const API_URL = process.env.API_URL;

  const formattedStartDate = new Date(tripStartDate).toLocaleDateString();
  const formattedEndDate = new Date(tripEndDate).toLocaleDateString();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${API_URL}/api/v1/package/${id}`);
        setImageURL(response.data.mediaPath[0].media);
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, []);

  return (
    <Card sx={{ maxWidth: 345 }}>
      <CardMedia
        component="img"
        alt={packageName}
        height="140"
        image={imageURL}
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {packageName}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Price: ${totalPackagePrice}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Start Date: {formattedStartDate}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          End Date: {formattedEndDate}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Customizable: {isPackageCustomizable ? "Yes" : "No"}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Agency: {agentDetails.companyName}
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">
          <Link to={`/package/${data.packageId}`}>Explore</Link>
        </Button>
      </CardActions>
    </Card>
  );
};

export default ImgMediaCard;
