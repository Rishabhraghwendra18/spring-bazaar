"use client"
import React from 'react';
import "./page.css"
import Card from '@/components/Card';
import ItemCart from '@/components/ItemCart';
import productPhoto1 from "@/assets/complete-product-images/image 1.png";
import CustomButton from '@/components/CustomButton';

function Cart() {
  const items = [
    {
      photo: productPhoto1,
      name: 'Gradient Graphic T-shirt',
      size: 'Large',
      price: 120
    },
    {
      photo: productPhoto1,
      name: 'Gradient Graphic T-shirt',
      size: 'Large',
      price: 120
    },
    {
      photo: productPhoto1,
      name: 'Gradient Graphic T-shirt',
      size: 'Large',
      price: 120
    },
    {
      photo: productPhoto1,
      name: 'Gradient Graphic T-shirt',
      size: 'Large',
      price: 120
    }
  ];
  return (
    <div className='cart-container'>
      <h2>YOUR CART</h2>
      <div className='cart'>
        <Card className="cart-card">
          {items.map((item,index)=>{
            if (index !== items.length -1) {
              return (
                <div className='item'>
                <ItemCart item={item}/>
                <hr className="divider" />
                </div>
              )
            }
            return <ItemCart item={item}/>;
          })}
        </Card>
        <Card className="summary-card">
          <h3 className='order-summary-title'>Order Summary</h3>
          <div className='order-summary-container'>
            <div className='order-summary'>
              <span className='title-summary'>Subtotal</span>
              <span className='summary-value'>$565</span>
            </div>
            <div className='order-summary'>
              <span className='title-summary'>Discount(-20%)</span>
              <span className='summary-value' style={{color:"#FF3333"}}>-$113</span>
            </div>
            <div className='order-summary'>
              <span className='title-summary'>Delivery Fee</span>
              <span className='summary-value'>$15</span>
            </div>
            <hr className="divider" />
            <div className='order-summary'>
              <span className='total-title-summary'>Total</span>
              <span className='total-summary-value'>$15</span>
            </div>
          </div>
          <CustomButton>Go To Checkout</CustomButton>
        </Card>
      </div>
    </div>
  )
}

export default Cart;