import { Injectable } from '@angular/core';
import {OrderService} from "./order.service";
import {Billing} from "../model/Billing";
import {OrderDetailsService} from "./order-details.service";
import {concatMap, Observable, of} from "rxjs";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class BillingService {

  constructor(private orderService: OrderService,
              private orderDetailService: OrderDetailsService,
              private userService: UserService) {}

  getAllBilling(){
    //
    // let bills: Billing[] = [];
    //
    // this.orderService.getAllOrder().subscribe((data:any) => {
    //
    //   data.forEach((order: Order) => {
    //     this.orderService.getOrderDetails(order.orderId).subscribe((res: any) => {
    //       // console.log(res);
    //       let bill: Billing = {
    //         order: order,
    //         order_details: res
    //       }
    //       bills.push((bill));
    //     });
    //   });
    //
    // });
    //
    // return bills;
  }

  getBillsByCurrentDay() {
    // let bills: Billing[] = [];
    // this.orderService.getOrderByCurrentDay().subscribe((orders:any) => {
    //
    //   if(orders.length){
    //     orders.forEach((e: Order) => {
    //       this.orderDetailService.getOrderDetailsByOrderId(e.orderId).pipe().subscribe( (list: any) => {
    //         let bill: Billing = {
    //           order: e,
    //           order_details: list
    //         };
    //         bills.push(bill);
    //       });
    //     })
    //   }
    // });
    //
    // return bills;
  }

  getBillByOrderId(orderId: number){

    // let bill: Billing = {
      // order: {
      //   orderedDate: '',
      //   orderId: 0,
      //   userId: 0,
      //   status: false,
      //   createdDate: '',
      //   couponId: 0
      // }, order_details: []
    // };

    // this.orderService.getOrderById(orderId).subscribe((order: any) => {
    //   this.orderDetailService.getOrderDetailsByOrderId(order.id).subscribe((data: any) => {
    //     bill.order = order;
    //     bill.order_details = data;
    //   })
    // });

    // return bill;
  }

  getAllBillingByUser(userId: number) {
    //
    // let bills: Billing[] = [];
    //
    // this.orderService.getAllOrderByUser(userId).subscribe((data:any) => {
    //
    //   data.forEach((order: Order) => {
    //     this.orderService.getOrderDetails(order.orderId).subscribe((res: any) => {
    //       // console.log(res);
    //       let bill: Billing = {
    //         order: order,
    //         order_details: res
    //       }
    //       bills.push((bill));
    //     });
    //   });
    //
    // });
    //
    // return bills;
  }

}
