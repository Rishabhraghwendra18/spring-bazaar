import React from "react";
import Link from "next/link";
import "./index.css";
import { FaShoppingCart, FaUser } from 'react-icons/fa';

function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-title"><Link href={"/"}>Spring Bazaar</Link></div>
      <ul className="navbar-links">
        <li>Shop</li>
        <li>On Sale</li>
        <li>New Arrivals</li>
        <li>Brands</li>
      </ul>
      <input className="search-bar" type="text" placeholder="Search products" />
      <div className="navbar-icons">
        <FaShoppingCart style={{fontSize:'20px'}}/>
        <FaUser style={{fontSize:'20px'}}/>
      </div>
    </nav>
  );
}

export default Navbar;
