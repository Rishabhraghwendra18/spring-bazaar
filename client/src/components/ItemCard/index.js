import React from "react";
import Image from "next/image";
import { FaStar } from "react-icons/fa";
import "./index.css"

function ItemCard({image,productName,productPrice,onClick=()=>{}}) {
  console.log("images: ",image)
  return (
    <div className="product-card" onClick={onClick}>
      <Image src={`/images/${image}`} alt={productName} className="product-image" width={295} height={298}/>
      <div className="product-name">{productName}</div>
      <div className="product-reviews">
        <FaStar className="gold-star" />
        <FaStar className="gold-star" />
        <FaStar className="gold-star" />
        <FaStar className="gold-star" />
        <FaStar className="gold-star" />
      </div>
      <div className="product-price">Rs {productPrice}</div>
    </div>
  );
}

export default ItemCard;
