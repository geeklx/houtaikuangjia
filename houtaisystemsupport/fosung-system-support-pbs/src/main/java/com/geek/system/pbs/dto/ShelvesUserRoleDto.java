package com.geek.system.pbs.dto;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShelvesUserRoleDto extends AppBasePageParam {

	private List<Long> userIds;

	private List<Long> roleIds;


}