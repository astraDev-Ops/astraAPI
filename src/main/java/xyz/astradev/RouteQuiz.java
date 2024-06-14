package xyz.astradev;

import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import xyz.astradev.objects.Quiz;

import java.io.IOException;

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
    public Quiz getReview() throws IOException {
        builder.url(baseUrl + "quiz/review").get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200){
                if (response.body() != null) {
                    String body = response.body().string();
                    return new Gson().fromJson(body, Quiz.class);
                }
                return null;
            }
            throw new IOException("Quiz Review [GET] returned " + response.code());
        }
    }

    public Quiz[] get(int amount, String category) throws IOException {
        if (amount <= 0 || amount >10){
            throw new IOException("Count must be <= 10");
        }
        String url;
        if (category == null){
            url = "quiz?amount=" + amount;
        }else{
            if (category.matches("[^a-zA-Z]")){
                throw new IOException("You cannot have special characters in the category.");
            }
            url = "quiz?amount=" + amount + "&category="+ category;
        }

        builder.url(baseUrl + url).get();
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200){
                if (response.body() != null) {
                    String body = response.body().string();
                    return new Gson().fromJson(body, Quiz[].class);
                }
                return null;
            }
            throw new IOException("Quiz [GET] returned " + response.code());
        }
    }

    public long post(@NotNull String question, @NotNull String answer, @NotNull String wrongA, @NotNull String wrongB, @NotNull String wrongC, @NotNull String category, @NotNull String keywords) throws IOException {
        Gson gson = new Gson();
        Quiz quiz = new Quiz(null, question,answer,wrongA,wrongB,wrongC,category,keywords);
        String json = gson.toJson(quiz);
        RequestBody formBody = RequestBody.create(json, JSON);
        builder.url(baseUrl + "quiz").post(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

    public int patch(long id) throws IOException {
        RequestBody formBody = RequestBody.create("{\"id\":"+"\""+id+"\"}", JSON);
        builder.url(baseUrl + "quiz").patch(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

    public int delete(long id) throws IOException {
        RequestBody formBody = RequestBody.create("{\"id\":"+"\""+id+"\"}", JSON);
        builder.url(baseUrl + "quiz").delete(formBody);
        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }
}