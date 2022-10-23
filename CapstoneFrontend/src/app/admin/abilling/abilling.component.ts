import {Component, Input, OnInit} from '@angular/core';
import {Billing} from "../../model/Billing";
import {BillingService} from "../../services/billing.service";

@Component({
  selector: 'app-abilling',
  templateUrl: './abilling.component.html',
  styleUrls: ['./abilling.component.css']
})
export class AbillingComponent implements OnInit {

  // @Input()
  // item: Billing = {
  //   order: {
  //     orderId: 0,
  //     userId: 0,
  //     orderedDate: '',
  //     status: false,
  //     createdDate: '',
  //     couponId: 0
  //   },
  //   order_details: []
  // }

  @Input() type = 'all';

  constructor() { }

  ngOnInit(): void {
  }

  getUnits(){
    // let total = 0;
    // this.item.order_details?.forEach(e=>{
    //   total += e.quantity;
    // })
    // return total;
  }

  getTotal() {
    // let total = 0;
    // this.item.order_details?.forEach(e=>{
    //   total += e.quantity* e.price;
    // })
    // return total;
  }
}
