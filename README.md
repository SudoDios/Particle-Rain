<img src="https://github.com/SudoDios/Particle-Rain/raw/master/app/src/main/ic_launcher-playstore.png" alt="drawing" width="160"/>

# Particle-Rain
Particle-Rain is library to rain your drawable on android

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![](https://jitpack.io/v/SudoDios/Particle-Rain.svg)](https://jitpack.io/#SudoDios/Particle-Rain)

<img src="https://github.com/SudoDios/Particle-Rain/raw/master/screen_shot.jpg" width="144" height="321"/>

### How to use
Step 1 : Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2 : Add the dependency:
```gradle
dependencies {
	implementation 'com.github.SudoDios:Particle-Rain:1.0.1'
}
```

### XML Layout
```xml
<me.sudodios.particlerain.ParticleRainView
        android:id="@+id/particleRainView"
        android:layout_width="match_parent"
        app:prv_color="#fffafa"
        app:prv_maxCount="300"
        app:prv_res="@drawable/snow"
        app:prv_speed="0.5"
        android:layout_height="match_parent"/>
```

### Kotlin
```kotlin
binding.particleRainView.apply {

      //set color of particles
      color = Color.WHITE

      //max count to draw particles
      maxCount = 100

      //speed of rain
      speed = 0.5f

      //drawable res ID to draw on particles view
      setParticleDrawableResId(me.sudodios.particlerain.R.drawable.snow)
}
```

### Coming soon
```
1 - Support multiple drawable
2 - Support multiple colors
```
reference to main code [Telegram](https://github.com/DrKLO/Telegram)
