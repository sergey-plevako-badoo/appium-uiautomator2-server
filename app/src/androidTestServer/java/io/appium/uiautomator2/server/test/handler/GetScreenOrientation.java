package io.appium.uiautomator2.server.test.handler;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.model.ScreenOrientation;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Device;

public class GetScreenOrientation extends SafeRequestHandler {

    public GetScreenOrientation(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        ScreenOrientation orientation;
        int rotation = Device.getUiDevice().getDisplayRotation();
        if (rotation == 1 || rotation == 3) {
            orientation = ScreenOrientation.LANDSCAPE;
        } else {
            orientation = ScreenOrientation.PORTRAIT;
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, orientation);
    }
}
