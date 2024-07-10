"use client";
import {useState} from "react";
import { useForm,useFormContext,FormProvider } from "react-hook-form";
import { FaTrashAlt } from 'react-icons/fa';
import { FaPlus } from "react-icons/fa6";
import "./page.css";

function ProductDetails({ params }) {
  const [image, setImage] = useState(null);
  // const { setValue, trigger } = useFormContext();
  const methods = useForm();
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue, trigger
  } = useForm();

  const onSubmit = (data) => {
    console.log(data);
  };

  const handleImageUpload = (event) => {
    const file = event.target.files[0];
    if (file && file.type.startsWith('image/')) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImage(reader.result);
        setValue("image", file); 
        trigger("image"); 
      };
      reader.readAsDataURL(file);
    }
  };

  const handleImageDelete = () => {
    setImage(null);
    setValue("image", null); 
    trigger("image");
  };
  return (
    <div className="product-details-container">
      <h1>Edit Product {params.id}</h1>
      <div className="details-container">
      <div className="image-container">
      <div className="image-upload-container">
      {image ? (
        <div className="image-preview">
          <img src={image} alt="Uploaded Preview" className="uploaded-image" />
          <FaTrashAlt className="delete-icon" onClick={handleImageDelete} />
        </div>
      ) : (
        <div className="no-image">
          <FaPlus className="add-icon" />
          <p>Please select an image by clicking the button below</p>
        </div>
      )}
      <div className="upload-button-container">
        <label className="upload-button">
          Upload File
          <input type="file" accept="image/*" onChange={handleImageUpload} hidden />
        </label>
      </div>
    </div>
      </div>
      <div className="form-container">
      <FormProvider {...methods}>
        <form onSubmit={handleSubmit(onSubmit)} className="product-form">
          <div className="form-group">
            <label htmlFor="title">Title</label>
            <input
              id="title"
              {...register("title", { required: "Title is required" })}
            />
            {errors.title && (
              <span className="error-message">{errors.title.message}</span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
              id="description"
              {...register("description", {
                required: "Description is required",
              })}
            />
            {errors.description && (
              <span className="error-message">
                {errors.description.message}
              </span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="price">Price</label>
            <input
              id="price"
              type="number"
              step="0.01"
              {...register("price", {
                required: "Price is required",
                valueAsNumber: true,
                validate: (value) => value > 0 || "Price must be positive",
              })}
            />
            {errors.price && (
              <span className="error-message">{errors.price.message}</span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="quantity">Quantity</label>
            <input
              id="quantity"
              type="number"
              {...register("quantity", {
                required: "Quantity is required",
                valueAsNumber: true,
                validate: (value) =>
                  (Number.isInteger(value) && value > 0) ||
                  "Quantity must be a positive whole number",
              })}
            />
            {errors.quantity && (
              <span className="error-message">{errors.quantity.message}</span>
            )}
          </div>

          <div className="form-buttons">
            <button type="submit" className="submit-button">
              Submit
            </button>
            <button
              type="button"
              className="reset-button"
              onClick={() => reset()}
            >
              Reset
            </button>
          </div>
        </form>
        </FormProvider>
      </div>
      </div>
    </div>
  );
}

export default ProductDetails;
