package com.capstone.client.order.domain.model.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderFilter {
    private Date startDate;
    private Date endDate;
    private List<Integer> userIdList;
}
