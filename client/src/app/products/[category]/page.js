import React from 'react'
import Card from '@/components/Card'
import ItemCard from '@/components/ItemCard';
import product1 from "../../../assets/Frame 32.png";
import product2 from "../../../assets/Frame 33.png";
import product3 from "../../../assets/Frame 34.png";
import product4 from "../../../assets/Frame 38.png";
import "./page.css"
function Category({params}) {
  const products = [
    {
      id: 1,
      name: "T-Shirt With Tape Details",
      image: product1,
      price: "$100",
    },
    { id: 2, name: "Skinny Fit Jeans", image: product2, price: "$120" },
    { id: 3, name: "Checkered Shirt", image: product3, price: "$150" },
    { id: 4, name: "Sleeve Striped T-Shirt", image: product4, price: "$200" },
    { id: 2, name: "Skinny Fit Jeans", image: product2, price: "$120" },
    { id: 3, name: "Checkered Shirt", image: product3, price: "$150" },
    { id: 4, name: "Sleeve Striped T-Shirt", image: product4, price: "$200" },
    { id: 2, name: "Skinny Fit Jeans", image: product2, price: "$120" },
    { id: 3, name: "Checkered Shirt", image: product3, price: "$150" },
    { id: 4, name: "Sleeve Striped T-Shirt", image: product4, price: "$200" },
  ];

  return (
    <div className='container'>
        <div className='contents-container'>
            <div className='filters'>
              <Card>Filters</Card>
            </div>
            <div className='products-container'>
              <h2>{params.category}</h2>
              <div className='products'>
                {products.map((product,index)=>(
                  <ItemCard image={product.image} productName={product.name} productPrice={product.price} key={index} />
                ))}
              </div>
            </div>
        </div>
    </div>
  )
}

export default Category