package com.rnimmersivemode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class RNImmersiveModeModule extends ReactContextBaseJavaModule {

    private static final String ModuleName = "RNImmersiveMode";
    private int currentLayout = View.VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    private int currentMode = View.VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    private int currentStatusStyle = View.VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    private int currentNavigationStyle = View.VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

    private boolean hasColorChange = false;
    private int defaultStatusColor;
    private int defaultNavigationColor;

    RNImmersiveModeModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return ModuleName;
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();

        //mode
        constants.put("Normal", ImmersiveMode.Normal);
        constants.put("Full", ImmersiveMode.Full);
        constants.put("FullSticky", ImmersiveMode.FullSticky);
        constants.put("Bottom", ImmersiveMode.Bottom);
        constants.put("BottomSticky", ImmersiveMode.BottomSticky);

        //event
        constants.put("OnSystemUiVisibilityChange", ImmersiveEvent.OnSystemUiVisibilityChange);
        return constants;
    }

    @ReactMethod
    public void fullLayout(final Boolean fullscreen) {
        if (fullscreen) {
            // set layout fullscreen (including layout navigation bar on bottom)
            this.currentLayout = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        } else {
            // set layout normal (not including layout navigation bar on bottom)
            this.currentLayout = View.VISIBLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }

        this.setUiOnUiThread();
    }

    @ReactMethod
    public void setBarMode(String immersive) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Log.w(ModuleName, "Sdk Version must be >= " + Build.VERSION_CODES.KITKAT);
            return;
        }

        switch (immersive) {
            case BarMode.Normal:
                this.currentMode = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                break;
            case BarMode.Full:
                this.currentMode = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
                break;
            case BarMode.FullSticky:
                this.currentMode = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                break;
            case BarMode.Bottom:
                this.currentMode = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
                break;
            case BarMode.BottomSticky:
                this.currentMode = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                break;
            default:
        }

        this.setUiOnUiThread();
    }

    @ReactMethod
    public void setBarColor(final String hexColor) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Activity activity = getCurrentActivity();
                if (activity != null) {
                    Window window = activity.getWindow();
                    if (window != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                            try {
                                if (hexColor != null) {
                                    if (!hasColorChange) {
                                        hasColorChange = true;
                                        defaultStatusColor = window.getStatusBarColor();
                                        defaultNavigationColor = window.getNavigationBarColor();
                                    }

                                    window.setStatusBarColor(Color.parseColor(hexColor));
                                    window.setNavigationBarColor(Color.parseColor(hexColor));
                                } else if (hasColorChange) {
                                    hasColorChange = false;
                                    window.setStatusBarColor(defaultStatusColor);
                                    window.setNavigationBarColor(defaultNavigationColor);
                                }
                            } catch (Exception e) {
                                Log.e(ModuleName, e.getMessage());
                            }

                        } else {
                            Log.w(ModuleName, "Sdk Version must be >= " + Build.VERSION_CODES.LOLLIPOP);
                        }
                    }
                }
            }
        });
    }

    @ReactMethod
    public void setBarStyle(final String style) {
        switch (style) {
            case BarStyle.Dark:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.currentStatusStyle = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    Log.w(ModuleName, "Sdk Version must be >= " + Build.VERSION_CODES.M);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.currentNavigationStyle = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                } else {
                    Log.w(ModuleName, "Sdk Version must be >= " + Build.VERSION_CODES.O);
                }
                break;
            case BarStyle.Light:
                this.currentStatusStyle = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                this.currentNavigationStyle = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                break;
        }

        this.setUiOnUiThread();
    }

    @ReactMethod
    public void setBarTranslucent(final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Activity activity = getCurrentActivity();
                if (activity != null) {
                    Window window = activity.getWindow();
                    if (window != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (enable) {
                                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                            } else {
                                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                            }
                        }
                    }
                }
            }
        });
    }

    @ReactMethod
    private void setOnSystemUiVisibilityChangeListener() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Activity activity = getCurrentActivity();
                if (activity != null) {
                    Window window = activity.getWindow();
                    if (window != null) {
                        View view = window.getDecorView();
                        view.setOnSystemUiVisibilityChangeListener(
                                new View.OnSystemUiVisibilityChangeListener() {
                                    @Override
                                    public void onSystemUiVisibilityChange(int visibility) {
                                        WritableMap params = Arguments.createMap();

                                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                            params.putBoolean("statusBar", true);
                                        } else {
                                            params.putBoolean("statusBar", false);
                                        }

                                        if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
                                            params.putBoolean("navigationBottomBar", true);
                                        } else {
                                            params.putBoolean("navigationBottomBar", false);
                                        }

                                        sendEvent(getReactApplicationContext(),
                                                params);
                                    }
                                });
                    }
                }
            }
        });
    }

    private void setUiOnUiThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Activity activity = getCurrentActivity();
                if (activity != null) {
                    Window window = activity.getWindow();
                    if (window != null) {
                        View view = window.getDecorView();
                        view.setSystemUiVisibility(currentLayout | currentMode | currentStatusStyle | currentNavigationStyle);
                    }
                }
            }
        });
    }

    private void sendEvent(ReactContext reactContext,
                           WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(ImmersiveEvent.OnSystemUiVisibilityChange, params);
    }
}