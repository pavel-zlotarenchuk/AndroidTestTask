package tanat.androidtesttask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by TaNaT on 05.01.2018.
 */

public class JSONFile {


    //метод что б распарсить InputStream
/*    public String examineJSONFile(InputStream is){
        String x = "";
        try
        {
//          InputStream is = getResources().openRawResource(R.raw.jsontwitter);
            byte [] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            jsontext = new String(buffer);
            JSONObject post = new JSONObject(jsontext);
            is.close();

            String command = post.getString("success");
            JSONArray arrayData = post.getJSONArray("data");

            x = "JSON parsed " + command + ".\nThere are [" + arrayData.length() + "]\n\n";

            for (int i = 0; i < arrayData.length(); i++)
            {
                JSONObject jsonArrayRoute= new JSONObject(arrayData.getString(i));
                x += "id: " + jsonArrayRoute.getString("id") + "\n";
                x += "From city: " + "\n";

                JSONObject jsonArrayFromCity = jsonArrayRoute.getJSONObject("from_city");
                x += "  Highlight: " + jsonArrayFromCity.getString("highlight") + "\n";
                x += "  id: " + jsonArrayFromCity.getString("id") + "\n";
                x += "  Name: " + jsonArrayFromCity.getString("name") + "\n";

                x += "From date: " + jsonArrayRoute.getString("from_date") + "\n";
                x += "From time: " + jsonArrayRoute.getString("from_time") + "\n";
                x += "From info: " + jsonArrayRoute.getString("from_info") + "\n";

                JSONObject jsonArrayToCity = jsonArrayRoute.getJSONObject("to_city");
                x += "  Highlight: " + jsonArrayToCity.getString("highlight") + "\n";
                x += "  id: " + jsonArrayToCity.getString("id") + "\n";
                x += "  Name: " + jsonArrayToCity.getString("name") + "\n";

                x += "To date: " + jsonArrayRoute.getString("to_date") + "\n";
                x += "To time: " + jsonArrayRoute.getString("to_time") + "\n";
                x += "To info: " + jsonArrayRoute.getString("to_info") + "\n";
                x += "Route: " + jsonArrayRoute.getString("info") + "\n";
                x += "Price: " + jsonArrayRoute.getString("price") + "\n";
                x += "Number bus: " + jsonArrayRoute.getString("bus_id") + "\n";
                x += "Reservation: " + jsonArrayRoute.getString("reservation_count") + "\n\n";

            }
            return x;
        }
        catch (Exception je)
        {
            return ("Error w/file: "  + x + je.getMessage());
        }
    }*/

    public String[] examineJSONDemoFile(InputStream is){
        String[] fiveData = new String[4];


        return fiveData;
    }

    //метод что б распарсить строку
        public String examineJSONString(String jsontext){
        String x = "";
        try
        {
            //вся строка это обьект {}
            JSONObject post = new JSONObject(jsontext);

            String command = post.getString("success");
            JSONArray arrayData = post.getJSONArray("data");

            x = "JSON parsed " + command + ".\nThere are [" + arrayData.length() + "]\n\n";

            for (int i = 0; i < arrayData.length(); i++)
            {
                //а вот Data это уже список []
                JSONObject jsonArrayRoute= new JSONObject(arrayData.getString(i));
                x += "id: " + jsonArrayRoute.getString("id") + "\n";
                x += "From city: " + "\n";

                //from_city это обьэкт {}
                JSONObject jsonArrayFromCity = jsonArrayRoute.getJSONObject("from_city");
                x += "  Highlight: " + jsonArrayFromCity.getString("highlight") + "\n";
                x += "  id: " + jsonArrayFromCity.getString("id") + "\n";
                x += "  Name: " + jsonArrayFromCity.getString("name") + "\n";

                x += "From date: " + jsonArrayRoute.getString("from_date") + "\n";
                x += "From time: " + jsonArrayRoute.getString("from_time") + "\n";
                x += "From info: " + jsonArrayRoute.getString("from_info") + "\n";

                //to_city это обьэкт {}
                JSONObject jsonArrayToCity = jsonArrayRoute.getJSONObject("to_city");
                x += "  Highlight: " + jsonArrayToCity.getString("highlight") + "\n";
                x += "  id: " + jsonArrayToCity.getString("id") + "\n";
                x += "  Name: " + jsonArrayToCity.getString("name") + "\n";

                x += "To date: " + jsonArrayRoute.getString("to_date") + "\n";
                x += "To time: " + jsonArrayRoute.getString("to_time") + "\n";
                x += "To info: " + jsonArrayRoute.getString("to_info") + "\n";
                x += "Route: " + jsonArrayRoute.getString("info") + "\n";
                x += "Price: " + jsonArrayRoute.getString("price") + "\n";
                x += "Number bus: " + jsonArrayRoute.getString("bus_id") + "\n";
                x += "Reservation: " + jsonArrayRoute.getString("reservation_count") + "\n\n";

            }
            return x;
        }
        catch (Exception je)
        {
            return ("Error w/file: "  + x + je.getMessage());
        }
    }
}
