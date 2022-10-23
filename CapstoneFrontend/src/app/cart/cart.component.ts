import {Component, Input, OnInit} from '@angular/core';
import {CartService} from "../services/cart.service";
import {CartItemService} from "../services/cart-item.service";
import {Item} from "../model/Item";
import {User} from "../model/User";
import {UserService} from "../services/user.service";
import {CartItem} from "../model/CartItem";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})

export class CartComponent implements OnInit {

  @Input() item: Item = {
    product: {
      productId: 0,
      name: '',
      price: 0,
      active: true,
      categoryIdList: [],
      images: [],
      inStock: 0,
      description: ''
    },
    quantity: 0
  }

  user: User = {
    userId: 0, username: '',email:'', role:'', status: true, password: '', address: ''
  }

  hidden = false;

  constructor(private cartService: CartService,
              private cartItemService: CartItemService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
  }

  updateCartItem(quantity: number) {

    if(quantity <= 0 && confirm("This action will be delete product from cart, continue?")){
      this.item.quantity = 0;
      this.cartItemService.deleteCartItem(this.user.userId, this.item.product.productId).subscribe((res: any)=> {
          console.log(res);
          this.hidden = true;
        }, err  => {
        console.log(err)
      });

    } else {
      this.item.quantity = quantity;
      let objTmp: CartItem = {
        productId: this.item.product.productId,
        quantity: quantity
      }
      this.cartItemService.updateCartItem(this.user.userId, objTmp).subscribe((res: any) => {
        console.log(res);

      });
    }
  }

  increaseQuantity(){
    this.updateCartItem(this.item.quantity+1);
  }

  decreaseQuantity(){
    this.updateCartItem(this.item.quantity-1);
  }

  updateQuantity(quantity: number){
    this.updateCartItem(this.item.quantity);
  }

}
