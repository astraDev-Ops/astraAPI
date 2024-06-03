package xyz.astradev;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public final class AstraApi {
    private static AstraApi INSTANCE;
    
    private static String apiKey;
    private final String baseUrl = "https://api.astradev.xyz/v3/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private AstraApi() {
        OkHttpClient client = new OkHttpClient();
        api = new RouteApi(apiKey, client, baseUrl, JSON);
        hash = new RouteHash(apiKey, client, baseUrl, JSON);
        quiz = new RouteQuiz(apiKey, client, baseUrl, JSON);
        website = new RouteWebsite(apiKey, client, baseUrl, JSON);
    }

    public synchronized static AstraApi getInstance(String key) {
        if(INSTANCE == null) {
            apiKey = key;
            INSTANCE = new AstraApi();
        }
        return INSTANCE;
    }
    public String getBaseUrl() {
        return baseUrl;
    }
    public String getApiKey(){
        return apiKey;
    }

    public RouteApi api;
    public RouteHash hash;
    public RouteQuiz quiz;
    public RouteWebsite website;
}
