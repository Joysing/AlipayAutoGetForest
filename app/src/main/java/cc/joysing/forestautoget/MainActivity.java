package cc.joysing.forestautoget;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import cc.joysing.forestautoget.service.AccessibilityServiceMonitor;
import cc.joysing.forestautoget.util.AccessibilitUtil;
import cc.joysing.forestautoget.util.Config;
import cc.joysing.forestautoget.util.ShareUtil;

public class MainActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener {

    private ShareUtil mShareUtil;

    private TimePicker timepick;

    private TextView timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initVaule();
        initListener();
        startService();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void initView() {
        timepick = findViewById(R.id.timepick);
        timeText = findViewById(R.id.time_text);
    }

    private void initVaule() {
        mShareUtil = new ShareUtil(this);
        timepick.setIs24HourView(true);
        timepick.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
    }

    private void initListener() {
        timepick.setOnTimeChangedListener(this);
    }

    private void updateUI() {
        int hour = mShareUtil.getInt(Config.KEY_HOUR, -1);
        int minute = mShareUtil.getInt(Config.KEY_MINUTE, -1);

        if (hour == -1 && minute == -1) {
            // do nothing
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timepick.setHour(hour);
                timepick.setMinute(minute);
            }
        }
    }

    private void startService() {
        Intent mIntent = new Intent(this, AccessibilityServiceMonitor.class);
        startService(mIntent);
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
        if (mShareUtil != null) {
            mShareUtil.setShare(Config.KEY_HOUR, hourOfDay);
            mShareUtil.setShare(Config.KEY_MINUTE, minute);
            timeText.setText(String.format("设置时间成功：%d:%d", hourOfDay, minute));
            MyApplication.startAlarmTask(MainActivity.this);
        }
    }
}
