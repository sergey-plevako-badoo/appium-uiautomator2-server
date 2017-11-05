package io.appium.uiautomator2.server.test.server;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.appium.uiautomator2.server.test.handler.AppStrings;
import io.appium.uiautomator2.server.test.handler.CaptureScreenshot;
import io.appium.uiautomator2.server.test.handler.Clear;
import io.appium.uiautomator2.server.test.handler.Click;
import io.appium.uiautomator2.server.test.handler.CompressedLayoutHierarchy;
import io.appium.uiautomator2.server.test.handler.DeleteSession;
import io.appium.uiautomator2.server.test.handler.Drag;
import io.appium.uiautomator2.server.test.handler.FindElement;
import io.appium.uiautomator2.server.test.handler.FindElements;
import io.appium.uiautomator2.server.test.handler.Flick;
import io.appium.uiautomator2.server.test.handler.GetDeviceSize;
import io.appium.uiautomator2.server.test.handler.GetElementAttribute;
import io.appium.uiautomator2.server.test.handler.GetName;
import io.appium.uiautomator2.server.test.handler.GetRotation;
import io.appium.uiautomator2.server.test.handler.GetScreenOrientation;
import io.appium.uiautomator2.server.test.handler.GetSize;
import io.appium.uiautomator2.server.test.handler.GetText;
import io.appium.uiautomator2.server.test.handler.Location;
import io.appium.uiautomator2.server.test.handler.LongPressKeyCode;
import io.appium.uiautomator2.server.test.handler.MultiPointerGesture;
import io.appium.uiautomator2.server.test.handler.NetworkConnection;
import io.appium.uiautomator2.server.test.handler.NewSession;
import io.appium.uiautomator2.server.test.handler.OpenNotification;
import io.appium.uiautomator2.server.test.handler.PressBack;
import io.appium.uiautomator2.server.test.handler.PressKeyCode;
import io.appium.uiautomator2.server.test.handler.RotateScreen;
import io.appium.uiautomator2.server.test.handler.ScrollTo;
import io.appium.uiautomator2.server.test.handler.SendKeysToElement;
import io.appium.uiautomator2.server.test.handler.Source;
import io.appium.uiautomator2.server.test.handler.Status;
import io.appium.uiautomator2.server.test.handler.Swipe;
import io.appium.uiautomator2.server.test.handler.TouchDown;
import io.appium.uiautomator2.server.test.handler.TouchLongClick;
import io.appium.uiautomator2.server.test.handler.TouchMove;
import io.appium.uiautomator2.server.test.handler.TouchUp;
import io.appium.uiautomator2.server.test.handler.request.BaseRequestHandler;
import io.appium.uiautomator2.server.test.http.AppiumResponse;
import io.appium.uiautomator2.server.test.http.IHttpRequest;
import io.appium.uiautomator2.server.test.http.IHttpResponse;
import io.appium.uiautomator2.server.test.http.IHttpServlet;


public class AppiumServlet implements IHttpServlet {

    public static final String SESSION_ID_KEY = "SESSION_ID_KEY";

    public static final String ELEMENT_ID_KEY = "id";
    public static final String COMMAND_NAME_KEY = "COMMAND_KEY";
    public static final String NAME_ID_KEY = "NAME_ID_KEY";
    protected static ConcurrentMap<String, BaseRequestHandler> getHandler = new ConcurrentHashMap<String, BaseRequestHandler>();
    protected static ConcurrentMap<String, BaseRequestHandler> postHandler = new ConcurrentHashMap<String, BaseRequestHandler>();
    protected static ConcurrentMap<String, BaseRequestHandler> deleteHandler = new ConcurrentHashMap<String, BaseRequestHandler>();
    private ConcurrentMap<String, String[]> mapperUrlSectionsCache = new ConcurrentHashMap<>();


    public AppiumServlet() {
        init();
    }

    private void init() {
        registerGetHandler();
        registerPostHandler();
        registerDeleteHandler();
    }

    private void registerDeleteHandler() {
        register(deleteHandler, new DeleteSession("/wd/hub/session/:sessionId"));
    }

