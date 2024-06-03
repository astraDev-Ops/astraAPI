package xyz.astradev;


import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import xyz.astradev.objects.Message;

import java.io.IOException;

public class RouteApi {
    OkHttpClient client;
    Request.Builder builder;
    String baseUrl;
    MediaType JSON;

    protected RouteApi(@NotNull String apiKey, @NotNull OkHttpClient client, @NotNull String baseUrl, @NotNull MediaType JSON) {
        this.builder = new Request.Builder().addHeader("x-auth-key", apiKey);
        this.client = client;
        this.baseUrl = baseUrl;
        this.JSON = JSON;
    }

    public Message get(@NotNull String discord_id) throws IOException {
        builder.url(baseUrl+ "api?query=" + discord_id).get();
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
        builder.url(baseUrl+"api").post(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }
    public Message patch(@NotNull String discord_id) throws IOException {
        RequestBody formBody = RequestBody.create("{\"discord_id\":" + "\"" + discord_id + "\"}", JSON);
        builder.url(baseUrl+"api").patch(formBody);
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
        builder.url(baseUrl+"api").post(formBody);
        if (reason != null){
            builder.addHeader("x-audit-reason", reason);
        }
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }
}
