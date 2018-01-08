package io.appium.uiautomator2.server.test.handler;


import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Device;
import io.appium.uiautomator2.server.test.utils.Logger;

import static io.appium.uiautomator2.server.test.utils.API.API_18;

public class OpenNotification extends SafeRequestHandler {

    public OpenNotification(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        boolean isNotificationOpened;
        // method was only introduced in API Level 18
        if (!API_18) {
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, "Unable to open notifications on device below API level 18");
        }
        isNotificationOpened = Device.getUiDevice().openNotification();

        if (isNotificationOpened) {
            Logger.info("Opened Notification");
            return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, true);
        } else {
            Logger.info("Unable to Open Notification");
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, "Device failed to open notifications.");
        }
    }

}
