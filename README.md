# CurrencyApiTest
Revolut Android Test

## Getting Started

This project fetches currency conversion rates based on Euro. These conversion rates are being updated periodically on the background.    
### Prerequisites
Android  Studio 3.0+
<br/> Latest Android SDK 


Libraries (App level build.gradle)

```
dependencies {
    ...
    //Retrofit & RxJava
    implementation "com.squareup.retrofit2:retrofit:2.5.0"
    implementation "com.squareup.retrofit2:converter-gson:2.5.0"
    implementation "com.squareup.retrofit2:adapter-rxjava2:2.5.0"
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.0.0'

    //Dagger2
    implementation "com.google.dagger:dagger:2.21"
    implementation "com.google.dagger:dagger-android:2.21"
    implementation "com.google.dagger:dagger-android-support:2.21"
    kapt "com.google.dagger:dagger-compiler:2.21"
    kapt "com.google.dagger:dagger-android-processor:2.21"
    implementation 'com.google.android.material:material:1.0.0'

    //Mockito
    testImplementation 'org.mockito:mockito-core:2.19.0'
    ...
}
```

Libraries (Project level build.gradle)

```
buildscript {
    ext.kotlin_version = '1.3.41'
    dependencies {
        ...
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        ...
    }
}
```

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* https://www.countryflags.io
* https://medium.com/@dbottillo/how-to-unit-test-your-rxjava-code-in-kotlin-d239364687c9
* https://medium.com/@PaulinaSadowska/writing-unit-tests-on-asynchronous-events-with-rxjava-and-rxkotlin-1616a27f69aa
* https://www.raywenderlich.com/262-dependency-injection-in-android-with-dagger-2-and-kotlin

