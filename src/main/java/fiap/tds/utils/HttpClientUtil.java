package fiap.tds.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpClientUtil {
    private static final OkHttpClient client = new OkHttpClient();

    public static String sendGET(String url, String token) throws IOException {
        Request request = new Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer " + token)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
}
