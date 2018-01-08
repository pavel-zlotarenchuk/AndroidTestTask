package tanat.androidtesttask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by TaNaT on 05.01.2018.
 */

public class JSONFile {

    static String jsomString;


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

            //вся строка это обьект {}
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
    }*/

    public ArrayList examineJSONDemoString(String jsontext){
        jsomString = jsontext;
        ArrayList demoData = new ArrayList();
        String id;
        String nameFromCity;
        String nameToCity;
        String fromDate;
        String toDate;

        try
        {
            //вся строка это обьект {}
            JSONObject post = new JSONObject(jsontext);
            JSONArray arrayData = post.getJSONArray("data");

            for (int i = 0; i < arrayData.length(); i++) {
                JSONObject jsonArrayRoute = new JSONObject(arrayData.getString(i));
                id = jsonArrayRoute.getString("id");

                JSONObject jsonArrayFromCity = jsonArrayRoute.getJSONObject("from_city");
                nameFromCity = jsonArrayFromCity.getString("name");

                JSONObject jsonArrayToCity = jsonArrayRoute.getJSONObject("to_city");
                nameToCity = jsonArrayToCity.getString("name");

                fromDate = jsonArrayRoute.getString("from_date");
                toDate = jsonArrayRoute.getString("from_time");

                demoData.add(id + " | " + nameFromCity + " | " + nameToCity + " | " + fromDate + " | " + toDate);
            }
        }
        catch (Exception je)
        {
        }
        return demoData;
    }

    //метод что б распарсить строку
        public String examineJSONString(int sch){
            String x = "";
        try
        {
            //вся строка это обьект {}
            JSONObject post = new JSONObject(jsomString);
            JSONArray arrayData = post.getJSONArray("data");

            for (int i = 0; i <= arrayData.length(); i++)
            {
                x = "Подробная информация по маршруту №";
                //а вот Data это уже список []
                JSONObject jsonArrayRoute= new JSONObject(arrayData.getString(i));
                x += jsonArrayRoute.getString("id") + "\n\n";

                JSONObject jsonArrayFromCity = jsonArrayRoute.getJSONObject("from_city");
                x += "Из города: " + jsonArrayFromCity.getString("name")+ "\n";
                x += "  значение: " + jsonArrayFromCity.getString("highlight") + "\n";
                x += "  номер города: " + jsonArrayFromCity.getString("id") + "\n\n";

                JSONObject jsonArrayToCity = jsonArrayRoute.getJSONObject("to_city");
                x += "В город: " + jsonArrayToCity.getString("name")+ "\n";
                x += "  значение: " + jsonArrayToCity.getString("highlight") + "\n";
                x += "  номер города: " + jsonArrayToCity.getString("id") + "\n\n";

                x += "Маршрут: " + jsonArrayRoute.getString("info") + "\n\n";

                x += "Дата отправки: " + jsonArrayRoute.getString("from_date") + "\n";
                x += "Вряме отправки: " + jsonArrayRoute.getString("from_time") + "\n";
                x += "Дополнительная инф.: " + jsonArrayRoute.getString("from_info") + "\n\n";

                x += "Дата прибытия: " + jsonArrayRoute.getString("to_date") + "\n";
                x += "Врямя прибытия: " + jsonArrayRoute.getString("to_time") + "\n";
                x += "Дополнительная инф.: " + jsonArrayRoute.getString("to_info") + "\n\n";

                x += "Цена: " + jsonArrayRoute.getString("price") + "\n";
                x += "Номер автобуса: " + jsonArrayRoute.getString("bus_id") + "\n";
                x += "Зарезервировано мест: " + jsonArrayRoute.getString("reservation_count") + "\n\n";

                if (i == sch){
                    return x;
                }
            }
            return x;
        }
        catch (Exception je) {
            return ("Error w/file: " + je.getMessage());
        }
    }
}
