package hassassin.com.roundedrectimage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import hassassin.com.roundedrectimage.StreamItem;

/**
 * Created by mac on 06/07/15.
 */
public class StreamActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        StreamAdapter adapter = new StreamAdapter(this);

        ((ListView) findViewById(R.id.principal_list)).setAdapter(adapter);

        // Debo poner imagenes de paisajes en Mèxico.
        adapter.add(new StreamItem(this, R.drawable.photo1, "Tufa at night", "Mono Lake, CA"));
        adapter.add(new StreamItem(this, R.drawable.photo2, "Starry night", "Lake Powell, AZ"));
        adapter.add(new StreamItem(this, R.drawable.photo3,"Racetrack Playa","Death Valley, CA"));
        adapter.add(new StreamItem(this, R.drawable.photo4,"Napli Coast","Kauai, HI"));
        adapter.add(new StreamItem(this, R.drawable.photo5,"Delicate Arch","Arches, UT"));
        adapter.add(new StreamItem(this, R.drawable.photo6,"Sierra Sunset","Lone Pine, CA"));
        adapter.add(new StreamItem(this, R.drawable.photo7,"Majestic","Grand Teton, CA"));

        //adapter.add(new StreamItem(this, R.drawable.photo1, "Tufa at night", "Mono Lake, CA"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
    class StreamDrawable extends Drawable{
        private static final boolean USE_VIGNETTE = true;

        private final float cornerRadius;
        private final RectF rectF = new RectF();
        private final BitmapShader bitmapShader;
        private final Paint paint;
        private final int margin;

        StreamDrawable(Bitmap bitmap, float cornerRadius, int margin){
            this.cornerRadius = cornerRadius;
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint =  new Paint();
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);

            this.margin = margin;
       }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);

            rectF.set(margin, margin, bounds.width()- margin, bounds.height() - margin);
            if(USE_VIGNETTE){
                RadialGradient vignette = new RadialGradient(
                        rectF.centerX(), rectF.centerY() * 1.0f/0.7f, rectF.centerX() * 1.3f,
                        new int[] {0,0, 0x7f000000},new float[]{0.0f, 0.7f, 1.0f},
                        Shader.TileMode.CLAMP);

                Matrix oval = new Matrix();
                oval.setScale(1.0f, 0.7f);
                vignette.setLocalMatrix(oval);

                paint.setShader(
                        new ComposeShader(bitmapShader, vignette, PorterDuff.Mode.SRC_OVER));
            }
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }
    }


