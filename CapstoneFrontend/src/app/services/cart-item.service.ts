import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CartItem} from "../model/CartItem";

@Injectable({
  providedIn: 'root'
})
export class CartItemService {

  private userApiUrl = "http://localhost:8666";

  constructor(private http: HttpClient) { }

  addItemToCart(userId: number, item: CartItem) {
    return this.http.post(this.userApiUrl + "/users/"+userId+"/cart/cart-item", item);
  }

  deleteCartItem(userId: number, productId: number) {
    return this.http.delete(this.userApiUrl + "/users/"+userId+"/cart/cart-item/"+productId);
  }

  updateCartItem(userId: number, objTmp: CartItem) {
    return this.http.put(this.userApiUrl + "/users/"+userId+"/cart/cart-item/"+objTmp.productId, objTmp);
  }

}
