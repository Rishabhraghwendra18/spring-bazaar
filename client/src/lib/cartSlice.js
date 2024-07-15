import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addItemToCart: (state, action) => {
      state.push(action.payload);
    },
    removeItemFromCart: (state, action) => {
      state = state.filter(
        (item) => item.id !== action.payload.id
      );
    },
    clearCart: (state) => {
      console.log("clearning the sate");
      return [];
    },  
  },
});

export const { addItemToCart, removeItemFromCart, clearCart } = cartSlice.actions;

export default cartSlice.reducer;