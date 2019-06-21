package com.supcon.mes.mbap.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.supcon.common.view.util.LogUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

public class ContactExecutor {
    private static final ContactExecutor INSTANCE = new ContactExecutor();
    //关键类，用以调度线程池中的线程
    private ExecutorService mExecutorService;

    private ContactExecutor() {
    }

    public static ContactExecutor getInstance() {
        return INSTANCE;
    }

    /**
     * 基于线程
     * @param runnable
     */
    public void runWorker(@NonNull Runnable runnable) {
        ensureWorkerHandlerNotNull();
        try {
            mExecutorService.execute(runnable);
        } catch (Exception e) {
            LogUtil.d("runnable stop running unexpected. " + e.getMessage());
        }
    }


    /**
     * 基于lambda
     * @param callable
     * @return
     */
    @Nullable
    public FutureTask<Boolean> runWorker(@NonNull Callable<Boolean> callable) {
        ensureWorkerHandlerNotNull();
        FutureTask<Boolean> task = null;
        try {
            task = new FutureTask<>(callable);
            mExecutorService.submit(task);
            return task;
        } catch (Exception e) {
            LogUtil.d("callable stop running unexpected. " + e.getMessage());
        }
        return task;
    }

    public void runUI(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        }
        Handler handler = ensureUiHandlerNotNull();
        try {
            handler.post(runnable);
        } catch (Exception e) {
            LogUtil.d("update UI task fail. " + e.getMessage());
        }
    }

    private void ensureWorkerHandlerNotNull() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool();
        }
    }

    private Handler ensureUiHandlerNotNull() {
        return new Handler(Looper.getMainLooper());
    }
}
