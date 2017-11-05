package io.appium.uiautomator2.server.test.handler;

import io.appium.uiautomator2.server.test.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.server.test.core.UiAutomatorBridge;
import io.appium.uiautomator2.server.test.utils.Logger;

public class TouchUp extends TouchEvent {

    public TouchUp(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public boolean executeTouchEvent() throws UiAutomator2Exception {
        printEventDebugLine("TouchUp");
        try {
            boolean isTouchUpPerformed = UiAutomatorBridge.getInstance().getInteractionController().touchUp(clickX, clickY);
            return isTouchUpPerformed;
        } catch (Exception e) {
            Logger.error("Problem invoking touchUp: " + e);
            return false;
        }
    }
}
