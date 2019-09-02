import React, { Component } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  Text,
  TouchableOpacity
} from 'react-native';
import ImmersiveMode from 'react-native-immersive-mode';

export default class App extends Component {

  state = {
    color: '#ff0000',
  }

  buttonImmersiveMode = [
    'Normal',
    'Full',
    'FullSticky',
    'Bottom',
    'BottomSticky',
  ]

  componentDidMount() {
    ImmersiveMode.fullLayout(true);
    this.listen = ImmersiveMode.addEventListener((e) => {
      /**
       * e = {
       *  statusBar: boolean,
       *  navigationBottomBar: boolean,
       * }
       */
      console.log(e)
    })
  }

  componentWillUnmount() {
    this.listen.remove();
    ImmersiveMode.fullLayout(false);
  }

  render() {
    return (
      <KeyboardAvoidingView style={{ flex: 1 }} behavior='padding'>
        <SafeAreaView style={styles.container}>
          <Text style={{ fontSize: 30 }}>Main</Text>

          {
            this.buttonImmersiveMode.map(v =>
              <TouchableOpacity key={'key' + v} style={styles.button} onPress={() => {
                ImmersiveMode.setBarMode(v);
              }}>
                <Text>{v}</Text>
              </TouchableOpacity>
            )
          }

          <TextInput
            style={{
              minWidth: 100,
              padding: 4,
              marginHorizontal: 16,
              backgroundColor: '#aaa',
              fontSize: 16,
              borderRadius: 4,
            }}
            numberOfLines={1}
            value={this.state.color}
            onChangeText={(t) => this.setState({ color: t })}
            maxLength={9} />
          <TouchableOpacity style={styles.button} onPress={() => {
            ImmersiveMode.setBarColor(this.state.color);
          }}>
            <Text>Bar color {this.state.color}</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => {
            ImmersiveMode.setBarColor(null);
          }}>
            <Text>Bar color default</Text>
          </TouchableOpacity>

          <View style={{
            flexDirection: 'row'
          }}>
            <TouchableOpacity style={styles.button} onPress={() => {
              ImmersiveMode.setBarStyle('Dark');
            }}>
              <Text>Bar Style Dark</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.button} onPress={() => {
              ImmersiveMode.setBarStyle('Light');
            }}>
              <Text>Bar Style Light</Text>
            </TouchableOpacity>
          </View>

          <View style={{
            flexDirection: 'row'
          }}>
            <TouchableOpacity style={styles.button} onPress={() => {
              ImmersiveMode.setBarTranslucent(true);
            }}>
              <Text>Translucent On</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.button} onPress={() => {
              ImmersiveMode.setBarTranslucent(false);
            }}>
              <Text>Translucent Off</Text>
            </TouchableOpacity>
          </View>

        </SafeAreaView>
      </KeyboardAvoidingView>
    );
  }
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#ddd',
  },
  button: {
    margin: 8,
    paddingVertical: 8,
    paddingHorizontal: 16,
    borderRadius: 4,
    backgroundColor: '#5fba7d',
  }
});
