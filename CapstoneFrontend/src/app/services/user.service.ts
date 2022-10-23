import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/User";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:3000/';

  private adminApiUrl = 'http://localhost:8777/admin/users';
  private userApiUrl = 'http://localhost:8777/users';

  private currentUser: User = {
    userId: 0,
    status: true,
    password: '',
    username: '',
    email: '',
    address: '',
    role:''
  }

  constructor(private http: HttpClient) { }
  //===============from spring server================

  getAllUserAdmin(){
    return this.http.get(this.adminApiUrl);
  }

  login(user: User){
    return this.http.post('http://localhost:8777/login', user);
  }

  signUp(user: User){
    return this.http.post('http://localhost:8777/signup', user);
  }

  //=================================
  getUserById(id: number){
    return this.http.get(this.baseUrl+'users/'+id);
  }

  getAllUser(){
    return this.http.get(this.baseUrl+'users');
  }

  getCurrentUser(){
    if(localStorage.getItem("currentUser")){
      this.currentUser = JSON.parse(localStorage.getItem("currentUser")+'');
    }
    return this.currentUser;
  }

  setCurrentUser(currentUser: User){
    this.currentUser = currentUser;
    localStorage.setItem("currentUser", JSON.stringify(currentUser));
  }

  logout() {
    localStorage.removeItem("currentUser");
  }

  addUser(user: object) {
    return this.http.post(this.baseUrl +'users', user);
  }

  deleteUser(userId: number){
    return this.http.delete(this.baseUrl + 'users/' + userId);
  }

  updateUser(user: User){
    return this.http.patch(this.baseUrl + 'users/'+ user.userId, user);
  }

}
