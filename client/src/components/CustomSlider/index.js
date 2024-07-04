import React, { useState } from "react";
import { Slider, Typography, Tooltip } from "@mui/material";
// import { makeStyles } from "@mui/styles";

// const useStyles = makeStyles({
//   slider: {
//     color: "#000000",
//     width: "80%", // Adjust the width as needed
//     margin: "0 auto", // Center the slider horizontally
//   },
//   tooltip: {
//     color: "#000000",
//     textAlign: "center",
//     marginTop: "10px",
//   },
// });

function CustomSlider() {
//   const classes = useStyles();
  const [value, setValue] = useState([0, 200]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const formatValue = (value) => {
    return `$${value}`;
  };
  return (
    <div>
      {/* <Tooltip title={formatValue(value[0]) + ' - ' + formatValue(value[1])} placement="bottom">
      </Tooltip> */}
        <Slider
          value={value}
          onChange={handleChange}
          valueLabelDisplay="off"
          
          min={0}
          max={200}
          color="primary"
          style={{ color: '#000000' }}
        />
      <Typography variant="body2" color="text.secondary" style={{ color: '#000000' }}>
        Selected range: {formatValue(value[0])} - {formatValue(value[1])}
      </Typography>
    </div>
  );
}

export default CustomSlider;
