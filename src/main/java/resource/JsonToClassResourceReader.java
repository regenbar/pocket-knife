package resource;

import com.google.gson.Gson;
import io.ResourceRead;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class JsonToClassResourceReader<T> {

    public abstract String getResourceFilePath();

    protected T readConfig(Class<T> contentClass){
        List<T> configs = new ArrayList<>();
        Gson gson = new Gson();

        String jsonInString = ResourceRead.readString(getResourceFilePath());
        T config= gson.fromJson(jsonInString, contentClass);
        return config;
    }
}