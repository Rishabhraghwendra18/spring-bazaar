import React from 'react'
import "./page.css"

function ProductDetails({params}) {
  return (
    <div>ProductDetails {params.id}</div>
  )
}

export default ProductDetails;