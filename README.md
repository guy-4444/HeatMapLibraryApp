# HeatMapLibraryApp

[![](https://jitpack.io/v/guy-4444/HeatMapLibraryApp.svg)](https://jitpack.io/#guy-4444/HeatMapLibraryApp)

## Setup
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
	    maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency:

```
dependencies {
	    implementation 'com.github.guy-4444:HeatMapLibraryApp:1.00.02'
}
```
## Usage

###### StepProgress Constructor:
```java

    private double[][] dataMatrix;
    HeatMap.generateBitmap(dataMatrix, new HeatMap.CallBack_HeatMap() {
        @Override
        public void bitmapReady(Bitmap bitmap) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    });
```

## What's new
1.00.02:
1. AsyncTask to TaskRunner
