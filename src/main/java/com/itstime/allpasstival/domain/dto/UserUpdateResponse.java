package com.itstime.allpasstival.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponse {
    private String email;
    private String nickname;
    private String profilePicUrl;
}
