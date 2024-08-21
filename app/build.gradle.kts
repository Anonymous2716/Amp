plugins {
    id("com.android.application")
}

android {
    namespace = "aqh.ui.power"
    compileSdk = 34

    defaultConfig {
        applicationId = "aqh.ui.power"
        minSdk = 22
        targetSdk = 34
        versionCode = 1
        versionName = "2716"

    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
