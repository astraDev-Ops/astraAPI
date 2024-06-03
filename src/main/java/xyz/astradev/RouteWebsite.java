package xyz.astradev;

import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import xyz.astradev.objects.Message;
import xyz.astradev.objects.WebsiteArray;

import java.io.IOException;


public class RouteWebsite {
    OkHttpClient client;
    Request.Builder builder;
    StringBuilder baseUrl = new StringBuilder();
    MediaType JSON;

    protected RouteWebsite(@NotNull String apiKey, @NotNull OkHttpClient client, @NotNull String baseUrl, @NotNull MediaType JSON) {
        this.builder = new Request.Builder().addHeader("x-auth-key", apiKey);
        this.client = client;
        this.baseUrl.append(baseUrl).append("url");
        this.JSON = JSON;
    }

    public WebsiteArray get(@NotNull String... url) throws IOException {
        baseUrl.append("?query=").append(url[0]);
        for (int i = 1; i < url.length; i++) {
            baseUrl.append(",").append(url[i]);
        }
        builder.url(baseUrl.toString()).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return new Gson().fromJson(response.body().string(), WebsiteArray.class);
                }
                return null;
            } else {
                if (response.body() != null) {
                    Message message = new Gson().fromJson(response.body().string(), Message.class);
                    throw new IOException("Website [GET] returned " + response.code() + " with message: " + message.message());
                }
                throw new IOException("Website [GET] returned " + response.code());
            }
        }
    }
    public WebsiteArray getPage(long page) throws IOException {
        baseUrl.append("?page=").append(page);
        builder.url(baseUrl.toString()).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return new Gson().fromJson(response.body().string(), WebsiteArray.class);
                }
                return null;
            } else {
                if (response.body() != null) {
                    Message message = new Gson().fromJson(response.body().string(), Message.class);
                    throw new IOException("Website [GET] returned " + response.code() + " with message: " + message.message());
                }
                throw new IOException("Website [GET] returned " + response.code());
            }
        }
    }
    public int post(@NotNull String url, @NotNull String reason) throws IOException {
        RequestBody formBody = RequestBody.create("{\"url\":"+"\""+url+"\", \"reason\":"+"\""+reason+"\"}", JSON);
        builder.url(baseUrl.toString()).post(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }
    public int patch(@NotNull String url, @NotNull String reason) throws IOException {
        RequestBody formBody = RequestBody.create("{\"url\":"+"\""+url+"\", \"reason\":"+"\""+reason+"\"}", JSON);
        builder.url(baseUrl.toString()).patch(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }
}