import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ApiCall {
    private HashMap<String, String> locationData = new HashMap<>();
    private String location;

    public ApiCall(String location) {
        this.location = location;
        call(this.location);
    }

    private void geocode(String location) {
        try {
            URL url = new URL(String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=62d50fd38f48aba39355b8ae5a3ae053", location));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int resCode = conn.getResponseCode();
            if (resCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + resCode);
            } else {
                JSONParser parse = new JSONParser();
                JSONArray dataArray = (JSONArray) parse.parse(new InputStreamReader(conn.getInputStream()));
                JSONObject data = (JSONObject) dataArray.get(0);
                System.out.println(data);
                locationData.put("country", (String) data.get("country"));
                locationData.put("name", (String) data.get("name"));
                locationData.put("lon", String.valueOf(data.get("lon")));
                locationData.put("lat", String.valueOf(data.get("lat")));

            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    private void call(String location) {
        geocode(location);
        try {
            // a URL object is created based on the API endpoint
            URL url = new URL(String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&hourly=temperature_2m,weathercode&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max,precipitation_sum,rain_sum,windspeed_10m_max&timezone=auto"
                    , locationData.get("lat"), locationData.get("lon")));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // we then connect to the API endpoint , the response code is collected
            conn.connect();
            int resCode = conn.getResponseCode();

            // if the connection fails an error is thrown, else the data provided is parsed into a JSON object
            if (resCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + resCode);
            } else {

                // Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(new InputStreamReader(conn.getInputStream()));
                System.out.println(data_obj);
            }


        } catch (Exception error) {
            System.out.println(error);
        }

    }

    public Map getLocationData() {
        return locationData;
    }


}
