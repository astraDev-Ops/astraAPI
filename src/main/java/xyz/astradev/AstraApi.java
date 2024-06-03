package xyz.astradev;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public final class AstraApi {
    private static AstraApi INSTANCE;
    
    private static String apiKey = "";
    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "https://api.astradev.xyz/v3/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private AstraApi() {
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

    public Api api = new Api(apiKey, client, baseUrl, JSON);
    public Hash hash = new Hash(apiKey, client, baseUrl, JSON);
    public Quiz quiz = new Quiz(apiKey, client, baseUrl, JSON);
    public Website website = new Website(apiKey, client, baseUrl, JSON);
}
