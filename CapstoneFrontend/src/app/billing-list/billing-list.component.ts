import { Component, OnInit } from '@angular/core';
import {OrderService} from "../services/order.service";
import {OrderDetailsService} from "../services/order-details.service";
import {UserService} from "../services/user.service";
import {User} from "../model/User";
import {Billing} from "../model/Billing";
import {BillingService} from "../services/billing.service";

@Component({
  selector: 'app-billing-list',
  templateUrl: './billing-list.component.html',
  styleUrls: ['./billing-list.component.css']
})
export class BillingListComponent implements OnInit {

  user: User = {
    userId: 0,
    username: '',
    role: '',
    email:'',
    password:'',
    address:'',
    status: true
  }

  bills: Billing[] = [];

  constructor(private orderService: OrderService,
              private orderDetailService: OrderDetailsService,
              private userService: UserService,
              private billingService: BillingService) { }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();

    // if(this.user) {
      // this.bills = this.billingService.getAllBillingByUser(this.user.userId);
    // }
  }

  getTotal(bill: Billing) {
    let total = 0;

    // if(bill.order_details?.length && bill.order_details.length > 0){
    //   bill.order_details.forEach((e) => {
    //     total += e.quantity*e.price;
    //   })
    // }

    return total;
  }

  getUnits(bill: Billing) {
    let total = 0;

    // if(bill.order_details?.length && bill.order_details.length > 0) {
    //   bill.order_details.forEach((e) => {
    //     total += e.quantity;
    //   })
    // }

    return total;
  }
}
