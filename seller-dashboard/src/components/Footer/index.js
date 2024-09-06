import React from 'react';
import './index.css';

const Footer = () => {
  return (
    <div className="footer">
      <div className="footer-row">
        <div className="footer-column footer-column-1">
          <h2 className="footer-title">Spring Bazaar</h2>
          <p className="footer-description">
            We have clothes that suit your style and which you’re proud to wear. From women to men.
          </p>
        </div>
        <div className="footer-column footer-column-2">
          <h3 className="footer-heading">Company</h3>
          <ul className="footer-links">
            <li><a href="#">About Us</a></li>
            <li><a href="#">Careers</a></li>
            <li><a href="#">Blog</a></li>
          </ul>
        </div>
        <div className="footer-column footer-column-3">
          <h3 className="footer-heading">Help</h3>
          <ul className="footer-links">
            <li><a href="#">Contact Us</a></li>
            <li><a href="#">Shipping</a></li>
            <li><a href="#">Returns</a></li>
          </ul>
        </div>
        <div className="footer-column footer-column-4">
          <h3 className="footer-heading">FAQ</h3>
          <ul className="footer-links">
            <li><a href="#">Order Status</a></li>
            <li><a href="#">Payment Options</a></li>
            <li><a href="#">Size Guide</a></li>
          </ul>
        </div>
        <div className="footer-column">
          <h3 className="footer-heading">Resources</h3>
          <ul className="footer-links">
            <li><a href="#">Free eBooks</a></li>
            <li><a href="#">Fashion Events</a></li>
          </ul>
        </div>
      </div>
      <hr className="footer-divider" />
      <div className="footer-row footer-row-2">
        <p className="footer-developed-by">
          Developed by Rishabh Raghwendra © 2024, All Rights Reserved.
        </p>
        {/* <div className="footer-payment-logos">
          <img src="path_to_visa_logo" alt="Visa" />
          <img src="path_to_mastercard_logo" alt="Mastercard" />
          <img src="path_to_paypal_logo" alt="Paypal" />
          <img src="path_to_apple_pay_logo" alt="Apple Pay" />
          <img src="path_to_gpay_logo" alt="GPay" />
        </div> */}
      </div>
    </div>
  );
}

export default Footer;
