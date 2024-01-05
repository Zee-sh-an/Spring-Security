package com.jwt.models;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String jwtToken;

    private String userName;

}
