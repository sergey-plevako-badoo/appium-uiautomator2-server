package io.appium.uiautomator2.server.test.model;

import android.support.test.uiautomator.BySelector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.appium.uiautomator2.server.test.exceptions.ElementNotFoundException;
import io.appium.uiautomator2.server.test.exceptions.InvalidSelectorException;
import io.appium.uiautomator2.server.test.exceptions.UiAutomator2Exception;

import static io.appium.uiautomator2.server.test.model.internal.CustomUiDevice.getInstance;
import static io.appium.uiautomator2.server.test.utils.Device.getAndroidElement;

public class KnownElements {
    private static Map<String, AndroidElement> cache = new HashMap<String, AndroidElement>();

    private static String getCacheKey(AndroidElement element) {
        for (Map.Entry<String, AndroidElement> entry : cache.entrySet()) {
            if (entry.getValue().equals(element)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String getIdOfElement(AndroidElement element) {
        if (cache.containsValue(element)) {
            return getCacheKey(element);
        }
        return null;
    }

    public static AndroidElement getElementFromCache(String id) {
        return cache.get(id);
    }

    /**
     *
     * @param ui2BySelector, for finding {@link android.support.test.uiautomator.UiObject2} element derived using {@link By}
     * @param by, user provided selector criteria from appium client.
     * @return
     */
    public static AndroidElement geElement(final BySelector ui2BySelector, By by) throws ElementNotFoundException, InvalidSelectorException, UiAutomator2Exception, ClassNotFoundException {
        Object ui2Object = getInstance().findObject(ui2BySelector);
        if (ui2Object == null) {
            throw new ElementNotFoundException();
        }
        String id = UUID.randomUUID().toString();
        AndroidElement androidElement = getAndroidElement(id, ui2Object, by);
        cache.put(androidElement.getId(), androidElement);
        return androidElement;
    }

    public String add(AndroidElement element) {
        if (cache.containsValue(element)) {
            return getCacheKey(element);
        }
        cache.put(element.getId(), element);
        return element.getId();
    }

    public void clear() {
        if (!cache.isEmpty()) {
            cache.clear();
            System.gc();
        }

    }
}
