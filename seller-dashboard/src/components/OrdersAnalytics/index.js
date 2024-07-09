import React from "react";
import Card from "../Card";
import "./index.css";

function OrdersAnalytics() {
  return (
    <div className="order-analytics-container">
      <h1>Welcome Back!</h1>
      <div className="analytics">
        <Card className={"analytics-card"}>
            <h3>294</h3>
            <span>Total Orders</span>
        </Card>
        <Card className={"analytics-card"}>
            <h3>200</h3>
            <span>Completed Orders</span>
        </Card>
        <Card className={"analytics-card"}>
            <h3>94</h3>
            <span>New Orders</span>
        </Card>
      </div>
    </div>
  );
}

export default OrdersAnalytics;
