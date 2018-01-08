package io.appium.uiautomator2.server.test.handler;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.model.AllowInvisibleElements;
import io.appium.uiautomator2.server.test.model.CompressedLayoutHierarchy;
import io.appium.uiautomator2.server.test.model.Session;
import io.appium.uiautomator2.server.test.model.WaitForIdleTimeout;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Logger;
import java.util.Map;

public class UpdateSettings extends SafeRequestHandler {

    public UpdateSettings(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {

        try {
            Map<String, Object> settings = getPayload(request, "settings");
            Session.capabilities.putAll(settings);
            Logger.debug("Update settings: " + settings.toString());

            if (settings.containsKey(AllowInvisibleElements.SETTING_NAME)) {
                AllowInvisibleElements.updateSetting((boolean) settings.get(AllowInvisibleElements.SETTING_NAME));
            }

            if (settings.containsKey(CompressedLayoutHierarchy.SETTING_NAME)) {
                CompressedLayoutHierarchy.updateSetting((boolean) settings.get(CompressedLayoutHierarchy.SETTING_NAME));
            }

            if (settings.containsKey(WaitForIdleTimeout.SETTING_NAME)) {
                WaitForIdleTimeout.updateSetting((int) settings.get(WaitForIdleTimeout.SETTING_NAME));
            }
        } catch (Exception e) {
            Logger.error("error settings " + e.getMessage());
            return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, e);
        }

        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, true);
    }

}
