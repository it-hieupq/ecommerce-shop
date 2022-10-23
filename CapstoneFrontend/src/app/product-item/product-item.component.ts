import { Component, Input, OnInit } from '@angular/core';
import {ProductService} from "../services/product.service";
import {CartService} from "../services/cart.service";
import {Product} from "../model/Product";
import {CartItem} from "../model/CartItem";

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {

  @Input() product: Product = {
    productId: 0,
    name: '',
    price: 0,
    description: '',
    inStock: 0,
    active: true,
    categoryIdList: [],
    images: []
  };

  quantity: number = 1;

  constructor(private service: ProductService,
              private cartService: CartService) { }

  ngOnInit(): void {
    // this.cartService.getCart().subscribe((res: any) => {
    //   if(res &&  res.data && res.data.cart){
    //     this.cart = res.data.cart;
    //   }
    // });
  }

  addToCart(){
    let item: CartItem = {
      productId: this.product.productId,
      quantity: this.quantity
    }

    return this.cartService.addToCart(item).subscribe((res: any)=> {
      console.log(res);
      if(res && res.message){
        alert(res.message)
      } else {
        alert("Error has occurred!");
      }
    });
  }

  increaseQuantity(){
    this.quantity++;
  }

  decreaseQuantity(){
    if(this.quantity > 1) {
      this.quantity--;
    }
  }

  addToWishlist() {

  }

  getFeatureImage(){
    if(this.product.images && this.product.images.length > 0){
      return this.product.images[0].url;
    } else {
      return "https://dummyimage.com/450x300/dee2e6/6c757d.jpg";
    }
  }

}
