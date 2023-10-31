import Grid from "@mui/material/Grid";

const TravelLogo = () => {
  return (
    <Grid
      item
      xs={false}
      sm={4}
      md={7}
      sx={{
        backgroundImage:
          "url(https://freedesignfile.com/upload/2014/06/Blurred-summer-travel-creative-background-02.jpg)",
        backgroundRepeat: "no-repeat",
        backgroundColor: (t) =>
          t.palette.mode === "light" ? t.palette.grey[50] : t.palette.grey[900],
        backgroundSize: "cover",
        backgroundPosition: "center",
        width: "200px", // Adjust the width as needed
        height: "100%", // Adjust the height as needed
      }}
    />
  );
};

export default TravelLogo;
