package cc.joysing.forestautoget.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.net.URISyntaxException;
import java.util.List;

import cc.joysing.forestautoget.util.Config;

public class AlipayForestMonitor {


    /**
     * 启动支付宝蚂蚁森林界面
     */
    public static void startAlipay(Context mContext) {
        Intent intent = null;
        try {
            intent = Intent.parseUri("alipays://platformapi/startapp?appId=20000067&url=https://60000002.h5app.alipay.com/app/src/home.html",
                    Intent.URI_INTENT_SCHEME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mContext.startActivity(intent);
    }
}
