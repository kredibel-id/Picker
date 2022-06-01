package io.kredibel.picker;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

public class Picker {

    private PickerObserver observer;

    private Picker picker;

    private Picker() {
    }

    public Picker(AppCompatActivity activity) {
        observer = new PickerObserver(activity);
        activity.getLifecycle().addObserver(observer);
        if (picker == null) {
            picker = new Picker();
        }
    }

    public Picker(Fragment fragment){
        observer = new PickerObserver(fragment);
        fragment.getLifecycle().addObserver(observer);
        if (picker == null) {
            picker = new Picker();
        }
    }

    public void pickCamera(PickerListener pickerListener) {
        observer.pickCamera(pickerListener);
    }

    public void pickGallery(PickerListener pickerListener) {
        observer.pickGallery(pickerListener);
    }

    public void pickImage(PickerListener pickerListener){
        observer.pickImage(pickerListener);
    }
}
