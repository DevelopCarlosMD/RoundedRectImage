package hassassin.com.roundedrectimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by mac on 06/07/15.
 */
public class StreamAdapter extends ArrayAdapter<StreamItem> {
    private static final int CORNER_RADIUS = 24;
    private static final int MARGIN = 12;

    private final int cornerRadius;
    private final int margin;
    private final LayoutInflater inflater;

    public StreamAdapter(Context context) {
        super(context, 0);
        final float density = context.getResources().getDisplayMetrics().density;
        cornerRadius = (int) (CORNER_RADIUS * density + 0.5f);
        margin = (int) (MARGIN * density + 0.5f);

        inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup viewGroup = null;

        if (convertView == null) {
            viewGroup = (ViewGroup) inflater.inflate(R.layout.stream_item, parent, false);
        } else {
            viewGroup = (ViewGroup) convertView;
        }
        StreamItem item = getItem(position);

        StreamDrawable d = new StreamDrawable(item.bitmap, cornerRadius, margin);
        viewGroup.setBackground(d);

        ((TextView) viewGroup.findViewById(R.id.tv_item)).setText(item.line1);
        ((TextView) viewGroup.findViewById(R.id.tv_item2)).setText(item.line2);

        int w = item.bitmap.getWidth();
        int h = item.bitmap.getHeight();

        float ratio = w / (float) h;

        ViewGroup.LayoutParams lp = viewGroup.getLayoutParams();
        lp.width = getContext().getResources().getDisplayMetrics().widthPixels;
        lp.height = (int) (lp.width / ratio);

        return viewGroup;
    }

}