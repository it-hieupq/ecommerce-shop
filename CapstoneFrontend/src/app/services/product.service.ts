import { Injectable } from '@angular/core';
import {HttpClient, HttpParams, HttpRequest} from "@angular/common/http";
import {Product} from "../model/Product";
import {ProductFilter} from "../model/dto/ProductFilter";

@Injectable({
  providedIn: 'root'
})

export class ProductService {

  private adminApiUrl = 'http://localhost:8888/admin/products';
  private userApiUrl = 'http://localhost:8888/products';
  private quantity: number = 10;

  constructor(private http: HttpClient) { }

  importProduct(file: File) {
    console.log(file.name)
    const formData: FormData = new FormData();
    formData.append('file', file);
    const req = new HttpRequest('POST', `${this.adminApiUrl}/import`, formData, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request(req);
  }

  getProductIn(ids: number[]) {
    let params = new HttpParams();
    params = params.append('ids', ids.join(', '));
    return this.http.get(this.userApiUrl+'/in', { params: params })
  }

  getLatestProducts(){
    return this.http.get(this.userApiUrl+"/latest/"+this.quantity);
  }

  getAllProducts(){
    return this.http.get(this.userApiUrl);
  }

  getProductByIdAdmin(productId: number) {
    return this.http.get(this.adminApiUrl+'/'+productId);
  }

  updateByIdAdmin(product: Product) {
    return this.http.put(this.adminApiUrl+'/'+product.productId, product);
  }

  deleteByIdAdmin(productId: number) {
    return this.http.delete(this.adminApiUrl+'/'+productId);
  }

  addProduct(obj: Product) {
    return this.http.post(this.adminApiUrl, obj);
  }

  //======================================

  getProductByCategoryId(categoryId: number){
    return this.http.get("http://localhost:8889/categories/"+categoryId+"/products");
  }

  search(filter: ProductFilter) {
    return this.http.post(this.userApiUrl+'/search', filter);
  }
}
