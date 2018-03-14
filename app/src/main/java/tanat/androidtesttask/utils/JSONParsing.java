package tanat.androidtesttask.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import tanat.androidtesttask.BuildConfig;
import tanat.androidtesttask.errorreporter.Log;

public class JSONParsing {

    private static String jsonString;

    // parse the string
    public String examineJSONString(int sch) {
        String x = "";
        try {
            // the entire string is an object {}
            JSONObject post = new JSONObject(jsonString);
            JSONArray arrayData = post.getJSONArray("data");

            for (int i = 0; i <= arrayData.length(); i++) {
                x = "Подробная информация по маршруту №";
                // Data is already a list []
                JSONObject jsonArrayRoute = new JSONObject(arrayData.getString(i));
                x += jsonArrayRoute.getString("id") + "\n\n";

                JSONObject jsonArrayFromCity = jsonArrayRoute.getJSONObject("from_city");
                x += "Из города: " + jsonArrayFromCity.getString("name") + "\n";
                x += "  значение: " + jsonArrayFromCity.getString("highlight") + "\n";
                x += "  номер города: " + jsonArrayFromCity.getString("id") + "\n\n";

                JSONObject jsonArrayToCity = jsonArrayRoute.getJSONObject("to_city");
                x += "В город: " + jsonArrayToCity.getString("name") + "\n";
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

                if (i == sch) {
                    return x;
                }
            }
            return x;
        } catch (Exception je) {
            return ("Error: " + je.getMessage());
        }
    }

    // parsed 5 values
    public ArrayList examineJSONDemoString(String jsontext) {
        if (jsontext == null) {
            return null;
        }
        jsonString = jsontext;
        ArrayList demoData = new ArrayList();
        String id;
        String nameFromCity;
        String nameToCity;
        String fromDate;
        String toDate;
        Boolean success;

        try {
            JSONObject post = new JSONObject(jsontext);
            success = post.getBoolean("success");
            if (success) {
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
            } else {
                demoData.add("false");
                demoData.add(post.getString("message_error"));
            }
        } catch (Throwable je) {
            demoData.add("false");
            demoData.add(je);
        }
        return demoData;
    }

    public ArrayList<String[]> examineJSONObj() {
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        try {
            JSONObject post = new JSONObject(jsonString);
            JSONArray arrayData = post.getJSONArray("data");

            for (int i = 0; i <= arrayData.length(); i++) {
                String[] row = new String[17];
                JSONObject jsonArrayRoute = new JSONObject(arrayData.getString(i));
                row[0] = jsonArrayRoute.getString("id");

                JSONObject jsonArrayFromCity = jsonArrayRoute.getJSONObject("from_city");
                row[1] = jsonArrayFromCity.getString("name");
                row[2] = jsonArrayFromCity.getString("highlight");
                row[3] = jsonArrayFromCity.getString("id");

                JSONObject jsonArrayToCity = jsonArrayRoute.getJSONObject("to_city");
                row[4] = jsonArrayToCity.getString("name");
                row[5] = jsonArrayToCity.getString("highlight");
                row[6] = jsonArrayToCity.getString("id");

                row[7] = jsonArrayRoute.getString("info");

                row[8] = jsonArrayRoute.getString("from_date");
                row[9] = jsonArrayRoute.getString("from_time");
                row[10] = jsonArrayRoute.getString("from_info");

                row[11] = jsonArrayRoute.getString("to_date");
                row[12] = jsonArrayRoute.getString("to_time");
                row[13] = jsonArrayRoute.getString("to_info");

                row[14] = jsonArrayRoute.getString("price");
                row[15] = jsonArrayRoute.getString("bus_id");
                row[16] = jsonArrayRoute.getString("reservation_count");

                arrayList.add(row);
            }
        } catch (Exception je) {
            if (BuildConfig.USE_LOG) {
                Log.d(je.getMessage());
            }
        }
        return arrayList;
    }

    // method for returning a string
    public String dispatch() {
        return jsonString;
    }
}
