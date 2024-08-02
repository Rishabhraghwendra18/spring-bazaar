"use client"
import {useState,useEffect} from 'react';
import { useSelector } from 'react-redux';
import "./page.css"
import Card from '@/components/Card';
import ItemCart from '@/components/ItemCart';
import productPhoto1 from "@/assets/complete-product-images/image 1.png";
import CustomButton from '@/components/CustomButton';
import CheckoutModal from '@/components/Checkout Modal';

function Cart() {
  const items = useSelector(state=>state.cart);
  const [totalCartCost, setTotalCartCost] = useState(0);
  const [totalDiscount, setTotalDiscount] = useState(0);
  const [checkoutModalOpen, setCheckoutModalOpen] = useState(false);

  const calculateTotalCartCostBeforeCharges = ()=>{
    if(items?.length != 0){
      const totalCost = items?.reduce((acc,currentItem)=>acc+currentItem?.itemPrice,0);
      setTotalCartCost(totalCost);
    }
  }
  const calculateDiscount = (discount) =>{
    return (totalCartCost * discount)/100;
  }
  const calculatePriceAfterDiscount = (discount) =>{
    console.log("type: ",typeof calculateDiscount())
    return totalCartCost - calculateDiscount(discount);
  }
  const totalCartCostAfterCharges = (discount,charges)=>{
    return calculatePriceAfterDiscount(discount)+charges;
  }
  console.log("cartItems: ",items)
  useEffect(() => {
    calculateTotalCartCostBeforeCharges()
  }, [items])
  
  // Reference for items object format
  // const items = [
  //   {
  //     photo: productPhoto1,
  //     name: 'Gradient Graphic T-shirt',
  //     size: 'Large',
  //     price: 120
  //   },
  // ];
  return (
    <div className='cart-container'>
      <h2>YOUR CART</h2>
      {items?.length > 0 ? (
        <div className='cart'>
        <Card className="cart-card">
          {items.map((item,index)=>{
            if (index !== items.length -1) {
              return (
                <div className='item' key={index}>
                <ItemCart item={item}/>
                <hr className="divider" />
                </div>
              )
            }
            return <ItemCart item={item} key={index}/>;
          })}
        </Card>
        <Card className="summary-card">
          <h3 className='order-summary-title'>Order Summary</h3>
          <div className='order-summary-container'>
            <div className='order-summary'>
              <span className='title-summary'>Subtotal</span>
              <span className='summary-value'>Rs {totalCartCost}</span>
            </div>
            <div className='order-summary'>
              <span className='title-summary'>Discount(-20%)</span>
              <span className='summary-value' style={{color:"#FF3333"}}>-Rs {calculateDiscount(20)}</span>
            </div>
            <div className='order-summary'>
              <span className='title-summary'>Delivery Fee</span>
              <span className='summary-value'>Rs 15</span>
            </div>
            <hr className="divider" />
            <div className='order-summary'>
              <span className='total-title-summary'>Total</span>
              <span className='total-summary-value'>Rs {totalCartCostAfterCharges(20,15)}</span>
            </div>
          </div>
          <CustomButton onClick={()=>setCheckoutModalOpen(true)}>Go To Checkout</CustomButton>
        </Card>
      </div>
      ):(
        <div style={{height:"100vh",display:'flex',justifyContent:"center"}}><h2>Cart Is Empty</h2></div>
      )}
      {checkoutModalOpen && <CheckoutModal items={items} totalCost={totalCartCostAfterCharges(20,15)} open={checkoutModalOpen} handleClose={()=>setCheckoutModalOpen(false)}/>}
    </div>
  )
}

export default Cart;