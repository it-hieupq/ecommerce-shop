import { Component, OnInit } from '@angular/core';
import {OrderService} from "../../services/order.service";
import {BillingService} from "../../services/billing.service";
import {Billing} from "../../model/Billing";

@Component({
  selector: 'app-abilling-list',
  templateUrl: './abilling-list.component.html',
  styleUrls: ['./abilling-list.component.css']
})

export class AbillingListComponent implements OnInit {

  salesMonth = 0;

  bills: any = [];

  billsByDay: Billing[] = [];

  billsByMonth: Billing[] = [];

  testBills = [];

  constructor(private billingService: BillingService,private orderService: OrderService) { }

  ngOnInit(): void {

  let date = new Date();

  let monthFilter: string = date.getFullYear() +'-'+ ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1)));

    // this.orderService.getAllOrder().subscribe((data:any) => {

    //   data.forEach((order: Order) => {
    //     this.orderService.getOrderDetails(order.orderId).subscribe((res: any) => {
    //       let bill: Billing = {
    //         order: order,
    //         order_details: res
    //       }
    //
    //       this.bills.push(bill);
    //
    //       if(bill.order.orderedDate.includes(monthFilter)){
    //         this.billsByMonth.push(bill);
    //       }
    //
    //     });
    //   });
    // });

    // this.billsByDay = this.billingService.getBillsByCurrentDay();
  }

  totalSales(billsByMonth: Billing[]){
    this.salesMonth = 0;

    // billsByMonth.forEach((e) => {
    //   e.order_details?.forEach( item => {
    //     this.salesMonth += item.quantity*item.price;
    //   })
    // });

    return this.salesMonth;
  }



}
