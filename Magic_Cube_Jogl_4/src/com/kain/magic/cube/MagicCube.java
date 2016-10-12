package com.kain.magic.cube;

public class MagicCube {

    public static void main(String[] args) {
        GLDisplay myGLDisplay = GLDisplay.createGLDisplay("用OJGL做的魔方游戏");
        CubeRenderer cubeRenderer = new CubeRenderer();
        //定义一个键盘代理  
        KeyHandler keyHandler = new KeyHandler(cubeRenderer, myGLDisplay);

        //定义一个鼠标代理  
        MouseHandler mouseHander = new MouseHandler(cubeRenderer, myGLDisplay);

        //注册GLEvent监听 
        myGLDisplay.addGLEventListener(cubeRenderer);
        //注册键盘监听  
        myGLDisplay.addKeyListener(keyHandler);

        //注册鼠标监听 
        myGLDisplay.addMouseMotionListener(mouseHander);
        myGLDisplay.addMouseListener(mouseHander);
        myGLDisplay.start();
    }
}
