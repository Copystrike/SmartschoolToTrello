package dev.copystrike.smtr.file;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Nick on 21/12/20 16:57.
 */
public class Config {

    private String configName;

    public Config(String configName) {
        this.configName = System.getProperty("user.dir") + File.separator +"config" + File.separator + configName;
    }

    public boolean write(JSONObject jsonObject) {
        try (FileWriter file = new FileWriter(configName)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public JSONObject getConfig() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(configName)) {
            return (JSONObject) jsonParser.parse(reader);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
