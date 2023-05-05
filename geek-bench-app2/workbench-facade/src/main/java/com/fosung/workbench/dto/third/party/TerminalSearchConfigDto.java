package com.fosung.workbench.dto.third.party;

import com.fosung.workbench.dto.terminal.TerminalThirdPartyCommonConfigDto;
import lombok.Data;

/**
 * @Description 终端搜索配置数据传输
 * @Author gaojian
 * @Date 2022/2/28 16:18
 * @Version V1.0
 */
@Data
public class TerminalSearchConfigDto extends TerminalThirdPartyCommonConfigDto {

    /**
     * 描述:  知识分类名称
     * @createDate: 2022/2/28 16:19
     * @author: gaojian
     */
    private String knowledgeCategoryName;

    /**
     * 描述:  知识分类编码
     * @createDate: 2022/2/28 16:19
     * @author: gaojian
     */
    private String knowledgeCategoryCode;

    /**
     * 描述:  知识分类描述
     * @createDate: 2022/2/28 16:19
     * @author: gaojian
     */
    private String knowledgeCategoryRemark;
}
