package io.appium.uiautomator2.server.test.handler;

import android.support.test.uiautomator.UiObjectNotFoundException;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.model.AndroidElement;
import io.appium.uiautomator2.server.test.model.KnownElements;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Logger;

public class GetText extends SafeRequestHandler {

    public GetText(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        Logger.info("Get Text of element command");
        String id = getElementId(request);
        String text;
        AndroidElement element = KnownElements.getElementFromCache(id);
        if (element == null) {
            return new AppiumResponse(getSessionId(request), WDStatus.NO_SUCH_ELEMENT);
        }
        try {
            text = element.getText();
            Logger.info("Get Text :" + text);
        } catch (UiObjectNotFoundException e) {
            Logger.error("Element not found: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.NO_SUCH_ELEMENT);
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, text);
    }

}