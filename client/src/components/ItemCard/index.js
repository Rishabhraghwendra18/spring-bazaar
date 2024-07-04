import React from "react";
import Image from "next/image";
import { FaStar } from "react-icons/fa";
import "./index.css"

function ItemCard({image,productName,productPrice}) {
  return (
    <div className="product-card">
      <Image src={image} alt={productName} className="product-image" />
      <div className="product-name">{productName}</div>
      <div className="product-reviews">
        <FaStar className="gold-star" />
        <FaStar className="gold-star" />
        <FaStar className="gold-star" />
        <FaStar className="gold-star" />
        <FaStar className="gold-star" />
      </div>
      <div className="product-price">{productPrice}</div>
    </div>
  );
}

export default ItemCard;
