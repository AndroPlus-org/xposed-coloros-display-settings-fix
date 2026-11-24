# xposed-coloros-display-settings-fix

ColorOS で「システム最適化を無効にする」（認可の監視を無効化）をオンにしていると、設定の「ディスプレイと明るさ」が AOSP 風の画面になってしまう問題をシステム最適化は無効のまま直す Xposed / LSPosed モジュール。

## 原因
`com.oplus.settings.feature.display.DisplaySettingsFragment#onCreate` は、`OplusActivityManager#isPermissionInterceptEnabled()` が false のとき AOSP の `com.android.settings.DisplaySettings` をコンテンツ領域に重ねて表示する（CTS 向けの挙動と思われる）。

## 仕組み
`DisplaySettingsFragment#showFragment(Fragment, int)` をフックし、渡されたのが `com.android.settings.DisplaySettings` のときだけ表示を抑止する。`isPermissionMonitorEnable()` 自体は変更しないため、他の画面でのシステム最適化無効の挙動は変わらない。

## 使い方
1. APK をインストールし、LSPosed / Vector でモジュールを有効化
2. スコープに「設定（com.android.settings）」を指定
3. 設定アプリを強制停止して開き直す

ログ: `adb logcat | grep OplusDisplaySettingsFix`

## 動作確認
- OPPO Find N6（CPH2765_16.0.10.500(EX01)）/ ColorOS 16 / Vector v2.2

## ビルド
JDK 27 では jlink で失敗するため Android Studio 同梱の JBR を使う。

```
JAVA_HOME="C:/Program Files/Android/Android Studio/jbr" ./gradlew assembleRelease
```
