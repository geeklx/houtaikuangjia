package com.fosung.workbench.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Description 文件工具
 * @Author gaojian
 * @Date 2021/10/26 11:00
 * @Version V1.0
 */
public class FileUtil {

    /**
     * 描述:  MultipartFile 转 file
     * @createDate: 2021/10/26 11:01
     * @author: gaojian
     * @modify:
     * @param file
     * @return: java.io.File
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * 描述:  获取流文件
     * @createDate: 2021/10/26 11:01
     * @author: gaojian
     * @modify:
     * @param ins
     * @param file
     * @return: void
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
