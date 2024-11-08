package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.dto.Transaction;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    private String csvUrl = "https://docs.google.com/spreadsheets/d/1Y7LktZ7FE56UQ8jhdmJFE2_g-qXzYlOWJOMxKyrfITw/export?format=csv&gid=0"; // Adjust gid as needed

    public boolean checkPayment(String noiDung, int price){
        int maxRows = 50; // Set the maximum number of rows to read
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(csvUrl).openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                int rowCount = 0;

                // Skip header row
                if ((line = reader.readLine()) != null) {
                    // Continue to read data, up to maxRows
                    while ((line = reader.readLine()) != null && rowCount < maxRows) {
                        String[] values = line.split(",");
                        if(values[1].trim().toLowerCase().startsWith(noiDung.toLowerCase())){
                            if(Double.parseDouble(values[2].trim().replace(".", "").replace(",", ".")) == price){
                                return true;
                            }
                        }
                        rowCount++; // Increment the row count
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
//        List<Transaction> transactions = new ArrayList<>();
//        int maxRows = 50; // Set the maximum number of rows to read
//
//        try {
//            HttpURLConnection connection = (HttpURLConnection) new URL(csvUrl).openConnection();
//            connection.setRequestMethod("GET");
//
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//                String line;
//                int rowCount = 0;
//
//                // Skip header row
//                if ((line = reader.readLine()) != null) {
//                    // Continue to read data, up to maxRows
//                    while ((line = reader.readLine()) != null && rowCount < maxRows) {
//                        String[] values = line.split(",");
//
//                        // Assuming the order of columns in CSV matches the Transaction fields
//                        Transaction transaction = new Transaction();
//                        transaction.setTransactionCode(values[0].trim());
//                        transaction.setDescription(values[1].trim());
//                        transaction.setValue(Double.parseDouble(values[2].trim().replace(".", "").replace(",", "."))); // Adjust if currency format is different
//                        transaction.setDate(values[3].trim());
//                        transaction.setAccountNumber(values[4].trim());
//                        transaction.setReferenceCode(values[5].trim());
////                        transaction.setBalance(Double.parseDouble(values[6].trim().replace(".", "").replace(",", "."))); // Adjust as needed
////                        transaction.setVirtualAccountNumber(values[7].trim());
////                        transaction.setVirtualAccountName(values[8].trim());
//                        transaction.setCounterpartAccountNumber(values[9].trim());
//                        transaction.setCounterpartAccountName(values[10].trim());
//                        transaction.setBankBIN(values[11].trim());
//                        transaction.setCounterpartBankName(values[12].trim());
//
//                        // Add transaction to the list
//                        transactions.add(transaction);
//                        rowCount++; // Increment the row count
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Print transactions or do further processing
//        for (Transaction transaction : transactions) {
//            System.out.println(transaction.getTransactionCode() + " - " + transaction.getDescription());
//            // Add more fields as needed
//        }
    }
}
