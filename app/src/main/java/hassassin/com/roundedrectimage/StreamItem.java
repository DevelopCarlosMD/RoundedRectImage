package hassassin.com.roundedrectimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by mac on 06/07/15.
 */
class StreamItem{

    final Bitmap bitmap;
    final String line1;
    final String line2;

    StreamItem(Context c, int resid, String line1, String line2){
        bitmap = BitmapFactory.decodeResource(c.getResources(), resid);
        this.line1 = line1;
        this.line2 = line2;
    }
}