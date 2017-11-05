package io.appium.uiautomator2.server.test.handler;

import android.os.Environment;

import java.io.File;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Device;
import io.appium.uiautomator2.server.test.utils.Logger;

public class CaptureScreenshot extends SafeRequestHandler {

    public CaptureScreenshot(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        Logger.info("Capture screenshot command");
        boolean isActionPerformed;
        String actionMsg;
        final File screenshot = new File(Environment.getExternalStorageDirectory() + File.separator + "screenshot.png");
        screenshot.getParentFile().mkdirs();
        if (screenshot.exists()) {
            screenshot.delete();
        }
        isActionPerformed = Device.getUiDevice().takeScreenshot(screenshot);
        if (isActionPerformed) {
            actionMsg = "Captured Screen Successfully";
            Logger.info("ScreenShot captured at location: " + screenshot, actionMsg);
        } else {
            actionMsg = "Failed to capture Screen Shot";
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, actionMsg);
    }
}
