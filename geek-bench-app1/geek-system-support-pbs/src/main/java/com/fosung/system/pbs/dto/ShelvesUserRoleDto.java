package com.fosung.system.pbs.dto;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

import java.util.List;


@Data
public class ShelvesUserRoleDto extends AppBasePageParam {

	private List<Long> userIds;

	private List<Long> roleIds;


}