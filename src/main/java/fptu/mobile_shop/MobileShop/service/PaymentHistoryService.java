package fptu.mobile_shop.MobileShop.service;
import com.mysql.cj.util.StringUtils;
import fptu.mobile_shop.MobileShop.dto.jsonDTO.RecordPayment;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentHistoryService {
    private final OkHttpClient client = new OkHttpClient();
    private String URL = "https://oauth.casso.vn/v2/transactions";
    private String APIKEY = "AK_CS.f5b814d0aa6711ef93018931a30376f5.L0uF7bUewYuYzaA8TGwIyzNBVD1uZvrEz8PqdFas6hPzOC7rHERe7Pm7lygLJJckog0R4IWH";

    public boolean callApi(String noiDung, int price) {
        try {
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

                // Parse JSON response nếu cần
                JSONObject json = new JSONObject(responseData);
                if (json.has("data") && json.getJSONObject("data").has("records")) {
                    JSONArray recordsArray = json.getJSONObject("data").getJSONArray("records");

                    for (int i = 0; i < recordsArray.length(); i++) {
                        JSONObject recordJson = recordsArray.getJSONObject(i);
                        RecordPayment record = new RecordPayment(
                                recordJson.getInt("id"),
                                recordJson.getString("tid"),
                                recordJson.getInt("amount"),
                                recordJson.getString("description"));
                        if(!StringUtils.isNullOrEmpty(record.getDescription()) && noiDung.length() > 25){
                            if(record.getDescription().toLowerCase().contains(noiDung.toLowerCase())){
                                if(price == record.getAmount()){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){

        }
        return false;
    }
}
