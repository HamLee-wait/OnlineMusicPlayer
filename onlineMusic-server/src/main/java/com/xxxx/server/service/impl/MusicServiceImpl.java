package com.xxxx.server.service.impl;

import com.xxxx.server.pojo.Music;
import com.xxxx.server.mapper.MusicMapper;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2022-08-01
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements IMusicService {
    @Autowired
    private MusicMapper musicMapper;
    @Autowired
    private IUserService userService;

    @Override
    public RespBean upload(String title, String author, String path, HttpServletRequest request) {
        Music music=new Music();
        music.setTitle(title);
        music.setAuthor(author);
        music.setPath(path);
        LocalDateTime localDateTime=LocalDateTime.now();
        music.setUploadTime(localDateTime);
        String username = (String) request.getSession().getAttribute("username");
        int uId=userService.getUserByUsername(username).getUserId();
        music.setUserId(uId);
        if(1==musicMapper.insert(music)){
            return RespBean.success("上传成功(*^_^*)");
        }
        return RespBean.error("上传失败/(ㄒoㄒ)/~~");

    }
}
