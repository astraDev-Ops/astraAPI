package xyz.astradev;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public final class AstraApi {
    private static AstraApi INSTANCE;
    private static String API_KEY;
    private static Request.Builder builder;
    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "https://api.astradev.xyz/v3/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private AstraApi() { }


    public synchronized static AstraApi getInstance(String apiKey) {
        if(INSTANCE == null) {
            INSTANCE = new AstraApi();
        }
        API_KEY = apiKey;
        builder = new Request.Builder().addHeader("x-auth-key", apiKey);
        return INSTANCE;
    }
    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey(){
        return API_KEY;
    }

    public Api api = new Api(builder, client, baseUrl, JSON);
    public Hash hash = new Hash(builder, client, baseUrl, JSON);
    public Quiz quiz = new Quiz(builder, client, baseUrl, JSON);
    public Website website = new Website(builder, client, baseUrl, JSON);
}
