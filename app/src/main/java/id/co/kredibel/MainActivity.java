package id.co.kredibel;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import io.kredibel.picker.Picker;
import io.kredibel.picker.PickerListener;
import io.kredibel.picker.PickerObserver;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Picker picker = new Picker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgResult = findViewById(R.id.imgResult);
        TextView txtPath = findViewById(R.id.txtPath);

        PickerListener pickerListener = new PickerListener() {
            @Override
            public void onPicked(Uri uri, File file, Bitmap bitmap) {
                imgResult.setImageURI(uri);
                txtPath.setText(file.getAbsolutePath()+"(" + (file.exists()) + ")");
                //Log.d("CEK", "CEK-EXIST: Bitmap|Camera: " + (bitmap != null));
                //Log.d("CEK", "CEK-EXIST: File|Camera: " + (file.exists()) + "|" + file.getAbsolutePath());
            }
        };

        findViewById(R.id.btnChoose).setOnClickListener(view -> picker.pickImage(pickerListener));

        findViewById(R.id.btnCamera).setOnClickListener(view -> picker.pickCamera(pickerListener));

        findViewById(R.id.btnGalery).setOnClickListener(view -> picker.pickGallery(pickerListener));
    }
}