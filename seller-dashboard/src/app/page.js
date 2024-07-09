"use client";
import { useState } from "react";
import Image from "next/image";
import Link from 'next/link'
import { Button } from "@mui/material";
import LoginSignUpModal from "@/components/LoginSignUpModal";
// import calvinKleinLogo from "../assets/calvin-klein.png";
// import gucciLogo from "../assets/gucci-logo-1.png";
// import pradaLogo from "../assets/prada-logo-1.png";
// import versaceLogo from "../assets/versace.png";
// import zaraLogo from "../assets/zara-logo-1.png";
// import product1 from "../assets/Frame 32.png";
// import product2 from "../assets/Frame 33.png";
// import product3 from "../assets/Frame 34.png";
// import product4 from "../assets/Frame 38.png";
import { FaStar } from "react-icons/fa";
import "./page.css";

export default function Home() {
  const [modalOpen, setModalOpen] = useState(false);
  const handleModalOpen = () =>{
    setModalOpen(!modalOpen);
  }
  const products = [
    {
      id: 1,
      name: "T-Shirt With Tape Details",
      image: "/images/product1",
      price: "$100",
    },
    { id: 2, name: "Skinny Fit Jeans", image: "/images/product2", price: "$120" },
    { id: 3, name: "Checkered Shirt", image: "/images/product3", price: "$150" },
    { id: 4, name: "Sleeve Striped T-Shirt", image: "/images/product4", price: "$200" },
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
            MANAGE YOUR <br />
            INVENTORY AND <br />
            SALES EASILY
          </h1>
          <p className="hero-subtitle">
          Easily track your product listings, manage inventory, and view sales
          analytics to optimize your store's performance.
          </p>
          <Button variant="contained" className="shop-button" onClick={handleModalOpen}>
            Log In/Sign Up Now
          </Button>
          <div className="brand-features">
            <div className="hero-brands">
              <span className="brand-count">200+</span>
              <br />
              <span className="brand-text">Happy Sellers</span>
            </div>
            <div className="hero-brands">
              <span className="brand-count">2,000+</span>
              <br />
              <span className="brand-text">Daily Active Customers</span>
            </div>
            <div className="hero-brands">
              <span className="brand-count">30,000+</span>
              <br />
              <span className="brand-text">Happy Customers</span>
            </div>
          </div>
        </div>
      </div>
      {modalOpen && <LoginSignUpModal open={modalOpen} handleClose={handleModalOpen}/>}
      </>
  );
}
