package com.summer.photos.collector;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuofa.liu on 17-12-2.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    private static Activity currActivity;

    public static void addActivity(Activity activity) {
        activities.add(activity);
        currActivity = activity;
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAllActivity() {
        for (Activity activity : activities) {
            if (!activity.isFinishing() ) {
                activity.finish();
            }
        }
    }
}
