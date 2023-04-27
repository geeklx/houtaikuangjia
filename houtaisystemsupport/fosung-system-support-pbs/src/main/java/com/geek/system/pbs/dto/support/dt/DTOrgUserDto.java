package com.geek.system.pbs.dto.support.dt;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DTOrgUserDto implements Serializable {

    private static final long serialVersionUID = 1L ;
	
    private List<DTOrgUser> users ;
}