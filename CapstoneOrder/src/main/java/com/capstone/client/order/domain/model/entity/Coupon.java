package com.capstone.client.order.domain.model.entity;

import com.capstone.client.order.domain.model.dto.response.CouponDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer couponId;
    private String name;
    private String description;
    private Float discountPercent;
    private Long endDate;
    private Boolean status = true;

    public CouponDTO toDTO(){
        return CouponDTO.builder()
                .couponId(this.couponId)
                .description(this.description)
                .discountPercent(this.discountPercent)
                .endDate(this.endDate)
                .name(this.name)
                .status(this.status).build();
    }

}