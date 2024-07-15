"use client"
import {useEffect} from 'react';
import { useDispatch } from 'react-redux';
import { clearCart } from "@/lib/cartSlice";
import './page.css';

const VerifiedPayment = () => {
  const dispatch = useDispatch();
  const cleanCart=()=>{
    dispatch(clearCart())
  }
  useEffect(() => {
    cleanCart()
  }, [])
  
  return (
    <div className="verified-payment-container">
      <div className="checkmark-container">
        {/* <div className="checkmark"></div> */}
        <img src='/images/yes.png' className='checkmark-png'></img>
      </div>
      <p className="message">Payment Verified. Thanks for shopping with us.</p>
    </div>
  );
};

export default VerifiedPayment;
