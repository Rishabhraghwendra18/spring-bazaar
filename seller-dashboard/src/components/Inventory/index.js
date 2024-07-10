import React from "react";
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
import { styled } from '@mui/material/styles';
import { useRouter } from 'next/navigation'
import { FaPlus } from "react-icons/fa6";
import "./index.css";

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
function createData(id,name, calories, fat, carbs, protein) {
  return { id,name, calories, fat, carbs, protein };
}

const rows = [
  createData(1,"Frozen yoghurt", 159, 6.0),
  createData(2,"Ice cream sandwich", 237, 9.0),
  createData(3,"Eclair", 262, 16.0),
  createData(4,"Cupcake", 305, 3.7),
  createData(5,"Gingerbread", 356, 16.0),
];
function Inventory() {
    const router = useRouter();
  return (
    <div className="inventory-container">
      <h1 className="section-title">Inventory</h1>
      <div className="add-product-container">
        <Button variant="contained" color="success" startIcon={<FaPlus />} onClick={()=>router.push(`/addProduct`)}>
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
          {rows.map((row) => (
            <StyledTableRow key={row.name} onClick={()=>router.push(`/dashboard/${row.id}`)} style={{cursor:'pointer'}}>
                <StyledTableCell component="th" scope="row">
                Image here
              </StyledTableCell>
              <StyledTableCell component="th" scope="row">
                {row.name}
              </StyledTableCell>
              <StyledTableCell component="th" scope="row">
                Description Here
              </StyledTableCell>
              <StyledTableCell align="right">{row.calories}</StyledTableCell>
              <StyledTableCell align="right">Rs {row.fat}</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    </div>
  );
}

export default Inventory;
