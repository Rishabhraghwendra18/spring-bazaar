"use client";
import { useEffect, useState } from "react";
import { getSellerDashboardAnalytics } from "@/services/orders";
import Card from "../Card";
import "./index.css";

function OrdersAnalytics() {
  const [dashboardAnalytics, setDashboardAnalytics] = useState({});
  const [loading, setLoading] = useState(false);
  const fetchDashboardAnalytics = async () => {
    setLoading(true);
    try {
      const response = await getSellerDashboardAnalytics();
      setDashboardAnalytics(response.data);
      setLoading(false);
    } catch (error) {
      console.log("error while fetching dashboard analytics data: ", error);
      setLoading(false);
    }
  };
  useEffect(() => {
    fetchDashboardAnalytics();
  }, []);
  return (
    <div className="order-analytics-container">
      <h1>Welcome Back!</h1>
      <div className="analytics">
        <Card className={"analytics-card"}>
          {loading ? (
            <h3>Loading...</h3>
          ) : (
            <>
              <h3>{dashboardAnalytics?.totalOrders}</h3>
              <span>Total Orders</span>
            </>
          )}
        </Card>
        <Card className={"analytics-card"}>
          {loading ? (
            <h3>Loading...</h3>
          ) : (
            <>
              <h3>{dashboardAnalytics?.completedOrders}</h3>
              <span>Completed Orders</span>
            </>
          )}
        </Card>
        <Card className={"analytics-card"}>
          {loading ? (
            <h3>Loading...</h3>
          ) : (
            <>
              <h3>{dashboardAnalytics?.newOrders}</h3>
              <span>New Orders</span>
            </>
          )}
        </Card>
      </div>
    </div>
  );
}

export default OrdersAnalytics;
