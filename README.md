<h1 align="center"> 
    <img width="150" src="https://github.com/kredibel-id/SamplePicker/blob/main/picker-icon.png?raw=true"/><br/>
    Picker
</h1>

## Getting started
### Support API Level
![minsdk](https://img.shields.io/badge/Min%20SDK-API%2024-%233DDC84?logo=android) ![targetsdk](https://img.shields.io/badge/Max%20Support-API%2031-%233DDC84?logo=android)

## Setup
#### 1. Add kredibel repository.
```groovy
maven{url 'https://repo.repsy.io/mvn/kredibel/sdk'}
```

#### 2. Add this dependency to gradle script on app module.
```groovy
dependencies {
    implementation 'io.kredibel:picker:0.0.1-beta' // Please check latest version
}
```
## Using Picker
Initialize Picker object on your Activity or Fragment<br/>   
![java](https://img.shields.io/badge/-Java-%23B07119)
```kotlin
Picker picker = new Picker(this);
```
![kotlin](https://img.shields.io/badge/-Kotlin-%23BA00BB)
```kotlin
var picker = Picker(this)
```

## Pick image from Gallery
![java](https://img.shields.io/badge/-Java-%23B07119)
```kotlin
picker.pickGallery(new PickerListener() {
    @Override
    public void onPicked(Uri uri, File file, Bitmap bitmap) {
        //do something
    }
});
```
![kotlin](https://img.shields.io/badge/-Kotlin-%23BA00BB)
```kotlin
picker.pickGallery { uri, file, bitmap ->
    //do something
}
```

## Pick from Camera
![java](https://img.shields.io/badge/-Java-%23B07119)
```kotlin
picker.pickCamera(new PickerListener() {
    @Override
    public void onPicked(Uri uri, File file, Bitmap bitmap) {
        //do something
    }
});
```
![kotlin](https://img.shields.io/badge/-Kotlin-%23BA00BB)
```kotlin
picker.pickCamera { uri, file, bitmap ->
    //do something
}
```

## With lambda (JDK 1.8+)
![java](https://img.shields.io/badge/-Java-%23B07119)
```kotlin
picker.pickCamera((uri, file, bitmap) -> {
    //do something;
});
```
<br/><br/>
<a target="_blank" href="https://github.com/kredibel-id/SamplePicker">See Sample</a>

