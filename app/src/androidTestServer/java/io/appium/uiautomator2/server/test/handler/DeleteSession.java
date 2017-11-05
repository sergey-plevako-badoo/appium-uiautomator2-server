package io.appium.uiautomator2.server.test.handler;

import android.support.test.InstrumentationRegistry;

import io.appium.uiautomator2.server.test.handler.request.SafeRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.model.NotificationListener;
import io.appium.uiautomator2.server.test.server.ServerConfig;
import io.appium.uiautomator2.server.test.server.ServerInstrumentation;
import io.appium.uiautomator2.server.test.server.WDStatus;
import io.appium.uiautomator2.server.test.utils.Logger;

public class DeleteSession extends SafeRequestHandler {

    public DeleteSession(String mappedUri) {
        super(mappedUri);
    }

    @Override

    public AppiumResponse safeHandle(IHttpRequest request) {
        Logger.info("Delete session command");
        String sessionId = getSessionId(request);
        NotificationListener.getInstance().stop();
        ServerInstrumentation.getInstance(InstrumentationRegistry.getInstrumentation().getContext(),
                ServerConfig.getServerPort()).stopServer();
        return new AppiumResponse(sessionId, WDStatus.SUCCESS, "Session deleted");
    }
}
