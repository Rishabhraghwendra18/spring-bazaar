"use client"
import { useState } from "react";
import { setCookie } from 'cookies-next';
import { Modal, Box, Typography, Alert, Snackbar, Link } from "@mui/material";
import { useForm } from "react-hook-form";
import "./index.css";
import { createOrder,verifyAndUpdateOrder } from '@/services/order';
import CustomButton from "../CustomButton";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)", 
  width: 400,
  bgcolor: "background.paper",
  //   border: "2px solid #000",
  borderRadius: "20px",
  boxShadow: 24,
  p: 4,
};

function CheckoutModal({ open, handleClose,items,totalCost }) {
  const [isSignUp, setIsSignUp] = useState(false);
  const [snackBarData, setSnackBarData] = useState({open:false,messageType:"",message:""});
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();
  const onPayment = async (data) => {
    try {
      let payload={
        deliveryAddress:data?.address,
        pinCode:data?.pinCode,
        itemId:items[0]?.id,
        orderValue:Math.round(totalCost)
      }
      const response = await createOrder(payload);
      console.log("response: ",response.data);
      console.log("data: ",payload)
    } catch (error) {
      let errorMessage = error.response?.data?.message
      console.log("Error while creating user",errorMessage);
      setSnackBarData({open:true,messageType:"error",message:errorMessage})
    }
  };
 
  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Typography className="modal-title">
          Order Details
        </Typography>
        <form onSubmit={handleSubmit(onPayment)} className="modal-form">
            <div className="input-container">
            <label htmlFor="name" className="input-label">
              Name *
            </label>
            <input
              id="name"
              name="name"
              type="text"
              {...register("name",{required:true})}
              required
              className="modal-input"
            />
          </div>
          <div className="input-container">
            <label htmlFor="address" className="input-label">
              Address *
            </label>
            <input
              id="address"
              name="address"
              type="text"
              // value={formData.email}
              // onChange={handleChange}
              {...register("address",{required:true})}
              className="modal-input"
              required
            />
          </div>
          <div className="input-container">
            <label htmlFor="pinCode" className="input-label">
              Pin Code *
            </label>
            <input
              id="pinCode"
              name="pinCode"
              type="number"
              {...register("pinCode", { required: true })}
              // value={formData.password}
              // onChange={handleChange}
              className="modal-input"
              required
            />
          </div>
          <div className="input-container">
            <label htmlFor="phoneNo" className="input-label">
              Phone Number *
            </label>
            <input
              id="phoneNo"
              name="phoneNo"
              type="number"
              {...register("phoneNo", { required: true })}
              // value={formData.password}
              // onChange={handleChange}
              className="modal-input"
              required
            />
          </div>
          <CustomButton type={"submit"}>Confirm Payment</CustomButton>

        </form>
        {snackBarData?.open && <span className={`snackbar-message ${snackBarData?.messageType == "error"?"snackbar-message-red":"snackbar-message-green"}`}>{snackBarData?.message}</span>}
      </Box>
    </Modal>
  );
}

export default CheckoutModal;
