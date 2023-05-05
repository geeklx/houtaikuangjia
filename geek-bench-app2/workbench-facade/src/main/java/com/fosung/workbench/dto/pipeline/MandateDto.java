package com.fosung.workbench.dto.pipeline;

import com.fosung.workbench.dict.AppType;
import com.fosung.workbench.dict.MandateOpt;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author gaojian
 * @Date 2021/10/14 18:05
 * @Version V1.0
 */
@Data
public class MandateDto {

    /**
     * 描述:  授权操作
     * @createDate: 2021/10/15 9:37
     * @author: gaojian
     */
    @Enumerated(EnumType.STRING)
    private MandateOpt mandateOpt ;

    /**
     * 描述:  授权类型
     * @createDate: 2021/10/14 18:13
     * @author: gaojian
     */
    private String mandateType;

    /**
     * 描述:  授权值
     * @createDate: 2021/10/14 18:13
     * @author: gaojian
     */
    @NotBlank(message = "授权内容不能为空")
    @Length(min = 2, max = 255, message = "授权内容长度应在2-255个字符之间")
    private String mandateValue;

}
