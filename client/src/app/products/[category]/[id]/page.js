"use client";
import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import Image from "next/image";
import { Tabs, Tab, Alert,Snackbar, Typography } from "@mui/material";
import { FaStar } from "react-icons/fa";
import productPhoto1 from "@/assets/complete-product-images/image 1.png";
import productPhoto2 from "@/assets/complete-product-images/image 2.png";
import productPhoto3 from "@/assets/complete-product-images/image 3.png";
import "./page.css";
import CustomButton from "@/components/CustomButton";
import Card from "@/components/Card";
import { addItemToCart } from "@/lib/cartSlice";
import { getProductById } from "@/services/home";

function Product({ params }) {
  const [selectedTab, setSelectedTab] = useState(0);
  const [selectedImage, setSelectedImage] = useState(productPhoto1);
  const [selectedSize, setSelectedSize] = useState("Medium");
  const [productDetails, setProductDetails] = useState({});
  const [openSnackBar, setOpenSnackBar] = useState(false);
  const sizes =[
  {
    option:"Small",
    value:"SMALL"
  },
  {
    option:"Medium",
    value:"MEDIUM"
  },
  {
    option:"Large",
    value:"LARGE"
  },
  {
    option:"X-Large",
    value:"XLARGE"
  },

]
  const dispatch = useDispatch();

  const fetchProductById = async () => {
    try {
      const response = await getProductById(params.id);
      setProductDetails(response?.data);
      setSelectedImage(`/images/${response?.data?.fileName}`);
    } catch (error) {
      console.log("Error while fetching product by id: ", error);
    }
  };
  useEffect(() => {
    fetchProductById();
  }, []);

  const handleTabChange = (event, newValue) => {
    setSelectedTab(newValue);
  };

  const handleImageClick = (imagePath) => {
    setSelectedImage(imagePath);
  };

  const handleSizeClick = (size) => {
    setSelectedSize(size);
  };
  const handleAddToCart = () => {
    dispatch(addItemToCart({...productDetails,size:selectedSize}));
    setOpenSnackBar(true);
  };
  return (
    <div className="product-page">
      <div className="product-details">
        <div className="product-images">
          <div className="small-image" onClick={() => {}}>
            <Image src={selectedImage} alt="Product" width={152} height={167} />
          </div>
          <div className="small-image" onClick={() => {}}>
            <Image src={selectedImage} alt="Product" width={152} height={167} />
          </div>
          <div
            className="small-image"
            // onClick={() => handleImageClick(productPhoto3)}
            onClick={() => {}}
          >
            <Image src={selectedImage} alt="Product" width={152} height={167} />
          </div>
        </div>
        <div className="large-image">
          <Image
            src={selectedImage}
            alt="Selected Product"
            width={100}
            height={100}
          />
        </div>
        <div className="product-info">
          <Typography variant="h4" className="product-title">
            {productDetails?.itemTitle}
          </Typography>
          <div className="product-rating">
            <FaStar className="gold-star" />
            <FaStar className="gold-star" />
            <FaStar className="gold-star" />
            <FaStar className="gold-star" />
            <FaStar className="gold-star" />
          </div>
          <Typography className="product-price">
            Rs {productDetails.itemPrice}
          </Typography>
          <Typography className="product-meta">
            {productDetails?.itemDescription}
          </Typography>
          <hr className="divider" />
          <Typography className="choose-size">Choose Size</Typography>
          <div className="size-options">
            {sizes.map((size) => (
              <div
                key={size}
                className={`size-option ${
                  selectedSize === size.value ? "selected" : ""
                }`}
                onClick={() => handleSizeClick(size.value)}
              >
                {size.option}
              </div>
            ))}
          </div>
          <hr className="divider" />
          <CustomButton style={{ maxWidth: 600 }} onClick={handleAddToCart}>
            Add to Cart
          </CustomButton>
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
        {selectedTab === 0 && (
          <div className="tabs-container">
            {productDetails?.itemDescription}
          </div>
        )}
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
      {
        openSnackBar && (<Snackbar open={openSnackBar} autoHideDuration={6000} onClose={()=>setOpenSnackBar(false)} anchorOrigin={{ vertical: 'top', horizontal: 'right' }}>
          <Alert
            onClose={()=>setOpenSnackBar(false)}
            severity="success"
            variant="filled"
            sx={{ width: "100%" }}
          >
            Added Item to Cart
          </Alert>
        </Snackbar>)
      }
    </div>
  );
}

export default Product;
