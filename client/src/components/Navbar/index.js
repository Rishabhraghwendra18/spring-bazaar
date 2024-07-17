"use client"
import {useState} from "react";
import Link from "next/link";
import { useRouter } from 'next/navigation'
import "./index.css";
import { FaShoppingCart, FaUser,FaSearch } from 'react-icons/fa';
import LoginSignUpModal from "../LoginSignUpModal";

function Navbar() {
  const router = useRouter();
  const [modalOpen, setModalOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const handleModalOpen = () =>{
    setModalOpen(!modalOpen);
  }
  return (
    <nav className="navbar">
      <div className="navbar-title"><Link href="/">Spring Bazaar</Link></div>
      <ul className="navbar-links">
        <li>Shop</li>
        <li>On Sale</li>
        <li>New Arrivals</li>
        <li>Brands</li>
      </ul>
      <div className="search-container">
        <input className="search-bar" type="text" placeholder="Search products" onChange={(e)=>setSearchQuery(e.target.value)}/>
        <FaSearch className="search-icon" onClick={()=>{
          router.push(`/products/search?search=${searchQuery}`)
        }}/>
      </div>
      <div className="navbar-icons">
        <Link href="/cart">
          <FaShoppingCart style={{ fontSize: '20px' }} />
        </Link>
        <FaUser style={{ fontSize: '20px' }} onClick={handleModalOpen}/>
      </div>
      {modalOpen && <LoginSignUpModal open={modalOpen} handleClose={handleModalOpen} />}
    </nav>
  );
}

export default Navbar;
