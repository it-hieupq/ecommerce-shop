import {Component, OnInit} from '@angular/core';
import {Product} from "../model/Product";
import {ActivatedRoute} from "@angular/router";
import {CategoryService} from "../services/category.service";
import {Category} from "../model/Category";
import {ProductFilter} from "../model/dto/ProductFilter";
import {ProductService} from "../services/product.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  products: Product[] = [];

  categories: Category[] = [];

  filter: ProductFilter = {
    categoryIdList: [],
  }

  constructor(private productService: ProductService,
              private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.categoryService.getAllCategories().subscribe((res: any) => {
      console.log(res)
      if(res && res.data && res.data.categories){
        this.categories = res.data.categories;
      }
    });

    this.productService.getAllProducts().subscribe((res: any)=> {
      if(res && res.data && res.data.products){
        this.products = res.data.products
      }
    })

  }

  submitSearchForm() {
    console.log(this.filter)
    if(this.filter.minPrice && this.filter.maxPrice && this.filter.minPrice > this.filter.maxPrice){
      alert("Min price have to smaller max price");
    } else {
      this.productService.search(this.filter).subscribe((res: any)=>{
        console.log(res)
        if(res && res.data && res.data.products){
          this.products = res.data.products
        }
      });
    }
  }

  onCheckboxChange(e: any) {
    if (e.target.checked) {
      if(this.filter.categoryIdList == null)
        this.filter.categoryIdList = [];
      this.filter.categoryIdList.push(e.target.value);
    } else {
      for(let i =0; i< this.filter.categoryIdList.length; i++){
        if(e.target.value == this.filter.categoryIdList[i]){
          this.filter.categoryIdList.splice(i, 1);
          break;
        }
      }
    }
    // console.log(this.filter.categoryIdList);
  }
}
