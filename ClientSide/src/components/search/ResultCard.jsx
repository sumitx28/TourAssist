import * as React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { Link } from "react-router-dom";

const ImgMediaCard = ({ data, id }) => {
  const [imageURL, setImageURL] = React.useState(
    "https://media.istockphoto.com/id/155439315/photo/passenger-airplane-flying-above-clouds-during-sunset.jpg?s=612x612&w=0&k=20&c=LJWadbs3B-jSGJBVy9s0f8gZMHi2NvWFXa3VJ2lFcL0="
  );
  const {
    packageName,
    totalPackagePrice,
    tripStartDate,
    tripEndDate,
    isPackageCustomizable,
    agentDetails,
  } = data;

  const formattedStartDate = new Date(tripStartDate).toLocaleDateString();
  const formattedEndDate = new Date(tripEndDate).toLocaleDateString();

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
