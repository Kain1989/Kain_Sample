package com.kain.magic.cube;

import com.kain.magic.cube.model.Block;
import com.kain.magic.cube.model.Point3D;
import com.kain.magic.cube.model.Selected;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.gl2.GLUgl2;


public class CubeRenderer implements GLEventListener {

    public final static float length = 0.6f;

    private float curXRot;
    private float curYRot;

    //深入屏幕的距离  
    private float z = -6.0f;

    private GLUgl2 glu = new GLUgl2();

    private Block[][][] blocks4 = new Block[4][4][4];

    private boolean rotating = false;

    public final static int blue = 0;
    public final static int red = 1;
    public final static int green = 2;
    public final static int orange = 3;
    public final static int yellow = 4;
    public final static int white = 5;
    //private int selected=blue;

    private Selected selected = new Selected(Selected.X, 0);

    public Selected getSelected() {
        return selected;
    }

    public void setSelected(Selected selected) {
        this.selected = selected;
    }

    public boolean isRotating() {
        return rotating;
    }

    public void setRotating(boolean rotating) {
        this.rotating = rotating;
    }

    public void setCurXRot(float curXRot) {
        this.curXRot = curXRot;
    }

    public void setCurYRot(float curYRot) {
        this.curYRot = curYRot;
    }

    public void display(GLAutoDrawable glDrawable) {
        final GL2 gl2 = glDrawable.getGL().getGL2();
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl2.glLoadIdentity();
        gl2.glTranslatef(0.0f, 0.0f, this.z);
        gl2.glRotatef(curXRot, 1.0f, 0.0f, 0.0f);
        gl2.glRotatef(curYRot, 0.0f, 1.0f, 0.0f);  
        
        /*drawSelected(glDrawable);
        for(int i=1;i<=20;i++){
        	blocks[i].draw(glDrawable);
        }
        blockBlue.draw(glDrawable);
        blockRed.draw(glDrawable);
        blockGreen.draw(glDrawable);
        blockOrange.draw(glDrawable);
        blockWhite.draw(glDrawable);
        blockYellow.draw(glDrawable);*/
        drawSelected(glDrawable, selected);
        for (int x = 0; x < blocks4.length; x++) {
            for (int y = 0; y < blocks4[0].length; y++) {
                for (int z = 0; z < blocks4[0][0].length; z++) {
                    blocks4[x][y][z].draw(glDrawable);
                }
            }
        }


    }

    public void init(GLAutoDrawable glDrawable) {
        //获取OpenGL的操作类对象
        GL2 gl = glDrawable.getGL().getGL2();
        //启用高斯模糊，即阴影平滑
        gl.glShadeModel(GL2.GL_SMOOTH);
        //设置背景色为黑色  
        gl.glClearColor(0.0f, 1.0f, 1.0f, 0.5f);
        //启用深度缓存  
        gl.glClearDepth(1.0f);
        //启用深度测试  
        gl.glEnable(GL.GL_DEPTH_TEST);
        //所作的深度测试的类型  
        gl.glDepthFunc(GL.GL_LEQUAL);
        //真正的精细的透视修正  
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);


