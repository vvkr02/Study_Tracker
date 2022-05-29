package CoreClasses;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Interfaces.VolleyCallBack;


public class DataBaseHandler extends AppCompatActivity {

    private RequestQueue requestQueue;
    private final Context context;
    private JSONArray jsonArrayResponse;
    private final String serverURL = "https://studev.groept.be/api/a21pt319/";

    public static List<User> userList = new ArrayList<>();
    public static User user;
    public static BasicDetails basicDetails;

    public DataBaseHandler(Context context){
        this.context = context;
    }

    public static BasicDetails getBasicDetails() {
        return basicDetails;
    }

    public static void setBasicDetails(BasicDetails basicDetails) {
        DataBaseHandler.basicDetails = basicDetails;
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
                                        this.user = new User(
                                                response.getJSONObject(i).getInt("idUser"),
                                                response.getJSONObject(i).getString("Username"),
                                                response.getJSONObject(i).getString("Password"),
                                                response.getJSONObject(i).getString("Name"),
                                                response.getJSONObject(i).getInt("Score"),
                                                response.getJSONObject(i).getString("Image")
                                        );

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                progressDialog.dismiss();
                                callBack.onSuccess();
                            } else {
                                callBack.onFail();
                                progressDialog.dismiss();
                            }
                        },
                        error -> Log.d("DB: ", error.getMessage(), error)
                ));
    }

    public void getBasicUserDetails(int id, final VolleyCallBack callBack) {
        String requestURL = serverURL + "get_allabout_user" + "/" +id;
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
                                        basicDetails = new BasicDetails(
                                                response.getJSONObject(i).getInt("idUser"),
                                                response.getJSONObject(i).getString("Name"),
                                                response.getJSONObject(i).getInt("Score"),
                                                response.getJSONObject(i).getString("Image")
                                        );
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

    public void registerUser(User user)
    {

        String imageString = user.getProfilePic();
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Registering User");
        progressDialog.show();
        System.out.println(user);
        String requestURL = serverURL + "add_user/";

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        newRequestQueue(this.context).add(new StringRequest (Request.Method.POST, requestURL,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Turn the progress widget off
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){ //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("var", imageString);
                params.put("n",user.getName());
                params.put("u",user.getUsername());
                params.put("p",user.getPassword());
                return params;
            }
        });

    }

    public void createTeam(String gname,User user, final VolleyCallBack callBack)
    {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Creating Team, please wait...");
        progressDialog.show();
        String requestURL = serverURL + "creategroup" + "/" +
                gname + "/" +
                user.getIdUser() + "/" +
                "1" ;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {
                            progressDialog.dismiss();
                            callBack.onSuccess();
                            },
                        error -> {
                            callBack.onFail();
                            progressDialog.dismiss();
                        }
                ));

    }

    public void joinTeam(String gname,User user, final VolleyCallBack callBack)
    {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Creating Team, please wait...");
        progressDialog.show();
        String requestURL = serverURL + "joinGroup" + "/" +
                gname + "/" +
                user.getIdUser();
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {
                            progressDialog.dismiss();
                            callBack.onSuccess();
                        },
                        error -> {
                            callBack.onFail();
                            progressDialog.dismiss();
                        }
                ));

    }

    public void deleteTeam(String gname,final VolleyCallBack callBack)
    {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Creating Team, please wait...");
        progressDialog.show();
        String requestURL = serverURL + "delete_team" + "/" +gname;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {
                            progressDialog.dismiss();
                            callBack.onSuccess();
                        },
                        error -> {
                            callBack.onFail();
                            progressDialog.dismiss();
                        }
                ));

    }

    public void leaveGroup(int gmem,final VolleyCallBack callBack)
    {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Leaving Group, please wait...");
        progressDialog.show();
        String requestURL = serverURL + "leavegroup" + "/" +gmem;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {
                            progressDialog.dismiss();
                            callBack.onSuccess();
                        },
                        error -> {
                            callBack.onFail();
                            progressDialog.dismiss();
                        }
                ));

    }

    public void updateScore(int scoreUpdate,int id,final VolleyCallBack callBack)
    {
        String requestURL = serverURL + "updatescore" + "/" +scoreUpdate+ "/" +id;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {

                            callBack.onSuccess();
                        },
                        error -> {
                            callBack.onFail();

                        }
                ));

    }

}
