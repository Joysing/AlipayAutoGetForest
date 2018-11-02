package cc.joysing.forestautoget.service;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import de.robv.android.xposed.XposedBridge;

public class AlipayForestMonitor {
    /**
     * 启动支付宝蚂蚁森林界面
     */
    public static void startAlipay(final Context mContext, int delay) {
        Toast.makeText(mContext, "正在打开蚂蚁森林",Toast.LENGTH_LONG).show();
        Intent intent = null;
        try {
            intent = Intent.parseUri("alipays://platformapi/startapp?appId=20000067&url=https://60000002.h5app.alipay.com/app/src/home.html",
                    Intent.URI_INTENT_SCHEME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        XposedBridge.log(delay/1000+"秒后打开蚂蚁森林");
        final Intent finalIntent = intent;
        new Timer().schedule(new TimerTask() {
            public void run() {
                XposedBridge.log("正在打开蚂蚁森林");
                mContext.startActivity(finalIntent);
            }
        }, delay);

    }
}
