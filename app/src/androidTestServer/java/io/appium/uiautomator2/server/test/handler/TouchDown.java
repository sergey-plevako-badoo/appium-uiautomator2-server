package io.appium.uiautomator2.server.test.handler;

import io.appium.uiautomator2.server.test.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.server.test.core.UiAutomatorBridge;
import io.appium.uiautomator2.server.test.utils.Logger;

public class TouchDown extends TouchEvent {

    public TouchDown(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public boolean executeTouchEvent() throws UiAutomator2Exception {
        printEventDebugLine("TouchDown");
        try {
            boolean isTouchDownPerformed = UiAutomatorBridge.getInstance().getInteractionController().touchDown(clickX, clickY);
            return isTouchDownPerformed;
        } catch (Exception e) {
            Logger.error("Problem invoking touchDown: " + e);
            return false;
        }
    }
}
