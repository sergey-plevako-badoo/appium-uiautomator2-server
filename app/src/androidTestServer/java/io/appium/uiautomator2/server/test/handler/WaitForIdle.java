package io.appium.uiautomator2.server.test.handler;

import org.json.JSONException;
import org.json.JSONObject;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Device;
import io.appium.uiautomator2.server.test.utils.Logger;

public class WaitForIdle extends SafeRequestHandler {

    public WaitForIdle(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {

        Integer timeout = 1000;
        try {
            JSONObject payload = getPayload(request);
            if (payload.has("timeout")) {
                timeout = Integer.parseInt(payload.getString("timeout"));
            }
            Device.waitForIdle(timeout);
        } catch (JSONException e) {
            Logger.error("Unable to get timeout value from the json payload", e);
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, e);
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, "Device waited");
    }
}
