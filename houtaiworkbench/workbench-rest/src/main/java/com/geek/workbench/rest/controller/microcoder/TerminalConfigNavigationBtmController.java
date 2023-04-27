package com.geek.workbench.rest.controller.microcoder;

import java.util.*;
import java.util.stream.Collectors;

import com.geek.workbench.config.OptLog;
import com.geek.workbench.config.OptLogConst;
import com.geek.workbench.config.TerminalContent;
import com.geek.workbench.dict.StatusType;
import com.geek.workbench.dto.microcoder.TerminalConfigNavigationBtmDto;
import com.geek.workbench.entity.microcoder.*;
import com.geek.workbench.service.microcoder.*;
import com.geek.workbench.entity.microcoder.TerminalConfigNavigationBtmEntity;
import com.geek.workbench.service.microcoder.TerminalConfigNavigationBtmService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fosung.framework.common.dto.params.AppBaseIdParam;

import org.springframework.web.bind.annotation.*;
import com.fosung.framework.common.util.UtilCollection;
import com.fosung.framework.web.http.ResponseParam;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.common.dto.UtilDTO;
import com.fosung.framework.common.dto.support.DTOCallbackHandler;

@RestController
@RequestMapping(value = TerminalConfigNavigationBtmController.BASE_PATH)
public class TerminalConfigNavigationBtmController extends AppIBaseController {
    /**
     * 当前模块跟路径
     */
    public static final String BASE_PATH = "/api/terminal/navigation/config";

    @Autowired
    private TerminalConfigNavigationBtmService navigationConfigurationInfoService;

    /**
     * 记录分页查询
     *
     * @return
     * @throws Exception
     */
    @PostMapping("query")
    @OptLog(optModule="终端管理-基础配置",optName="查询底部导航",optType= OptLogConst.QUERY)
    public ResponseParam query(@RequestBody TerminalConfigNavigationBtmDto navigationConfigurationInfoDto) {
        //获取查询参数
        Map<String, Object> searchParam = UtilDTO.toDTO(navigationConfigurationInfoDto, null);
        //执行分页查询
        Page<TerminalConfigNavigationBtmEntity> navigationConfigurationInfoPage = navigationConfigurationInfoService
                .queryByPage(searchParam, navigationConfigurationInfoDto.getPageNum(), navigationConfigurationInfoDto.getPageSize(),new String[]{"num_asc"});

        //数据库实体与前端展示字段之间的转换，需要指定前端展示需要的字段
        List<Map<String, Object>> navigationConfigurationInfoList = UtilDTO.toDTO(navigationConfigurationInfoPage.getContent(),
                null, getDtoCallbackHandler());

        return ResponseParam.success()
                .pageParam(navigationConfigurationInfoPage)
                .datalist(navigationConfigurationInfoList);
    }

    /**
     * 获取详情数据。
     */
    @PostMapping("get")
    @OptLog(optModule="终端管理-基础配置",optName="获取底部导航",optType= OptLogConst.GET)
    public ResponseParam detail(@RequestBody AppBaseIdParam param) {
        //查询终端导航配置
        TerminalConfigNavigationBtmEntity navigationConfigurationInfo = navigationConfigurationInfoService.get(param.getId());

        //将实体转换为数据传输对象
        Map<String, Object> dtoObject = UtilDTO.toDTO(navigationConfigurationInfo, null, getDtoCallbackHandler());

        return ResponseParam.success()
                .data(dtoObject);
    }

    /**
     * 保存实体对象 。在保存之前进行后端实体属性的验证，保证添加的数据符合业务要求。<br>
     * 如果实体id不为空，进行更新操作，否则进行添加。
     *
     * @param navigationConfigurationInfo
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @OptLog(optModule="终端管理-基础配置",optName="保存底部导航",optType= OptLogConst.SAVE)
    public ResponseParam save(@RequestBody TerminalConfigNavigationBtmEntity navigationConfigurationInfo) {
        if(navigationConfigurationInfo.getTerminalId() == null){
            return ResponseParam.success().message(TerminalContent.CHECK_TERMINAL_ID);
        }
        // 判断是否存在已开启默认显示
        Map<String, Object> searchDefaultShow = new HashMap<>();
        searchDefaultShow.put("defaultShow",true);
        searchDefaultShow.put("area",navigationConfigurationInfo.getArea());
        searchDefaultShow.put(TerminalContent.TERMINAL_ID,navigationConfigurationInfo.getTerminalId());
        List<TerminalConfigNavigationBtmEntity> checkIsExsit = navigationConfigurationInfoService.queryAll(searchDefaultShow);
        if(checkIsExsit != null && checkIsExsit.size()>0){
            for (TerminalConfigNavigationBtmEntity navInfo:checkIsExsit) {
                if(navigationConfigurationInfo.getId() != null){
                    if(Long.toString(navInfo.getId()).equals(Long.toString(navigationConfigurationInfo.getId()))){
                        break;
                    }else {
                        if(!navigationConfigurationInfo.getDefaultShow()){
                            break;
                        }else {
                            return ResponseParam.info(false).message(TerminalContent.CHECK_DEFALUT_SHOW_NAV);
                        }
                    }
                }
                if(Long.toString(navInfo.getTerminalId()).equals(Long.toString(navigationConfigurationInfo.getTerminalId()))){
                    if(!navigationConfigurationInfo.getDefaultShow()){
                        break;
                    }else {
                        return ResponseParam.info(false).message(TerminalContent.CHECK_DEFALUT_SHOW_NAV);
                    }
                }
            }
        }
        //id不为空，进行更新操作，否则进行添加
        if (navigationConfigurationInfo.getId() != null) {
            //按照字段更新对象
            navigationConfigurationInfoService.updateById(navigationConfigurationInfo);
            return ResponseParam.updateSuccess();
        } else {
            navigationConfigurationInfo.setStatus(StatusType.ENABLE);
            Integer maxNum = navigationConfigurationInfoService.queryMaxNum(navigationConfigurationInfo);
            if(maxNum == null){
                maxNum = 0;
            }
            navigationConfigurationInfo.setNum(++maxNum);
            navigationConfigurationInfoService.save(navigationConfigurationInfo);
            return ResponseParam.saveSuccess();
        }
    }

    /**
     * 删除信息
     *
     * @param list
     * @return
     */
    @PostMapping("delete")
    @OptLog(optModule="终端管理-基础配置",optName="删除底部导航",optType= OptLogConst.DELETE)
    public ResponseParam delete(@RequestBody List<AppBaseIdParam> list) {
        if (UtilCollection.isEmpty(list)) {
            return ResponseParam.deleteFail();
        }
        navigationConfigurationInfoService.deleteByBtmNav(list);
        return ResponseParam.deleteSuccess();
    }

