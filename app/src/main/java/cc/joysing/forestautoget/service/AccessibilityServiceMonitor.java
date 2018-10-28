package cc.joysing.forestautoget.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import cc.joysing.forestautoget.MyApplication;
import cc.joysing.forestautoget.util.Config;
import cc.joysing.forestautoget.util.DateTimeUtil;
import cc.joysing.forestautoget.util.ShareUtil;

public class AccessibilityServiceMonitor extends AccessibilityService {

    private static final String TAG = AccessibilityServiceMonitor.class.getSimpleName();

    public static final String ACTION_ALAM_TIMER= "action_alarm_timer";

    private boolean isNewday;

    /**
     * 支付宝 App 辅助功能
     */

    private H mHandle = new H();
    private static final int MSG_DELAY_ENTER_FOREST = 0;
    private static final int MSG_DELAY_ENTER_LIANGTONG = 1;
    private static final int DEFAULT_DELAY_TIME = 1000;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        MyBroadCast myBroadCast = new MyBroadCast();
        myBroadCast.init(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startUI();
        if (null == intent) {
            return super.onStartCommand(null, flags, startId);
        }

        String action = intent.getAction();
        Log.d(TAG, "onStartCommand Aciton: " + action);

        if (ACTION_ALAM_TIMER.equals(action)) {
            MyApplication.startAlarmTask(this);
            startUI();
        }

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.packageNames = new String[]{"com.eg.android.AlipayGphone"};// 监控的app
        serviceInfo.notificationTimeout = 100;
        serviceInfo.flags = serviceInfo.flags | AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY;
        setServiceInfo(serviceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {

    }

    private static class H extends Handler {

        H() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_DELAY_ENTER_FOREST:
                    break;
            }
        }
    }

    class MyBroadCast extends BroadcastReceiver {

        public void init(Context mContext) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_USER_PRESENT);
            mContext.registerReceiver(this, intentFilter);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (null == intent) {
                return;
            }

            String action = intent.getAction();
            Log.d(Config.TAG,"action = " + action);

            if (Intent.ACTION_USER_PRESENT.equals(action)) {
                isNewday = isNewDay();
//                if (isNewday) {
                    startUI();
//                }
            }
        }
    }

    /**
     * 判断是否新的一天
     */
    private boolean isNewDay() {
        boolean result = false;

        ShareUtil mShareUtil = new ShareUtil(this);
        int saveDay = mShareUtil.getInt(Config.KEY_NEW_DAY, -1);
        int curDay = DateTimeUtil.getDayOfYear();

        if (saveDay != curDay) {
            result = true;
            mShareUtil.setShare(Config.KEY_NEW_DAY, curDay);
        }

        Log.d(Config.TAG, "isNewDay = " + result);
        return result;
    }


    /**
     * 启动UI界面
     */
    public void startUI() {
        startAlipayUI();
    }

    private void startAlipayUI() {
        AlipayForestMonitor.startAlipay(this);
        mHandle.sendEmptyMessageDelayed(MSG_DELAY_ENTER_LIANGTONG, DEFAULT_DELAY_TIME * 10);
    }
}
