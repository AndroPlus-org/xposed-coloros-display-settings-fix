package jp.androplus.coloros.displaysettings;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * ColorOS の設定アプリ（com.oplus.settings.feature.display.DisplaySettingsFragment#onCreate）は、
 * 権限監視（OplusActivityManager#isPermissionInterceptEnabled）が OFF だと
 * AOSP の com.android.settings.DisplaySettings をコンテンツ領域に重ねて表示する。
 * 権限監視は OFF のまま、この重ね表示だけを抑止する。
 */
public class Hook implements IXposedHookLoadPackage {
    private static final String TAG = "OplusDisplaySettingsFix: ";
    private static final String SETTINGS = "com.android.settings";
    private static final String OPLUS_FRAGMENT = "com.oplus.settings.feature.display.DisplaySettingsFragment";
    private static final String AOSP_FRAGMENT = "com.android.settings.DisplaySettings";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!SETTINGS.equals(lpparam.packageName)) return;
        ClassLoader cl = lpparam.classLoader;
        try {
            Class<?> fragment = XposedHelpers.findClass("androidx.fragment.app.Fragment", cl);
            XposedHelpers.findAndHookMethod(OPLUS_FRAGMENT, cl, "showFragment",
                    fragment, int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            Object f = param.args[0];
                            if (f != null && AOSP_FRAGMENT.equals(f.getClass().getName())) {
                                param.setResult(null);
                            }
                        }
                    });
            XposedBridge.log(TAG + "hooked " + OPLUS_FRAGMENT + "#showFragment");
        } catch (Throwable t) {
            XposedBridge.log(TAG + "failed to hook showFragment: " + t);
        }
    }
}
