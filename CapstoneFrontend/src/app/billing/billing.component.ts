import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Billing} from "../model/Billing";
import {BillingService} from "../services/billing.service";
import {User} from "../model/User";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-billing',
  templateUrl: './billing.component.html',
  styleUrls: ['./billing.component.css']
})
export class BillingComponent implements OnInit {

  orderId: number = 0;
  status: boolean = false;
  // bill: Billing = {
  //   order: {
  //     orderedDate: '',
  //     orderId: 0,
  //     userId: 0,
  //     status: false,
  //     couponId: 0,
  //     createdDate: ''
  //   }, order_details: []
  // };

  user: User = {
    userId: 0,
    address: '',
    password: '',
    email: '',
    role: '',
    username:'',
    status: true
  };

  constructor(private _Activatedroute:ActivatedRoute,
              private billingService: BillingService,
              private userService: UserService) {}

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
    // let _orderId = this._Activatedroute.snapshot.paramMap.get("orderId");
    // if(_orderId != null){
    //   this.orderId = parseInt(_orderId + "");
    //   if(this.orderId) {
    //     this.status = true;
        // this.bill = this.billingService.getBillByOrderId(this.orderId);
      // } else this.status = false;
    // }
  }

  getUnits(bill: Billing) {
    let total = 0;
    //
    // if(bill.order.orderId>0){
    //
    //   bill.order_details?.forEach(e=>{
    //     total += e.quantity;
    //   })
    //
    //   return total;
    //
    // } else
      return 0;
  }

  getTotal(bill: Billing) {
    let total = 0;

    // if(bill.order.orderId>0){
    //
    //   bill.order_details?.forEach(e=>{
    //     total += e.quantity * e.price;
    //   })
    //
    //   return total;
    //
    // } else return 0;
  }
}
