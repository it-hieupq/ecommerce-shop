import {OrderDetails} from "./OrderDetails";
import {OrderItem} from "./OrderItem";

export interface Billing{
  order?: OrderDetails;
  orderItem?: OrderItem[];
}
