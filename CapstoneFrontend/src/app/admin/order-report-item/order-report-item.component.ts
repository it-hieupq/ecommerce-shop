import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {UserService} from "../../services/user.service";
import {CouponService} from "../../services/coupon.service";
import {ActivatedRoute} from "@angular/router";
import {OrderDetails} from "../../model/OrderDetails";
import {OrderStatus} from "../../model/OrderStatus";
import {PaymentMethod} from "../../model/PaymentMethod";

@Component({
  selector: 'app-order-report-item',
  templateUrl: './order-report-item.component.html',
  styleUrls: ['./order-report-item.component.css']
})
export class OrderReportItemComponent implements OnInit {

  order: OrderDetails = {
    orderId: 0,
    orderStatus: OrderStatus.PROCESSING,
    total: 0,
    address: '',
    fullName: '',
    orderItem: [],
    orderDate: '',
    couponId: 0,
    paymentMethod: PaymentMethod.COD,
    phoneNumber: '',
    userId: 0
  };

  orderStatus: string[] = [];

  constructor(private orderService: OrderService,
              private userService: UserService,
              private couponService: CouponService,
              private _activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {

    for (var enumMember in OrderStatus) {
      var isValueProperty = parseInt(enumMember, 10) >= 0
      if (isValueProperty) {
        this.orderStatus.push(OrderStatus[enumMember]);
      }
    }

    let _orderId = this._activatedRoute.snapshot.paramMap.get("orderId");

    if (_orderId != null) {
      this.order.orderId = parseInt(_orderId + "");
      if (this.order.orderId) {
        this.getOrder(this.order.orderId);
      }
    }
  }

  getOrder(orderId: number) {
    this.orderService.getOrderById(orderId).subscribe((res: any) => {
      console.log(res)
      if (res && res.data && res.data.order) {
        this.order = res.data.order;
      }
    })
  }

  getOriginTotal() {
    let total = 0;

    for (let item of this.order.orderItem) {
      total += item.itemPrice * item.quantity;
    }

    return total;
  }

  getSaleTotal() {
    let total = this.getOriginTotal();
    return total - this.order.total;
  }

  updateOrderStatus(event: Event) {
    if (this.order.orderId && this.order.orderId > 0) {
      let status: string = ((event.target as HTMLInputElement).value)
      this.orderService.updateOrderStatus(this.order.orderId, status).subscribe((res: any) => {
        console.log(res)
        if (res && res.message)
          alert(res.message)
      })
    }
  }
}
