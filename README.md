# HeatMapLibraryApp

[![](https://jitpack.io/v/guy-4444/HeatMapLibraryApp.svg)](https://jitpack.io/#guy-4444/HeatMapLibraryApp)
![GitHub](https://img.shields.io/github/license/guy-4444/HeatMapLibraryApp)


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


## License

    Copyright 2020 Guy Isakov

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

