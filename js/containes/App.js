import React from 'react';
import {
	StyleSheet,
	Navigator,
	StatusBar,
	BackAndroid,
	View
} from 'react-native';
import {
	naviGoBack
} from '../utils/CommonUtil.js';

import NewsContainer from "./NewsContaines.js"

var _navigator;
class App extends React.Component {
	constructor(props) {
		super(props);
		this.renderScene = this.renderScene.bind(this);
		this.goBack = this.goBack.bind(this);
		this.configureScene = this.configureScene.bind(this);
		BackAndroid.addEventListener('hardwareBackPress', this.goBack);
	}

	goBack() {
		return naviGoBack(_navigator);
	}

	renderScene(route, navigator) {
		let Component = route.component;
		_navigator = navigator;
		return (<Component navigator={navigator} route={route} />);
	}

	configureScene(route) {
		if (route.sceneConfig) {
			return route.sceneConfig
		}

		return Navigator.SceneConfigs.PushFromRight;
	}

	render() {
		return (
			<Navigator
				style={styles.navigator}
				configureScene={this.configureScene}
				renderScene={this.renderScene}
				initialRoute = {{
					component: NewsContainer,
					name: 'NewsContainer'
				}}
				/>
		);
	}
}

const styles = StyleSheet.create({
	navigator: {
		flex: 1
	}
});

export default App;