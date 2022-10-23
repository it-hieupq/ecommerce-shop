import { Component, OnInit } from '@angular/core';
import {CartService} from "../../services/cart.service";
import {Coupon} from "../../model/Coupon";
import {CouponService} from "../../services/coupon.service";
import {UserService} from "../../services/user.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-admin-coupon-list',
  templateUrl: './admin-coupon-list.component.html',
  styleUrls: ['./admin-coupon-list.component.css']
})
export class AdminCouponListComponent implements OnInit {

  coupons: Coupon[] = [];
  currentDate: number = new Date().getTime();

  addForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    description: new FormControl(''),
    discountPercent: new FormControl('', [Validators.required, Validators.min(1), Validators.max(100)]),
    endDate: new FormControl('', [Validators.required, Validators.min(1)]),
    status: new FormControl('true')
  });

  constructor(private couponService: CouponService,
              private cartService: CartService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.getAllCouponsAdmin();
  }

  getAllCouponsAdmin(){
    this.couponService.getAll().subscribe((res: any) => {
      console.log(res)
      if(res != null && res.data != null && res.data.coupons != null){
        this.coupons = res.data.coupons;
      }
    });
  }

  calculateTimeLeft(endDate: number){
    if(endDate - this.currentDate > 0)
      return Math.ceil((endDate - this.currentDate)/(1000*3600*24)) + " day(s) left";
    else return "Expired";
  }

  addCoupon() {
    // if(this.addForm.valid){
    //   this.addForm.controls['endDate'].setValue(this.addForm.controls['endDate'].value*3600*24*1000 + (new Date().getTime()));
    //   let coupon: Coupon = this.addForm.value;
    //   this.couponService.add(coupon).subscribe((res: any) => {
    //     console.log(res)
    //     if(res && res.data && res.data.coupon){
    //       this.coupons.push(res.data.coupon);
    //       alert(res.message)
    //       this.addForm.reset()
    //     }
    //   })
    // } else {
    //   alert("Form invalid");
    // }
  }
}
