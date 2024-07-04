"use client";
import React, { useState } from "react";
import Image from "next/image";
import { Tabs, Tab, Button, Typography } from "@mui/material";
import { FaStar } from "react-icons/fa";
import productPhoto1 from "@/assets/complete-product-images/image 1.png";
import productPhoto2 from "@/assets/complete-product-images/image 2.png";
import productPhoto3 from "@/assets/complete-product-images/image 3.png";
import "./page.css";
import CustomButton from "@/components/CustomButton";
import Card from "@/components/Card";

function Product() {
  const [selectedTab, setSelectedTab] = useState(0);
  const [selectedImage, setSelectedImage] = useState(productPhoto1);
  const [selectedSize, setSelectedSize] = useState("Medium");

  const handleTabChange = (event, newValue) => {
    setSelectedTab(newValue);
  };

  const handleImageClick = (imagePath) => {
    setSelectedImage(imagePath);
  };

  const handleSizeClick = (size) => {
    setSelectedSize(size);
  };
  return (
    <div className="product-page">
      <div className="product-details">
        <div className="product-images">
          <div
            className="small-image"
            onClick={() => handleImageClick(productPhoto1)}
          >
            <Image src={productPhoto1} alt="Product" />
          </div>
          <div
            className="small-image"
            onClick={() => handleImageClick(productPhoto2)}
          >
            <Image src={productPhoto2} alt="Product" />
          </div>
          <div
            className="small-image"
            onClick={() => handleImageClick(productPhoto3)}
          >
            <Image src={productPhoto3} alt="Product" />
          </div>
        </div>
        <div className="large-image">
          <Image src={selectedImage} alt="Selected Product" />
        </div>
        <div className="product-info">
          <Typography variant="h4" className="product-title">
            One Life Graphic T-shirt
          </Typography>
          <div className="product-rating">
            <FaStar className="gold-star" />
            <FaStar className="gold-star" />
            <FaStar className="gold-star" />
            <FaStar className="gold-star" />
            <FaStar className="gold-star" />
          </div>
          <Typography className="product-price">$320</Typography>
          <Typography className="product-meta">
            Short meta description about the product.
          </Typography>
          <hr className="divider" />
          <Typography className="choose-size">Choose Size</Typography>
          <div className="size-options">
            {["Small", "Medium", "Large", "X-Large"].map((size) => (
              <div
                key={size}
                className={`size-option ${
                  selectedSize === size ? "selected" : ""
                }`}
                onClick={() => handleSizeClick(size)}
              >
                {size}
              </div>
            ))}
          </div>
          <hr className="divider" />
          <CustomButton style={{maxWidth:600}}>Add to Cart</CustomButton>
        </div>
      </div>
      <Tabs
        value={selectedTab}
        onChange={handleTabChange}
        className="product-tabs"
        TabIndicatorProps={{
          style: {
            backgroundColor: "#000000", // Black color for the indicator
          },
        }}
      >
        <Tab label="Product Details" />
        <Tab label="Rating & Reviews" />
      </Tabs>
      <div className="tab-content">
        {selectedTab === 0 && <div className="tabs-container">Product Details Content</div>}
        {selectedTab === 1 && (
          <div className="reviews-container tabs-container">
            <Card>
              <div className="review-stars">
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
              </div>
              <div className="review-name">ABC</div>
              <div className="review-text">"ABCDEFGH"</div>
            </Card>
            <Card>
              <div className="review-stars">
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
              </div>
              <div className="review-name">ABC</div>
              <div className="review-text">"ABCDEFGH"</div>
            </Card>
            <Card>
              <div className="review-stars">
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
              </div>
              <div className="review-name">ABC</div>
              <div className="review-text">"ABCDEFGH"</div>
            </Card>
            <Card>
              <div className="review-stars">
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
                <FaStar className="gold-star" />
              </div>
              <div className="review-name">ABC</div>
              <div className="review-text">"ABCDEFGH"</div>
            </Card>
          </div>
        )}
      </div>
    </div>
  );
}

export default Product;
