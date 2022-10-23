import { Injectable } from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {UserService} from "../services/user.service";
import {User} from "../model/User";

@Injectable({
  providedIn: 'root'
})

export class AuthAdminService implements CanActivate{

  user: User = {
    userId: 0,
    status: true,
    password: '',
    username: '',
    email: '',
    address: '',
    role:''
  };
  constructor(private userService: UserService, public router: Router) { }

  canActivate(): boolean {
    this.user = this.userService.getCurrentUser();
    if(this.user.userId && this.user.role.toLowerCase() == 'admin'){
      return true;
    } else{
      this.router.navigate(['/forbidden']);
      return false;
    }
  }


}
