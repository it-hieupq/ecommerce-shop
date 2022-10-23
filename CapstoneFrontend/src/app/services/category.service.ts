import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Category} from "../model/Category";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private userApiUrl = 'http://localhost:8888/categories';
  private adminApiUrl = 'http://localhost:8888/admin/categories';

  constructor(private http: HttpClient) { }

  getAllCategories(){
    return this.http.get(this.userApiUrl);
  }

  getCategoryById(categoryId: number){
    return this.http.get(this.userApiUrl + '/' + categoryId);
  }

  deleteCategoryByIdAdmin(categoryId: number){
    return this.http.delete(this.adminApiUrl + '/' + categoryId)
  }

  getAllCategoriesAdmin() {
    return this.http.get(this.adminApiUrl);
  }

  addCategory(obj: Category){
    return this.http.post(this.adminApiUrl, obj);
  }

  updateCategoryAdmin(obj: Category) {
    return this.http.put(this.adminApiUrl + '/' + obj.categoryId, obj);
  }

}
