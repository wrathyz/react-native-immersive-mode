
# react-native-immersive-mode

## Getting started

`$ npm install react-native-immersive-mode --save`

### Mostly automatic installation

`$ react-native link react-native-immersive-mode`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-immersive-mode` and add `RNImmersiveMode.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNImmersiveMode.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.rnimmersivemode.RNImmersiveModePackage;` to the imports at the top of the file
  - Add `new RNImmersiveModePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-immersive-mode'
  	project(':react-native-immersive-mode').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-immersive-mode/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-immersive-mode')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNImmersiveMode.sln` in `node_modules/react-native-immersive-mode/windows/RNImmersiveMode.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Immersive.Mode.RNImmersiveMode;` to the usings at the top of the file
  - Add `new RNImmersiveModePackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNImmersiveMode from 'react-native-immersive-mode';

// TODO: What to do with the module?
RNImmersiveMode;
```
  