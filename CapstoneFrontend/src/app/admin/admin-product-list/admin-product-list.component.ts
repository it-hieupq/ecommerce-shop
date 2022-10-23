import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserService} from "../../services/user.service";
import {ProductService} from "../../services/product.service";
import { FormGroup, Validators, FormControl } from '@angular/forms';
import {Product} from "../../model/Product";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {MediaService} from "../../services/media.service";

@Component({
  selector: 'app-admin-product-list',
  templateUrl: './admin-product-list.component.html',
  styleUrls: ['./admin-product-list.component.css']
})
export class AdminProductListComponent implements OnInit {

  products: Product[] = [];

  active: boolean = false;
  showing: boolean = false;

  addForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    description: new FormControl(''),
    price: new FormControl(0, [
      Validators.required,
      Validators.min(0)
    ]),
    inStock: new FormControl('', [Validators.min(1), Validators.required]),
    active: new FormControl('true'),
  });

  item: Product = {
    productId: 0,
    name: '',
    description: '',
    price: 0,
    inStock: 0,
    active: true,
    categoryIdList: [],
    images: []
  }

  // Import file
  selectedFile?: File;
  message: string = "";
  progressInfo: any;
  @ViewChild('filesInput')
  filesInput: ElementRef<HTMLInputElement> = {} as ElementRef;
  importedProduct: any[] = [];

  constructor(private userService: UserService,
              private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getAllProducts().subscribe((res : any) => {
      if(res != null && res.data != null && res.data.products != null){
        res.data.products.forEach((product: Product) => {
          this.products.push(product);
        });
      }
    });
  }

  addProduct(){
    if(this.addForm.valid){
      console.log(this.addForm.value);
      this.productService.addProduct(this.addForm.value).subscribe((res: any) => {
        console.log(res);

        if(res && res.data && res.data.product) {
          this.products.push(res.data.product);
        }

        alert('Added product');
      })
    } else this.active = true;
  }

  getFeatureImage(product: Product){
    if(product.images && product.images.length > 0){
      return product.images[0].url;
    } else {
      return "https://dummyimage.com/450x300/dee2e6/6c757d.jpg";
    }
  }

  selectFile(event: any): void {
    this.selectedFile = event.target.files[0];
    this.message ="";
    this.importedProduct = [];
    this.progressInfo = undefined;
  }

  clearFiles(event: any) {
    event.target.files = undefined;
    this.selectedFile = event.target.files;
    this.importedProduct = [];
    this.message = "";
    this.progressInfo =  undefined;
    this.filesInput.nativeElement.value = "";
  }

  startingImport(){
    if (this.selectedFile && this.selectedFile.name) {
      this.progressInfo = { value: 0, fileName: this.selectedFile.name };

      this.productService.importProduct(this.selectedFile).subscribe({
        next: (event: any) => {
          console.log(event)
          if(event.body && event.body.data && event.body.data.products){
            console.log(event.body.data.products)
            let importedProducts: Product[] = event.body.data.products;
            if(importedProducts && importedProducts.length > 0 )
              importedProducts.forEach(e=> {this.products.push(e)})
          }
          if (event.type === HttpEventType.UploadProgress) {
            this.progressInfo.value = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            this.message = 'Uploaded file: ' + this.selectedFile?.name;
          }
        }, error: (err: any) => {
          this.progressInfo.value = 0;
          this.message = 'Could not upload the file: ' + this.selectedFile?.name;
        }});
    } else {
      alert("Can not upload empty file");
    }
  }
}
