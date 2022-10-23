import { Component, OnInit } from '@angular/core';
import {ProductService} from "../services/product.service";
import {CartService} from "../services/cart.service";
import {Product} from "../model/Product";
import {Category} from "../model/Category";
import {CategoryService} from "../services/category.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService,
              private cartService: CartService,) { }

  ngOnInit(): void {
    this.getLatestProducts();
  }

  getLatestProducts(){
    return this.productService.getLatestProducts().subscribe((res: any) => {
      if(res != null && res.data != null && res.data.products != null){
        res.data.products.forEach((product: Product) => {
          this.products.push(product);
        });
      }
    });
  }

}
