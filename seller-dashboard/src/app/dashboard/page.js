import React from 'react'
import "./page.css";
import OrdersAnalytics from '@/components/OrdersAnalytics';

function Dashboard() {
  return (
    <div className='dashbaord-container'>
        <div className='side-bar'>
            <h3>Order Navigation</h3>
            Dashboard
            All orders
            Inventory
            Payments
        </div>
        <div className='order-management-container'>
            <OrdersAnalytics/>
        </div>
    </div>
  )
}

export default Dashboard;