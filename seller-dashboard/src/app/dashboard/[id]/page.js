"use client";
import { useState, useEffect } from "react";
import { Snackbar, Alert } from "@mui/material";
import CustomButton from "@/components/CustomButton";
import { getOrderById,updateOrder } from "@/services/orders";
import "./page.css";

function ProductDetails({ params }) {
  const [orderDetails, setOrderDetails] = useState({});
  const [openSnackBar, setOpenSnackBar] = useState({open:false,type:"",message:""});
  const getOrder = async () => {
    try {
      const response = await getOrderById(params?.id);
      console.log("response: ", response.data);
      setOrderDetails(response.data);
    } catch (error) {
      console.log(
        `Error while getting order with order id: ${params.id}`,
        error
      );
      setOpenSnackBar({open:true,type:"error",message:`Error while fetching order with order id: ${params.id}`})
    }
  };
  const handleCloseSnackBar = () =>setOpenSnackBar({...openSnackBar,open:false});
  const handleMarkAsComplete = async()=>{
    const payload = {completed:true};
    try {
      await updateOrder(params?.id,payload);
      setOpenSnackBar({open:true,type:"success",message:`Order with order id ${params.id} updated successfully`})
    } catch (error) {
      console.log("error while updating order: ",error);
      setOpenSnackBar({open:true,type:"error",message:`Error in updating order with order id ${params.id}`})
    }
  }

  useEffect(() => {
    getOrder();
  }, []);

  return (
    <div className="order-details-page">
      <h1 className="order-heading">Order Details #{params.id}</h1>
      <div className="order-content">
        <div className="order-image" style={{backgroundImage:`url(${orderDetails?.productPhotoUrl})`,backgroundSize:'fill',backgroundRepeat:'no-repeat'}}></div>
        <div className="order-info">
          <p>
            <strong>Order Id:</strong> {params.id}
          </p>
          <p>
            <strong>Product Title:</strong> {orderDetails?.productTitle}
          </p>
          <p>
            <strong>Delivery Address:</strong> {orderDetails?.deliveryAddress}
          </p>
          <p>
            <strong>Delivery Pin Code:</strong> {orderDetails?.pinCode}
          </p>
          <p>
            <strong>Buyer Id:</strong> {orderDetails?.buyerId}
          </p>
          <p>
            <strong>Size:</strong> {orderDetails?.productSize}
          </p>
          <CustomButton className="delivered-button" onClick={handleMarkAsComplete} disabled={orderDetails?.isCompleted}>
            {orderDetails?.isCompleted ? "Order Completed Already":"Mark as Delivered"}
          </CustomButton>
        </div>
      </div>
      <Snackbar open={openSnackBar.open} autoHideDuration={6000} onClose={handleCloseSnackBar}>
        <Alert
          onClose={handleCloseSnackBar}
          severity={openSnackBar.type}
          variant="filled"
          sx={{ width: "100%" }}
          anchorOrigin={{ vertical:"top", horizontal:"right" }}
        >
          {openSnackBar.message}
        </Alert>
      </Snackbar>
    </div>
  );
}

export default ProductDetails;
