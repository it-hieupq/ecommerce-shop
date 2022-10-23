import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {CategoryService} from "../../services/category.service";
import {Category} from "../../model/Category";
import {User} from "../../model/User";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  currentUser: User = {
    userId: 0,
    status: true,
    password: '',
    username: '',
    email: '',
    address: '',
    role:''
  }

  categories: Category[] = [];

  constructor(private userService: UserService, private categoryService: CategoryService, private router: Router) {  }

  ngOnInit(): void {
    this.currentUser = this.userService.getCurrentUser();
    this.getAllCategories();
  }

  logoutUser() {
    this.userService.logout();
    this.router.navigate(['']).then(() => {
      window.location.reload();
    });
  }

  getAllCategories(){
    this.categoryService.getAllCategories().subscribe( (res: any) => {
      if ( res != null && res.data != null && res.data.categories) {
        res.data.categories.forEach( (e: Category) => {
          this.categories.push(e);
        });
      }
    });
  }

}
