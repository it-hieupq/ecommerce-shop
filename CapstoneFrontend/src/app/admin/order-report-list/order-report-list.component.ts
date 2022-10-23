import { Component, OnInit } from '@angular/core';
import {OrderService} from "../../services/order.service";
import {OrderDetails} from "../../model/OrderDetails";

@Component({
  selector: 'app-order-report-list',
  templateUrl: './order-report-list.component.html',
  styleUrls: ['./order-report-list.component.css']
})
export class OrderReportListComponent implements OnInit {

  orders: OrderDetails[] = [];

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.initOrders();
  }

  initOrders(){
    this.orderService.getAllOrder().subscribe((res: any)=> {
      console.log(res);
      if(res && res.data && res.data.orders){
        this.orders = res.data.orders;
      }
    })
  }

}
