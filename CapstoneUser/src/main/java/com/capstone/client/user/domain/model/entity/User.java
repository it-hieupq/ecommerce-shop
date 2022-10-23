package com.capstone.client.user.domain.model.entity;

import com.capstone.client.user.domain.common.Role;
import com.capstone.client.user.domain.model.dto.response.UserDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer userId;

    @Column(unique = true)
    @NotNull
    private String username;

    @Column(unique = true)
    @NotNull
    @Email
    private String email;

    @Length(min = 6)
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private Boolean status = true;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .email(this.email)
                .role(this.role)
                .userId(this.userId)
                .status(this.status)
                .username(this.username)
                .build();
    }
}
