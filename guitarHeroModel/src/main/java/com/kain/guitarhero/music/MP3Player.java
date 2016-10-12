package com.kain.guitarhero.music;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;

/**
 * Created on 11/20/2015.
 */
public class MP3Player {

    public static void main(String[] args) {
        try {
            FileInputStream fileau = new FileInputStream("\"D:\\Document_Kain\\test.mp3");
            AudioStream as = new AudioStream(fileau);
            AudioPlayer.player.start(as);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
