package com.kyle.file.controller;

import com.kyle.file.service.FileService;
import com.kyle.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther kyle
 * @creat 2022-11-19:53
 */
@RestController
@CrossOrigin
@RequestMapping("/actfile/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("上传头像")
    @PostMapping("uploadAvatar")
    public R uploadAvatar(MultipartFile file){
        String url = fileService.uploadAvatar(file);
        if (url != null){
            System.out.println(url);
            return R.ok().data("fileUrl",url);
        }else {
            return R.error().message("上传失败！");
        }
    }

    @ApiOperation("删除头像")
    @PostMapping("delAvatar")
    public R delAvatar(@RequestParam("url") String url){
        boolean isDelete = fileService.delAvatar(url);
        if (isDelete){
            return R.ok();
        }else {
            return R.error().message("删除失败！");
        }
    }

//    @ApiOperation("获取学生头像")
//    @GetMapping(value = "getStudentAvatar")
//    public void getStudentAvatar(@RequestBody Map<String,String> data, HttpServletRequest req, HttpServletResponse resp){
//        try {
//            System.out.println("aaa="+data.get("fileUrl"));
//            String fileUrl = data.get("fileUrl");
//            FileInputStream fileInputStream = new FileInputStream(fileUrl);
//            byte[] bytesByStream = new byte[fileInputStream.available()];
//            fileInputStream.read(bytesByStream, 0, fileInputStream.available());
//        } catch (IOException e) {
//            e.printStackTrace();
//            req.setAttribute("json",R.error().message("找不到头像！"));
//        }
//    }

    @ApiOperation("上传简历")
    @PostMapping("uploadResume")
    public R uploadResume(MultipartFile file){
        String url = fileService.uploadResume(file);
        return R.ok().data("fileUrl",url);
    }

}
