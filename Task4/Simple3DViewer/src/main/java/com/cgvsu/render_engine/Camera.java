package com.cgvsu.render_engine;

import com.cgvsu.math.Vector.Vector3f;
import com.cgvsu.math.Matrix.Matrix4f;

public class Camera {

    private Vector3f position; // Позиция камеры
    private Vector3f target;   // Цель камеры
    private float fov;         // Поле зрения (угол)
    private float aspectRatio; // Соотношение сторон
    private float nearPlane;   // Ближняя плоскость отсечения
    private float farPlane;    // Дальняя плоскость отсечения

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public Camera(javax.vecmath.Vector3f vector3f, javax.vecmath.Vector3f vector3f1, float fov, int aspectRatio, float nearPlane, int farPlane) {
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(final Vector3f translation) {
        this.position = this.position.add(translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target = this.target.add(translation);
    }

    public Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4f getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public void movePosition(javax.vecmath.Vector3f vector3f) {
        
    }
}
