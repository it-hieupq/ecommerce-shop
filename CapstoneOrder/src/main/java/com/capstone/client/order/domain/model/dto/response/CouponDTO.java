package com.capstone.client.order.domain.model.dto.response;

import com.capstone.client.order.domain.model.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    private Integer couponId;
    private String name;
    private String description;
    private Float discountPercent;
    private Long endDate;
    private Boolean status = true;

    public Coupon toEntity(){
        return Coupon.builder()
                .couponId(this.couponId)
                .status(this.status)
                .name(this.name)
                .endDate(this.endDate)
                .discountPercent(this.discountPercent)
                .description(this.description)
                .build();
    }
}
