package com.xxxx.server.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * 音乐播放工具，测试能不能将接口传递的文件byte流正常播放，因为没有写前端播放器
 */
public class MusicPlay extends Application {
    public static void main(String[] args) throws IOException, JavaLayerException {
            MusicPlays.AudioPlayer();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File("D:/Music/不要说话.mp3");
        URI uri = file.toURI();
        System.out.println(uri);

        Media media = new Media(uri.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);

        // 可以使用按钮控制
        //mediaPlayer.play();
        primaryStage.setScene(new Scene(new AnchorPane(mediaView)));
        primaryStage.show();
    }
}
