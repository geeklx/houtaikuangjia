package com.geek.system.support.mq;

public class MQMessageConstant {

    public final static String TOPIC = "cloud_sso";

    public final static String FACE_TOPIC = "cloud_face";

    public final static String USER_TAG = "user";

    public final static String ORG_TAG = "org";

    public final static String POST_TAG = "post";

    public final static String FACE_TAG = "face";

    public final static String OPERATE_TYPE_UPDATE_USER = "updateUser";

    public final static String OPERATE_TYPE_ADD_FACE = "ADD";
    public final static String OPERATE_TYPE_DELETE_FACE = "DELETE";
    public final static String OPERATE_TYPE_UPDATE_FACE = "UPDATE";

    public final static String OPERATE_TYPE_UPDATE_PHONE = "updatePhone";

    public final static String OPERATE_TYPE_UPDATE_ORG = "updateOrg";

    public final static String OPERATE_TYPE_ADD_USER = "addUser";

    /**
     * 处理历史数据，更新不合法的身份证号
     */
    public final static String OPERATE_TYPE_UPDATE_ID_CARD = "updateIdCard";

    public final static String OPERATE_TYPE_ADD_ORG = "addOrg";

    public final static String OPERATE_TYPE_DELETE_ORG = "deleteOrg";

    public final static String OPERATE_TYPE_DELETE_USER = "deleteUser";

    public final static String ADD = "ADD";

    public final static String DEL = "DEl";

    /**
     * 该用户所属组织任职, 非所属组织非首次任职
     */
    public final static String OPERATE_TYPE_ADD_POST = "addPost";

    /**
     * 该用户所属组织任职, 非所属组织非首次任职
     */
    public final static String OPERATE_TYPE_ADD_COM_POST = "addComPos";

    /**
     *  该用户所属组织任职, 非所属组织非最后职务；
     */
    public final static String OPERATE_TYPE_DELETE_POST = "delPos";

    /**
     *  该用户非所属组织任职, 非所属组织无其他职务；
     */
    public final static String OPERATE_TYPE_DEL_COM_POST = "delComPos";

    /**
     * 储备干部职务
     */
    public final static String CHU_BEI_GAN_BU = "CHUBEIGANBU";

    /**
     * 普通村民职务
     */
    public final static String ORDINARY_VILLAGER = "ORDINARY_VILLAGER";
}
