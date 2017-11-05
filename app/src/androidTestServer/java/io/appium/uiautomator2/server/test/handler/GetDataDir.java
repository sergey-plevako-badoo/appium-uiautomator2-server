package io.appium.uiautomator2.server.test.handler;

import android.os.Environment;

import java.io.File;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Logger;

public class GetDataDir extends SafeRequestHandler {

    File dataDirectory;

    public GetDataDir(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {

        try {
            dataDirectory = Environment.getDataDirectory();
            Logger.info("data directory at " + dataDirectory);
        } catch (Exception e) {
            Logger.error("Exception while accessing data directory" + e);
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, e);
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, dataDirectory);
    }
}
