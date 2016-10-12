package com.kain.magic.cube;

import com.kain.magic.cube.model.Selected;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

    private CubeRenderer renderer;

    public KeyHandler(CubeRenderer renderer, GLDisplay glDisplay) {
        this.renderer = renderer;
    }

    //键按下  
    public void keyPressed(KeyEvent e) {
        processKeyEvent(e, true);
    }

    //处理键盘按下的操作  
    private void processKeyEvent(KeyEvent e, boolean pressed) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                if (!renderer.isRotating()) {
                    renderer.ratate90(false, renderer.getSelected());
                }
                break;
            case KeyEvent.VK_D:
                if (!renderer.isRotating()) {
                    renderer.ratate90(true, renderer.getSelected());
                }
                break;
            case KeyEvent.VK_W:
                if (renderer.getSelected().axis == Selected.X) {
                    renderer.setSelected(
                            new Selected(Selected.Y, renderer.getSelected().index));
                } else if (renderer.getSelected().axis == Selected.Y) {
                    renderer.setSelected(
                            new Selected(Selected.Z, renderer.getSelected().index));
                } else if (renderer.getSelected().axis == Selected.Z) {
                    renderer.setSelected(
                            new Selected(Selected.X, renderer.getSelected().index));
                }
                break;
            case KeyEvent.VK_S:
                renderer.setSelected(
                        new Selected(renderer.getSelected().axis, (renderer.getSelected().index + 1) % 4));
                break;
            case KeyEvent.VK_SPACE:

                break;
        }
    }
}
