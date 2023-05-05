package com.fosung.workbench.dto.pipeline;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Description 授权信息
 * @Author gaojian
 * @Date 2021/10/14 18:15
 * @Version V1.0
 */
@Data
public class MandateParams {

    /**
     * 描述:  终端id
     * @createDate: 2021/10/14 18:13
     * @author: gaojian
     */
    @NotNull(message = "终端id不能为空")
    private Long teimalId;

    /**
     * 描述:  授权应用id
     * @createDate: 2021/10/14 18:13
     * @author: gaojian
     */
    @NotBlank(message = "应用主键信息不能为空")
    @Size(min = 0, max = 255, message = "应用主键信息长度不能超过128个字符")
    private String appId;

    /**
     * 描述:  数据来源
     * @createDate: 2021/10/14 18:13
     * @author: gaojian
     */
    private String dataSource;

    /**
     * 描述:  
     * @createDate: 2021/10/14 18:16
     * @author: gaojian
     * @modify:
     * @param null
     * @return: 
     */
    @Valid
    @Size(min = 1,message = "最少同步一条授权记录。")
    private List<MandateDto> mandate;
}
