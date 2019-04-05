package com.supcon.mes.mbap.listener;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by wangshizhan on 2019/3/15
 * Email:wangshizhan@supcom.com
 */
public class ImageTouchListener implements View.OnTouchListener {

    private ImageView mImageView;


    //1声明：当前矩阵；
    private Matrix startMx = new Matrix();
    //2声明: 变化后矩阵：
    private Matrix changegMx = new Matrix();
    //3声明：双指按下时的点：
    private PointF startPF = new PointF();
    //4声明 ：双指按下时的 两指间的中点距离，也是缩放地1中心
    private PointF miPF = new PointF();
    //5声明：双指按下（开始）的的距离
    private float startDistance;
    //6声明 用来记录图片（矩阵）变化的标志量  来判断实现调用什么方法！
    /**
     * 当前模式
     * 0 初始状态
     * 1 移动状态
     * 2 缩放状态
     **/
    private int sign = 0;

    public ImageTouchListener(ImageView imageView){
        mImageView = imageView;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //判断 获取的 触摸动作 与 触摸点动作（标志）是 那个动作或者动作（标志）：
        /*
         *动作判断事件分别为：
         * 1： MotionEvent.ACTION_DOWN  触摸动作（单指）down
         * 2： MotionEvent.ACTION_MOVE  触摸动作（单指）move
         * 3： MotionEvent.ACTION_UP    触摸（单指）up
         * 4： MotionEvent.ACTION_POINTER_UP    触摸点的动作（标志）（双指）up
         * 5： MotionEvent.ACTION_POINTER_DOWN    触摸点的动作（标志）（双指）down
         * */
        switch (event.getAction() & MotionEvent.ACTION_MASK) {


            case MotionEvent.ACTION_DOWN:
                /*
                 * 标志量给1 将图片的矩阵赋给startMx  把开始的点的x，y值赋给startPF
                 * */
                sign = 1;
                startMx.set(mImageView.getImageMatrix());
                startPF.set(event.getX(), event.getY());


                break;
            case MotionEvent.ACTION_UP:
                //标志量给0
                sign = 0;


            case MotionEvent.ACTION_POINTER_UP:
                //标志量给0
                sign = 0;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //标志量给2  把开始的矩阵设置给变化后的矩阵  changegMx.set(startMx);
                // 获取点距给startDistance
                //获取开始的点与点 中点距给 miPF
                sign = 2;
                changegMx.set(startMx);
                startDistance = getDistance(event);
                miPF = getDistanceMid(event);
                break;
            case MotionEvent.ACTION_MOVE:
                //这里才是重点：
                //判断Sion 应该做什么
                if (sign == 1) {
                    //移动
                    //把开始的矩阵设置给变化后的矩阵
                    //获取当前 变化的x ，y 值设置到移动矩阵的方法中 changegMx.postTranslate(offx, offy);
                    //移动变化后的矩阵
                    float offx = event.getX() - startPF.x;
                    float offy = event.getY() - startPF.y;
                    changegMx.set(startMx);
                    changegMx.postTranslate(offx, offy);
                } else if (sign == 2) {
                    //缩放
                    //把开始的矩阵设置给变化后的矩阵
                    //获取当前的移动距离 给现在的dismove
                    //计算缩放倍数  现在的距离/以前的距离=倍数=scale
                    //按倍数 （x，y）的倍数与 x点，y点缩放 changegMx.postScale(scale, scale, miPF.x, miPF.y);
                    float dismove = getDistance(event);
                    float scale = dismove / startDistance;
                    changegMx.set(startMx);
                    changegMx.postScale(scale, scale, miPF.x, miPF.y);
                }
                break;




        }
        //设置图片的矩阵为变化后的矩阵


        mImageView.setImageMatrix(changegMx);


        return true;
    }

    /*得到 点（两）与 点之间的直线距离*/
    public float getDistance(MotionEvent event) {
        float disX = event.getX(1) - event.getX(0);
        float disy = event.getY(1) - event.getY(0);
        double sqrt = Math.sqrt((disX * disX) + (disy * disy));
        return (float) sqrt;
    }


    /*得到点（两）与 点的中点坐标*/
    public PointF getDistanceMid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midy = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midy);
    }

}
