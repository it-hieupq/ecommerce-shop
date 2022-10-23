package com.capstone.client.order.domain.service;

import com.capstone.client.order.domain.model.entity.CouponUser;
import com.capstone.client.order.domain.repository.CouponUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponUserService {
    @Autowired
    private WebClientService webClientService;
    @Autowired
    private CouponUserRepo couponUserRepo;

    public CouponUser save(CouponUser couponUser){
        return couponUserRepo.save(couponUser);
    }

    public void delete(CouponUser couponUser){
        couponUserRepo.delete(couponUser);
    }

    public List<CouponUser> findByCouponId(int couponId) {
        return couponUserRepo.findByCouponId(couponId);
    }

    public List<Integer> getCheckedUserIdList(int couponId) {
        return this.findByCouponId(couponId).stream().map(CouponUser::getUserId).collect(Collectors.toList());
    }

    public int update(int couponId, List<Integer> userIdList) {
        int result = 0;

        List<Integer> existUserId = new ArrayList<>();

        userIdList.forEach(id -> {
            if(webClientService.userExist(id))
                existUserId.add(id);
        });

        if(!existUserId.isEmpty()){
            couponUserRepo.deleteUserIdFromCoupon(couponId, existUserId);
            existUserId.forEach(userId->{ couponUserRepo.save(CouponUser.builder().couponId(couponId).userId(userId).build());});
        }

        return result;
    }

    public boolean isExisted(CouponUser obj) {
        return couponUserRepo.existsByCouponIdAndUserId(obj.getCouponId(), obj.getUserId());
    }
}