package io.kredibel.picker;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class PickerObserver implements DefaultLifecycleObserver {
    private final ActivityResultRegistry registry;
    private final Activity activity;
    private final Compressor compressor;
    private PickerListener pickerListener;
    ActivityResultCallback<Uri> galleryResultUri = new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            if (uri != null) {
                try {
                    File file = compressor.compressUritoFile(uri);
                    pickerListener.onPicked(uri, file, uriToBitmap(uri));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    };
    ActivityResultCallback<ActivityResult> cameraResultIntent = new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int resultCode = result.getResultCode();
            Intent data = result.getData();
            if (resultCode == RESULT_OK && data != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                //File file = compressor.compressToFile()

                ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

                String child = new Random().nextInt(20) + System.currentTimeMillis() + "_picker" + ".jpg";

                File file = new File(directory, child);
                if (!file.exists()) {
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
                pickerListener.onPicked(Uri.fromFile(file), file, bitmap);
            }
        }
    };
    private ActivityResultLauncher<String> imageLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;

    public PickerObserver(@NonNull AppCompatActivity activity) {
        this.compressor = new Compressor(activity);
        this.activity = activity;
        this.registry = activity.getActivityResultRegistry();
    }

    public PickerObserver(@NonNull Fragment fr) {
        this.activity = fr.requireActivity();
        this.compressor = new Compressor(this.activity);
        this.registry = fr.requireActivity().getActivityResultRegistry();
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);
        imageLauncher = registry.register("key", owner, new ActivityResultContracts.GetContent(), galleryResultUri);
        cameraLauncher = registry.register("key1", owner, new ActivityResultContracts.StartActivityForResult(), cameraResultIntent);
    }

    private Bitmap uriToBitmap(Uri selectedFileUri) {
        Bitmap bitmap = null;
        try {
            ParcelFileDescriptor parcelFileDescriptor = activity.getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);

            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void pickGallery(PickerListener pickerListener) {
        this.pickerListener = pickerListener;
        imageLauncher.launch("image/*");
    }

    public void pickCamera(PickerListener pickerListener) {
        this.pickerListener = pickerListener;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }
}
