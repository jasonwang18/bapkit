package com.supcon.mes.mbap.utils;

import android.os.Handler;
import android.os.Message;

import com.supcon.mes.mbap.view.CustomAdView;

import java.lang.ref.WeakReference;

/**
 * Created by wangshizhan on 2018/1/5.
 * Email:wangshizhan@supcon.com
 */

public class ImageCarouseHandler extends Handler {
    /**
     * 请求更新显示的View
     */
    public static final int MSG_UPDATE_IMAGE = 1;
    /**
     * 请求暂停轮播
     */
    public static final int MSG_KEEP_SILENT   = 2;
    /**
     * 请求恢复轮播。
     */
    public static final int MSG_BREAK_SILENT  = 3;
    /**
     * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
     * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
     * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
     */
    public static final int MSG_PAGE_CHANGED  = 4;
    //轮播间隔时间
    public static final long MSG_DELAY = 3000;

    //这里使用弱引用避免Handler泄露
    private WeakReference<CustomAdView> weakReference;
    private int currentItem = Integer.MAX_VALUE/2;
    private int itemSize = 0;

    public ImageCarouseHandler(WeakReference<CustomAdView> wk) {
        weakReference = wk;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        CustomAdView customAdView = weakReference.get();
        if (customAdView == null) {
            //HomeFrag已经回收，无需继续处理UI
            return;
        }

        if(itemSize==0){
            itemSize = customAdView.getItemCount();
        }
        //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
        /**
         * 这段会把第一次的自动轮播事件吃掉,所以可以加个条件,Position!=Max/2的时候才清除事件.因为第一次Position一定等于Max/2
         */

        if ((  customAdView.handler.hasMessages(MSG_UPDATE_IMAGE))&&(currentItem!=Integer.MAX_VALUE/2)){
            customAdView.handler.removeMessages(MSG_UPDATE_IMAGE);
        }
        switch (msg.what) {
            case MSG_UPDATE_IMAGE:
                currentItem++;
                if(currentItem >= itemSize){
                    currentItem = 0;
                }
                customAdView.ChanggeViewPagerCurrentItem(currentItem);
                //准备下次播放
                customAdView.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            case MSG_KEEP_SILENT:
                //只要不发送消息就暂停了
                break;
            case MSG_BREAK_SILENT:
                customAdView.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            case MSG_PAGE_CHANGED:
                //记录当前的页号，避免播放的时候页面显示不正确。
                currentItem = msg.arg1;
                break;
            default:
                break;
        }
    }
}