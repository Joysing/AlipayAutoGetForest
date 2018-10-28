package cc.joysing.forestautoget.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Locale;

public class AlarmTaskUtil {

    /**
     * RepeatAlarmTask
     */
    public static void starRepeatAlarmTaskByService(Context context, int hour, int minute, long intervalMillis, Intent intent) {
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        }

        long triggerAtMillis = calendar.getTimeInMillis();

        PendingIntent operation;
        operation = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (mAlarmManager != null) {
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
        }
    }

}
