package com.capstone.client.order.domain.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="coupon_user")
@IdClass(CouponIdClass.class)
public class CouponUser implements Serializable {
    @Id
    private Integer userId;
    @Id
    private Integer couponId;
}
