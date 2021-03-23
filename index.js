import { NativeModules, DeviceEventEmitter, Platform } from 'react-native';

const { RNImmersiveMode } = NativeModules;

const checkModule = () => {
    if (Platform.OS === 'android' && !RNImmersiveMode) {
        throw Error('RNImmersiveMode is not properly linked');
    }
    // else: (maybe iOS)

    return Platform.OS === 'android' && RNImmersiveMode;
}

const ImmersiveMode = {

    fullLayout(full) {
        if (checkModule()) {
            RNImmersiveMode.fullLayout(full);
        }
    },

    setBarMode(mode) {
        if (checkModule()) {
            RNImmersiveMode.setBarMode(mode);
        }
    },

    setBarStyle(style) {
        if (checkModule()) {
            RNImmersiveMode.setBarStyle(style);
        }
    },

    setBarTranslucent(enable) {
        if (checkModule()) {
            RNImmersiveMode.setBarTranslucent(enable);
        }
    },

    setBarColor(color) {
        if (checkModule()) {
            if (typeof color === 'string') {
                if (color.length === 9) {
                    // convert #rgba to #argb
                    color = '#' + color.substr(7, 2) + color.substr(1, 6);
                } else if (color.length === 4) {
                    color = '#' + color[1] + color[1] + color[2] + color[2] + color[3] + color[3];
                }
            }

            RNImmersiveMode.setBarColor(color);
        }
    },

    setBarDefaultColor() {
        if (checkModule()) {
            RNImmersiveMode.setBarColor(null);
        }
    },

    addEventListener(callback) {
        if (checkModule()) {
            if (typeof callback !== 'function') return;

            RNImmersiveMode.setOnSystemUiVisibilityChangeListener();

            const subscription = DeviceEventEmitter.addListener(
                RNImmersiveMode.OnSystemUiVisibilityChange,
                (e) => callback(e));

            return subscription;
        }
    }
}

export default ImmersiveMode;
