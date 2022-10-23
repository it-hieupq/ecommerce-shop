import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserService} from "../services/user.service";
import {User} from "../model/User";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  active: boolean = false;

  signupForm = new FormGroup({
    userId: new FormControl(0),
    username: new FormControl ('', [
      Validators.required,
      Validators.minLength(6)
    ]),
    address: new FormControl(''),
    password: new FormControl( '', [Validators.minLength(6), Validators.required]),
    email: new FormControl ('',  [Validators.required, Validators.email]),
    role: new FormControl('user', [Validators.required]),
    status: new FormControl('true')
  });

  user : User = {
    userId: 0,
    status: true,
    password: '',
    username: '',
    email: '',
    address: '',
    role:''
  }

  constructor(private http: HttpClient, private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    if (this.userService.getCurrentUser().userId > 0){
      alert('Please logout current user before signup new user');
      this.router.navigate(['']);
    }
  }

  get role(){
    return this.signupForm.get('role');
  }

  get username(){
    return this.signupForm.get('username');
  }

  get address(){
    return this.signupForm.get('address');
  }

  get password(){
    return this.signupForm.get('password');
  }

  get email(){
    return this.signupForm.get('email');
  }

  doSignUp(){
    console.log(this.signupForm.value)
    if(this.signupForm.valid){
      console.log("valid")
      let _user: User = this.signupForm.value;
      this.userService.signUp(_user).subscribe((res) => {
        alert('Signup successfully');
        this.signupForm.reset();
        this.router.navigate(['/login']);
      });
    } else {
      this.active = true;
      alert("Form invalid");
    }

  }

}
