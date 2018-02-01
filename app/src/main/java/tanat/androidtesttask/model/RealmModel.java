package tanat.androidtesttask.model;

import io.realm.RealmObject;

public class RealmModel extends RealmObject {

    private int id;
    private String name_from_city;
    private String highlight_from_city;
    private int id_from_city;

    private String name_to_city;
    private String highlight_to_city;
    private int id_to_city;

    private String info;

    private String from_date;
    private String from_time;
    private String from_info;

    private String to_date;
    private String to_time;
    private String to_info;

    private int price;
    private int bus_id;
    private int reservation_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_from_city() {
        return name_from_city;
    }

    public void setName_from_city(String name_from_city) {
        this.name_from_city = name_from_city;
    }

    public String getHighlight_from_city() {
        return highlight_from_city;
    }

    public void setHighlight_from_city(String highlight_from_city) {
        this.highlight_from_city = highlight_from_city;
    }

    public int getId_from_city() {
        return id_from_city;
    }

    public void setId_from_city(int id_from_city) {
        this.id_from_city = id_from_city;
    }

    public String getName_to_city() {
        return name_to_city;
    }

    public void setName_to_city(String name_to_city) {
        this.name_to_city = name_to_city;
    }

    public String getHighlight_to_city() {
        return highlight_to_city;
    }

    public void setHighlight_to_city(String highlight_to_city) {
        this.highlight_to_city = highlight_to_city;
    }

    public int getId_to_city() {
        return id_to_city;
    }

    public void setId_to_city(int id_to_city) {
        this.id_to_city = id_to_city;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getFrom_info() {
        return from_info;
    }

    public void setFrom_info(String from_info) {
        this.from_info = from_info;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getTo_info() {
        return to_info;
    }

    public void setTo_info(String to_info) {
        this.to_info = to_info;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public int getReservation_count() {
        return reservation_count;
    }

    public void setReservation_count(int reservation_count) {
        this.reservation_count = reservation_count;
    }
}
