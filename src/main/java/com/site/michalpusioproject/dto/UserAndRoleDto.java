package com.site.michalpusioproject.dto;

import com.site.michalpusioproject.domains.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
public class UserAndRoleDto {

    @Valid
    private User user;

    @Valid
    private String nameOfRole;

}
