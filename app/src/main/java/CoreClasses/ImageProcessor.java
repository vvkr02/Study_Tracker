package CoreClasses;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


public class ImageProcessor{


    public Bitmap process(String imageString)
    {
        String b64String = imageString;
        byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
        Bitmap bitmap2 = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
        return bitmap2;
    }

}
