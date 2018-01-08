package io.appium.uiautomator2.http;

public interface IHttpServlet {
    public void handleHttpRequest(IHttpRequest IHttpRequest, IHttpResponse httpResponse) throws Exception;
}
