package com.geek.workbench.dto.ums;

import com.geek.workbench.dict.UmsMessageType;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Description UMS 数据
 * @Author gaojian
 * @Date 2022/2/22 15:44
 * @Version V1.0
 */
@Data
public class UmsDto implements Serializable {

    /**
     * 描述:  业务id
     * @createDate: 2022/2/22 15:51
     * @author: gaojian
     */
    private String busId;

    /**
     * 描述:  消息类型
     * @createDate: 2022/2/22 15:51
     * @author: gaojian
     * @modify:
     * @param null
     * @return: 
     */
    private UmsMessageType type;

    /**
     * 描述:  模板编码
     * @createDate: 2022/2/22 15:52
     * @author: gaojian
     * @modify:
     * @param null
     * @return:
     */
    private String templateCode;

    /**
     * 描述:  提示内容
     * @createDate: 2022/2/22 15:52
     * @author: gaojian
     * @modify:
     * @return:
     */
    private String messageContent;

    /**
     * 描述:  用户信息集合
     * @createDate: 2022/2/22 15:53
     * @author: gaojian
     * @modify:
     * @return:
     */
    private Set<String> userIds;
    /**
     * 描述:  提示标题
     * @createDate: 2022/5/17 15:52
     * @author: lihuiming
     * @modify:
     * @return:
     */
    private String messageTitle;

}
