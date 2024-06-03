package xyz.astradev;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public final class AstraApi {
    private static AstraApi INSTANCE;
    
    private String apiKey = "";
    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "https://api.astradev.xyz/v3/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private AstraApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public synchronized static AstraApi getInstance(String apiKey) {
        if(INSTANCE == null) {
            INSTANCE = new AstraApi(apiKey);
        }
        return INSTANCE;
    }
    public String getBaseUrl() {
        return baseUrl;
    }

    public Api api = new Api(apiKey, client, baseUrl, JSON);
    public Hash hash = new Hash(apiKey, client, baseUrl, JSON);
    public Quiz quiz = new Quiz(apiKey, client, baseUrl, JSON);
    public Website website = new Website(apiKey, client, baseUrl, JSON);
}
