"use client";
import { useState } from "react";
import { setCookie, getCookie, deleteCookie } from "cookies-next";
import { useRouter } from "next/navigation";
import { Modal, Box, Typography, Alert, Snackbar, Link } from "@mui/material";
import { useForm } from "react-hook-form";
import "./index.css";
import { createUser, loginUser } from "@/services/user";
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

function LoginSignUpModal({ open, handleClose }) {
  const router = useRouter();
  const [isSignUp, setIsSignUp] = useState(false);
  const [snackBarData, setSnackBarData] = useState({
    open: false,
    messageType: "",
    message: "",
  });
  const isLoggedIn = getCookie("Authorization");
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();
  const onSignUp = async (data) => {
    try {
      let payload = { ...data, role: "ROLE_SELLER" };
      const response = await createUser(payload);
      console.log("response data: ", response.data);
      handleClose();
    } catch (error) {
      let errorMessage = error.response?.data?.message;
      console.log("Error while creating user", errorMessage);
      setSnackBarData({
        open: true,
        messageType: "error",
        message: errorMessage,
      });
    }
  };
  const onLogIn = async (data) => {
    try {
      let payload = { ...data };
      const response = await loginUser(payload);
      const jwtToken = response.data?.token;
      const expirationTime = response.data?.expirationDate;
      if (jwtToken != null) {
        setCookie("Authorization", `Bearer ${jwtToken}`, {
          expires: new Date(expirationTime),
        });
        console.log("response data: ", jwtToken);
        router.push("/dashboard");
        handleClose();
      } else {
        throw new Error("JWT token not present in response");
      }
    } catch (error) {
      console.log("Error while creating user", error);
      setSnackBarData({
        open: true,
        messageType: "error",
        message: "Unable to login",
      });
    }
  };
  const handleLogOut = () => {
    deleteCookie("Authorization");
    handleClose();
  };

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        {isLoggedIn != null ? (
          <Box>
            <Typography className="modal-title">
              Do you want to Log out?
            </Typography>
            <Box
              display="flex"
              justifyContent="space-around"
              mt={2}
              gap={"0.8rem"}
            >
              <CustomButton
                variant="outlined"
                style={{ backgroundColor: "white", color: "black", flex: 1 }}
                onClick={handleClose}
              >
                No
              </CustomButton>
              <CustomButton
                style={{ backgroundColor: "red", color: "white", flex: 1 }}
                onClick={handleLogOut}
              >
                Ok
              </CustomButton>
            </Box>
          </Box>
        ) : (
          <Box>
            <Typography className="modal-title">
              {isSignUp ? "Sign Up" : "Login"}
            </Typography>
            {isSignUp ? (
              <form onSubmit={handleSubmit(onSignUp)} className="modal-form">
                <div className="input-container">
                  <label htmlFor="name" className="input-label">
                    Name
                  </label>
                  <input
                    id="name"
                    name="name"
                    type="text"
                    {...register("name")}
                    className="modal-input"
                  />
                </div>
                <div className="input-container">
                  <label htmlFor="email" className="input-label">
                    Email *
                  </label>
                  <input
                    id="email"
                    name="email"
                    type="email"
                    // value={formData.email}
                    // onChange={handleChange}
                    {...register("email", { required: true })}
                    className="modal-input"
                    required
                  />
                </div>
                <div className="input-container">
                  <label htmlFor="password" className="input-label">
                    Password *
                  </label>
                  <input
                    id="password"
                    name="password"
                    type="password"
                    {...register("password", { required: true })}
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
                <CustomButton type={"submit"}>Sign Up</CustomButton>
                <Typography className="modal-switch-text">
                  Already have an account?{" "}
                  <Link
                    onClick={() => setIsSignUp(false)}
                    href="#"
                    className="modal-switch-link"
                  >
                    Log In here.
                  </Link>
                </Typography>
              </form>
            ) : (
              <form onSubmit={handleSubmit(onLogIn)} className="modal-form">
                <div className="input-container">
                  <label htmlFor="email" className="input-label">
                    Email *
                  </label>
                  <input
                    id="email"
                    name="email"
                    type="email"
                    // value={formData.email}
                    // onChange={handleChange}
                    {...register("email", { required: true })}
                    className="modal-input"
                    required
                  />
                </div>
                <div className="input-container">
                  <label htmlFor="password" className="input-label">
                    Password *
                  </label>
                  <input
                    id="password"
                    name="password"
                    type="password"
                    {...register("password", { required: true })}
                    // value={formData.password}
                    // onChange={handleChange}
                    className="modal-input"
                    required
                  />
                </div>
                <CustomButton type={"submit"}>Log In</CustomButton>
                <Typography className="modal-switch-text">
                  Don't have an account?{" "}
                  <Link
                    onClick={() => setIsSignUp(true)}
                    href="#"
                    className="modal-switch-link"
                  >
                    Sign Up here.
                  </Link>
                </Typography>
              </form>
            )}
          </Box>
        )}
        {snackBarData?.open && (
          <span
            className={`snackbar-message ${
              snackBarData?.messageType == "error"
                ? "snackbar-message-red"
                : "snackbar-message-green"
            }`}
          >
            {snackBarData?.message}
          </span>
        )}
      </Box>
    </Modal>
  );
}

export default LoginSignUpModal;
