import React, {
	Component
} from 'react';
import {
	ActivityIndicator,
	Text,
	StyleSheet,
	View
} from 'react-native';
class LoadingView extends Component {
	render() {
		return (
			<View style={styles.containers}>
				<ActivityIndicator size='large' color='#FF4081' />
				<Text style={ styles.text }>数据加载中...</Text>
			</View>
		)


	}
}

const styles = StyleSheet.create({
	containers: {
		flex: 1,
		alignItems: 'center',
		justifyContent: 'center'
	},
	text: {
		marginTop: 10,
		textAlign: 'center'
	}
})

export default LoadingView;