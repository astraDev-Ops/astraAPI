package xyz.astradev;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public final class AstraApi {
    private static AstraApi INSTANCE;
    
    private static String apiKey = "";
    private final String baseUrl = "https://api.astradev.xyz/v3/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private AstraApi() {
        OkHttpClient client = new OkHttpClient();
        api = new Api(apiKey, client, baseUrl, JSON);
        hash = new Hash(apiKey, client, baseUrl, JSON);
        quiz = new Quiz(apiKey, client, baseUrl, JSON);
        website = new Website(apiKey, client, baseUrl, JSON);
    }

    public synchronized static AstraApi getInstance(String key) {
        if(INSTANCE == null) {
            INSTANCE = new AstraApi();
            apiKey = key;
        }
        return INSTANCE;
    }
    public String getBaseUrl() {
        return baseUrl;
    }
    public String getApiKey(){
        return apiKey;
    }

    public Api api;
    public Hash hash;
    public Quiz quiz;
    public Website website;
}
