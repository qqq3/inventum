package org.asdtm.fas.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

public class AppUtils {

    public static boolean isNetworkAvailableAndConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] files = dir.list();
            for (String file : files) {
                boolean success = deleteDir(new File(dir, file));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else {
            return dir != null && dir.isFile() && dir.delete();
        }
    }

    public static double getDirSize(File dir) {
        long bytesSize = 0;
        if (dir != null && dir.exists()) {
            for (File file : dir.listFiles()) {
                if (file != null && file.isDirectory()) {
                    bytesSize += getDirSize(file);
                } else if (file != null && file.isFile()) {
                    bytesSize += file.length();
                }
            }

            return bytesSize / 1024.0 / 1024.0;
        }

        return bytesSize;
    }

    public static void copyToClipboard(Context context, CharSequence charSequence) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(charSequence, charSequence);
        clipboardManager.setPrimaryClip(clipData);
    }
}
