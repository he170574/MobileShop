package fptu.mobile_shop.MobileShop.service;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

@Service
public class PaymentHistoryService {
    private final OkHttpClient client = new OkHttpClient();
    private String URL = "https://oauth.casso.vn/v2/transactions";
    private String APIKEY = "AK_CS.95e284609d4111ef8a02890bf6befcfe.KyL7IOJvRHsc1CA8R48I9nPOWODlUa4F661XI3OB51TNDP4plCc2EOmZz7KEoaoiqSOry8AT";

    public String callApi() throws IOException {
        // Tạo request với header "Authorization: apikey <apiKey>"
        Request request = new Request.Builder()
                .url(URL)
                .addHeader("Authorization", "apikey " + APIKEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Đọc dữ liệu từ response
            String responseData = response.body().string();
            System.out.println("Full Response Data: " + responseData);

            // Parse JSON response nếu cần
            JSONObject json = new JSONObject(responseData);
            if (json.has("data") && json.getJSONObject("data").has("records")) {
                return json.getJSONObject("data").getJSONArray("records").toString();
            } else {
                return "No records found";
            }
        }
    }
}
