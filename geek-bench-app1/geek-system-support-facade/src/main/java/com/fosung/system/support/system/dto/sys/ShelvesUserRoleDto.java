package com.fosung.system.support.system.dto.sys;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import lombok.Data;

import java.util.List;


@Data
public class ShelvesUserRoleDto extends AppBasePageParam {

	private Long projectId;

	private List<Long> userIds;

	private List<Long> roleIds;


}