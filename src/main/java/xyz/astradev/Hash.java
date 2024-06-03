package xyz.astradev;

import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import xyz.astradev.objects.Message;

import java.io.IOException;

class Hash {
    OkHttpClient client;
    Request.Builder builder;
    StringBuilder baseUrl = new StringBuilder();
    MediaType JSON;

    public Hash(@NotNull Request.Builder builder, @NotNull OkHttpClient client, @NotNull String baseUrl, @NotNull MediaType JSON) {
        this.builder = builder;
        this.client = client;
        this.baseUrl.append(baseUrl).append("file");
        this.JSON = JSON;
    }

    public xyz.astradev.objects.Hash[] get(@NotNull String... hash) throws IOException {
        baseUrl.append("?query=").append(hash[0]);
        for (int i = 1; i < hash.length; i++) {
            baseUrl.append(",").append(hash[i]);
        }
        builder.url(baseUrl.toString()).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return new Gson().fromJson(response.body().string(), xyz.astradev.objects.Hash[].class);
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
    public xyz.astradev.objects.Hash[] getPage(long page) throws IOException {
        baseUrl.append("?page=").append(page);
        builder.url(baseUrl.toString()).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return new Gson().fromJson(response.body().string(), xyz.astradev.objects.Hash[].class);
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
        builder.url(baseUrl.toString()).patch(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }
}
