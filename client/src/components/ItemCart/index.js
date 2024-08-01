import React from "react";
import { useDispatch } from "react-redux";
import { removeItemFromCart } from "@/lib/cartSlice";
import { FaTrashAlt } from 'react-icons/fa';
import "./index.css";
import Image from "next/image";

function ItemCart({item}) {
  const dispatch = useDispatch();
  const handleRemoveItem = ()=>{
    dispatch(removeItemFromCart(item));
    // console.log("deleted successfully")
  }
  return (
    <div className="item-card">
      <div className="item-photo">
        <Image src={item?.itemPhoto} alt={item.itemTitle} width={100} height={100}/>
      </div>
      <div className="item-details">
        <div className="item-name">{item.itemTitle}</div>
        <div className="item-size">
          Size: <span className="item-size-value">{item.size}</span>
        </div>
        <div className="item-price">Rs {item.itemPrice}</div>
      </div>
      <div className="item-delete">
        <FaTrashAlt className="delete-icon" onClick={handleRemoveItem}/>
      </div>
    </div>
  );
}

export default ItemCart;
