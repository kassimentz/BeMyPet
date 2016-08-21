package br.com.bemypet.bemypet.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.bemypet.bemypet.R;

/**
 * Created by Cassio on 20/08/16.
 */
public class ImageAdapter extends PagerAdapter {
    Context context;
    private List<String> GalImages = new ArrayList<>();

    public ImageAdapter(Context context, List<String> galImages){
        this.context = context;
        this.GalImages = galImages;
    }
    @Override
    public int getCount() {
        return GalImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context).load(GalImages.get(position)).into(imageView);

        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