    private void registerPostHandler() {
        register(postHandler, new NewSession("/wd/hub/session"));
        register(postHandler, new FindElement("/wd/hub/session/:sessionId/element"));
        register(postHandler, new FindElements("/wd/hub/session/:sessionId/elements"));
        register(postHandler, new Click("/wd/hub/session/:sessionId/element/:id/click"));
        register(postHandler, new Click("/wd/hub/session/:sessionId/appium/tap"));
        register(postHandler, new Clear("/wd/hub/session/:sessionId/element/:id/clear"));
        register(postHandler, new RotateScreen("/wd/hub/session/:sessionId/orientation"));
        register(postHandler, new RotateScreen("/wd/hub/session/:sessionId/rotation"));
        register(postHandler, new PressBack("/wd/hub/session/:sessionId/back"));
        register(postHandler, new SendKeysToElement("/wd/hub/session/:sessionId/element/:id/value"));
        register(postHandler, new SendKeysToElement("/wd/hub/session/:sessionId/keys"));
        register(postHandler, new Swipe("/wd/hub/session/:sessionId/touch/perform"));
        register(postHandler, new TouchLongClick("/wd/hub/session/:sessionId/touch/longclick"));
        register(postHandler, new OpenNotification("/wd/hub/session/:sessionId/appium/device/open_notifications"));
        register(postHandler, new PressKeyCode("/wd/hub/session/:sessionId/appium/device/press_keycode"));
        register(postHandler, new LongPressKeyCode("/wd/hub/session/:sessionId/appium/device/long_press_keycode"));
        register(postHandler, new Drag("/wd/hub/session/:sessionId/touch/drag"));
        register(postHandler, new AppStrings("/wd/hub/session/:sessionId/appium/app/strings"));
        register(postHandler, new Flick("/wd/hub/session/:sessionId/touch/flick"));
        register(postHandler, new ScrollTo("/wd/hub/session/:sessionId/touch/scroll"));
        register(postHandler, new MultiPointerGesture("/wd/hub/session/:sessionId/touch/multi/perform"));
        register(postHandler, new TouchDown("/wd/hub/session/:sessionId/touch/down"));
        register(postHandler, new TouchUp("/wd/hub/session/:sessionId/touch/up"));
        register(postHandler, new TouchMove("/wd/hub/session/:sessionId/touch/move"));
        register(postHandler, new CompressedLayoutHierarchy("/wd/hub/session/:sessionId/appium/device/compressedLayoutHierarchy"));
        register(postHandler, new NetworkConnection("/wd/hub/session/:sessionId/network_connection"));
    }

    private void registerGetHandler() {
        register(getHandler, new Status("/wd/hub/status"));
        register(getHandler, new CaptureScreenshot("/wd/hub/session/:sessionId/screenshot"));
        register(getHandler, new GetScreenOrientation("/wd/hub/session/:sessionId/orientation"));
        register(getHandler, new GetRotation("/wd/hub/session/:sessionId/rotation"));
        register(getHandler, new GetText("/wd/hub/session/:sessionId/element/:id/text"));
        register(getHandler, new GetElementAttribute("/wd/hub/session/:sessionId/element/:id/attribute/:name"));
        register(getHandler, new GetSize("/wd/hub/session/:sessionId/element/:id/size"));
        register(getHandler, new GetName("/wd/hub/session/:sessionId/element/:id/name"));
        register(getHandler, new Location("/wd/hub/session/:sessionId/element/:id/location"));
        register(getHandler, new GetDeviceSize("/wd/hub/session/:sessionId/window/:windowHandle/size"));
        register(getHandler, new Source("/wd/hub/session/:sessionId/source"));
    }

    protected void register(Map<String, BaseRequestHandler> registerOn, BaseRequestHandler handler) {
        registerOn.put(handler.getMappedUri(), handler);
    }

