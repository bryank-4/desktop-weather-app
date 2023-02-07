import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class ApiCall {
    private URL url;
    public void call(){
        try {
            url = new URL("https://api.open-meteo.com/v1/forecast?latitude=-1.28&longitude=36.82&hourly=temperature_2m,relativehumidity_2m,apparent_temperature,precipitation,rain,snow_depth,visibility,windspeed_10m");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int resCode = conn.getResponseCode();

            if(resCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + resCode);
            } else {
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);
                System.out.println(data_obj);
            }


        }catch (Exception error) {
            System.out.println("Error has occurred");
        }

    }
}
