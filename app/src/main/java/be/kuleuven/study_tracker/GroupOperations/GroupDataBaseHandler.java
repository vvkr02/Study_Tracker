package be.kuleuven.study_tracker.GroupOperations;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import CoreClasses.User;
import Interfaces.VolleyCallBack;

public class GroupDataBaseHandler {
    private final Context context;
    private final String serverURL = "https://studev.groept.be/api/a21pt319/";
    private boolean isPresent;
    private String groupname;
    private int temp;

    public GroupDataBaseHandler(Context context) {
        this.context = context;
    }

    public void checkIfPresent(int id,final VolleyCallBack callBack)
    {
        String requestURL = serverURL + "check_if_present_in_group" + "/" + id;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {

                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        setGroupname(response.getJSONObject(i).getString("groupName"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                callBack.onSuccess();

                            } else {
                                callBack.onFail();
                            }
                        },
                        error -> Log.d("DB: ", error.getMessage(), error)
                ));

    }


    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
