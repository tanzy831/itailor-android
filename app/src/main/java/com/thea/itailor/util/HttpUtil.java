package com.thea.itailor.util;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * Created by hunter on 2015/6/24.
 */
public class HttpUtil {
    public static final String ENCODING = "utf-8";

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setTimeout(10000);
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    public static void get(Context context, String urlStr, AsyncHttpResponseHandler handler) {
        client.get(context, urlStr, handler);
    }

    public static void get(Context context, String urlStr, RequestParams params,
        AsyncHttpResponseHandler handler) {
        client.get(context, urlStr, params, handler);
    }

    public static void get(Context context, String urlStr, Header[] headers, RequestParams params,
        AsyncHttpResponseHandler handler) {
        client.get(context, urlStr, headers, params, handler);
    }

    public static void get(Context context, String urlStr, JsonHttpResponseHandler handler) {
        client.get(context, urlStr, handler);
    }

    public static void get(Context context, String urlStr, RequestParams params,
        JsonHttpResponseHandler handler) {
        client.get(context, urlStr, params, handler);
    }

    public static void get(Context context, String urlStr, Header[] headers, RequestParams params,
        JsonHttpResponseHandler handler) {
        client.get(context, urlStr, headers, params, handler);
    }

    public static void get(Context context, String urlStr, BinaryHttpResponseHandler handler) {
        client.get(context, urlStr, handler);
    }

    public static void get(Context context, String urlStr, RequestParams params,
        BinaryHttpResponseHandler handler) {
        client.get(context, urlStr, params, handler);
    }

    public static void get(Context context, String urlStr, FileAsyncHttpResponseHandler handler) {
        client.get(context, urlStr, handler);
    }

    public static void get(Context context, String urlStr, RequestParams params,
                           FileAsyncHttpResponseHandler handler) {
        client.get(context, urlStr, params, handler);
    }

    public static void post(Context context, String urlStr, HttpEntity entity,
        String contentType, ResponseHandlerInterface handlerInterface) {
        client.post(context, urlStr, entity, contentType, handlerInterface);
    }

    public static void post(Context context, String urlStr, Header[] headers, HttpEntity entity,
        String contentType, ResponseHandlerInterface handlerInterface) {
        client.post(context, urlStr, headers, entity, contentType, handlerInterface);
    }

    public static void post(Context context, String urlStr, RequestParams params,
        ResponseHandlerInterface handlerInterface) {
        client.post(context, urlStr, params, handlerInterface);
    }

    public static void post(Context context, String urlStr, Header[] headers, String contentType,
        RequestParams params, ResponseHandlerInterface handlerInterface) {
        client.post(context, urlStr, headers, params, contentType, handlerInterface);
    }

    public static void put(Context context, String urlStr, HttpEntity entity,
        String contentType, ResponseHandlerInterface handlerInterface) {
        client.put(context, urlStr, entity, contentType, handlerInterface);
    }

    public static void delete(Context context, String urlStr, HttpEntity entity,
        String contentType, ResponseHandlerInterface handlerInterface) {
        client.delete(context, urlStr, entity, contentType, handlerInterface);
    }

    public static void delete(Context context, String urlStr, Header[] headers,
        ResponseHandlerInterface handlerInterface) {
        client.delete(context, urlStr, headers, handlerInterface);
    }
}
