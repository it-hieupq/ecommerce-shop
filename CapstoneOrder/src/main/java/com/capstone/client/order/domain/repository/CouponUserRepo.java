package com.capstone.client.order.domain.repository;

import com.capstone.client.order.domain.model.entity.CouponUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CouponUserRepo extends JpaRepository<CouponUser, Integer> {
    List<CouponUser> findByCouponId(Integer couponId);
    List<CouponUser> findByUserId(Integer userId);
    void deleteAllByCouponId(Integer couponId);
    void deleteAllByUserId(Integer userId);

    boolean existsByCouponIdAndUserId(Integer couponId, Integer userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM coupon_user WHERE coupon_id = :couponId AND user_id NOT IN (:userIdList)", nativeQuery = true)
    void deleteUserIdFromCoupon(@Param("couponId") Integer couponId,@Param("userIdList") List<Integer> userIdList);
}
