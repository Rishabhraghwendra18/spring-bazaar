'use client'
import Image from "next/image";
import { Button } from "@mui/material";
import calvinKleinLogo from "../assets/calvin-klein.png";
import gucciLogo from "../assets/gucci-logo-1.png";
import pradaLogo from "../assets/prada-logo-1.png";
import versaceLogo from "../assets/versace.png";
import zaraLogo from "../assets/zara-logo-1.png";
import product1 from "../assets/Frame 32.png"
import product2 from "../assets/Frame 33.png"
import product3 from "../assets/Frame 34.png"
import product4 from "../assets/Frame 38.png"
import { FaStar } from 'react-icons/fa';
import "./page.css";

export default function Home() {
  const products = [
    { id: 1, name: "T-Shirt With Tape Details", image: product1, price: '$100' },
    { id: 2, name: 'Skinny Fit Jeans', image: product2, price: '$120' },
    { id: 3, name: 'Checkered Shirt', image: product3, price: '$150' },
    { id: 4, name: 'Sleeve Striped T-Shirt', image: product4, price: '$200' },
  ];

  return (
    <>
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
    <div className="brand-section">
        <div className="brands">
          <Image src={calvinKleinLogo} alt="Calvin Klein"  className="brand" />
          <Image src={gucciLogo} alt="Gucci" className="brand" />
          <Image src={pradaLogo} alt="Prada" className="brand" />
          <Image src={versaceLogo} alt="Versace" className="brand" />
          <Image src={zaraLogo} alt="Zara" className="brand" />
        </div>
      </div>
      <div className="new-arrivals-section">
      <h2 className="new-arrivals-title">NEW ARRIVALS</h2>
      <div className="new-arrivals">
        {products.map((product) => (
          <div className="product-card" key={product.id}>
            <Image src={product.image} alt={product.name} className="product-image" />
            <div className="product-name">{product.name}</div>
            <div className="product-reviews">
              <FaStar className="gold-star" /><FaStar className="gold-star" /><FaStar className="gold-star" /><FaStar className="gold-star"/><FaStar className="gold-star"/>
            </div>
            <div className="product-price">{product.price}</div>
          </div>
        ))}
      </div>
    </div>
    <hr className="divider" />
    <div className="new-arrivals-section">
      <h2 className="new-arrivals-title">TOP SELLING</h2>
      <div className="new-arrivals">
        {products.map((product) => (
          <div className="product-card" key={product.id}>
            <Image src={product.image} alt={product.name} className="product-image" />
            <div className="product-name">{product.name}</div>
            <div className="product-reviews">
              <FaStar className="gold-star" /><FaStar className="gold-star" /><FaStar className="gold-star" /><FaStar className="gold-star"/><FaStar className="gold-star"/>
            </div>
            <div className="product-price">{product.price}</div>
          </div>
        ))}
      </div>
    </div>
    </>
  );
}
