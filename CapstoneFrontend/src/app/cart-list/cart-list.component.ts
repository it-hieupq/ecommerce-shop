import {Component, OnInit} from '@angular/core';
import {CartService} from "../services/cart.service";
import {UserService} from "../services/user.service";
import {OrderService} from "../services/order.service";
import {Router} from "@angular/router";
import {Cart} from "../model/Cart";
import {ProductService} from "../services/product.service";
import {Product} from "../model/Product";
import {Item} from "../model/Item";
import {CartItem} from "../model/CartItem";
import {User} from "../model/User";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PaymentMethod} from "../model/PaymentMethod";
import {OrderItem} from "../model/OrderItem";
import {formatDate} from "@angular/common";
import {OrderDetails} from "../model/OrderDetails";
import {OrderStatus} from "../model/OrderStatus";
import {Coupon} from "../model/Coupon";
import {CouponService} from "../services/coupon.service";

@Component({
  selector: 'app-cart-list',
  templateUrl: './cart-list.component.html',
  styleUrls: ['./cart-list.component.css']
})

export class CartListComponent implements OnInit {

  cart: Cart = {
    cartId: 0
  };

  coupons: Coupon[] = [];

  user: User = {
    userId: 0,
    status: true,
    password: '',
    address:'',
    role:'',
    email:'',
    username:''
  }

  orderInfoForm = new FormGroup({
    fullName: new FormControl (this.user.username, [Validators.required]),
    address: new FormControl(this.user.address, [Validators.required]),
    phoneNumber: new FormControl( '', [Validators.required]),
    paymentMethod: new FormControl('', Validators.required),
    couponId: new FormControl('')
  });

  items: Item[] = [];

  paymentMethod: string[] = [];

  discountPercent: number = 0;

  constructor(
    private cartService: CartService,
    private userService: UserService,
    private orderService: OrderService,
    private router: Router,
    private productService: ProductService,
    private couponService: CouponService
  ) {}

  ngOnInit(): void {
    this.initCart();
    this.user = this.userService.getCurrentUser();
    this.couponService.getCouponsByUser(this.user.userId).subscribe((res: any) => {
      console.log(res);
      if(res && res.data && res.data.coupons){
        this.coupons = res.data.coupons
      }
    })
  }

  initCart(){
    for (var enumMember in PaymentMethod) {
      var isValueProperty = parseInt(enumMember, 10) >= 0
      if (isValueProperty) {
        this.paymentMethod.push(PaymentMethod[enumMember]);
      }
    }

    this.cartService.getCart().subscribe((res: any) => {
      console.log(res);
      if(res &&  res.data && res.data.cart){
        this.cart = res.data.cart;

        if(res.data.cart.cartItemDTOList) {
          let ids: number[] = [];
          let cartItems: CartItem[] = res.data.cart.cartItemDTOList;

          cartItems.forEach((e: CartItem) => {
            ids.push(e.productId);
          });

          this.productService.getProductIn(ids).subscribe((resp: any) => {
            console.log(resp);

            if(resp && resp.data && resp.data.products){
              resp.data.products.forEach((e: Product) => {

                for (let i=0; i<cartItems.length; i++) {
                  if (cartItems[i].productId == e.productId){
                    let itemTmp: Item = {
                      product: e, quantity:
                      cartItems[i].quantity
                    }
                    this.items.push(itemTmp);
                    break;
                  }
                }

              })
            }
          });
        }

      }
    });
  }

  clearCart() {
    if(confirm("This action will be delete all item from your cart, continue?")){
      this.cartService.clearCart(this.user.userId).subscribe((res) => {
        console.log(res);
        this.items = [];
      });
    }
  }

  placeOrder(){
    if(this.orderInfoForm.valid && this.items.length >0) {
      console.log(this.orderInfoForm.value)

      let orderItems: OrderItem[] = [];

      this.items.forEach( e => {
        if(e.quantity > 0) {
          let tmp: OrderItem = {
            productId: e.product.productId,
            quantity: e.quantity,
            itemName: e.product.name,
            itemPrice: e.product.price
          };
          orderItems.push(tmp);
        }
      });

      let order: OrderDetails = {
        userId: this.user.userId,
        address: this.orderInfoForm.get('address')?.value,
        fullName: this.orderInfoForm.get('fullName')?.value,
        paymentMethod: this.orderInfoForm.get('paymentMethod')?.value,
        phoneNumber: this.orderInfoForm.get('phoneNumber')?.value,
        couponId: this.orderInfoForm.get('couponId')?.value,
        orderStatus: OrderStatus.PROCESSING,
        total: this.getFinalTotalSale(),
        orderDate: formatDate(new Date(), 'yyyy-MM-dd', 'en-US'),
        orderItem: orderItems
      }

      console.log(order);

      this.orderService.placeAnOrder(this.user.userId, order).subscribe( (res: any) => {
        console.log(res);
        if(res && res.message) {
          alert(res.message);
        }
      }, err => {
          alert('Order failed');
      } );

    } else {
      alert('Invalid information to checkout');
    }
  }

  getFinalTotalSale(){
    let total = this.getOriginalTotal();
    let saleTotal = this.getSaleTotal();
    return total - saleTotal;
  }

  getOriginalTotal(){
    let total = 0;
    this.items.forEach( (obj:Item) => {
      return total += obj.product.price*obj.quantity;
    });
    return total;
  }

  getSaleTotal(){
    let total = this.getOriginalTotal();
    return total * (this.discountPercent/100);
  }

  getTotalQuantity(){
    let total = 0;
    this.items.forEach( (obj:Item) => {
      return total += obj.quantity;
    });
    return total;
  }

  getCouponPercent(couponId: number){
    for(let item of this.coupons){
      if(item.couponId == couponId){
        return item.discountPercent
      }
    }
    return 0;
  }

  updateDiscountPercent($event: Event) {
    let couponId = ($event.target as HTMLInputElement).value;

    if(couponId){
      this.discountPercent = this.getCouponPercent(parseInt(couponId));
    } else {
      this.discountPercent = 0;
    }
  }

}
