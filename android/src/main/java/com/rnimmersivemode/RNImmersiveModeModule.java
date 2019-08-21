package com.rnimmersivemode;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;

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
            this.setUiOnUiThread(this.currentLayout);
        } else {
            // set layout normal (not including layout navigation bar on bottom)
            this.currentLayout = View.VISIBLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            this.setUiOnUiThread(this.currentLayout);
        }
    }

    @ReactMethod
    public void setImmersive(int immersive) {
        int sysUi;
        switch (immersive) {
            case ImmersiveMode.Normal:
                this.setUiOnUiThread(this.currentLayout);
                break;
            case ImmersiveMode.Full:
                sysUi = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
                this.setUiOnUiThread(this.currentLayout | sysUi);
                break;
            case ImmersiveMode.FullSticky:
                sysUi = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                this.setUiOnUiThread(this.currentLayout | sysUi);
                break;
            case ImmersiveMode.Bottom:
                sysUi = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
                this.setUiOnUiThread(this.currentLayout | sysUi);
                break;
            case ImmersiveMode.BottomSticky:
                sysUi = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                this.setUiOnUiThread(this.currentLayout | sysUi);
                break;
            default:
        }
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
                        if (view != null) {
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
                                                    ImmersiveEvent.OnSystemUiVisibilityChange,
                                                    params);
                                        }
                                    });
                        }
                    }
                }
            }
        });
    }

    private void setUiOnUiThread(final int visibility) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Activity activity = getCurrentActivity();
                if (activity != null) {
                    Window window = activity.getWindow();
                    if (window != null) {
                        View view = window.getDecorView();
                        if (view != null) {
                            view.setSystemUiVisibility(visibility);
                        }
                    }
                }
            }
        });
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}