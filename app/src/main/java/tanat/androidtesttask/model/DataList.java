package tanat.androidtesttask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
public class DataList {
 
    @SerializedName("contacts")
    @Expose
    private ArrayList<PojoModel> contacts = new ArrayList<>();
 
    /**
     * @return The contacts
     */
    public ArrayList<PojoModel> getContacts() {
        return contacts;
    }
 
    /**
     * @param contacts The contacts
     */
    public void setContacts(ArrayList<PojoModel> contacts) {
        this.contacts = contacts;
    }
}