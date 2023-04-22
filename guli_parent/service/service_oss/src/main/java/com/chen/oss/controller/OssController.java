package com.chen.oss.controller;
import com.chen.commonutils.Result;
import com.chen.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss/file")
//@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public Result uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return Result.ok().messgae("文件上传成功").data("url", url);
    }
}