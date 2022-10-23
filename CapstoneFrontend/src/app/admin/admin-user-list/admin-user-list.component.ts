import { Component, OnInit } from '@angular/core';
import {User} from "../../model/User";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-admin-user-list',
  templateUrl: './admin-user-list.component.html',
  styleUrls: ['./admin-user-list.component.css']
})
export class AdminUserListComponent implements OnInit {

  u: User = {
    userId: 0,
    username: '',
    password: '',
    role: '',
    address: '',
    email: '',
    status: true
  }

  currentUser: any;

  showing: boolean = false;
  active: boolean = false;
  users: User[] = [];

  addForm = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.minLength(6)
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(6)
    ]),
    email: new FormControl('', [
      Validators.required,
      Validators.email
    ]),
    role: new FormControl('user', [
      Validators.required
    ]),
    address: new FormControl(''),
    status: new FormControl(true)
  });

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getAllUser().subscribe((res: any) => { this.users = res; });
    this.currentUser = this.userService.getCurrentUser();
  }

  get username(){
    return this.addForm.get('username');
  }

  get password(){
    return this.addForm.get('password');
  }

  get email(){
    return this.addForm.get('email');
  }

  get address(){
    return this.addForm.get('address');
  }

  get role(){
    return this.addForm.get('role');
  }

  get status(){
    return this.addForm.get('status')
  }

  deleteUser(userId: number) {
    if(confirm("Are you sure to delete? " + userId)) {
      this.userService.deleteUser(userId).subscribe(res => {

        for(let i = 0; i < this.users.length; i++){
          if ( this.users[i].userId == userId) {
            this.users.splice(i, 1);
            alert('User has been deleted.');
            break;
          }
        }

      });
    }
  }

  addUser() {
    if(this.addForm.valid){
      // this.userService.getUserByUsername(this.username?.value).subscribe( (res: any) => {
      //   if(res.length > 0){
      //     alert('Current username has been used by other user');
      //   } else {
      //     this.userService.addUser(this.addForm.value).subscribe((user: any) => {
      //       alert('Added user');
      //       this.users.push(user);
      //     });
      //   }
      // })
    } else this.active = true;
  }

  updateUser() {
    this.userService.updateUser(this.u).subscribe(res => {
      alert('User has been updated');
    });
  }

}
