package be.kuleuven.study_tracker.ChallengeOperations;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import CoreClasses.User;
import Interfaces.VolleyCallBack;

public class ChallengeDatabaseHandler extends AppCompatActivity {

    private final Context context;
    private final String serverURL = "https://studev.groept.be/api/a21pt319/";

    public ChallengeDatabaseHandler(Context context) {
        this.context = context;
    }

    public void addQuestiontoDB(String img_string, int sender,int receiver)
    {

        String imageString = img_string;
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Sending Question");
        progressDialog.show();
        String requestURL = serverURL + "AddQuestion/";

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        newRequestQueue(this.context).add(new StringRequest(Request.Method.POST, requestURL,  new Response.Listener<String>() {
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
                params.put("img", imageString);
                params.put("sender",""+sender);
                params.put("receiver",""+receiver);
                return params;
            }
        });

    }

    public void checkIfQuestionExists(int sender,int receiver,final VolleyCallBack callBack)
    {
        String requestURL = serverURL + "checkQuestionexists" + "/" + sender + "/"+receiver;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {

                            if (response.length() > 0) {

                                callBack.onSuccess();

                            } else {
                                callBack.onFail();
                            }
                        },
                        error -> Log.d("DB: ", error.getMessage(), error)
                ));

    }

}
