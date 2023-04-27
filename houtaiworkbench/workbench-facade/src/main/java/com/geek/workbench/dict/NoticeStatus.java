package com.geek.workbench.dict;
import com.fosung.framework.common.support.AppRuntimeDict;
import com.fosung.framework.common.support.anno.AppDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 类的描述
 *
 * @author lihuiming
 * @version V1.0
 * @date 2022/06/24 14:01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@AppDict("noticeStatus")
public enum NoticeStatus  implements AppRuntimeDict {
    /**
     * 描述:  发送
     */
    send("发送"),

    /**
     * 描述:  定时发送
     */
    timingSend( "定时发送"),

    /**
     * 描述:  撤销
     */
    revoke("撤销"),

    /***
     * 描述: 取消
     * @date 2021/11/3 8:49
     **/
    cancle( "取消"),

    /***
     * 描述: 暂存
     * @author fuhao
     * @date 2021/11/3 8:49
     **/
    tempStore( "暂存");

    public String remark;


}
