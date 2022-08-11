package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Music;
import com.xxxx.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2022-08-01
 */
public interface IMusicService extends IService<Music> {

    RespBean upload(String title, String author, String path, HttpServletRequest request);
}
