plugins { id("com.android.application") }

android {
    namespace = "jp.androplus.coloros.displaysettings"
    compileSdk = 36
    defaultConfig {
        applicationId = "jp.androplus.coloros.displaysettings"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
}
