package xyz.astradev;


import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import xyz.astradev.objects.Message;

import java.io.IOException;

public class Api {
    OkHttpClient client;
    Request.Builder builder;
    StringBuilder baseUrl = new StringBuilder();
    MediaType JSON;

    protected Api(@NotNull Request.Builder builder, @NotNull OkHttpClient client, @NotNull String baseUrl, @NotNull MediaType JSON){
        this.builder = builder;
        this.client = client;
        this.baseUrl.append(baseUrl).append("api");
        this.JSON = JSON;
    }

    public Message get(@NotNull String discord_id) throws IOException {
        baseUrl.append("?query=").append(discord_id);
        builder.url(baseUrl.toString()).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null){
                return new Gson().fromJson(response.body().string(), Message.class);
            }
            else{
                return null;
            }
        }
    }
    public int post(@NotNull String discord_id) throws IOException {
        RequestBody formBody = RequestBody.create("{\"discord_id\":"+"\""+discord_id+"\"}", JSON);
        builder.url(baseUrl.toString()).post(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }
    public Message patch(@NotNull String discord_id) throws IOException {
        RequestBody formBody = RequestBody.create("{\"discord_id\":" + "\"" + discord_id + "\"}", JSON);
        builder.url(baseUrl.toString()).patch(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return new Gson().fromJson(response.body().string(), Message.class);
            } else {
                return null;
            }
        }
    }
    public int delete(@NotNull String discord_id, String reason) throws IOException {
        RequestBody formBody = RequestBody.create("{\"discord_id\":"+"\""+discord_id+"\"}", JSON);
        builder.url(baseUrl.toString()).post(formBody);
        if (reason != null){
            builder.addHeader("x-audit-reason", reason);
        }
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }
}
