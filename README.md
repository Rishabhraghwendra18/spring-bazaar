# spring-bazaar
A E-Commerce site built using Java Spring Boot and NextJs.

## Requirements (v1)
### User (Buyer)
- User should be able to register on the platform
- User can order the products

### User (Seller)
- Seller can able to register on the platform
- Seller should be able to add products to it's inventory, and manage orders.
- Each order would have audit trail about it's delivery status.

## APIs
- user
    - /register
    - /login
- home
    - /home
- order
    - /purchase
- inventory
    - /addProduct
    - /updateProduct
    - /removeProduct
    - /viewOrders

_Note: For V2, we will have Reviews table with fields such as item id, rating, review text, buyer id, etc.