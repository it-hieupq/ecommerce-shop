import {PaymentMethod} from "./PaymentMethod";
import {OrderItem} from "./OrderItem";
import {OrderStatus} from "./OrderStatus";

export interface OrderDetails {
  orderId?: number;
  couponId?: number;
  userId: number;
  orderDate: string;
  orderStatus: OrderStatus;
  phoneNumber: string,
  address: string,
  paymentMethod: PaymentMethod,
  fullName: string,
  total: number,
  orderItem: OrderItem[],
}
