package net.darkhax.ctweaks.features.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import net.darkhax.ctweaks.lib.Constants;

public class UpdateInfo {
    
    @SerializedName("version")
    private String version;
    
    @SerializedName("mainMessage")
    private String guiMessage;
    
    @SerializedName("chatMessage")
    private String chatMessage;
    
    public static UpdateInfo create (String url) {
        
        try {
            
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            final JsonReader reader = new JsonReader(new InputStreamReader((InputStream) connection.getContent()));
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            final UpdateInfo info = gson.fromJson(reader, UpdateInfo.class);
            System.out.println(info.toString());
            return info;
        }
        
        catch (IOException exception) {
            
            Constants.LOG.warn(exception);
        }
        
        return new UpdateInfo();
    }
    
    public String getVersion () {
        
        return version;
    }
    
    public String getGuiMessage () {
        
        return guiMessage;
    }
    
    public String getChatMessage () {
        
        return chatMessage;
    }
    
    public String toString () {
        
        return String.format("Version: %s Chat: %s GUI: %s", this.version, this.chatMessage, this.guiMessage);
    }
}
