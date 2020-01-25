package ru.samsung.itschool.funnybirds;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 10.11.2015.
 */
public class Sprite {
    private Bitmap bitmap; // Ссылка на изображение с набором кадров

    private List<Rect> frames; // Кадры, которые участвуют в анимационной последовательности
    private int frameWidth;
    private int frameHeight;
    private int currentFrame; // Номер текущего кадра в коллекции frames
    private double frameTime; // Время, отведенное на отображение каждого кадра анимационной последовательности
    private double timeForCurrentFrame; // 	Текущее время отображения кадра.

    private double x; // Местоположение левого верхнего угла спрайта на экране
    private double y;

    private double velocityX; // Скорости перемещения спрайта по оси X и Y соответственно
    private double velocityY;

    private int padding; // Внутренний отступ от границ спрайта,
    // необходимый для более точного определения пересечений спрайтов

    public Sprite(double x,
                  double y,
                  double velocityX,
                  double velocityY,
                  Rect initialFrame,
                  Bitmap bitmap) {

        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

        this.bitmap = bitmap;

        this.frames = new ArrayList<>();
        this.frames.add(initialFrame);

        this.bitmap = bitmap;

        this.timeForCurrentFrame = 0.0;
        this.frameTime = 25;
        this.currentFrame = 0;

        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();

        this.padding = 20;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public double getVx() {
        return velocityX;
    }

    public void setVx(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVy() {
        return velocityY;
    }

    public void setVy(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame % frames.size();
    }

    public double getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(double frameTime) {
        this.frameTime = Math.abs(frameTime);
    }

    public double getTimeForCurrentFrame() {
        return timeForCurrentFrame;
    }

    public void setTimeForCurrentFrame(double timeForCurrentFrame) {
        this.timeForCurrentFrame = Math.abs(timeForCurrentFrame);
    }

    // добавления кадров в анимационную последовательность
    /*  Добавление кадров в последовательность анимации заключается
        в добавлении соответствующей кадру прямоугольной
        области на изображении, заданной с помощью объекта Rect (прямоугольник).
    */
    public void addFrame(Rect frame) {
        frames.add(frame);
    }

    public int getFramesCount() {
        return frames.size();
    }

    //Метод update() вызывается таймером с определенной периодичностью.
    public void update(int ms) { //время в миллисекундах, прошедшее с момента последнего вызова этого метода
        timeForCurrentFrame += ms;

        if (timeForCurrentFrame >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.size();
            timeForCurrentFrame = timeForCurrentFrame - frameTime;
        }

        x += velocityX * ms / 1000.0;
        y += velocityY * ms / 1000.0;
    }

    //Метод draw() рисует на переданном холсте текущий кадр frames.get(currentFrame)
    // в области экрана, заданной в прямоугольнике destination.
    public void draw(Canvas canvas) {
        Rect destination = new Rect((int) x, (int) y, (int) (x + frameWidth), (int) (y + frameHeight));
        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination, new Paint());
    }

    public Rect getBoundingBoxRect() {
        // прямоугольник с указанными координатами.
        return new Rect((int) x + padding,
                (int) y + padding,
                (int) (x + frameWidth - padding),
                (int) (y + frameHeight - padding));
    }


    public boolean intersect(Sprite s) {
        return getBoundingBoxRect().intersect(s.getBoundingBoxRect());
    }

}
