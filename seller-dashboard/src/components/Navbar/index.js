"use client"
import {useState} from "react";
import Link from "next/link";
import "./index.css";
import { FaShoppingCart, FaUser } from 'react-icons/fa';
import LoginSignUpModal from "../LoginSignUpModal";

function Navbar() {
  const [modalOpen, setModalOpen] = useState(false);
  const handleModalOpen = () =>{
    setModalOpen(!modalOpen);
  }
  return (
    <nav className="navbar">
      <div className="navbar-title"><Link href={"/"}>Spring Bazaar <span className="seller-dashboard-text">Seller Dashboard</span></Link></div>
      {/* <ul className="navbar-links">
        <li>Shop</li>
        <li>On Sale</li>
        <li>New Arrivals</li>
        <li>Brands</li>
      </ul> */}
      {/* <input className="search-bar" type="text" placeholder="Search products" /> */}
      <div className="navbar-icons">
        {/* <Link href={"/cart"}>
        <FaShoppingCart style={{fontSize:'20px'}} />
        </Link> */}
        <FaUser style={{fontSize:'20px'}} onClick={handleModalOpen}/>
      </div>
      {modalOpen && <LoginSignUpModal open={modalOpen} handleClose={handleModalOpen}/>}
    </nav>
  );
}

export default Navbar;
