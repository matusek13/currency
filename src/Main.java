import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {

    public static void main(String[] args) {
        try {

            //Input date and get the URL
            System.out.println("Please type in date in format: ‘YYYY-MM-DD’");
            Scanner scannerdate = new Scanner(System.in);
            String rawdate = scannerdate.nextLine ();
            String addon = "?to=USD";
            String data = rawdate + addon;
            String site = "https://www.frankfurter.app/";
            site +=data;
            URL url = new URL(site);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);

                //Get the required object from the above created object
                JSONObject obj = (JSONObject) data_obj.get("rates");

                //Get the required data using its key
                System.out.println("Rate of USD to EUR equals: ");
                System.out.println(obj.get("USD"));
                System.out.println("From the Date: ");
                System.out.println(rawdate);

                LocalTime localTime = LocalTime.now();
                System.out.println("Timestamp "+localTime);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}