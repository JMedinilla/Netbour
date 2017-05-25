package jvm.ncatz.netbour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityZoom extends AppCompatActivity {

    @BindView(R.id.photoZoom)
    PhotoView photoZoom;

    private AlertDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        loadingDialogCreate();
        //loadingDialogShow();

        ButterKnife.bind(this);

        //String url = "";
        Bitmap btm = null;
        Intent intent = getIntent();
        if (intent != null) {
            //url = intent.getStringExtra("photoZoom");
            btm = intent.getParcelableExtra("photoZoom");
        }
        if (btm != null) {
            photoZoom.setImageBitmap(btm);
        } else {
            finish();
        }
    }

    private void loadingDialogCreate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityZoom.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.loading_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        loading = builder.create();
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        if (loading.getWindow() != null) {
            loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void loadingDialogHide() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    public void loadingDialogShow() {
        if (loading != null) {
            loading.show();
        }
    }
}
