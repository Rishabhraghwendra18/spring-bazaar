"use client";
import Image from "next/image";
import Link from 'next/link'
import { Button } from "@mui/material";
import calvinKleinLogo from "../assets/calvin-klein.png";
import gucciLogo from "../assets/gucci-logo-1.png";
import pradaLogo from "../assets/prada-logo-1.png";
import versaceLogo from "../assets/versace.png";
import zaraLogo from "../assets/zara-logo-1.png";
import product1 from "../assets/Frame 32.png";
import product2 from "../assets/Frame 33.png";
import product3 from "../assets/Frame 34.png";
import product4 from "../assets/Frame 38.png";
import { FaStar } from "react-icons/fa";
import "./page.css";

export default function Home() {
  const products = [
    {
      id: 1,
      name: "T-Shirt With Tape Details",
      image: product1,
      price: "$100",
    },
    { id: 2, name: "Skinny Fit Jeans", image: product2, price: "$120" },
    { id: 3, name: "Checkered Shirt", image: product3, price: "$150" },
    { id: 4, name: "Sleeve Striped T-Shirt", image: product4, price: "$200" },
  ];

  const reviews = [
    {
      name: 'Sarah M.',
      review: "I'm blown away by the quality and style of the clothes I received from Shop.co. From casual wear to elegant dresses, every piece I've bought has exceeded my expectations."
    },
    {
      name: 'Alex K.',
      review: "Finding clothes that align with my personal style used to be a challenge until I discovered Shop.co. The range of options they offer is truly remarkable, catering to a variety of tastes and occasions."
    },
    {
      name: 'James L.',
      review: "As someone who's always on the lookout for unique fashion pieces, I'm thrilled to have stumbled upon Shop.co. The selection of clothes is not only diverse but also on-point with the latest trends."
    },
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
          <Image src={calvinKleinLogo} alt="Calvin Klein" className="brand" />
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
              <Image
                src={product.image}
                alt={product.name}
                className="product-image"
              />
              <div className="product-name">{product.name}</div>
              <div className="product-reviews">
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
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
              <Image
                src={product.image}
                alt={product.name}
                className="product-image"
              />
              <div className="product-name">{product.name}</div>
              <div className="product-reviews">
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
              </div>
              <div className="product-price">{product.price}</div>
            </div>
          ))}
        </div>
      </div>
      <div className="browse-by-style-section">
      <h2 className="section-title">Browse By Dress Style</h2>
      <div className="styles-container">
        <div className="style-row">
          <div className="style-card casual">
            <div className="style-text"><Link href={"/products/Casual"} className="category-link">Casual</Link></div>
          </div>
          <div className="style-card formal">
            <div className="style-text"><Link href={"/products/Fromal"} className="category-link">Formal</Link></div>
          </div>
        </div>
        <div className="style-row">
          <div className="style-card party">
            <div className="style-text"><Link href={"/products/Party"} className="category-link">Party</Link></div>
          </div>
          <div className="style-card gym">
            <div className="style-text"><Link href={"/products/Gym"} className="category-link">Gym</Link></div>
          </div>
        </div>
      </div>
    </div>

    <div className="reviews-section">
      <h2 className="reviews-title">OUR HAPPY CUSTOMERS</h2>
      <div className="reviews-container">
        {reviews.map((review, index) => (
          <div className="review-card" key={index}>
            <div className="review-stars">
              <FaStar className="gold-star" />
              <FaStar className="gold-star" />
              <FaStar className="gold-star" />
              <FaStar className="gold-star" />
              <FaStar className="gold-star" />
            </div>
            <div className="review-name">{review.name}</div>
            <div className="review-text">"{review.review}"</div>
          </div>
        ))}
      </div>
    </div>
    </>
  );
}
