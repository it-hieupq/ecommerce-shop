import {Coupon} from "../Coupon";

export interface CouponReqDTO{
  couponDTO: Coupon,
  userIdList: number[]
}
