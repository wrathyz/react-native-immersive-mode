import React, { Component } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  Text,
  TouchableOpacity
} from 'react-native';
import ImmersiveMode from 'react-native-immersive-mode';

export default class App extends Component {

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
      <SafeAreaView style={styles.container}>
        <Text style={{fontSize: 30}}>Main</Text>
        <TouchableOpacity style={styles.button} onPress={() => {
          ImmersiveMode.setImmersive(ImmersiveMode.Normal)
        }}>
          <Text>Immersive Normal</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => {
          ImmersiveMode.setImmersive(ImmersiveMode.Full);
        }}>
          <Text>Immersive Full</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => {
          ImmersiveMode.setImmersive(ImmersiveMode.FullSticky);
        }}>
          <Text>Immersive FullSticky</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => {
          ImmersiveMode.setImmersive(ImmersiveMode.Bottom);
        }}>
          <Text>Immersive Bottom</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => {
          ImmersiveMode.setImmersive(ImmersiveMode.BottomSticky);
        }}>
          <Text>Immersive BottomSticky</Text>
        </TouchableOpacity>
      </SafeAreaView>
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
