import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {

    public static String queryAPI(String input) {
        // Actual API integration
        try {
            // Replace "your_api_endpoint" with the actual API endpoint
            URL url = new URL("your_api_endpoint?input=" + input);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                return response.toString();
            } else {
                return "Error: Unable to connect to the API.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Exception occurred while querying the API.";
        }
    }
}
