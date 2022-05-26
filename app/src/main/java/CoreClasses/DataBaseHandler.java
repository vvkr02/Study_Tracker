package CoreClasses;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import Interfaces.VolleyCallBack;


public class DataBaseHandler extends AppCompatActivity {

    private RequestQueue requestQueue;
    private final Context context;
    private JSONArray jsonArrayResponse;
    private final String serverURL = "https://studev.groept.be/api/a21pt319/";

    public static List<User> userList = new ArrayList<>();
    public static User user;

    public DataBaseHandler(Context context){
        this.context = context;
    }


    public void getUsers() {
        String requestURL = serverURL + "selectUsers";
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.POST,
                        requestURL,
                        null,
                        response -> {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    User user = new User(
                                            response.getJSONObject(i).getInt("idUser"),
                                            response.getJSONObject(i).getString("Username"),
                                            response.getJSONObject(i).getString("Password"),
                                            response.getJSONObject(i).getString("Name"),
                                            response.getJSONObject(i).getInt("Score"),
                                            response.getJSONObject(i).getString("Profilepic")
                                    );
                                    userList.add(user);
                                }
                            } catch (Exception e) {
                                Log.d("JSONObject: ", e.getMessage(), e);
                            }
                        },
                        e -> Log.d("DB: ", e.getMessage(), e)
                ));
    }

    public void getUserFromLogin(User user, final VolleyCallBack callBack) {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Checking, please wait...");
        progressDialog.show();
        System.out.println(user);
        String requestURL = serverURL + "login_check" + "/" +
                user.getUsername() + "/" +
                user.getPassword() ;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {
                            System.out.println(response);
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        DataBaseHandler.user = new User(
                                                response.getJSONObject(i).getInt("idUser"),
                                                response.getJSONObject(i).getString("Username"),
                                                response.getJSONObject(i).getString("Password"),
                                                response.getJSONObject(i).getString("Name"),
                                                response.getJSONObject(i).getInt("Score"),
                                                response.getJSONObject(i).getString("Profilepic")
                                        );
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                progressDialog.dismiss();
                                callBack.onSuccess();
                            } else {
                                callBack.onFail();
                            }
                        },
                        error -> Log.d("DB: ", error.getMessage(), error)
                ));
    }
}
