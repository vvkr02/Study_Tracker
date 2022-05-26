package CoreClasses;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import Interfaces.VolleyCallBack;

public class ImageHandler extends AppCompatActivity {

    private RequestQueue requestQueue;
    private final Context context;
    private JSONArray jsonArrayResponse;
    private final String serverURL = "https://studev.groept.be/api/a21pt319/";
    public static User user;
    private Bitmap bitmap;

    public ImageHandler(Context context){
        this.context = context;
    }

    public Bitmap getProfilePic(int idUser, User user, final VolleyCallBack callBack)
    {
        String requestURL = serverURL + "getProfilePic" + "/" +idUser;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {
                            System.out.println(response);
                            if (response.length() > 0 && !response.isNull(0)) {
                                String b64String = null;
                                try {
                                    b64String = response.getJSONObject(0).getString("Image");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );

                                setBitmap(BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length ));

                                callBack.onSuccess();
                            } else {
                                callBack.onFail();

                            }
                        },
                        error -> Log.d("DB: ", error.getMessage(), error)
                ));


    return getBitmap();
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
