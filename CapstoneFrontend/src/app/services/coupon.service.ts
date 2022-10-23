import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Coupon} from "../model/Coupon";
import {CouponReqDTO} from "../model/dto/CouponReqDTO";

@Injectable({
  providedIn: 'root'
})
export class CouponService {

  private userApiUrl = 'http://localhost:8666/coupons';
  private adminApiUrl = 'http://localhost:8666/admin/coupons';

  constructor(private http: HttpClient) { }

  getAll(){
    return this.http.get(this.adminApiUrl);
  }

  getCouponsByUser(userId: number){
    return this.http.get(this.userApiUrl+'/users/'+userId);
  }

  getCouponByIdAdmin(couponId: number) {
    return this.http.get(this.adminApiUrl + '/' + couponId);
  }

  deleteByIdAdmin(couponId : number){
    return this.http.delete(this.adminApiUrl+ '/' + couponId);
  }

  updateByIdAdmin(couponReqDTO: CouponReqDTO){
    return this.http.put(this.adminApiUrl + '/' + couponReqDTO.couponDTO.couponId, couponReqDTO);
  }

  add(couponReqDTO: CouponReqDTO) {
    return this.http.post(this.adminApiUrl, couponReqDTO);
  }

}
