import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class ApiCall {
    private URL url;
    public void call(){
        try {
            // a URL object is created based on the API endpoint
            url = new URL("https://api.open-meteo.com/v1/forecast?latitude=-1.28&longitude=36.82&hourly=temperature_2m,relativehumidity_2m,apparent_temperature,precipitation,rain,snow_depth,visibility,windspeed_10m");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // we then connect to the API endpoint , the response code is collected
            conn.connect();
            int resCode = conn.getResponseCode();

            // if the connection fails an error is thrown, else the data provided is parsed into a JSON object
            if(resCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + resCode);
            } else {

                // Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(new InputStreamReader(conn.getInputStream()));
                System.out.println(data_obj);
            }


        }catch (Exception error) {
            System.out.println(error);
        }

    }
}
