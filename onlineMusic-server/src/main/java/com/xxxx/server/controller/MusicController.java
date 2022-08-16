package com.xxxx.server.controller;


import com.xxxx.server.enmu.MusicType;
import com.xxxx.server.pojo.Music;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.User;
import com.xxxx.server.service.IMusicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

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
    private String basePath;
    @ApiOperation(value = "测试一下getOriginalFilename()方法")
    @PostMapping("/testGetOriginalFilename")
    /**
     * 经过swagger2的测试Multipart的getOriginFilename只是包含后缀名而不包含前置路径
     */
    public String getOriginalFilename(@RequestPart(value = "FileName",required = true) MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return file.getOriginalFilename();
    }

    @ApiOperation(value = "上传音乐")
    @PostMapping("/uploadMusic")
    public RespBean uploadMusic( @RequestParam(value = "author",required = false,defaultValue = "不知名歌手") String author, HttpServletRequest request,
                                 @RequestPart(value = "FileName",required = true) MultipartFile file) throws IOException {
        /**
         * 由于使用了spring security所以不需要做登录验证
         */
        //文件的类型
        String title=file.getOriginalFilename();
        /**
         * 先判断上传的文件是不是音频文件,暂时只支持.wav和mp3.
         */
        int typeIndex=title.lastIndexOf('.');
        String type=title.substring(typeIndex);
        if(!MusicType.isContain(type)){
            return RespBean.error("暂时不支持该文件上传哦, w(ﾟДﾟ)w ");
        }
        /**
         * 类型正确后我们开始我们的上传操作，上传操作先创建一个歌手文件夹，然后把对应的音乐文件放在该路径下
         */
       //在确认可以上传即不存在同名文件之后在将文件信息入库
        String dirPath=basePath+author;
        //生成文件的文件名为上传的文件名（包含后缀名）
        String path=dirPath+"/"+title;
        File uploadFile=new File(path);
        File Dir=new File(dirPath);
        //判断一下该文件是否存在，理论上肯定是不存在的
        if(!Dir.exists()){
            Dir.mkdirs();
        }
        if (!uploadFile.exists()){
            file.transferTo(uploadFile);
        }
        else {
            return RespBean.error("该音乐已经存在了(✿◡‿◡)");
        }
        System.out.println("************Log1*******************");
        return musicService.upload(title,author,basePath,request);
    }
    @ApiOperation(value = "播放功能")
    @GetMapping("/display")
    public RespBean display(@RequestParam String filename,@RequestParam(required = false,defaultValue = "不知名歌手") String author) throws IOException {
        List<Music> list=musicService.getMusicByTitle(filename);
        for (Music m:list){
            if(m.getAuthor().equals(author)){
                File file=new File(basePath+author+"/"+filename);
                byte[] res=null;
                res= Files.readAllBytes(file.toPath());
                if(res!=null){
                    return RespBean.success("播放",res);
                }
                return RespBean.error("播放失败");
            }
        }
        return RespBean.error("找不到该音乐/(ㄒoㄒ)/~~");
    }
}
