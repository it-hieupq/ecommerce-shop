package com.capstone.client.order.domain.model.dto.request;

import com.capstone.client.order.domain.model.dto.response.CouponDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CouponReqDTO {
    private CouponDTO couponDTO;
    private List<Integer> userIdList;
}
