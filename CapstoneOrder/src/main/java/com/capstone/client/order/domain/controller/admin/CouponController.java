package com.capstone.client.order.domain.controller.admin;

import com.capstone.client.order.domain.common.ResponseDTO;
import com.capstone.client.order.domain.common.Stringify;
import com.capstone.client.order.domain.model.dto.request.CouponReqDTO;
import com.capstone.client.order.domain.model.dto.response.CouponDTO;
import com.capstone.client.order.domain.model.entity.CouponUser;
import com.capstone.client.order.domain.service.CouponService;
import com.capstone.client.order.domain.service.CouponUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("AdminCouponController")
@RequestMapping("/admin/coupons")
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
public class CouponController {
    @Autowired
    CouponService couponService;
    @Autowired
    CouponUserService couponUserService;

    @GetMapping("/{couponId}")
    public ResponseEntity<Object> getById(@PathVariable(name="couponId") int couponId){

        Map<String, Object> map = new HashMap<>();
        String message = "Get coupon successfully";
        HttpStatus status = HttpStatus.OK;
        CouponDTO couponDTO = null;
        List<Integer> checkedUserIdList = null;

        try {
            if(couponService.isExisted(couponId)) {
                couponDTO = couponService.findByCouponId(couponId);
                checkedUserIdList = couponUserService.getCheckedUserIdList(couponId);
            } else {
                message = "Not found coupon with ID = " + couponId;
                status = HttpStatus.NOT_FOUND;
            }

        } catch (Exception e) {
            message = e.getLocalizedMessage();
            status = HttpStatus.EXPECTATION_FAILED;
            e.printStackTrace();
        }

        map.put("coupon", couponDTO);
        map.put("checkedUserIdList", checkedUserIdList);

        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll(){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Get all user successfully";
        List<CouponDTO> couponDTOList = null;

        try {
            couponDTOList = couponService.getAll();
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
            e.printStackTrace();
        }

        map.put("coupons", couponDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }


    @PostMapping("")
    public ResponseEntity<Object> add(@RequestBody CouponReqDTO coupon) {

        Map<String, Object> map = new HashMap<>();
        String message = "Add coupons successfully";
        HttpStatus status = HttpStatus.OK;
        CouponDTO couponDTO = null;

        try {
            if(couponService.isValid(coupon)) {
                couponDTO = couponService.save(coupon);
            } else {
                message = "Name, endDate, discountPercent is require and discountPercent have to greater than 0 and smaller 100";
                status = HttpStatus.EXPECTATION_FAILED;
            }
        } catch (Exception e) {
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("coupon", couponDTO);
        return ResponseDTO.getResponse(message, status, map);
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Object> delete(@PathVariable(name="couponId", required = true) int couponId){

        Map<String, Object> map = new HashMap<>();
        String message = "Coupon has been deleted";
        HttpStatus status = HttpStatus.OK;

        try {
            if(couponService.isExisted(couponId)) {
                if(!couponService.deactiveCouponById(couponId)){
                    message = "Failed";
                    status = HttpStatus.EXPECTATION_FAILED;
                }
            } else {
                message = "Not found coupon with ID = " + couponId;
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        return ResponseDTO.getResponse(message, status, Collections.emptyMap());
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<Object> update(@PathVariable(name="couponId") int couponId, @RequestBody CouponReqDTO couponReqDTO) {

        Map<String, Object> map = new HashMap<>();
        String message = "Coupon has been updated";
        HttpStatus status = HttpStatus.OK;
        CouponDTO couponDTO = null;

        try {
            if(!couponService.isExisted(couponId)){
                message = "Not found coupon with ID = " + couponId;
                status = HttpStatus.NOT_FOUND;
            } else if(!couponService.isValid(couponReqDTO)) {
                message = "name, endDate, discountPercent is require and discountPercent have to greater than 0 and smaller 100";
                status = HttpStatus.EXPECTATION_FAILED;
            } else if(!couponReqDTO.getCouponDTO().getStatus()){
                message = "Cant update. This coupon ran into deactive status.";
                status = HttpStatus.EXPECTATION_FAILED;
            } else {
                couponDTO = couponService.update(couponReqDTO);
                couponUserService.update(couponId, couponReqDTO.getUserIdList());
            }
        } catch (Exception e) {
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
            e.printStackTrace();
        }

        map.put("coupon", couponDTO);

        return ResponseDTO.getResponse(message, status, map);
    }

}
