package xyz.astradev;

public final class AstraApi {
    private static AstraApi INSTANCE;
    private String API_KEY;

    private AstraApi() { }

    public synchronized static AstraApi getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AstraApi();
        }
        return INSTANCE;
    }
    public String getBaseUrl() {
        return "https://api.astradev.xyz/v3/";
    }

    public String getApiKey(){
        return this.API_KEY;
    }

    public void setApiKey(String ApiKey){
        this.API_KEY = ApiKey;
    }
}
