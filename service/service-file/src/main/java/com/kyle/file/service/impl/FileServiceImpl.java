package com.kyle.file.service.impl;

import com.kyle.file.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @auther kyle
 * @creat 2022-11-19:57
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadAvatar(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        fileName = fileName.toLowerCase();
        if (!fileName.matches("^.+\\.(jpg|png|gif)$")){
            return null;
        }
        //转化为图片对象
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (width==0||height==0){
                return null;
            }
            String path = "img/";
            Map<String, String> map = saveUtil(fileName, file, path);
            return "/file/"+path+map.get("dateDir")+"/"+map.get("realFileName");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean delAvatar(String url) {
        String delUrl = url.replace("http://localhost:8001/file/", System.getProperty("user.dir") + "/service/service-file/");
        return new File(delUrl).delete();
    }

    @Override
    public String uploadResume(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        fileName = fileName.toLowerCase();
        if (!fileName.matches("^.+\\.(pdf)$")){
            return null;
        }
        try {
            String path = "resume/";
            Map<String, String> map = saveUtil(fileName, file, path);
            return "http://localhost:8001/file/"+path+map.get("dateDir")+"/"+map.get("realFileName");
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private Map<String,String> saveUtil(String fileName, MultipartFile file, String path) throws IOException {
        //实现文件储存
        String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
//            String fileDirPath = "E:\\idea-workspace\\work_to_study\\service\\service-file\\img\\" + dateDir + "\\";
        String fileDirPath = System.getProperty("user.dir")+"/service/service-file/"+path+ dateDir + "/";
//            System.out.println(fileDirPath);
        File dirFile = new File(fileDirPath);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }

        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String realFileName = uuid+fileType;

        //实现文件保存
        file.transferTo(new File(fileDirPath+realFileName));

        Map<String, String> map = new HashMap<>();

        map.put("dateDir",dateDir);
        map.put("realFileName",realFileName);


        return map;
    }

}