    /**
     * 获取PO到DTO转换的接口。主要用于在前端展示数据之前首先将数据格式处理完成。
     *
     * @return
     */
    public DTOCallbackHandler getDtoCallbackHandler() {

        //创建转换接口类
        DTOCallbackHandler dtoCallbackHandler = new DTOCallbackHandler() {
            @Override
            public void doHandler(Map<String, Object> dtoMap, Class<?> itemClass) {

            }
        };

        return getDTOCallbackHandlerProxy(dtoCallbackHandler, true);
    }

    /**
     * 功能描述: 导航重排序
     * TODO
     *
     * @param navigationList
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/9/30 9:12
     */
    @PostMapping("navigationsort")
    @OptLog(optModule="终端管理-基础配置",optName="底部导航排序",optType= OptLogConst.UPDATE)
    public ResponseParam navigationSort(@RequestBody List<TerminalConfigNavigationBtmEntity> navigationList) {
        // 返回结果
        if(navigationList != null && navigationList.size() > 0){
            for (TerminalConfigNavigationBtmEntity navigationConfigurationInfo: navigationList) {
                navigationConfigurationInfoService.updateById(navigationConfigurationInfo);
            }
            return ResponseParam.updateSuccess();
        }
        //返回结果集
        return ResponseParam.updateFail();
    }

    /**
     * 功能描述: 查询全部终端基础配置接口
     * TODO
     *
     * @param navigationConfigurationInfoDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/9/28 15:35
     */
    @PostMapping("query/allnavigation")
    @OptLog(optModule="终端管理-基础配置",optName="查询全部终端基础配置",optType= OptLogConst.QUERY)
    public ResponseParam queryallnavigation(@RequestBody TerminalConfigNavigationBtmDto navigationConfigurationInfoDto) {
        String area = navigationConfigurationInfoDto.getArea();
        if(StringUtils.isNotBlank(area) && area.contains("-")){
            navigationConfigurationInfoDto.setArea(area.split("-")[1]);
        }
        // 返回结果
        //HashMap<String, Object> resultHashMap = Maps.newHashMap();
        //获取查询参数
        Map<String, Object> searchParam = UtilDTO.toDTO(navigationConfigurationInfoDto, null);
        //查询拼装导航配置
        List<TerminalConfigNavigationBtmEntity> navigationConfigurationInfoList = navigationConfigurationInfoService.queryAll(searchParam);
        //navigationConfigurationInfoList.sort((a,b)->a.getNum().compareTo(b.getNum()));
        List<TerminalConfigNavigationBtmEntity> ascNavigationConfigurationInfoList = navigationConfigurationInfoList.stream().sorted(Comparator.comparing(TerminalConfigNavigationBtmEntity::getNum)).collect(Collectors.toList());//resultHashMap.put("navigationConfigurationInfoList", navigationConfigurationInfoList);
        //返回结果集
        return ResponseParam.success().datalist(ascNavigationConfigurationInfoList);
    }

    /**
     * 描述: 开启/停用 功能
     * @param navigationConfigurationInfoDto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/8 13:49
     **/
    @PostMapping("update/status")
    @OptLog(optModule="终端管理-基础配置",optName="修改配置信息状态",optType= OptLogConst.UPDATE)
    public ResponseParam status(@RequestBody TerminalConfigNavigationBtmEntity navigationConfigurationInfoDto) {
        if(navigationConfigurationInfoDto.getId() == null){
            return ResponseParam.fail().message(TerminalContent.CHECK_PRIMARY_KEY);
        }
        navigationConfigurationInfoService.update(navigationConfigurationInfoDto,Arrays.asList("status"));
        return ResponseParam.updateSuccess();
    }

    /**
     * 描述: 获取底部导航下拉框
     * @param dto
     * @return com.fosung.framework.web.http.ResponseParam
     * @author fuhao
     * @date 2021/11/9 14:16
     **/
    @PostMapping("option")
    @OptLog(optModule="终端管理-基础配置",optName="获取底部导航下拉框",optType= OptLogConst.QUERY)
    public ResponseParam btmNavOption(@RequestBody TerminalConfigNavigationBtmDto dto){
        if(dto.getTerminalId() == null){
            return ResponseParam.fail().message(TerminalContent.CHECK_TERMINAL_ID);
        }
        if(StringUtils.isBlank(dto.getArea())){
            return ResponseParam.fail().message(TerminalContent.CHECK_AREA);
        }
        List<Map<String, Object>> mapList = UtilDTO.toDTO(navigationConfigurationInfoService.btmNavOption(dto), Arrays.asList("id", "navigationName", "terminalId", "area"));
        return ResponseParam.success().datalist(mapList);
    }
}