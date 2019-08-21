import { EmitterSubscription } from 'react-native'

type ImmersiveModeType = {
    Normal: String,
    Full: String,
    FullSticky: String,
    Bottom: String,
    BottomSticky: String,
}

type BarViisibilityType = {
    statusBar: boolean,
    navigationBottomBar: boolean
}

interface ImmersiveModeStatic extends ImmersiveModeType {
    fullLayout(full: boolean): void;

    setImmersive(mode: ImmersiveModeType): void;

    addEventListener(callback: (viisibility: BarViisibilityType) => void): EmitterSubscription;
}
declare const ImmersiveMode: ImmersiveModeStatic;
export default ImmersiveMode;