package resource;

import com.google.gson.Gson;
import io.ResourceRead;

import java.util.ArrayList;
import java.util.List;

public abstract class JsonToClassReader<T> {

    public abstract String getResourceFilePath();

    protected T readConfig(Class<T> contentClass, String jsonContent){
        Gson gson = new Gson();
        T config= gson.fromJson(jsonContent, contentClass);
        return config;
    }
}