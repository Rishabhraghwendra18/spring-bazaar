import React from 'react';
import "./index.css";

function Backdrop() {
  return (
    <div className="backdrop">
      <div className="progress-container">
        <div className="circular-progress"></div>
        <p>Verifying payment...</p>
      </div>
    </div>
  )
}

export default Backdrop;