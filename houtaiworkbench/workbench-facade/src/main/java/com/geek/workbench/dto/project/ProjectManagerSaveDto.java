package com.geek.workbench.dto.project;

import com.fosung.framework.common.dto.params.AppBasePageParam;
import com.geek.workbench.entity.project.ProjectManagerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.util.List;

/**
 * 描述:  项目管理员Dto
 * @createDate: 2021/10/13 15:06
 * @author: gaojian
 * @version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectManagerSaveDto extends AppBasePageParam {

   /**
	* 项目主键
	*/
   private Long projectId ;

   /**
    * 描述:
    * @createDate: 2021/11/4 14:41
    * @author: gaojian
    */
   @Valid
   private List<ProjectManagerEntity> list;

}