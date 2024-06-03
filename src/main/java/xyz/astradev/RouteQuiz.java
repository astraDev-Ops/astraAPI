package xyz.astradev;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public class RouteQuiz {
    OkHttpClient client;
    Request.Builder builder;
    String baseUrl;
    MediaType JSON;

    protected RouteQuiz(@NotNull String apiKey, @NotNull OkHttpClient client, @NotNull String baseUrl, @NotNull MediaType JSON) {
        this.builder = new Request.Builder().addHeader("x-auth-key", apiKey);
        this.client = client;
        this.baseUrl =baseUrl;
        this.JSON = JSON;
    }
    
}
