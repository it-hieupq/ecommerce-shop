import { Component, OnInit } from '@angular/core';
import {Billing} from "../../model/Billing";
import {BillingService} from "../../services/billing.service";
import {OrderService} from "../../services/order.service";
import {OrderDetails} from "../../model/OrderDetails";

@Component({
  selector: 'app-sale-report-list',
  templateUrl: './sale-report-list.component.html',
  styleUrls: ['./sale-report-list.component.css']
})
export class SaleReportListComponent implements OnInit {

  orders: OrderDetails[] = [];

  constructor(private orderService: OrderService) { }

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

  totalSales(){
  }

}
