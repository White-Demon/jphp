package php.runtime.ext.net;

import php.runtime.annotation.Reflection;
import php.runtime.annotation.Reflection.*;
import php.runtime.env.Environment;
import php.runtime.ext.NetExtension;
import php.runtime.lang.BaseWrapper;
import php.runtime.reflection.ClassEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.List;
import java.util.Map;

@Name(NetExtension.NAMESPACE + "URLConnection")
public class WrapURLConnection extends BaseWrapper<URLConnection> {
    interface WrappedInterface {
        @Property int connectTimeout();
        @Property int readTimeout();
        @Property("url") URL URL();

        @Property boolean doOutput();
        @Property boolean doInput();
        @Property boolean useCaches();

        @Property long ifModifiedSince();

        @Property("contentLength") long contentLengthLong();
        @Property String contentType();
        @Property String contentEncoding();
        @Property long expiration();
        @Property long date();
        @Property long lastModified();

        Map<String,List<String>> getHeaderFields();
        String getHeaderField(String name);

        void connect();
        void setRequestProperty(String key, String value);
        String getRequestProperty(String key);
        Map<String,List<String>> getRequestProperties();

        OutputStream getOutputStream();
    }

    public WrapURLConnection(Environment env, URLConnection wrappedObject) {
        super(env, wrappedObject);
    }

    public WrapURLConnection(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    @Signature
    protected void __construct(WrapURLConnection connection) {
        __wrappedObject = connection.getWrappedObject();
    }

    @Signature
    public void disconnect() {
        ((HttpURLConnection)getWrappedObject()).disconnect();
    }

    @Getter
    public int getResponseCode() throws IOException {
        return ((HttpURLConnection)getWrappedObject()).getResponseCode();
    }

    @Getter
    public String getResponseMessage() throws IOException {
        return ((HttpURLConnection)getWrappedObject()).getResponseMessage();
    }

    @Getter
    public boolean getFollowRedirects() throws IOException {
        return ((HttpURLConnection)getWrappedObject()).getInstanceFollowRedirects();
    }

    @Setter
    public void setFollowRedirects(boolean value) throws IOException {
        ((HttpURLConnection)getWrappedObject()).setInstanceFollowRedirects(value);
    }

    @Signature
    public void setChunkedStreamingMode(int chunklen) throws IOException {
        ((HttpURLConnection)getWrappedObject()).setChunkedStreamingMode(chunklen);
    }

    @Setter
    public void setRequestMethod(String method) throws IOException {
        ((HttpURLConnection)getWrappedObject()).setRequestMethod(method);
    }

    @Getter
    public String getRequestMethod() {
        return ((HttpURLConnection)getWrappedObject()).getRequestMethod();
    }

    @Getter
    public boolean isUsingProxy() {
        return ((HttpURLConnection)getWrappedObject()).usingProxy();
    }

    @Signature
    public InputStream getInputStream() throws IOException {
        return getWrappedObject().getInputStream();
    }

    @Signature
    public InputStream getErrorStream() throws IOException {
        return ((HttpURLConnection) getWrappedObject()).getErrorStream();
    }

    @Signature
    public static URLConnection create(String url, @Nullable Proxy proxy) throws IOException {
        URL _url = new URL(url);
        URLConnection urlConnection = proxy == null ? _url.openConnection() : _url.openConnection(proxy);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);

        if (urlConnection instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
        }

        return urlConnection;
    }

    @Signature
    public static URLConnection create(String url) throws IOException {
        return create(url, null);
    }
}
