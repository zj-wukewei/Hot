'use strict'
import React, {
  Component
} from 'react';
import {
  StyleSheet,
  WebView,
  View
} from 'react-native';
import Header from '../components/Header.js';
import LoadingView from '../components/LoadingView.js';
class WebViewPage extends Component {
    render() {
        const {
            navigator,
            route
        } = this.props;
        return (
            <View style={styles.container}>
               <Header title={route.title} navigator={navigator}></Header>
               <WebView ref="webview"
                 automaticallyAdjustContentInsets={false}
                 style={{flex: 1}}
                 source={{uri: route.url}}
                 javaScriptEnabled={true}
                 domStorageEnabled={true}
                 startInLoadingState={true}
                 scalesPageToFit={true}
                 decelerationRate="normal"
                 renderLoading={this.renderLoading.bind(this)}>
               </WebView>
            </View>
        );
    }
    renderLoading() {
        return <LoadingView/>
    }
}
const styles = StyleSheet.create({
    container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
  },
});
export default WebViewPage;