        for (float x = -length * 3 / 2; x <= length * 3 / 2; x += length) {
            for (float y = -length * 3 / 2; y <= length * 3 / 2; y += length) {
                for (float z = -length * 3 / 2; z <= length * 3 / 2; z += length) {
                    StringBuffer color = new StringBuffer();
                    if (x == -length * 3 / 2) {
                        color.append(Block.left);
                    }
                    if (x == length * 3 / 2) {
                        color.append(Block.right);
                    }
                    if (y == -length * 3 / 2) {
                        color.append(Block.down);
                    }
                    if (y == length * 3 / 2) {
                        color.append(Block.up);
                    }
                    if (z == -length * 3 / 2) {
                        color.append(Block.back);
                    }
                    if (z == length * 3 / 2) {
                        color.append(Block.front);
                    }
                    int xIndex = (int) ((x + length * 3 / 2) / length);
                    int yIndex = (int) ((y + length * 3 / 2) / length);
                    int zIndex = (int) ((z + length * 3 / 2) / length);
                    blocks4[xIndex][yIndex][zIndex] = new Block(new Point3D(x, y, z),
                            length - 0.05f, color.toString());
                }
            }
        }
    }

    public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) {
        final GL2 gl = glDrawable.getGL().getGL2();

        if (height <= 0) // avoid a divide by zero error!  
            height = 1;
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    public void dispose(GLAutoDrawable arg0) {
        //throw new UnsupportedOperationException("Not supported yet.");  
    }


    private void drawSelected(GLAutoDrawable glDrawable, Selected selected) {
        Point3D center = new Point3D();
        float R = 3 * length;
        if (selected.axis == Selected.X) {
            center.x = -3 * length / 2 + length * selected.index;
            center.y = 0;
            center.z = 0;
        } else if (selected.axis == Selected.Y) {
            center.x = 0;
            center.y = -3 * length / 2 + length * selected.index;
            center.z = 0;
        } else if (selected.axis == Selected.Z) {
            center.x = 0;
            center.y = 0;
            center.z = -3 * length / 2 + length * selected.index;
        }
        drawCircle(glDrawable, center, R);
    }

    private void drawCircle(GLAutoDrawable glDrawable, Point3D center, float R) {
        final GL2 gl2 = glDrawable.getGL().getGL2();
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl2.glColor3f(1.0f, 0.0f, 1.0f);
        gl2.glLineWidth(5.0f);
        int n = 40;
        float Pi = (float) Math.PI;

        if (center.x != 0 && center.y == 0 && center.z == 0) {
            gl2.glBegin(GL2.GL_LINE_LOOP);
            for (int i = 0; i < n; i++)
                gl2.glVertex3f(center.x, R * (float) Math.cos(2 * Pi / n * i), R * (float) Math.sin(2 * Pi / n * i));
            gl2.glEnd();
        } else if (center.x == 0 && center.y != 0 && center.z == 0) {
            gl2.glBegin(GL2.GL_LINE_LOOP);
            for (int i = 0; i < n; i++)
                gl2.glVertex3f(R * (float) Math.cos(2 * Pi / n * i), center.y, R * (float) Math.sin(2 * Pi / n * i));
            gl2.glEnd();
        } else if (center.x == 0 && center.y == 0 && center.z != 0) {
            gl2.glBegin(GL2.GL_LINE_LOOP);
            for (int i = 0; i < n; i++)
                gl2.glVertex3f(R * (float) Math.cos(2 * Pi / n * i), R * (float) Math.sin(2 * Pi / n * i), center.z);
            gl2.glEnd();
        }
    }

    public void ratate90(final Boolean clockWise, Selected selected) {
        final int index = selected.index;

        Block[][] temp = new Block[4][4];
        if (selected.axis == Selected.X) {
            new Thread(new Runnable() {
                int count = 0;

                @Override
                public void run() {
                    setRotating(true);
                    while (isRotating()) {
                        for (int y = 0; y < blocks4.length; y++) {
                            for (int z = 0; z < blocks4[0].length; z++) {
                                blocks4[index][y][z].rotateXYZ(1, 0, 0, (float) Math.PI / 240, clockWise);
                                ;
                            }
                        }
                        count++;
                        try {
                            Thread.sleep(8);
                        } catch (InterruptedException e) {
                            ;
                        }
                        if (count >= 120) {
                            setRotating(false);
                        }
                    }
                }

            }).start();
            for (int y = 0; y < blocks4.length; y++) {
                for (int z = 0; z < blocks4[0].length; z++) {
                    if (clockWise) {
                        temp[z][3 - y] = blocks4[index][y][z];
                    } else {
                        temp[3 - z][y] = blocks4[index][y][z];
                    }
                }
            }
            for (int y = 0; y < blocks4.length; y++) {
                for (int z = 0; z < blocks4[0].length; z++) {
                    blocks4[index][y][z] = temp[y][z];
                }
            }
        }
        if (selected.axis == Selected.Y) {
            new Thread(new Runnable() {
                int count = 0;

                @Override
                public void run() {
                    setRotating(true);
                    while (isRotating()) {
                        for (int x = 0; x < blocks4.length; x++) {
                            for (int z = 0; z < blocks4[0].length; z++) {
                                blocks4[x][index][z].rotateXYZ(0, 1, 0, (float) Math.PI / 240, clockWise);
                                ;
                            }
                        }
                        count++;
                        try {
                            Thread.sleep(8);
                        } catch (InterruptedException e) {
                            ;
                        }
                        if (count >= 120) {
                            setRotating(false);
                        }
                    }
                }

            }).start();
            for (int x = 0; x < blocks4.length; x++) {
                for (int z = 0; z < blocks4[0].length; z++) {
                    if (clockWise) {
                        temp[3 - z][x] = blocks4[x][index][z];
                    } else {
                        temp[z][3 - x] = blocks4[x][index][z];
                    }
                }
            }
            for (int x = 0; x < blocks4.length; x++) {
                for (int z = 0; z < blocks4[0].length; z++) {
                    blocks4[x][index][z] = temp[x][z];
                }
            }
        }
        if (selected.axis == Selected.Z) {
            new Thread(new Runnable() {
                int count = 0;

                @Override
                public void run() {
                    setRotating(true);
                    while (isRotating()) {
                        for (int x = 0; x < blocks4.length; x++) {
                            for (int y = 0; y < blocks4[0].length; y++) {
                                blocks4[x][y][index].rotateXYZ(0, 0, 1, (float) Math.PI / 240, clockWise);
                                ;
                            }
                        }
                        count++;
                        try {
                            Thread.sleep(8);
                        } catch (InterruptedException e) {
                            ;
                        }
                        if (count >= 120) {
                            setRotating(false);
                        }
                    }
                }

            }).start();
            for (int x = 0; x < blocks4.length; x++) {
                for (int y = 0; y < blocks4[0].length; y++) {
                    if (clockWise) {
                        temp[y][3 - x] = blocks4[x][y][index];
                    } else {
                        temp[3 - y][x] = blocks4[x][y][index];
                    }
                }
            }
            for (int x = 0; x < blocks4.length; x++) {
                for (int y = 0; y < blocks4[0].length; y++) {
                    blocks4[x][y][index] = temp[x][y];
                }
            }
        }
    }
}
