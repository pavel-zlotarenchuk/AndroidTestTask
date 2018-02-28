package tanat.androidtesttask.api;

import retrofit2.Call;
import retrofit2.http.GET;
import tanat.androidtesttask.model.PojoModel;

public interface ApiService {
 
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of DataList
    */
    //  @GET("/bins/7tghr")
    @GET("/web/index.php/api/trips?from_date=2016-01-01&to_date=2018-03-01")
    Call<PojoModel> getMyJSON();
}