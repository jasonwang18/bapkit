package com.supcon.mes.mbap.utils;

import com.supcon.common.com_http.util.RxSchedulers;
import com.supcon.common.view.util.LogUtil;

import org.w3c.dom.ProcessingInstruction;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.http.PUT;

/**
 * Created by wangshizhan on 2018/3/16.
 * Email:wangshizhan@supcon.com
 */

public class CustomTimer {

    public static final int NORMAL = 0;
    public static final int INTERAL = NORMAL+1;
    public static final int MAINTHREAD = INTERAL+1;

    private static class CustomTimerHolder{
        private static CustomTimer instance = new CustomTimer();
    }


    public static CustomTimer instance() {
        return CustomTimerHolder.instance;
    }

    private Disposable timer;
    private OnTimerFinishListener mOnTimerFinishListener;
    private OnTimerIntervalListener mOnTimerIntervalListener;

    private int type = NORMAL;
    private int count = 0;

    private long time;
    private TimeUnit mTimeUnit;

    private CustomTimer() {

    }

    public void setOnTimerFinishListener(OnTimerFinishListener onTimerFinishListener) {
        mOnTimerFinishListener = onTimerFinishListener;
    }


    public CustomTimer listener(OnTimerFinishListener onTimerFinishListener){
        setOnTimerFinishListener(onTimerFinishListener);
        return this;
    }

    public CustomTimer main(){
        type = MAINTHREAD;
        return this;
    }

    public CustomTimer intervalListener(OnTimerIntervalListener onTimerIntervalListener){
        type = INTERAL;
        this.mOnTimerIntervalListener = onTimerIntervalListener;
        return this;
    }

    public CustomTimer start(long time, TimeUnit unit){
        this.time = time;
        this.mTimeUnit = unit;
        startTimer();
        return this;
    }

    public void stop(){
        stopTimer();
        if(mOnTimerFinishListener!=null){
            mOnTimerFinishListener = null;
        }
        if(mOnTimerIntervalListener!=null){
            mOnTimerFinishListener = null;
        }
    }

    private void startTimer() {
        LogUtil.i("CustomTimer startTimer");
        if(type == MAINTHREAD){
            timer = Flowable.timer(time, mTimeUnit)
                    .compose(RxSchedulers.io_main())
                    .subscribe(mFinishConsumer);
        }else {
            timer = Flowable.timer(time, mTimeUnit)
                    .subscribe(mFinishConsumer);
        }

    }

    private void stopTimer() {
        LogUtil.i("CustomTimer stopTimer");
        if(timer!=null){
            timer.dispose();
            timer = null;
        }
    }

    public void resetTimer(){
        LogUtil.i("CustomTimer resetTimer");
        stopTimer();
        startTimer();
    }


    public interface OnTimerFinishListener{
        void onTimerFinished();
    }

    public interface OnTimerIntervalListener{
        void onInterval(int count);
    }

    private Consumer<Long> mFinishConsumer = new Consumer<Long>() {
        @Override
        public void accept(Long aLong) throws Exception {
            if(type == INTERAL){
                count++;
                if(mOnTimerIntervalListener!=null){
                    mOnTimerIntervalListener.onInterval(count);
                    resetTimer();
                }
            }
            if(mOnTimerFinishListener!=null){
                mOnTimerFinishListener.onTimerFinished();
            }
        }
    };
}
