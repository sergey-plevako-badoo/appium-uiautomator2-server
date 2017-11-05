package io.appium.uiautomator2.server.test.handler;

import io.appium.uiautomator2.server.test.exceptions.UiAutomator2Exception;
import io.appium.uiautomator2.server.test.core.UiAutomatorBridge;
import io.appium.uiautomator2.server.test.utils.Logger;

public class TouchMove extends TouchEvent {

    public TouchMove(String mappedUri) {
        super(mappedUri);
    }

    @Override
    public boolean executeTouchEvent() throws UiAutomator2Exception {
        printEventDebugLine("TouchMove");
        try {
            boolean isTouchMovePerformed = UiAutomatorBridge.getInstance().getInteractionController().touchMove(clickX, clickY);
            return isTouchMovePerformed;
        } catch (Exception e) {
            Logger.error("Problem invoking touchMove: " + e);
            return false;
        }
    }
}
