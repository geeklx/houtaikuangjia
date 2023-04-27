#FROM registry.cn-qingdao.aliyuncs.com/hub_fosung/jar:1.8
FROM swr.cn-north-4.myhuaweicloud.com/fosung_huawei/jar:1.8
#FROM registry.cn-qingdao.aliyuncs.com/hub_fosung/jar_arm64:1.8
MAINTAINER ToQuery <toquery@qq.com>
VOLUME /tmp

ARG JAR_FILE
ADD ${JAR_FILE} /home/project/app.jar
