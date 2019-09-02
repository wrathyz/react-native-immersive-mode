
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

### Bar Mode 
- **Normal** - show status and navigation 
- **Full**  - hide status and navigation
- **FullSticky** - hide status and navigation with sticky
- **Bottom** - hide navigation
- **BottomSticky** - hide navigation with sticky

### Bar Style
- **Dark**
- **Light**

### Methods

 - [fullLayout](#fullLayout)
 - [setBarMode](#setBarMode)
 - [setBarStyle](#setBarStyle)
 - [setBarTranslucent](#setBarTranslucent)
 - [setBarColor](#setBarColor)
 - [addEventListener](#addEventListener)

### fullLayout
`fullLayout(full: boolean): void`
use all area of screen

| name | type | description |
| ---- | ---- | ------------|
| full | boolean | `true` use all area of screen, `false` not include status and navigation bar |

```javascript
// should set full layout in componentDidMount
componentDidMount() {
	ImmersiveMode.fullLayout(true);
}
// and should restore layout
componentWillUnmount() {
	ImmersiveMode.fullLayout(false);
}
```

### setBarMode
`setBarMode(mode: string): void`
change status and navigation bar mode

**Note**. mode sticky will be disabled bar color.

| name | type | description |
| ---- | ---- | ------------|
| mode | string | [Bar Mode](#bar-mode) |

```javascript
// Deprecated
ImmersiveMode.setImmersive(ImmersiveMode.Normal);

// Use this instead
ImmersiveMode.setBarMode('Normal');
```

### setBarStyle
`setBarStyle(style: string): void`
chnage status and navigation style

**Note**. To change system Navigation(bottom) to Light, must be change bar color `setBarColor` to other color first.

| name | type | description |
| ---- | ---- | ------------|
| mode | string | [Bar Style](#bar-style) |

```javascript
ImmersiveMode.setBarStyle('Dark');
```

### setBarTranslucent
`setBarTranslucent(translucent: boolean): void`
change status and navigation bar is transparent 50%.

**Note**. When `true` bar color will be disabled.

| name | type | description |
| ---- | ---- | ------------|
| translucent | booelan |  |

```javascript
ImmersiveMode.setBarTranslucent(true);
```

### setBarColor
`setBarColor(color: string): void`
change status and navigation bar is transparent 50%.

| name | type | description |
| ---- | ---- | ------------|
| color | string | `#rgb`, `#rrggbb`, `#rrggbbaa`. if color is `null` will set default color |

> default color is color before changed by `setBarColor`

```javascript
ImmersiveMode.setBarColor('#ff0000');
```

### addEventListener
`addEventListener(callback: function): EmitterSubscription`
trigger event when bar visibility change (mode sticky not trigged)

| name | type | params | description |
| ---- | ---- | ------ | ------------|
| callback | function | (statusBar: boolean, navigationBottomBar: boolean) | description |

```javascript
componentDidMount() {
	this.listen = ImmersiveMode.addEventListener((e) => {
		console.log(e)
	})
}

componentWillUnmount() {
	this.listen.remove();
}
```