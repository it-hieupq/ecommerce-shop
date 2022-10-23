import { Component, OnInit } from '@angular/core';
import {Product} from "../../model/Product";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Category} from "../../model/Category";
import {CategoryService} from "../../services/category.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ProductService} from "../../services/product.service";
import {Image} from "../../model/Image";
import {MediaService} from "../../services/media.service";

@Component({
  selector: 'app-admin-product',
  templateUrl: './admin-product.component.html',
  styleUrls: ['./admin-product.component.css']
})
export class AdminProductComponent implements OnInit {

  isAdd = false;

  categories: Category[] = [];

  images: Image[] = [];

  product: Product = {
    productId: -1,
    name: '',
    description: '',
    price: 0,
    active: true,
    inStock: 0,
    categoryIdList: [],
    images: []
  }

  productForm = new FormGroup({
    productId: new FormControl(this.product.productId),
    name: new FormControl ('', [Validators.required]),
    price: new FormControl('', [Validators.required, Validators.min(0)]),
    description: new FormControl( ''),
    inStock: new FormControl (this.product.inStock, [Validators.required, Validators.min(0)]),
    active: new FormControl(this.product.active, [Validators.required])
  });

  constructor(private productService: ProductService,
              private categoryService: CategoryService,
              private fb: FormBuilder,
              private router: Router,
              private _activatedRoute: ActivatedRoute,
              private mediaService: MediaService
              ) {
  }

  ngOnInit(): void {

    this.getAllImages();
    this.getAllCategoriesAdmin();

    let _productId = this._activatedRoute.snapshot.paramMap.get("productId");

    if(_productId == 'add')
      this.isAdd = true;

    if(_productId != null && !this.isAdd){
      this.product.productId = parseInt(_productId + "");
      if(this.product.productId) {
        this.getProductByIdAdmin(this.product.productId);
      }
    }
  }

  private getProductByIdAdmin(productId: number) {
    this.productService.getProductByIdAdmin(productId).subscribe( (res: any) => {
      console.log(res)
      if(res!= null && res.data != null && res.data.product != null){
        this.product = res.data.product;
      }
    }, error => {
      this.product.productId = 0;
    } );
  }

  getAllImages(){
    this.mediaService.getAllImages().subscribe((res: any) => {
      console.log(res)
      if(res && res.data && res.data.images){
        this.images = res.data.images;
      }
    });
  }

  getAllCategoriesAdmin(){
    this.categoryService.getAllCategoriesAdmin().subscribe((res: any) => {
      console.log(res)
      if(res && res.data && res.data.categories){
        this.categories = res.data.categories;
      }
    })
  }

  isChecked(categoryId: number) {
    if(this.product.categoryIdList == null || this.product.categoryIdList.length == 0) return false;
    return this.product.categoryIdList.includes(categoryId);
  }

  submitForm(){
    if(this.productForm.valid){
      let product: Product = this.productForm.value;
      product.categoryIdList = this.product.categoryIdList;
      product.images = this.product.images;
      console.log(product);

      if(!this.isAdd && this.productForm.get("productId")?.value > 0) {
        console.log("Im updating...")
        this.productService.updateByIdAdmin(product).subscribe(res=>{
          console.log(res)
          alert("Updated");
        });
      } else {
        console.log("Im adding...")
        this.productService.addProduct(product).subscribe((res: any)=> {
          console.log(res)
          if(res && res.data && res.data.product){
            alert("Added product: " + res.data.product.name)
            window.location.href = '/admin/products/'+res.data.product.productId;
          } else {
            alert("Failed");
          }
        })
      }
    } else {
      console.log(this.productForm.value);
      console.log("Invalid form");
    }
  }

  delete(productId: number) {
    if(confirm("Are you sure to delete this product?")){
      this.productService.deleteByIdAdmin(productId).subscribe( res => {
        alert("Deleted");
        this.router.navigate(['/admin/products']);
      })
    }
  }

  onCheckboxChange(e: any) {
    if (e.target.checked) {
      if(this.product.categoryIdList == null)
        this.product.categoryIdList = [];
      this.product.categoryIdList.push(this.categories[e.target.value].categoryId);
    } else {
      for(let i =0; i< this.product.categoryIdList.length; i++){
        if(this.categories[e.target.value].categoryId == this.product.categoryIdList[i]){
          this.product.categoryIdList.splice(i, 1);
          break;
        }
      }
    }

    console.log(this.product.categoryIdList);
  }

  onCheckboxImageChange(position: number, event: any) {
    if(event.target.checked) {
      this.product.images.push(this.images[position])
    } else {
      for(let i=0; i<this.product.images.length; i++){
        if(this.product.images[i].imageId == this.images[position].imageId){
          this.product.images.splice(i,1);
          break;
        }
      }
    }

    console.log(this.product.images)
  }

  alreadyChecked(imageId: number){
    for(let i=0;i < this.product.images.length; i++){
      if (this.product.images[i].imageId == imageId)
        return true;
    }
    return false;
  }

}
