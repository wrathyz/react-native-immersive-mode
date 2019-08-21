
# react-native-immersive-mode

## Getting started

`$ npm install react-native-immersive-mode --save`

### Mostly automatic installation

Note. react-native `>0.60` no need to link

`$ react-native link react-native-immersive-mode`

### Manual installation


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
      implementation project(':react-native-immersive-mode')
  	```

## Usage


### Full Layout

```javascript
import ImmersiveMode from 'react-native-immersive-mode';

componentDidMount() {
	ImmersiveMode.fullLayout(true);
}

componentWillUnmount() {
	ImmersiveMode.fullLayout(true);
}
```

### Immersive

Immersive will change ui system (status bar or navigation bar bottom)

```javascript
import ImmersiveMode from 'react-native-immersive-mode';

ImmersiveMode.setImmersive(ImmersiveMode.Normal)
ImmersiveMode.setImmersive(ImmersiveMode.Full);
ImmersiveMode.setImmersive(ImmersiveMode.FullSticky);
ImmersiveMode.setImmersive(ImmersiveMode.Bottom);
ImmersiveMode.setImmersive(ImmersiveMode.BottomSticky);
```

### Event 

addEventListener will visibility of layout `statusBar` and `navigationBottomBar`

```javascript
import ImmersiveMode from 'react-native-immersive-mode';

componentDidMount() {
	this.listen = ImmersiveMode.addEventListener((e) => {
		console.log(e)
	})
}

componentWillUnmount() {
	this.listen.remove();
}
```