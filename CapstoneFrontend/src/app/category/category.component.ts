import {Component, OnInit } from '@angular/core';
import {ProductService} from "../services/product.service";
import {CartService} from "../services/cart.service";
import {Product} from "../model/Product";
import { ActivatedRoute, Router } from '@angular/router';
import {Category} from "../model/Category";
import {CategoryService} from "../services/category.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  products: Product[] = [];

  category: Category = {
    categoryId: 0,
    name: '',
    description: ''
  };

  constructor(private activatedRoute: ActivatedRoute,
              private productService: ProductService,
              private categoryService: CategoryService,
              private cartService: CartService,
              private router: Router) { }

  ngOnInit(): void {
    this.init();
  }

  init(){

    let categoryId = this.activatedRoute.snapshot.paramMap.get("categoryId");

    if (categoryId!=null && !isNaN(Number(categoryId))){

      this.categoryService.getCategoryById(parseInt(categoryId)).subscribe((res: any) => {
        console.log(res);
        if (res && res.data && res.data.category) {
          this.category = res.data.category;
          this.products = res.data.category.products
        } else {
          this.router.navigate(['/error']).then(window.location.reload);
        }
      });

    } else {
      this.category = {
        categoryId: 0,
        name: '',
        description: '',
      };
      this.products = [];
    }
  }

  getProductsByCategoryId(categoryId: number){
    return this.productService.getProductByCategoryId(categoryId);
  }

}
