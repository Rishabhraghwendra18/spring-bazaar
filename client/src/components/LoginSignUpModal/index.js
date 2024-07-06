import { useState } from "react";
import { Modal, Box, Typography, TextField, Button, Link } from "@mui/material";
import { useForm } from "react-hook-form";
import "./index.css";
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
  const [isSignUp, setIsSignUp] = useState(false);
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();
  const onSubmit = (data) => console.log("data is: ",data);

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Typography className="modal-title">
          {isSignUp ? "Sign Up" : "Login"}
        </Typography>
        {isSignUp ? (
          <form onSubmit={handleSubmit(onSubmit)} className="modal-form">
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
              {...register("email",{required:true})}
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
            <label htmlFor="phoneNumber" className="input-label">
              Phone Number *
            </label>
            <input
              id="phoneNumber"
              name="phoneNumber"
              type="number"
              {...register("phoneNumber", { required: true })}
              // value={formData.password}
              // onChange={handleChange}
              className="modal-input"
              required
            />
          </div>
          <CustomButton type={"submit"}>Log In</CustomButton>
          <Typography className="modal-switch-text">
            Already have an account? {" "}
            <Link
              onClick={()=>setIsSignUp(false)}
              href="#"
              className="modal-switch-link"
            >
              Log In here.
            </Link>
          </Typography>
        </form>
        ) : (
          <form onSubmit={handleSubmit(onSubmit)} className="modal-form">
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
                {...register("email",{required:true})}
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
              Don't have an account? {" "}
              <Link
                onClick={()=>setIsSignUp(true)}
                href="#"
                className="modal-switch-link"
              >
                Sign Up here.
              </Link>
            </Typography>
          </form>
        )}
      </Box>
    </Modal>
  );
}

export default LoginSignUpModal;
