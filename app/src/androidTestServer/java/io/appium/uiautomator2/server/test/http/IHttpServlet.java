package io.appium.uiautomator2.server.test.http;

public interface IHttpServlet {
    public void handleHttpRequest(IHttpRequest IHttpRequest, IHttpResponse httpResponse) throws Exception;
}
