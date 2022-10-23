import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {OrderService} from "./order.service";
import {UserService} from "./user.service";
import {User} from "../model/User";
import {CartItem} from "../model/CartItem";
import {CartItemService} from "./cart-item.service";
import {Cart} from "../model/Cart";

@Injectable({
  providedIn: 'root'
})

export class CartService {

  user: User = {
    userId: 0,
    status: true,
    password: '',
    username: '',
    email: '',
    address: '',
    role:''
  }

  private apiUrl = 'http://localhost:8666';

  constructor(private http: HttpClient,
              private orderService: OrderService,
              private cartItemService: CartItemService,
              private userService: UserService) {
    this.user = userService.getCurrentUser();
  }

  clearCart(userId: number){
    return this.http.delete(this.apiUrl+'/users/' + userId + '/cart/cart-item');
  }

  addToCart(item: CartItem){
    return this.cartItemService.addItemToCart(this.user.userId, item);
  }

  getCart() {
    return this.http.get(this.apiUrl+'/users/'+this.user.userId+'/cart');
  }

}
