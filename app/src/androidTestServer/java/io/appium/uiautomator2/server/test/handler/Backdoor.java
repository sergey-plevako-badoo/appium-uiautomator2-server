package io.appium.uiautomator2.server.test.handler;

import android.app.Activity;
import android.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.appium.uiautomator2.server.test.core.InvocationOperation;
import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Logger;

/**
 * Created by rajdeepvarma on 07/11/2017.
 */

public class Backdoor extends SafeRequestHandler {

    public Backdoor(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        Logger.info("Invoking Backdoor");

        Activity activity = null;
        Class activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            ArrayMap activities = (ArrayMap) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    activity = (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object invocationResult = null;

        try {
            String methodName = getPayload(request).getString("method_name");

            List<String> arguments = new ArrayList<String>();
            JSONArray jsonArray = null;
            try {
                jsonArray = getPayload(request).getJSONArray("args");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    arguments.add(jsonArray.get(i).toString());
                }
            }
            InvocationOperation operation = new InvocationOperation(methodName, arguments);
            invocationResult = operation.apply(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, invocationResult);
    }
}
