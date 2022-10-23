import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CartService} from "./cart.service";
import {Router} from "@angular/router";
import {OrderDetails} from "../model/OrderDetails";
import {OrderStatus} from "../model/OrderStatus";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  currentOrder: any;

  private baseUrl = 'http://localhost:3000/';
  private adminApiUrl = 'http://localhost:8666';
  private userApiUrl = 'http://localhost:8666';

  constructor(private http: HttpClient) {
  }

  // placeOrder(orderId: number) {
    // let status: boolean = false;
    //
    // this.addOrder({
    //   'userId': userId,
    //   'orderDate': new Date().toISOString().split('T')[0] // format: yyyy-mm-dd
    // }).subscribe(  async(order) => {
    //
    //     if (order && listCartItem.length > 0) {
    //
    //       let list: Observable<Object> = of({});
    //
    //
    //           list = list.pipe(concatMap((obj) => {
    //             return this.orderDetailsService.addOrderDetails(order.id, orderDetails);
    //           }));
    //
    //         }
    //       }
    //
    //       list.subscribe(data => {
    //         status = true;
    //         this.router.navigate(['/billing/'+order.id]);
    //         }, error => {
    //         alert("Error")
    //       });
    //
    //     } else
    //       alert('Error has been occur!');
    //   }
    // );
  // }

  getOrderDetails(id: number) {
    return this.http.get(this.baseUrl + 'orders/' + id+'/order_details');
  }

  getOrderByCurrentDay(){
    let day: string = new Date().toISOString().split('T')[0];
    return this.http.get(this.baseUrl+'orders?orderDate='+day);
  }

  getAllOrderByUser(userId: number) {
    return this.http.get(this.baseUrl+'orders?userId='+userId);
  }

  //========= Spring app ================

  placeAnOrder(userId: number, order: OrderDetails) {
    return this.http.post(this.userApiUrl+'/users/'+userId+'/orders', order);
  }

  getAllOrder() {
    return this.http.get(this.adminApiUrl+'/admin/orders');
  }

  getOrderById(orderId: number){
    return this.http.get(this.adminApiUrl+'/admin/orders/' + orderId);
  }

  updateOrderStatus(orderId: number, status: string){
    let obj = {
      orderStatus: status
    }
    return this.http.put(this.adminApiUrl+'/admin/orders/' + orderId, obj);
  }

}
