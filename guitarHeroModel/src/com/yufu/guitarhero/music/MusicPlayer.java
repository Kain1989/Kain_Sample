package com.yufu.guitarhero.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created on 11/25/2015.
 */
public class MusicPlayer {

    public static void main(String args[]) {

        String bip = "D:\\Document_Kain\\test.mp3";
        Media hit = new Media(bip);
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
}
