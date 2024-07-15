import React from 'react';
import './page.css';

const VerifiedPayment = () => {
  return (
    <div className="verified-payment-container">
      <div className="checkmark-container">
        {/* <div className="checkmark"></div> */}
        <img src='/images/yes.png' className='checkmark-png'></img>
      </div>
      <p className="message">Payment Verified. Thanks for shopping with us.</p>
    </div>
  );
};

export default VerifiedPayment;
