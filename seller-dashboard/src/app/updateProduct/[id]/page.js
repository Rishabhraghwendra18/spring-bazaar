"use client";
import { useState, useEffect } from "react";
import { useForm, FormProvider } from "react-hook-form";
import { Snackbar, Alert } from "@mui/material";
import { FaTrashAlt, FaPlus } from "react-icons/fa";
import { getProductById, updateInventoryProduct } from "@/services/inventory";
import "./page.css";

function ProductDetails({ params }) {
  const [image, setImage] = useState(null);
  const [file, setFile] = useState(null);
  const [ProductDetails, setProductDetails] = useState({});
  const [openSnackBar, setOpenSnackBar] = useState({ open: false, message: "" });

  const methods = useForm({
    defaultValues: {
      title: '',
      description: '',
      price: 0,
      quantity: 0,
      image: ''
    }
  });

  const { register, handleSubmit, formState: { errors }, setValue, trigger, reset } = methods;

  const onSubmit = async (data) => {
    const formData = new FormData();
    if (typeof file !== "string") {
      formData.append('file', file);
    }
    formData.append('productId', params?.id);
    formData.append('itemTitle', data?.title);
    formData.append('itemQuantity', data?.quantity);
    formData.append('itemDescription', data?.description);
    formData.append("itemPrice", data?.price);
    try {
      const response = await updateInventoryProduct(formData);
      console.log("response: ", response.data);
      setOpenSnackBar({ open: true, message: "Product updated successfully" });
    } catch (error) {
      console.log("Error while updating product details: ", error);
      setOpenSnackBar({ open: true, message: "Error while updating product details" });
    }
  };

  const fetchProductDetails = async () => {
    try {
      const response = await getProductById(params?.id);
      const { data } = response;
      setProductDetails(data);
      setValue("title", data?.itemTitle);
      setValue("description", data?.itemDescription);
      setValue("price", data?.itemPrice);
      setValue("quantity", data?.itemQuantity);
      setImage(data?.itemPhoto);
      setFile(data?.itemPhoto);
      setValue("image", data?.itemPhoto);
    } catch (error) {
      console.log("Error while fetching product details: ", error);
      setOpenSnackBar({ open: true, message: "Error while fetching product details" });
    }
  };

  useEffect(() => {
    console.log("Fetching product details");
    fetchProductDetails();
  }, [params?.id]);

  const handleImageUpload = (event) => {
    const file = event.target.files[0];
    if (file && file.type.startsWith('image/')) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImage(reader.result);
        setFile(file);
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
      <h1>Edit Product</h1>
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
                  <span className="error-message">{errors.description.message}</span>
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
      {openSnackBar.open && (
        <Snackbar
          open={openSnackBar.open}
          autoHideDuration={6000}
          onClose={() => setOpenSnackBar({ ...openSnackBar, open: false })}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        >
          <Alert
            onClose={() => setOpenSnackBar({ ...openSnackBar, open: false })}
            severity="error"
            variant="filled"
            sx={{ width: "100%" }}
          >
            {openSnackBar.message}
          </Alert>
        </Snackbar>
      )}
    </div>
  );
}

export default ProductDetails;
