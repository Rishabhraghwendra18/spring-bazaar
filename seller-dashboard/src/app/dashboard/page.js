"use client"
import {useState} from 'react'
import "./page.css";
import OrdersAnalytics from '@/components/OrdersAnalytics';
import AllOrders from '@/components/AllOrders';
import Inventory from '@/components/Inventory';
import Payments from '@/components/Payments';

function Dashboard() {
  const [selectedOption, setSelectedOption] = useState("dashboard");
  return (
    <div className='dashbaord-container'>
        <div className='side-bar'>
            <h3>Order Navigation</h3>
            <div className='side-bar-options-container'>
            <div className={`side-bar-option ${selectedOption == "dashboard"?"selected-option":""}`} onClick={()=>setSelectedOption("dashboard")}>Dashboard</div>
            <hr className="divider" />
            <div className={`side-bar-option ${selectedOption == "all_orders"?"selected-option":""}`} onClick={()=>setSelectedOption("all_orders")}>All orders</div>
            <hr className="divider" />
            <div className={`side-bar-option ${selectedOption == "inventory"?"selected-option":""}`} onClick={()=>setSelectedOption("inventory")}>Inventory</div>
            </div>
        </div>
        <div className='order-management-container'>
            {selectedOption == "dashboard" && <OrdersAnalytics/>}
            {selectedOption == "all_orders" && <AllOrders/>}
            {selectedOption == "inventory" && <Inventory/>}
        </div>
    </div>
  )
}

export default Dashboard;