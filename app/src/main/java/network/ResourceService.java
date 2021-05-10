package network;

import java.util.List;

import entity.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ResourceService {

    @GET("/get_resources_since/")
    Call<List<JsonObject>> getWeb();

}