    protected BaseRequestHandler findMatcher(IHttpRequest request, Map<String, BaseRequestHandler> handler) {
        String[] urlToMatchSections = getRequestUrlSections(request.uri());
        for (Map.Entry<String, ? extends BaseRequestHandler> entry : handler.entrySet()) {
            String[] mapperUrlSections = getMapperUrlSectionsCached(entry.getKey());
            if (isFor(mapperUrlSections, urlToMatchSections)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private String[] getRequestUrlSections(String urlToMatch) {
        if (urlToMatch == null) {
            return null;
        }
        int qPos = urlToMatch.indexOf('?');
        if (qPos != -1) {
            urlToMatch = urlToMatch.substring(0, urlToMatch.indexOf("?"));
        }
        return urlToMatch.split("/");
    }

    private String[] getMapperUrlSectionsCached(String mapperUrl) {
        String[] sections = mapperUrlSectionsCache.get(mapperUrl);
        if (sections == null) {
            sections = mapperUrl.split("/");
            for (int i = 0; i < sections.length; i++) {
                String section = sections[i];
                // To work around a but in Selenium Grid 2.31.0.
                int qPos = section.indexOf('?');
                if (qPos != -1) {
                    sections[i] = section.substring(0, qPos);
                }
            }
            mapperUrlSectionsCache.put(mapperUrl, sections);
        }
        return sections;
    }

    protected boolean isFor(String[] mapperUrlSections, String[] urlToMatchSections) {
        if (urlToMatchSections == null) {
            return mapperUrlSections.length == 0;
        }
        if (mapperUrlSections.length != urlToMatchSections.length) {
            return false;
        }
        for (int i = 0; i < mapperUrlSections.length; i++) {
            if (!(mapperUrlSections[i].startsWith(":") || mapperUrlSections[i].equals(urlToMatchSections[i]))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void handleHttpRequest(IHttpRequest request, IHttpResponse response) {
        BaseRequestHandler handler = null;
        if ("GET".equals(request.method())) {
            handler = findMatcher(request, getHandler);
        } else if ("POST".equals(request.method())) {
            handler = findMatcher(request, postHandler);
        } else if ("DELETE".equals(request.method())) {
            handler = findMatcher(request, deleteHandler);
        }

        handleRequest(request, response, handler);
    }

    public void handleRequest(IHttpRequest request, IHttpResponse response, BaseRequestHandler handler) {
        if (handler == null) {
            response.setStatus(HttpStatusCode.NOT_FOUND.getStatusCode()).end();
            return;
        }
        addHandlerAttributesToRequest(request, handler.getMappedUri());
        AppiumResponse result = handler.handle(request);
        handleResponse(request, response, result);
    }

    protected void handleResponse(IHttpRequest request, IHttpResponse response, AppiumResponse result) {
        if (result != null) {
            String resultString = result.render();
            response.setContentType("application/json");
            response.setEncoding(Charset.forName("UTF-8"));
            response.setContent(resultString);
            try {
                if (new JSONObject(resultString).getInt("status") == 0) {
                    response.setStatus(HttpStatusCode.OK.getStatusCode());
                } else {
                    response.setStatus(HttpStatusCode.INTERNAL_SERVER_ERROR.getStatusCode());
                }
            } catch (JSONException e) {
                response.setStatus(HttpStatusCode.INTERNAL_SERVER_ERROR.getStatusCode());
            }
        }
        response.end();
    }

    private void addHandlerAttributesToRequest(IHttpRequest request, String mappedUri) {
        String sessionId = getParameter(mappedUri, request.uri(), ":sessionId");
        if (sessionId != null) {
            request.data().put(SESSION_ID_KEY, sessionId);
        }

        String command = getParameter(mappedUri, request.uri(), ":command");
        if (command != null) {
            request.data().put(COMMAND_NAME_KEY, command);
        }

        String id = getParameter(mappedUri, request.uri(), ":id");
        if (id != null) {
            request.data().put(ELEMENT_ID_KEY, URLDecoder.decode(id));
        }
        String name = getParameter(mappedUri, request.uri(), ":name");
        if (name != null) {
            request.data().put(NAME_ID_KEY, name);
        }

        //request.data().put(DRIVER_KEY, driver);
    }


    protected String getParameter(String configuredUri, String actualUri, String param) {
        return getParameter(configuredUri, actualUri, param, true);
    }

    protected String getParameter(String configuredUri, String actualUri, String param, boolean sectionLengthValidation) {
        String[] configuredSections = configuredUri.split("/");
        String[] currentSections = actualUri.split("/");
        if (sectionLengthValidation) {
            if (configuredSections.length != currentSections.length) {
                return null;
            }
        }
        for (int i = 0; i < currentSections.length; i++) {
            if (configuredSections[i].contains(param)) {
                return currentSections[i];
            }
        }
        return null;
    }
}
