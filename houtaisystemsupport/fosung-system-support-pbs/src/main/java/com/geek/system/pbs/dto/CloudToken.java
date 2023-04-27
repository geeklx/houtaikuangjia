package com.geek.system.pbs.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *token信息
 */
@Data
public class CloudToken implements Serializable {

    private static final long serialVersionUID = 1L ;
    
    @JsonProperty("access_token")
    private String accessToken ;
    
    @JsonProperty("refresh_token")
    private String refreshToken ;
    
    @JsonProperty("token_type")
    private String tokenType ;
    
    @JsonProperty("expires_in")
    private String expiresIn ;
    
    @JsonProperty("scope")
    private String scope ;
    
}
