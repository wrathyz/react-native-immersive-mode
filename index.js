import { NativeModules, DeviceEventEmitter } from 'react-native';

const { RNImmersiveMode } = NativeModules;

const checkModule = () => {
    if (!RNImmersiveMode) {
        throw Error('RNImmersiveMode is not properly linked');
    }
}

const ImmersiveMode = {
    ...RNImmersiveMode, // for constants

    fullLayout(full) {
        checkModule();
        RNImmersiveMode.fullLayout(full);
    },

    setImmersive(mode) {
        checkModule();
        RNImmersiveMode.setImmersive(mode);
    },

    addEventListener(callback) {
        checkModule();
        if(typeof callback !== 'function') return;

        RNImmersiveMode.setOnSystemUiVisibilityChangeListener();

        const subscription = DeviceEventEmitter.addListener(
            RNImmersiveMode.OnSystemUiVisibilityChange,
            (e) => callback(e));

        return subscription;
    }
}

export default ImmersiveMode;