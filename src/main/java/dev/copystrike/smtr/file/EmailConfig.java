package dev.copystrike.smtr.file;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Nick on 21/12/20 17:25.
 */
public class EmailConfig extends Config {

    public EmailConfig() {
        super("email.json");
    }

    public boolean addEmailID(String emailID) {
        JSONArray employeeObject = new JSONArray();
        JSONArray checkedEmails = (JSONArray) getConfig().get("AlreadyCheckedEmails");
        for (Object checkedEmail : checkedEmails) employeeObject.add(checkedEmail);
        employeeObject.add(emailID);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("AlreadyCheckedEmails", employeeObject);

        write(jsonObject);
        return true;
    }

    public JSONArray getAlreadyCheckedEmails() {
        return ((JSONArray) getConfig().get("AlreadyCheckedEmails"));
    }
}
