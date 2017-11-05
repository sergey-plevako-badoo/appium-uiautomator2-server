package io.appium.uiautomator2.server.test.handler;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;

public class Status extends SafeRequestHandler {

    public Status(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        return new AppiumResponse("SESSIONID", "Status Invoked");
    }
}
