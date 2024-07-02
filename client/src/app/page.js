import Image from "next/image";
import { Button } from "@mui/material";
import "./page.css";

export default function Home() {
  return (
    <div className="hero-section">
      <div className="hero-content">
        <h1 className="hero-title">
          FIND CLOTHES <br />
          THAT MATCHES <br />
          YOUR STYLE
        </h1>
        <p className="hero-subtitle">
          Browse through our diverse range of meticulously crafted garments,
          designed to bring out your individuality and cater to your sense of
          style.
        </p>
        <Button variant="contained" className="shop-button">
          Shop Now
        </Button>
        <div className="brand-features">
          <div className="hero-brands">
            <span className="brand-count">200+</span>
            <br />
            <span className="brand-text">International Brands</span>
          </div>
          <div className="hero-brands">
            <span className="brand-count">2,000+</span>
            <br />
            <span className="brand-text">High Quality Products</span>
          </div>
          <div className="hero-brands">
            <span className="brand-count">30,000+</span>
            <br />
            <span className="brand-text">Happy Customers</span>
          </div>
        </div>
      </div>
    </div>
  );
}
