package id.co.kredibel;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import io.kredibel.picker.Picker;

public class MainActivity extends AppCompatActivity {

    Picker picker = new Picker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgResult = findViewById(R.id.imgResult);

        findViewById(R.id.btnCamera).setOnClickListener(view -> picker.pickCamera((uri, file, bitmap)
                -> imgResult.setImageURI(uri)
        ));

        findViewById(R.id.btnGalery).setOnClickListener(view -> picker.pickGallery((uri, file, bitmap)
                -> imgResult.setImageURI(uri))
        );
    }
}