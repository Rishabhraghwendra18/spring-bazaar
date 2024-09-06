'use client'
import {useState,useEffect,Suspense} from 'react'
import Card from '@/components/Card'
import ItemCard from '@/components/ItemCard';
import { useRouter,useSearchParams } from 'next/navigation'
import product1 from "../../../assets/Frame 32.png";
import product2 from "../../../assets/Frame 33.png";
import product3 from "../../../assets/Frame 34.png";
import product4 from "../../../assets/Frame 38.png";
import CustomSlider from '@/components/CustomSlider';
import "./page.css"
import CustomButton from '@/components/CustomButton';
import { searchProductByTitle } from '@/services/home';

function Category({params}) {
  const router = useRouter();
  const searchParams = useSearchParams();
  const [value, setValue] = useState([0, 2000]);

  const [products, setProducts] = useState([]);
  console.log("params: ",params)
  const fetchProducts = async ()=>{
    const query = searchParams.get("search");
    if(query == "" && query == null) return;
    try {
      const response = await searchProductByTitle(query);
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
  const handleChangeSlider = (_,value)=>{
    console.log("value: ",value)
    setValue(value);
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
                <CustomButton>Apply Filter</CustomButton>
                </div>
              </Card>
            </div>
            <div className='products-container'>
              <h2>Search</h2>
              <div className='products'>
                {products.map((product,index)=>(
                  <ItemCard image={product.image} productName={product.name} productPrice={product.price} key={index} onClick={()=>{
                    router.push(`/products/${searchParams.get("search")}/${product.id}`)
                  }}/>
                ))}
              </div>
            </div>
        </div>
    </div>
  )
}

export default function WrappedCategory(props) {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <Category {...props} />
    </Suspense>
  );
}