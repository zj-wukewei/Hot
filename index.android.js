/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {
  Component
} from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

class Hot extends Component {

  constructor(props) {
    super(props); //这一句不能省略，照抄即可s
  }

  render() {
    return (
      <View style={styles.container}>
             <Text>helloworld</Text>
     </View>
    )
  }

}



const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  }
});



AppRegistry.registerComponent('MyHot', () => Hot);