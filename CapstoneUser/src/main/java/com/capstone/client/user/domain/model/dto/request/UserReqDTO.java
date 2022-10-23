package com.capstone.client.user.domain.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserReqDTO {
    private String username;
    private String password;
}
