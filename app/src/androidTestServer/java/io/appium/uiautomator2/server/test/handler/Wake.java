package io.appium.uiautomator2.server.test.handler;

import android.os.RemoteException;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Logger;

import static io.appium.uiautomator2.server.test.utils.Device.wake;

public class Wake extends SafeRequestHandler {

    public Wake(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        try {
            wake();
        } catch (RemoteException e) {
            Logger.error("Error waking up device");
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, e);
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, "Wake up Device");
    }
}
