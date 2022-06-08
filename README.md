<h1 align="center"> 
    <img width="150" src="https://github.com/kredibel-id/SamplePicker/blob/main/picker-icon.png?raw=true"/><br/>
    Picker
</h1>

<p align="center">
    <a target="_blank" href="https://twitter.com/intent/tweet?text=Picker&url=https://github.com/kredibel-id/Picker"> <img width="30" src="https://camo.githubusercontent.com/35b0b8bfbd8840f35607fb56ad0a139047fd5d6e09ceb060c5c6f0a5abd1044c/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f747769747465722e737667" /></a><a target="_blank" href="https://www.facebook.com/sharer/sharer.php?u=https://github.com/kredibel-id/Picker"> <img width="30" src="https://camo.githubusercontent.com/8f245234577766478eaf3ee72b0615e99bb9ef3eaa56e1c37f75692811181d5c/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f66616365626f6f6b2e737667" /></a><a target="_blank" href="https://plus.google.com/share?url=https://github.com/kredibel-id/Picker"> <img width="30" src="https://camo.githubusercontent.com/15fdf0cbd71e1ca3db22839bf80a55d246e4a19e4a019021fdf121e2cc193488/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f676f6f676c655f706c75732e737667" /></a><a target="_blank" href="https://www.linkedin.com/shareArticle?mini=true&url=https://github.com/kredibel-id/Picker&title=Picker&summary=Simplify the way to handling the gallery or camera intent without rambling 😄. &source=https://github.com/kredibel-id/Picker"> <img width="30" src="https://camo.githubusercontent.com/c8a9c5b414cd812ad6a97a46c29af67239ddaeae08c41724ff7d945fb4c047e5/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f6c696e6b6564696e2e737667" /></a><a target="_blank" href="https://pinterest.com/pin/create/button/?url=https://github.com/kredibel-id/Picker&description=Simplify the way to handling the gallery or camera intent without rambling 😄. "> <img width="30" src="https://camo.githubusercontent.com/ef99a09dfa010e68c26ec4414631a47bbc1086677227bd97538d051b8b93ae21/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f70696e7465726573742e737667" /></a><a target="_blank" href="http://www.tumblr.com/share/link?url=https://github.com/kredibel-id/Picker&description=Simplify the way to handling the gallery or camera intent without rambling 😄. "> <img width="30" src="https://camo.githubusercontent.com/f47b844e7015760d6fd9c1fb86834af2cf82d215fc9c20c24edc8173c85059a1/68747470733a2f2f6564656e742e6769746875622e696f2f537570657254696e7949636f6e732f696d616765732f7376672f74756d626c722e737667" /></a> 
    </p>

### Support API Level
![minsdk](https://img.shields.io/badge/Min%20SDK-API%2024-%233DDC84?logo=android) ![targetsdk](https://img.shields.io/badge/Max%20Support-API%2031-%233DDC84?logo=android)


## Getting started

## Setup
#### 1. Add kredibel repository.
```groovy
maven{url 'https://repo.repsy.io/mvn/kredibel/sdk'}
```

#### 2. Add this dependency to gradle script on app module. 👉 <a href="https://github.com/kredibel-id/Picker/releases">[Latest release]</a>
```groovy
dependencies {
    implementation 'io.kredibel:picker:0.0.1' // Please check ☝️ latest version
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
## Sample Project
- <a target="_blank" href="https://github.com/kredibel-id/SamplePicker">See Sample [in Kotlin]</a>

