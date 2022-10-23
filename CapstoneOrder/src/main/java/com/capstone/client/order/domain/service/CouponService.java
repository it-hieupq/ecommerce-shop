package com.capstone.client.order.domain.service;

import com.capstone.client.order.domain.model.dto.request.CouponReqDTO;
import com.capstone.client.order.domain.model.dto.response.CouponDTO;
import com.capstone.client.order.domain.model.entity.Coupon;
import com.capstone.client.order.domain.model.entity.CouponUser;
import com.capstone.client.order.domain.repository.CouponRepo;
import com.capstone.client.order.domain.repository.CouponUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private CouponUserRepo couponUserRepo;

    public List<CouponDTO> getAll() {
        List<Coupon> coupons = couponRepo.findAll();
        return toDTOList(coupons);
    }

    private List<CouponDTO> toDTOList(List<Coupon> coupons) {
        if(coupons == null || coupons.isEmpty()) return Collections.emptyList();
        return coupons.stream().map(Coupon::toDTO).collect(Collectors.toList());
    }

    public Boolean isValid(CouponReqDTO couponReqDTO) {
        CouponDTO coupon = couponReqDTO.getCouponDTO();
        if(coupon.getName() == null || coupon.getName().isEmpty() ||
                coupon.getDiscountPercent() == null || coupon.getDiscountPercent() <= 0 || coupon.getDiscountPercent() > 100 ||
                coupon.getEndDate() == null )
            return false;
        return true;
    }

    public CouponDTO save(CouponReqDTO couponReqDTO) {
        Coupon coupon = couponRepo.save(couponReqDTO.getCouponDTO().toEntity());

        if(couponReqDTO.getUserIdList()!=null && !couponReqDTO.getUserIdList().isEmpty() && coupon.getCouponId()!=null && coupon.getCouponId() >0)
            couponReqDTO.getUserIdList().forEach(userId-> couponUserRepo.save(CouponUser.builder().couponId(coupon.getCouponId()).userId(userId).build()));

        return coupon.toDTO();
    }

    public CouponDTO findByCouponId(int couponId) {
        return couponRepo.findByCouponId(couponId).toDTO();
    }

    public Boolean isExisted(Integer id){
        return couponRepo.existsById(id);
    }

    public boolean deactiveCouponById(int couponId) {
        Coupon coupon = couponRepo.findByCouponId(couponId);
        coupon.setStatus(false);
        return !couponRepo.save(coupon).getStatus();
    }

    public CouponDTO update(CouponReqDTO couponReqDTO) {
        return couponRepo.save(couponReqDTO.getCouponDTO().toEntity()).toDTO();
    }

    public List<CouponDTO> getAllByUserId(Integer userId) {
        Long now = new Date().getTime();
        System.out.println(now);
        return toDTOList(couponRepo.findByUserId(userId, now));
    }
}