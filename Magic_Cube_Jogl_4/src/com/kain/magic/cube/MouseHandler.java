package com.kain.magic.cube;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class MouseHandler extends MouseMotionAdapter implements MouseListener {

    private CubeRenderer renderer;
    private Point oldPoint;
    private float angleX;
    private float angleY;
    private float curXRot = 0;
    private float curYRot = 0;

    public MouseHandler(CubeRenderer renderer, GLDisplay glDisplay) {
        this.renderer = renderer;
        //glDisplay.registerMouseEventForHelp(, modifiers, description);
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        angleX = (float) ((e.getY() - oldPoint.y));
        angleY = (float) ((e.getX() - oldPoint.x));
        if (curXRot > 45) {
            curXRot = 45;
        } else if (curXRot < -45) {
            curXRot = -45;
        }
        renderer.setCurXRot(curXRot);
        renderer.setCurYRot(curYRot);
        curXRot += angleX;
        curYRot += angleY;
        oldPoint = e.getPoint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mousePressed(MouseEvent e) {
        oldPoint = e.getPoint();
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }


}
