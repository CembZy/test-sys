package io.renren.common.utils;

import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


@RequestMapping("public")
@RestController
public class PublicUploadController {


    /**
     * 多文件上传,本地
     *
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
//    @PostMapping("uploadFile")
//    @RequiresPermissions("uploadFile:save")
//    public R uploadFile(HttpServletRequest request) throws IllegalStateException, IOException {
//        //创建一个通用的多部分解析器
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        //判断 request 是否有文件上传,即多部分请求
//        if (multipartResolver.isMultipart(request)) {
//            //转换成多部分request
//            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//            //取得request中的所有文件名
//            Iterator<String> iter = multiRequest.getFileNames();
//
//            String folderName = "file/";
//            //获取当前时间
//            Date date = new Date();
//            DateFormat format = new SimpleDateFormat("yyyyMMdd");
//            String timeDay = format.format(date);
//            //获取项目工程绝对路径
//            String path = request.getSession().getServletContext().getRealPath("/");
////            String path = this.getClass().getResourceAsStream("") + "statics/";
//            path = path + folderName + timeDay;
//
//            while (iter.hasNext()) {
//                //取得上传文件
//                MultipartFile file = multiRequest.getFile(iter.next());
//                if (file != null) {
//                    //取得当前上传文件的文件名称
//                    String myFileName = file.getOriginalFilename();
//                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
//                    if (myFileName.trim() != "") {
//                        //重命名上传后的文件名
//                        String fileName = UUID.randomUUID() + myFileName.substring(myFileName.lastIndexOf("."));
//                        File targetDir = new File(path);
//                        if (!targetDir.exists()) {
//                            targetDir.mkdirs();
//                        }
//                        String pathName = path + "/" + fileName;
//                        File targetFile = new File(pathName);
//                        file.transferTo(targetFile);
//                        String imageUrl = "/testSystem/statics/" + folderName + timeDay + '/' + fileName;
//                        return R.ok().put("fileUrl", imageUrl);
//                    }
//                }
//            }
//
//        }
//        return R.error();
//    }
    @PostMapping("uploadFile")
    @RequiresPermissions("uploadFile:save")
    public R uploadFile(HttpServletRequest request) {
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                //存储图片的物理路径
                Date date = new Date();
                DateFormat format01 = new SimpleDateFormat("yyyyMMdd");
                String time01 = format01.format(date);
                /**
                 * 修改需求。路径不能包括中文路径。所以将传过来的文件夹重命名为中文路径
                 */
                String folderName = "/file/upload/image/";
                //获得工程绝对路径
//                String string = request.getSession().getServletContext().getRealPath("/");
                String string = "/home";
                System.err.println("工程绝对路径" + string);
                String path = string + folderName + time01;
                System.out.println(path);
                String fileName = file.getOriginalFilename();
                //统一图片名字 新名字
                String newFileName = UUIDBuild.getUUID() + fileName.substring(fileName.lastIndexOf("."));
                File targetFile = new File(path, newFileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                // 保存
                try {
                    //将图片写入磁盘
                    file.transferTo(targetFile);
                    String imageUrl = "/testSystem/" + time01 + "/" + newFileName;
//                    String imageUrl = "/testSystem/" + folderName+ time01 + "/" + newFileName;
                    return R.ok().put("fileUrl", imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    return R.error();
                }
            }
        }
        return R.error();
    }


    /**
     * 导入用户，加入缓存
     *
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @PostMapping("importUsers")
    @RequiresPermissions("importUsers:save")
    public R importUsers(HttpServletRequest request) throws IllegalStateException {
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String key = file.getOriginalFilename() + ShiroUtils.getUserId();
                    CacheUtil.addCache(key, file);
                    return R.ok().put("key", key);
                }
            }

        }
        return R.error();
    }


    /**
     * 导入题库，加入缓存
     *
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @PostMapping("importTests")
    @RequiresPermissions("importTests:save")
    public R importTests(HttpServletRequest request) throws IllegalStateException {
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String key = file.getOriginalFilename() + ShiroUtils.getUserId();
                    CacheUtil.addTestsCache(key, file);
                    return R.ok().put("key", key);
                }
            }
        }
        return R.error();
    }


}
