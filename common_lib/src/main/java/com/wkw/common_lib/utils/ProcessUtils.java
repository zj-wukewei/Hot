package com.wkw.common_lib.utils;

import android.app.ActivityManager;
import android.content.Context;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by wukewei on 16/11/12.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public class ProcessUtils {

    private final static int[] EMPTY_INT_ARRAY = new int[0];

    private static volatile String sProcessName;
    private final static Object sNameLock = new Object();

    private static volatile Boolean sMainProcess;
    private final static Object sMainLock = new Object();

    private ProcessUtils() {
        // static usage.
    }

    /**
     * Returns the name of this process.
     *
     * @param context Application context.
     * @return The name of this process.
     */
    public static String myProcessName(Context context) {
        if (sProcessName != null) {
            return sProcessName;
        }
        synchronized (sNameLock) {
            if (sProcessName != null) {
                return sProcessName;
            }
            return sProcessName = obtainProcessName(context);
        }
    }

    /**
     * Check whether this process is the main process (it's name equals to {@link Context#getPackageName()}).
     *
     * @param context Application context.
     * @return Whether this process is the main process.
     */
    public static boolean isMainProcess(Context context) {
        if (sMainProcess != null) {
            return sMainProcess;
        }
        synchronized (sMainLock) {
            if (sMainProcess != null) {
                return sMainProcess;
            }
            final String processName = myProcessName(context);
            if (processName == null) {
                return false;
            }
            sMainProcess = processName.equals(context.getApplicationInfo().processName);
            return sMainProcess;
        }
    }

    /**
     * Kill this process itself.
     */
    public void killSelf() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * Kill all processes related this package.
     */
    public static void killAll(Context context) {
        killAll(context, EMPTY_INT_ARRAY);
    }

    /**
     * Kill all process related this package.
     *
     * @param excludePid exclude pid of processes.
     */
    public static void killAll(Context context, int... excludePid) {
        int myPid = android.os.Process.myPid();
        Collection<ActivityManager.RunningAppProcessInfo> runningProcess = collectRunningProcessInfo(context);
        Set<Integer> excludePidSet = collectUniqueSet(excludePid);
        // kill running process exclude required exception and self.
        if (runningProcess != null) {
            for (ActivityManager.RunningAppProcessInfo process : runningProcess) {
                if (excludePidSet != null && excludePidSet.contains(process.pid)) {
                    // exclude process.
                    continue;
                }
                if (myPid == process.pid) {
                    // my pid, ignore here.
                    continue;
                }
                android.os.Process.killProcess(process.pid);
            }
        }
        if (excludePidSet != null && excludePidSet.contains(myPid)) {
            // exclude self.
            return;
        }
        // kill self at last.
        android.os.Process.killProcess(myPid);
    }

    /**
     * Kill all process related this package.
     *
     * @param excludeName exclude name of processes.
     */
    public static void killAll(Context context, String... excludeName) {
        int myPid = android.os.Process.myPid();
        String myProcessName = null;
        Collection<ActivityManager.RunningAppProcessInfo> runningProcess = collectRunningProcessInfo(context);
        Set<String> excludeNameSet = collectUniqueSet(excludeName);
        // kill running process exclude required exception and self.
        if (runningProcess != null) {
            for (ActivityManager.RunningAppProcessInfo process : runningProcess) {
                if (excludeNameSet != null && excludeNameSet.contains(process.processName)) {
                    // exclude process.
                    continue;
                }
                if (myPid == process.pid) {
                    // my pid, ignore here.
                    myProcessName = process.processName;
                    continue;
                }
                android.os.Process.killProcess(process.pid);
            }
        }
        if (myProcessName != null && excludeNameSet != null && excludeNameSet.contains(myProcessName)) {
            // exclude self.
            return;
        }
        // kill self at last.
        android.os.Process.killProcess(myPid);
    }

    /**
     * Whether this process is foreground.
     *
     * @return true if foreground.
     */
    public static boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks == null || runningTasks.isEmpty()) {
            return false;
        }
        ActivityManager.RunningTaskInfo topTask = runningTasks.get(0);
        if (topTask == null) {
            return false;
        }
        String myPackageName = context.getPackageName();
        return (myPackageName.equals(topTask.baseActivity.getPackageName()))
            || (myPackageName.equals(topTask.topActivity.getPackageName()));
    }

    private static Collection<ActivityManager.RunningAppProcessInfo> collectRunningProcessInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Collection<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        // filter processes according to uid.
        if (runningProcesses != null) {
            int uid = android.os.Process.myUid();
            Iterator<ActivityManager.RunningAppProcessInfo> iterator = runningProcesses.iterator();
            while (iterator.hasNext()) {
                ActivityManager.RunningAppProcessInfo process = iterator.next();
                if (process == null || process.uid != uid) {
                    iterator.remove();
                }
            }
        }
        return runningProcesses;
    }

    private static <V> Set<V> collectUniqueSet(V... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Set<V> set = new HashSet<V>(values.length);
        for (V value : values) {
            set.add(value);
        }
        return set;
    }

    private static Set<Integer> collectUniqueSet(int... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        Set<Integer> set = new HashSet<Integer>(values.length);
        for (int value : values) {
            set.add(value);
        }
        return set;
    }

    private static String obtainProcessName(Context context) {
        final int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> listTaskInfo = am.getRunningAppProcesses();
        if (listTaskInfo != null && listTaskInfo.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo proc : listTaskInfo) {
                if (proc != null && proc.pid == pid) {
                    return proc.processName;
                }
            }
        }
        return null;
    }
}
