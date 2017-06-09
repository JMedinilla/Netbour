package jvm.ncatz.netbour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityZoom extends AppCompatActivity {

    @BindView(R.id.photoZoom)
    PhotoView photoZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("photoZoom");
            Glide.with(this).load(url).asBitmap().listener(new RequestListener<String, Bitmap>() {
                @Override
                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).error(R.drawable.glide_error).into(photoZoom);
        } else {
            finish();
        }
    }
}
