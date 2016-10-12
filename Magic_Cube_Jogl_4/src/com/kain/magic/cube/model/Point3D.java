package com.kain.magic.cube.model;


public class Point3D implements Cloneable {

    public float x;
    public float y;
    public float z;
    private float d;

    public Point3D() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Point3D(float x, float y, float z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void rotateX(float angle) {
        float sinAngle = (float) Math.sin(angle);
        float cosAngle = (float) Math.cos(angle);
        float y = this.y;
        float z = this.z;
        this.y = y * cosAngle - z * sinAngle;
        this.z = y * sinAngle + z * cosAngle;
    }

    public void rotateY(float angle) {
        float sinAngle = (float) Math.sin(angle);
        float cosAngle = (float) Math.cos(angle);
        float z = this.z;
        float x = this.x;
        this.z = z * cosAngle - x * sinAngle;
        this.x = z * sinAngle + x * cosAngle;
    }

    public void rotateZ(float angle) {
        float sinAngle = (float) Math.sin(angle);
        float cosAngle = (float) Math.cos(angle);
        float x = this.x;
        float y = this.y;
        this.x = x * cosAngle - y * sinAngle;
        this.y = x * sinAngle + y * cosAngle;
    }

    /**
     * @param angleX 绕X轴旋转的角度
     * @param angleY 绕Y轴旋转的角度
     */
    public void rotateXY(float angleX, float angleY) {
        rotateX(angleX);
        rotateY(angleY);
    }

    /**
     * 绕向量(0,0,0)-(x,y,z)旋转角度angle
     *
     * @param x
     * @param y
     * @param z
     * @param angle
     */
    public void rotateXYZ(float x, float y, float z, float angle) {
        float cosAngle = (float) Math.cos(angle);
        float sinAngle = (float) Math.sin(angle);
        float r = (float) Math.sqrt(x * x + y * y + z * z);
        float ax = x / r, ay = y / r, az = z / r;

        float m11 = ax * ax * (1 - cosAngle) + cosAngle;
        float m12 = ay * ax * (1 - cosAngle) + az * sinAngle;
        float m13 = az * ax * (1 - cosAngle) - ay * sinAngle;

        float m21 = ax * ay * (1 - cosAngle) - az * sinAngle;
        float m22 = ay * ay * (1 - cosAngle) + cosAngle;
        float m23 = az * ay * (1 - cosAngle) + ax * sinAngle;

        float m31 = ax * az * (1 - cosAngle) + ay * sinAngle;
        float m32 = ay * az * (1 - cosAngle) - ax * sinAngle;
        float m33 = az * az * (1 - cosAngle) + cosAngle;

        float x1 = this.x;
        float y1 = this.y;
        float z1 = this.z;
        this.x = x1 * m11 + y1 * m21 + z1 * m31;
        this.y = x1 * m12 + y1 * m22 + z1 * m32;
        this.z = x1 * m13 + y1 * m23 + z1 * m33;
    }

    @Override
    public Point3D clone() {
        Point3D point3D = new Point3D();
        try {
            point3D = (Point3D) super.clone();
            point3D.x = this.x;
            point3D.y = this.y;
            point3D.z = this.z;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return point3D;
    }
}
