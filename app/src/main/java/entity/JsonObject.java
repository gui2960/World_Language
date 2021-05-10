package entity;

import com.google.gson.annotations.SerializedName;

public class JsonObject {

    @SerializedName("resource")
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

}
