
# react-native-immersive-mode
[![npm version](https://badge.fury.io/js/react-native-immersive-mode.svg)](https://badge.fury.io/js/react-native-immersive-mode)

## Getting started

`$ npm install react-native-immersive-mode --save`

### Mostly automatic installation

Note. react-native `>0.60` no need to link

`$ react-native link react-native-immersive-mode`

### Manual installation


#### Android

1. Append the following lines to `android/settings.gradle`
  	```
	include ':react-native-immersive-mode'
	project(':react-native-immersive-mode').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-immersive-mode/android')
  	```
2. Insert the following lines inside the dependencies block in `android/app/build.gradle`
  	```
	implementation project(':react-native-immersive-mode')
  	```
3. Add it to your `MainApplication.java`
	```
	import com.rnimmersivemode.RNImmersiveModePackage;	// add this

	public class MainActivity extends ReactActivity {
		@Override
		protected List<ReactPackage> getPackages() {
			return Arrays.<ReactPackage>asList(
				new MainReactPackage(),
				new RNImmersiveModePackage()	// add this
			);
		}
	}
	```
## Usage


### Full Layout

```javascript
import ImmersiveMode from 'react-native-immersive-mode';

componentDidMount() {
	ImmersiveMode.fullLayout(true);
}

componentWillUnmount() {
	ImmersiveMode.fullLayout(false);
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
