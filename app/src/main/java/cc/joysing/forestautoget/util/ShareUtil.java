package cc.joysing.forestautoget.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class ShareUtil {

    private Context mContext;
    private String filename = null;

    public ShareUtil(Context context) {
        mContext = context;
    }

    private SharedPreferences getPreferences() {
        if (filename == null) {
            return PreferenceManager.getDefaultSharedPreferences(mContext);
        } else {
            return mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        }
    }

    private Editor getEditor() {
        if (filename == null) {
            return PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        } else {
            return mContext.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        }
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public boolean setShare(String key, boolean value) {
        return getEditor().putBoolean(key, value).commit();
    }

    public boolean setShare(String key, int value) {
        return getEditor().putInt(key, value).commit();
    }

}
