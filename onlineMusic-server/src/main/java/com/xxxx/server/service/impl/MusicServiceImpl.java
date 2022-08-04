package com.xxxx.server.service.impl;

import com.xxxx.server.pojo.Music;
import com.xxxx.server.mapper.MusicMapper;
import com.xxxx.server.service.IMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
