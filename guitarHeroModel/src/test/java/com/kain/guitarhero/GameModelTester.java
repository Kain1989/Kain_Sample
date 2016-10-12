package com.kain.guitarhero;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class GameModelTester {
    static LinkedBlockingQueue<Note> noteQueue;
    static GameModel game;

    @Before
    public void setup() throws InterruptedException {
        this.noteQueue = new LinkedBlockingQueue<Note>();
        for (int i = 1; i < 10000; i++) {
            final Note note = new Note(true, false, false, false, false, i * 1000);
            this.noteQueue.put(note);
        }
        this.game = new GameModel(new MockMusicPlayer(), this.noteQueue);
    }

    @Test
    public void test() throws InterruptedException {
        this.game.start();

        while (true) {
            final Scanner sc = new Scanner(System.in);
            System.out.println(sc.next());
            sc.nextLine();
            final Note inputNote = new Note(true, false, false, false, false, this.game.getMusicPlayer().getCurrentPosition());
            this.game.handleInput(inputNote);
        }
    }

    @Test
    public void testSomthing() throws IOException {

    }

    public static void main(String args[]) throws InterruptedException {
        noteQueue = new LinkedBlockingQueue<Note>();
        for (int i = 1; i < 10000; i++) {
            final Note note = new Note(true, false, false, false, false, i * 1000);
            noteQueue.put(note);
        }
        game = new GameModel(new MockMusicPlayer(), noteQueue);

        game.start();

        while (true) {
            final Scanner sc = new Scanner(System.in);
            sc.nextLine();
            final Note inputNote = new Note(true, false, false, false, false, game.getMusicPlayer().getCurrentPosition());
            game.handleInput(inputNote);
        }
    }

}
