package tanat.androidtesttask.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParsing {

    private static String jsonString;

    //метод что б распарсить строку
    public String examineJSONString(int sch){
            String x = "";
        try
        {
            //вся строка это обьект {}
            JSONObject post = new JSONObject(jsonString);
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
            return ("Error: " + je.getMessage());
        }
    }

    //метод что б распарсить 5 значений
    public ArrayList examineJSONDemoString(String jsontext){
        jsonString = jsontext;
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
            demoData.add("false");
            demoData.add(jsonString);
        }
        return demoData;
    }
}
