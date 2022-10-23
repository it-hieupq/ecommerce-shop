import {Component, OnInit} from '@angular/core';
import {Coupon} from "../../model/Coupon";
import {ActivatedRoute, Router} from "@angular/router";
import {CouponService} from "../../services/coupon.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../../model/User";
import {UserService} from "../../services/user.service";
import {CouponReqDTO} from "../../model/dto/CouponReqDTO";

@Component({
  selector: 'app-admin-coupon',
  templateUrl: './admin-coupon.component.html',
  styleUrls: ['./admin-coupon.component.css']
})

export class AdminCouponComponent implements OnInit {
  currentDate: number = new Date().getTime();

  users: Array<User> = [];
  userIdList: number[] = [];

  isAdd = false;

  coupon: Coupon = {
    couponId: 0,
    name: '',
    status: true,
    endDate: 0,
    description: '',
    discountPercent: 0
  };

  couponForm = new FormGroup({
    couponId: new FormControl(this.coupon.couponId),
    name: new FormControl ('', [Validators.required]),
    discountPercent: new FormControl('', [Validators.min(1), Validators.max(100)]),
    description: new FormControl( ''),
    endDate: new FormControl ('',  [Validators.required, Validators.min(0)]),
    status: new FormControl('true')
  });

  constructor(private _activatedRoute: ActivatedRoute,
              private couponService: CouponService,
              private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
    this.getAllUsers();

    let _couponId = this._activatedRoute.snapshot.paramMap.get("couponId");

    if(_couponId == 'add')
      this.isAdd = true;

    console.log(this._activatedRoute.snapshot.paramMap.get("couponId"))

    if(_couponId != null && !this.isAdd){
      this.coupon.couponId = parseInt(_couponId + "");
      if(this.coupon.couponId) {
        this.getCouponByIdAdmin(this.coupon.couponId);
      }
    }
  }

  isChecked(userId: number){
    return this.userIdList.includes(userId);
  }

  getAllUsers(){
    this.userService.getAllUserAdmin().subscribe((res: any) => {
      if(res!=null && res.data!=null && res.data.users!=null){
        res.data.users.forEach((e: User) => {
          this.users.push(e);
        })
      }
    })
  }

  getCouponByIdAdmin(couponId: number){
    this.couponService.getCouponByIdAdmin(couponId).subscribe( (res: any) => {
      console.log(res)
      if(res!= null && res.data != null && res.data.coupon != null){
        this.coupon = res.data.coupon;
      }

      if(res!= null && res.data != null && res.data.checkedUserIdList != null){
        this.userIdList = res.data.checkedUserIdList;
      }
    }, error => {
        this.coupon.couponId = 0;
    } );
  }

  calculateTimeLeft(endDate: number){
    if(endDate - this.currentDate > 0)
      return Math.ceil((endDate - this.currentDate)/(1000*3600*24)) + " day(s) left";
    else return "Expired";
  }

  submitForm(){

    if(this.couponForm.valid){
      let couponReqDTO: CouponReqDTO = {
        couponDTO: this.couponForm.value,
        userIdList: this.userIdList
      }

      if(!this.isAdd && this.couponForm.get("couponId")?.value > 0) {
        console.log("Im updating...")
        this.couponService.updateByIdAdmin(couponReqDTO).subscribe(res => {
          console.log(res)
          alert("Updated");
        });
      } else {
        console.log("Im adding...")
        couponReqDTO.couponDTO.endDate = couponReqDTO.couponDTO.endDate*3600*24*1000 + new Date().getTime();
        this.couponService.add(couponReqDTO).subscribe((res: any)=> {
          console.log(res)
          if(res && res.data && res.data.coupon){
            alert("Added coupon: " + res.data.coupon.name)
            window.location.href = '/admin/coupons/'+res.data.coupon.couponId;
          } else {
            alert("Failed");
          }
        })
      }

    } else {
      alert("Invalid Form.");
    }

    console.log(this.couponForm.value);
  }

  delete(couponId: number) {
    if(confirm("Are you sure to de-active this coupon?")){
      this.couponService.deleteByIdAdmin(couponId).subscribe( res => {
        alert("Deactive success");
        this.router.navigate(['/admin/coupons']);
      })
    }
  }

  onCheckboxChange(userId: number, event: any) {
    if(event.target.checked) {
      this.userIdList.push(userId);
    } else {
      let index = this.userIdList.indexOf(userId);
      this.userIdList.splice(index,1);
    }
    console.log(this.userIdList)
  }

}
