package com.xxxx.server.utils;

import com.xxxx.server.controller.MusicController;
import com.xxxx.server.pojo.Music;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IMusicService;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class MusicPlays {
    /**
     * 先测试一下能不能拿到controller播放的音频流
     */

    public static void AudioPlayer() throws IOException, JavaLayerException {
        RespBean respBean=readMusic("说好不哭.m4a","周杰伦");
        //应该是拿到了文件流了
        byte[] res= (byte[]) respBean.getObject();
        writerByteToFileInputStream(res);

    }

    /**
     * 8.16号更新一下，因为歌手增多，文件管理方式出现，所以需要重新进行测试
     * @param filename
     * @return
     * @throws IOException
     */
    //先拿一个容器，获取其对象
    @Autowired
    private static IMusicService musicService;
    private static RespBean readMusic(String filename,String author) throws IOException {
        File file=new File("D:/Music/"+author+'/'+filename);
        byte[] res=null;
        res= Files.readAllBytes(file.toPath());
        if(res!=null){
            return RespBean.success("播放",res);
        }
        return RespBean.error("播放失败");

    }
    //把字节流转化成AudioInput音频流
    private static AudioInputStream writerBytesBackToStream(byte[] bytes){
        ByteArrayInputStream baiut=new ByteArrayInputStream(bytes);
        AudioInputStream stream=null;
        try {
            {
                stream=AudioSystem.getAudioInputStream(baiut);
            }
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (stream.equals(null)||stream==null){
            System.out.println("转化失败");
        }
        return stream;

    }
    public static void  writerByteToFileInputStream(byte[] bytes) throws FileNotFoundException, JavaLayerException {
//        FileInputStream stream= new FileInputStream(String.valueOf(bytes));
        InputStream inputStream = new ByteArrayInputStream(bytes);
        Player player=new Player(inputStream);
        player.play();
    }
}
