"use client";
import { useState, useEffect } from "react";
import {
  Button,
  Table,
  TableBody,
  TableCell,
  tableCellClasses,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import { styled } from "@mui/material/styles";
import { useRouter } from "next/navigation";
import { FaPlus } from "react-icons/fa6";
import { getSellerAllProducts } from "@/services/inventory";
import "./index.css";
import Image from "next/image";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));
function createData(id, name, calories, fat, carbs, protein) {
  return { id, name, calories, fat, carbs, protein };
}

function Inventory() {
  const router = useRouter();
  const [productsList, setProductsList] = useState([]);
  const getSellerProducts = async () => {
    try {
      const response = await getSellerAllProducts();
      const { data } = response;
      setProductsList(data);
    } catch (error) {
      console.log("error while getting seller all products: ", error);
    }
  };
  useEffect(()=>{
    getSellerProducts();
  },[])
  return (
    <div className="inventory-container">
      <h1 className="section-title">Inventory</h1>
      <div className="add-product-container">
        <Button
          variant="contained"
          color="success"
          startIcon={<FaPlus />}
          onClick={() => router.push(`/addProduct`)}
        >
          Add product
        </Button>
      </div>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell>Product Image</StyledTableCell>
              <StyledTableCell>Title</StyledTableCell>
              <StyledTableCell>Description</StyledTableCell>
              <StyledTableCell align="right">Quantity</StyledTableCell>
              <StyledTableCell align="right">Price</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {productsList.map((product) => (
              <StyledTableRow
                key={product.itemTitle}
                // onClick={() => router.push(`/dashboard/${product.id}`)}
                style={{ cursor: "pointer" }}
              >
                <StyledTableCell component="th" scope="row">
                <Image src={product.itemPhoto} alt={product.itemTitle} className="table-image" width={100} height={100}/>
                </StyledTableCell>
                <StyledTableCell component="th" scope="row">
                  {product.itemTitle}
                </StyledTableCell>
                <StyledTableCell component="th" scope="row">
                {product.itemDescription}
                </StyledTableCell>
                <StyledTableCell align="right">{product.itemQuantity}</StyledTableCell>
                <StyledTableCell align="right">Rs {product.itemPrice}</StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}

export default Inventory;
