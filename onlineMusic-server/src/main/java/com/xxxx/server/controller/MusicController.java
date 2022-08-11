package com.xxxx.server.controller;


import com.xxxx.server.pojo.Music;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.User;
import com.xxxx.server.service.IMusicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2022-08-01
 */
@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private IMusicService musicService;
    @Value("${upload.path}")
    private String path;
    @ApiOperation(value = "上传音乐")
    @PostMapping("/uploadMusic")
    public RespBean uploadMusic(String title,String author,HttpServletRequest request){
        return musicService.upload(title,author,path,request);
    }
    @ApiOperation(value = "播放功能")
    @GetMapping("/display")
    public RespBean display(@RequestParam String filename) throws IOException {
        File file=new File(path+filename);
        byte[] res=null;
        res= Files.readAllBytes(file.toPath());
        if(res!=null){
            return RespBean.success("播放",res);
        }
        return RespBean.error("播放失败");

    }
}
