package xyz.astradev;

import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import xyz.astradev.objects.HashArray;
import xyz.astradev.objects.Message;

import java.io.IOException;

public class RouteHash {
    OkHttpClient client;
    Request.Builder builder;
    String baseUrl;
    MediaType JSON;

    protected RouteHash(@NotNull String apiKey, @NotNull OkHttpClient client, @NotNull String baseUrl, @NotNull MediaType JSON) {
        this.builder = new Request.Builder().addHeader("x-auth-key", apiKey);
        this.client = client;
        this.baseUrl = baseUrl;
        this.JSON = JSON;
    }

    public HashArray get(@NotNull String... hash) throws IOException {
        StringBuilder string = new StringBuilder();
        string.append(baseUrl).append("file?query=").append(hash[0]);
        for (int i = 1; i < hash.length; i++) {
            string.append(",").append(hash[i]);
        }
        builder.url(string.toString()).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return new Gson().fromJson(response.body().string(), HashArray.class);
                }
                return null;
            } else {
                if (response.body() != null) {
                    Message message = new Gson().fromJson(response.body().string(), Message.class);
                    throw new IOException("Hash [GET] returned " + response.code() + " with message: " + message.message());
                }
                throw new IOException("Hash [GET] returned " + response.code());
            }
        }
    }
    public HashArray getPage(long page) throws IOException {
        builder.url(baseUrl + "file?page=" + page).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return new Gson().fromJson(response.body().string(), HashArray.class);
                }
                return null;
            } else {
                if (response.body() != null) {
                    Message message = new Gson().fromJson(response.body().string(), Message.class);
                    throw new IOException("Hash [GET] returned " + response.code() + " with message: " + message.message());
                }
                throw new IOException("Hash [GET] returned " + response.code());
            }
        }
    }
    public int patch(@NotNull String sha256, String classification, Boolean sample_available) throws IOException {
        RequestBody formBody = RequestBody.create("{\"sha256\":"+"\""+sha256+"\", \"classification\":"+"\""+classification+"\", \"sample_available\":"+"\""+sample_available+"\" }", JSON);
        builder.url(baseUrl + "file").patch(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }
}
