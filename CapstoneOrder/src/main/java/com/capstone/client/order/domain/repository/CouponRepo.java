package com.capstone.client.order.domain.repository;

import com.capstone.client.order.domain.model.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    Coupon findByCouponId(Integer id);
    @Query(value = "SELECT * FROM coupon INNER JOIN coupon_user ON coupon_user.coupon_id = coupon.coupon_id AND coupon.end_date > :currentDate AND coupon_user.user_id = :userId", nativeQuery = true)
    List<Coupon> findByUserId(@Param("userId") Integer userId, @Param("currentDate") Long currentDate);
}
