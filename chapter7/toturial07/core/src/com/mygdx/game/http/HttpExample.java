package com.mygdx.game.http;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

public class HttpExample {
    public static void send() {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("https://www.google.de").content("q=libgdx&example=example").build();
        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
        //有参数的
//        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
//        HttpRequest httpRequest = requestBuilder.newRequest().method(HttpMethods.GET).url("https://www.google.de").content("q=libgdx&example=example").build();
//        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);

    }

    private static final Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求成功，响应内容：" + httpResponse.getResultAsString());
            } else {
                System.out.println("请求失败，状态码：" + httpResponse.getStatus().getStatusCode());
            }
        }

        @Override
        public void failed(Throwable throwable) {
            System.out.println("失败");
        }

        @Override
        public void cancelled() {
            System.out.println("取消");
        }
    };

}
