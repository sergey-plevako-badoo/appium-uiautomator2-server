package io.appium.uiautomator2.server.test.handler;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.server.WDStatus;

import static io.appium.uiautomator2.server.test.utils.Device.back;

public class PressBack extends SafeRequestHandler {

    public PressBack(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        back();
        // Press back returns false even when back was successfully pressed.
        // Always return true.
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, true);
    }
}
