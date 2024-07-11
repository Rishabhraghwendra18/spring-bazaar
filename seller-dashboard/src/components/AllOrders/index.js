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
import { getSellerAllOrders } from "@/services/orders";
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

const rows = [
  createData(1, "Frozen yoghurt", 159, 6.0),
  createData(2, "Ice cream sandwich", 237, 9.0),
  createData(3, "Eclair", 262, 16.0),
  createData(4, "Cupcake", 305, 3.7),
  createData(5, "Gingerbread", 356, 16.0),
];
function AllOrders() {
  const router = useRouter();
  const [allOrders, setAllOrders] = useState([]);
  const getOrders = async () => {
    try {
      const response = await getSellerAllOrders();
      const { data } = response;
      console.log("all orders: ",data);
      setAllOrders(data);
    } catch (error) {
      console.log("error while getting seller all orders: ", error);
    }
  };
  useEffect(()=>{
    getOrders();
  },[])
  return (
    <div className="orders-container">
      <h1 className="section-title">All Orders</h1>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell>Product Image</StyledTableCell>
              <StyledTableCell>Order Id</StyledTableCell>
              <StyledTableCell>Product Title</StyledTableCell>
              <StyledTableCell align="right">Buyer Id</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allOrders?.map((order) => (
              <StyledTableRow
                key={order.orderId}
                // onClick={() => router.push(`/dashboard/${product.id}`)}
                style={{ cursor: "pointer" }}
              >
                <StyledTableCell component="th" scope="row">
                <Image src={`/product.fileName`} alt={order.item.itemTitle} className="table-image" width={100} height={100}/>
                </StyledTableCell>
                <StyledTableCell component="th" scope="row">
                  {order.orderId}
                </StyledTableCell>
                <StyledTableCell component="th" scope="row">
                {order.item.itemTitle}
                </StyledTableCell>
                <StyledTableCell align="right">{order.buyerId}</StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}

export default AllOrders;
