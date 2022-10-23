import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class OrderDetailsService {

  baseUrl = 'http://localhost:3000/';

  constructor(private http: HttpClient) { }

  addOrderDetails(orderId: number, obj: object){
    return this.http.post(this.baseUrl + 'orders/'+orderId + '/order_details', obj);
  }

  getOrderDetailsByOrderId(orderId: number){
    return this.http.get(this.baseUrl + 'orders/'+orderId + '/order_details');
  }



}
