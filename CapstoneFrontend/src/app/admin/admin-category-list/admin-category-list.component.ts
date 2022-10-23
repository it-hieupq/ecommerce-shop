import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Category} from "../../model/Category";
import {CategoryService} from "../../services/category.service";

@Component({
  selector: 'app-admin-category-list',
  templateUrl: './admin-category-list.component.html',
  styleUrls: ['./admin-category-list.component.css']
})
export class AdminCategoryListComponent implements OnInit {
  showing: boolean = false;
  active: boolean = false;
  categories: Category[] = [];

  category: Category = {
    categoryId: 0,
    name: '',
    description: ''
  }

  addForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    description: new FormControl('')
  });

  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getAllCategories().subscribe((res: any) => {
      if(res && res.data != null && res.data.categories != null){
        console.log(res)
        res.data.categories.forEach((e: Category) => {
          this.categories.push(e);
        })
      }
    });
  }

  get name() {
    return this.addForm.get('name');
  }

  get description(){
    return this.addForm.get('description')
  }

  deleteCategory(categoryId: number) {
    if(confirm("Are you sure to delete this category, whether there have products? " + categoryId)) {
      this.categoryService.deleteCategoryByIdAdmin(categoryId).subscribe( res => {
        console.log(res)
        this.categories = this.categories.filter(function (element){
          return element.categoryId != categoryId;
        })

      } );
    }
  }

  addCategory() {
    if(this.addForm.valid) {
      console.log(this.addForm.value);
      this.categoryService.addCategory(this.addForm.value).subscribe((res: any) => {
        alert('Added category');
        if(res && res.data != null && res.data.category != null) {
          let category: Category = res.data.category;
          this.categories.push(category);
        }
      });
    } else {
      alert("Invalid form")
    }
  }

  updateCategory() {
    console.log(this.category)
    if(this.category.name != null && this.category.name.length > 0){
      this.categoryService.updateCategoryAdmin(this.category).subscribe((res: any) => {
        console.log(res)
        if(res && res.data != null && res.data.category != null){
          let _category: Category = res.data.category;
          this.categories.map((element) => {
            if(element.categoryId == this.category.categoryId){
              element = this.category;
            }
          });
          alert('Category has been updated');
        }

      });
    }
  }

}
