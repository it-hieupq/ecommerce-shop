import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../services/user.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {User} from "../model/User";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm = new FormGroup({
    username: new FormControl('', [
      Validators.required,
    ]),
    password: new FormControl('', [
      Validators.required,
    ])
  });

  constructor( private http: HttpClient, private router: Router, private userService: UserService) {
    if(userService.getCurrentUser().userId > 0){
      router.navigate(['']);
    }
  }

  ngOnInit(): void { }

  get username(){
    return this.loginForm.get('username');
  }

  get password(){
    return this.loginForm.get('password');
  }

  doLogin(){
    if(this.loginForm.valid){
      let user: User = {
        userId: 0,
        status: true,
        password: this.password?.value,
        username: this.username?.value,
        email: '',
        address: '',
        role:''
      }
      this.userService.login(user).subscribe((res: any) => {
          console.log(res);
          if(res && res.data && res.data.user){
              user = res.data.user;
              this.loginForm.reset();//reset the login form
              this.userService.setCurrentUser(user);
              this.router.navigate(['']).then(() => {
                window.location.reload();//
              })
          }
        });
    }
  }
}
