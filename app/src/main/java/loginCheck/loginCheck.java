package loginCheck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class loginCheck {

    private JSONArray response;
    private boolean isPresent;

    public loginCheck(JSONArray response,String username,String password)
    {
        int temp = 0;

        //JSONArray response = new JSONArray(response1);
        /*Toast.makeText(MainActivity.this, response1, Toast.LENGTH_SHORT).show();*/
        for (int i = 0; i < response.length(); ++i) {
            JSONObject o = null;
            try {
                o = response.getJSONObject(i);
                if (o.getString("username").toString().equals(username)) {
                    if (o.getString("password").toString().equals(password)) {
                        temp++;
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (temp ==0){
            setPresent(false);
        }
        else
        {
            setPresent(true);
        }

    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
