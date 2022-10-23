package com.capstone.client.user.domain.model.dto.response;

import com.capstone.client.user.domain.common.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
    private String username;
    private String email;
    private Role role;
    private Boolean status;
}