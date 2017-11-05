package io.appium.uiautomator2.server.test.handler;

import android.support.test.uiautomator.UiObjectNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import io.appium.uiautomator2.server.test.exceptions.ElementNotFoundException;
import io.appium.uiautomator2.server.test.exceptions.InvalidSelectorException;
import io.appium.uiautomator2.server.test.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.model.AndroidElement;
import io.appium.uiautomator2.server.test.model.KnownElements;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Logger;

public class Clear extends SafeRequestHandler {
    public Clear(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public AppiumResponse safeHandle(IHttpRequest request) {
        try {
            Logger.info("Clear element command");
            JSONObject payload = getPayload(request);
            AndroidElement element;
            if (payload.has("elementId")) {
                String id = payload.getString("elementId");
                element = KnownElements.getElementFromCache(id);
                if (element == null) {
                    return new AppiumResponse(getSessionId(request), WDStatus.NO_SUCH_ELEMENT);
                }
            } else {
                //perform action on focused element
                try {
                    element = KnownElements.geElement(android.support.test.uiautomator.By.focused(true), null /* by */);
                } catch (ElementNotFoundException e) {
                    Logger.debug("Error retrieving focused element: " + e);
                    return new AppiumResponse(getSessionId(request), WDStatus.NO_SUCH_ELEMENT);
                } catch (InvalidSelectorException e) {
                    Logger.error("Invalid selector: ", e);
                    return new AppiumResponse(getSessionId(request), WDStatus.INVALID_SELECTOR, e);
                }  catch ( UiAutomator2Exception | ClassNotFoundException e) {
                    Logger.debug("Error in finding focused element: " + e);
                    return new AppiumResponse(getSessionId(request), WDStatus.UNKNOWN_ERROR, "Unable to find a focused element." + e.getStackTrace());
                }
            }
            element.clear();
        } catch (UiObjectNotFoundException e) {
            Logger.error("Element not found: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.NO_SUCH_ELEMENT);
        } catch (JSONException e) {
            Logger.error("Exception while reading JSON: ", e);
            return new AppiumResponse(getSessionId(request), WDStatus.JSON_DECODER_ERROR, e);
        }
        return new AppiumResponse(getSessionId(request), WDStatus.SUCCESS, "Element Cleared");
    }
}
