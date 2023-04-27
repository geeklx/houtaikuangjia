package com.geek.system.pbs.dto.support.dt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DTOrgUser implements Serializable {

    private static final long serialVersionUID = 1L ;
    
    private String orgId ;

    private String orgCode ;

    private String orgName ;

    private String hash ;

    private String idCardHash;

    private String userId ;

    private String userName ;

    private String telephone ;
    
    private String sex ;

    @JsonProperty("param1")
    private String realName ;

    private Date joinPartyDate;

}
