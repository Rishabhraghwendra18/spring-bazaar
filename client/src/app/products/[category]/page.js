'use client'
import {useState,useEffect} from 'react'
import Card from '@/components/Card'
import ItemCard from '@/components/ItemCard';
import { useRouter } from 'next/navigation'
import {CircularProgress} from "@mui/material";
import CustomSlider from '@/components/CustomSlider';
import "./page.css"
import CustomButton from '@/components/CustomButton';
import { getAllProducts } from '@/services/home';

function Category({params}) {
  const router = useRouter();
  const [products, setProducts] = useState([]);
  const [originalProductsList, setOriginalProductsList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [value, setValue] = useState([0, 2000]);
  const fetchProducts = async ()=>{
    setIsLoading(true);
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
      setIsLoading(false);
      setProducts(products);
      setOriginalProductsList(products);
    } catch (error) {
      console.log("error while fetching products: ",error)
      setIsLoading(false);
    }
  }
  useEffect(() => {
    fetchProducts();
  }, [])
  const handleChangeSlider = (_,value)=>{
    console.log("value: ",value)
    setValue(value);
  }
  const handleApplyFliter = () =>{
    let filteredProducts = originalProductsList?.filter(product => value[0]<=product?.price && product?.price <= value[1]);
    console.log("filtered: ",filteredProducts)
    setProducts(filteredProducts);
  }

  return (
    <div className='container'>
        <div className='contents-container'>
            <div className='filters'>
              <Card className="filter-card-holder">
                <h3>Filters</h3>
                <hr className="divider" />
                <div className='filter'>
                <h4>Price</h4>
                <CustomSlider handleChange={handleChangeSlider} value={value}/>
                <CustomButton onClick={handleApplyFliter}>Apply Filter</CustomButton>
                </div>
              </Card>
            </div>
            {isLoading ? <div className='products-container' style={{display:"flex",alignItems:'center',justifyContent:"center"}}>
              <CircularProgress color="info" />
            </div>:(
            <div className='products-container'>
              <h2>{params.category}</h2>
              <div className='products'>
                {products.map((product,index)=>(
                  <ItemCard image={product.image} productName={product.name} productPrice={product.price} key={index} onClick={()=>{
                    router.push(`/products/${params.category}/${product.id}`)
                  }}/>
                ))}
              </div>
            </div>
            )}
        </div>
    </div>
  )
}

export default Category