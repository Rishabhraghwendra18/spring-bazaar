'use client'
import {useState,useEffect} from 'react'
import Card from '@/components/Card'
import ItemCard from '@/components/ItemCard';
import { useRouter } from 'next/navigation'
import product1 from "../../../assets/Frame 32.png";
import product2 from "../../../assets/Frame 33.png";
import product3 from "../../../assets/Frame 34.png";
import product4 from "../../../assets/Frame 38.png";
import CustomSlider from '@/components/CustomSlider';
import "./page.css"
import CustomButton from '@/components/CustomButton';
import { getAllProducts } from '@/services/home';

function Category({params}) {
  const router = useRouter();
  const [products, setProducts] = useState([]);
  const fetchProducts = async ()=>{
    try {
      const response = await getAllProducts();
      const {data} = response;
      let products = data?.map(product=>({
        id:product.id,
        name:product.itemTitle,
        image:product.itemPhoto,
        fileName:product.fileName,
        price: product.itemPrice
      }));
      setProducts(products);
    } catch (error) {
      console.log("error while fetching products: ",error)
    }
  }
  useEffect(() => {
    fetchProducts();
  }, [])
  
 
  // const products = [
  //   {
  //     id: 1,
  //     name: "T-Shirt With Tape Details",
  //     image: product1,
  //     price: "$100",
  //   },
  //   { id: 2, name: "Skinny Fit Jeans", image: product2, price: "$120" },
  //   { id: 3, name: "Checkered Shirt", image: product3, price: "$150" },
  //   { id: 4, name: "Sleeve Striped T-Shirt", image: product4, price: "$200" },
  //   { id: 2, name: "Skinny Fit Jeans", image: product2, price: "$120" },
  //   { id: 3, name: "Checkered Shirt", image: product3, price: "$150" },
  //   { id: 4, name: "Sleeve Striped T-Shirt", image: product4, price: "$200" },
  //   { id: 2, name: "Skinny Fit Jeans", image: product2, price: "$120" },
  //   { id: 3, name: "Checkered Shirt", image: product3, price: "$150" },
  //   { id: 4, name: "Sleeve Striped T-Shirt", image: product4, price: "$200" },
  // ];

  return (
    <div className='container'>
        <div className='contents-container'>
            <div className='filters'>
              <Card className="filter-card-holder">
                <h3>Filters</h3>
                <hr className="divider" />
                <div className='filter'>
                <h4>Price</h4>
                <CustomSlider/>
                <CustomButton>Apply Filter</CustomButton>
                </div>
              </Card>
            </div>
            <div className='products-container'>
              <h2>{params.category}</h2>
              <div className='products'>
                {products.map((product,index)=>(
                  <ItemCard image={product.fileName} productName={product.name} productPrice={product.price} key={index} onClick={()=>{
                    router.push(`/products/${params.category}/${product.id}`)
                  }}/>
                ))}
              </div>
            </div>
        </div>
    </div>
  )
}

export default Category