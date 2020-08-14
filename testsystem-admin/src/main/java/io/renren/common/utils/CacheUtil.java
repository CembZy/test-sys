package io.renren.common.utils;

import io.renren.modules.subject.entity.SysSubjectEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil {

    public static final Map<String, List<SysUserEntity>> map = new ConcurrentHashMap<>();

    public static final Map<String, List<SysSubjectEntity>> testsMap = new ConcurrentHashMap<>();


    public static List<SysUserEntity> getCache(String key) {
        List<SysUserEntity> sysUserEntities = map.get(key);
        return sysUserEntities;
    }

    public static void removeCache(String key) {
        map.remove(key);
    }

    public static void addCache(String key, MultipartFile mFile) {
        if (!map.containsKey(key)) {
            //创建处理EXCEL的类
            ReadExcel readExcel = new ReadExcel();
            //解析excel，获取上传的事件单
            List<Map<String, Object>> userList = readExcel.getExcelInfo(mFile, 1);
            //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
            List<SysUserEntity> sysUserEntities = new ArrayList<>();
            Date date = DateUtil.getDate();
            for (Map<String, Object> usert : userList) {
                SysUserEntity sysUserEntity = new SysUserEntity();
                sysUserEntity.setUsername(usert.get("username") == null ? "" : usert.get("username").toString());
                sysUserEntity.setPassword(usert.get("password") == null ? "" : usert.get("password").toString());
                sysUserEntity.setName(usert.get("name") == null ? "" : usert.get("name").toString());
                sysUserEntity.setSex(usert.get("sex") == null ? "" : usert.get("sex").toString());
                sysUserEntity.setIdtype(usert.get("idType") == null ? "" : usert.get("idType").toString());
                sysUserEntity.setIdcard(usert.get("idcard") == null ? "" : usert.get("idcard").toString());
                sysUserEntity.setMobile(usert.get("mobile") == null ? "" : usert.get("mobile").toString());
                sysUserEntity.setEmail(usert.get("email") == null ? "" : usert.get("email").toString());
                sysUserEntity.setCreateTime(date);
                sysUserEntities.add(sysUserEntity);
            }
            map.put(key, sysUserEntities);
        }

    }


    public static List<SysSubjectEntity> getTestsCache(String key) {
        List<SysSubjectEntity> sysSubjectEntities = testsMap.get(key);
        return sysSubjectEntities;
    }

    public static void removeTestsCache(String key) {
        testsMap.remove(key);
    }

    public static void addTestsCache(String key, MultipartFile mFile) {
        if (!testsMap.containsKey(key)) {
            //创建处理EXCEL的类
            ReadExcel readExcel = new ReadExcel();
            //解析excel，获取上传的事件单
            List<Map<String, Object>> sysUserEntities = readExcel.getExcelInfo(mFile, 2);
            //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
            List<SysSubjectEntity> sysSubjectEntities = new ArrayList<>();
            Date date = DateUtil.getDate();
            for (Map<String, Object> tests : sysUserEntities) {
                SysSubjectEntity sysSubjectEntity = new SysSubjectEntity();
                String topicName = tests.get("topicName").toString();
                sysSubjectEntity.setTopicName(topicName == null ? "" : topicName);
                sysSubjectEntity.setDifficulty(tests.get("difficulty") == null ? "" : tests.get("difficulty").toString());
                sysSubjectEntity.setParse(tests.get("price") == null ? "" : tests.get("price").toString());
                sysSubjectEntity.setContent(tests.get("content") == null ? "" : tests.get("content").toString());
                sysSubjectEntity.setAnswer(tests.get("answer") == null ? "" : tests.get("answer").toString());
                sysSubjectEntity.setParse(tests.get("parse") == null ? "" : tests.get("parse").toString());
                sysSubjectEntity.setCreateTime(date);
                sysSubjectEntities.add(sysSubjectEntity);
            }
            testsMap.put(key, sysSubjectEntities);
        }

    }


}
