"use client";
import { useState } from "react";
import { setCookie } from "cookies-next";
import {
  Modal,
  Box,
  Typography,
  Link,
} from "@mui/material";
import { useForm } from "react-hook-form";
import { useRouter } from 'next/navigation'
import { createOrder, verifyAndUpdateOrder } from "@/services/order";
import CustomButton from "../CustomButton";
import "./index.css";

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

function loadScript(src) {
  return new Promise((resolve) => {
    const script = document.createElement("script");
    script.src = src;
    script.onload = () => {
      resolve(true);
    };
    script.onerror = () => {
      resolve(false);
    };
    document.body.appendChild(script);
  });
}

function CheckoutModal({ open, handleClose, items, totalCost }) {
  const router = useRouter();
  const [paymentConfirmation, setPaymentConfirmation] = useState({open:false,message:""});

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();
  async function displayRazorpay(receipt, prefill) {
    const res = await loadScript(
      "https://checkout.razorpay.com/v1/checkout.js"
    );

    if (!res) {
      alert("Razropay failed to load!!");
      return;
    }

    const options = {
      key: process.env.NEXT_PUBLIC_RAZORPAY_KEY_ID, // Enter the Key ID generated from the Dashboard
      amount: `${receipt.amount}`, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
      currency: "INR",
      name: "Spring Bazaar",
      description: "Test Transaction",
      image: "https://example.com/your_logo",
      order_id: receipt.id, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
      handler: async function (response) {
        // alert(response.razorpay_payment_id);
        // alert(response.razorpay_order_id);
        // alert(response.razorpay_signature)
        verifyAndUpdate(
          receipt,
          response.razorpay_payment_id,
          response.razorpay_order_id,
          response.razorpay_signature
        );
      },
      prefill,
      notes: {
        address: "Spring bazaar Office",
      },
      theme: {
        color: "#3399cc",
      },
    };
    const paymentObject = new window.Razorpay(options);
    paymentObject.open();
  }
  const verifyAndUpdate = async (
    receipt,
    razorpayPaymentId,
    razorpayOrderId,
    razorpaySignature
  ) => {
    setPaymentConfirmation({open:true,message:"Confirming Payment"})
    try {
      let payload = {
        orderIds:receipt?.orderIdsList,
        razorpayPaymentId,
        razorpayOrderId,
        razorpaySignature,
      };
      console.log("payload: ", payload);
      const response = await verifyAndUpdateOrder(payload);
      console.log("response data: ", response.data)
      router.push("/verify")
    } catch (error) {
      console.log("error while verifying order: ", error);
      setPaymentConfirmation({...paymentConfirmation,message:"Cannot Confirm Payment"})
    }
  };
  const onPayment = async (data) => {
    try {
      // let payload = items?.map(item=>({
      //   deliveryAddress: data?.address,
      //   pinCode: data?.pinCode,
      //   itemId: item?.id,
      //   orderValue: Math.round(totalCost),
      // }))
      let payload = {
        deliveryAddress: data?.address,
        pinCode: data?.pinCode,
        orderValue: Math.round(totalCost),
        size:items[0]?.size
      };
      payload.productDetails = items?.map((item)=>({
        itemId:item.id,
        size:item?.size
      }));

      const response = await createOrder(payload);
      console.log("response: ", response.data);
      console.log("data: ", payload);
      displayRazorpay(response.data, data);
    } catch (error) {
      let errorMessage = error.response?.data?.message;
      console.log("Error while creating user", errorMessage);
    }
  };

  return (
    <>
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      {paymentConfirmation.open ? <Box sx={style}>
          {paymentConfirmation.message}
        </Box>:(
      <Box sx={style}>
        <Typography className="modal-title">Order Details</Typography>
        <form onSubmit={handleSubmit(onPayment)} className="modal-form">
          <div className="input-container">
            <label htmlFor="name" className="input-label">
              Name *
            </label>
            <input
              id="name"
              name="name"
              type="text"
              {...register("name", { required: true })}
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
              {...register("address", { required: true })}
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
        {/* {snackBarData?.open && (
          <span
            className={`snackbar-message ${
              snackBarData?.messageType == "error"
                ? "snackbar-message-red"
                : "snackbar-message-green"
            }`}
          >
            {snackBarData?.message}
          </span>
        )} */}
      </Box>
        )}
    </Modal>
    </>
  );
}

export default CheckoutModal;
