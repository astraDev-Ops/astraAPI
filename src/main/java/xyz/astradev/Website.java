package xyz.astradev;

import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import xyz.astradev.objects.Message;

import java.io.IOException;


class Website {
    OkHttpClient client;
    Request.Builder builder;
    StringBuilder baseUrl = new StringBuilder();
    MediaType JSON;

    public Website(@NotNull Request.Builder builder, @NotNull OkHttpClient client, @NotNull String baseUrl, @NotNull MediaType JSON) {
        this.builder = builder;
        this.client = client;
        this.baseUrl.append(baseUrl).append("url");
        this.JSON = JSON;
    }

    public Website[] get(@NotNull String... url) throws IOException {
        baseUrl.append("?query=").append(url[0]);
        for (int i = 1; i < url.length; i++) {
            baseUrl.append(",").append(url[i]);
        }
        builder.url(baseUrl.toString()).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return new Gson().fromJson(response.body().string(), Website[].class);
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
    public Website[] getPage(long page) throws IOException {
        baseUrl.append("?page=").append(page);
        builder.url(baseUrl.toString()).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return new Gson().fromJson(response.body().string(), Website[].class);
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