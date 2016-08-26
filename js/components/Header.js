import React, {
	PropTypes
} from 'react';
import {
	StyleSheet,
	ToolbarAndroid,
	Platform,
	View,
	Text
} from 'react-native';
import {
	naviGoBack
} from '../utils/CommonUtil';

const iconLeft = require('../image/icon_left.png');

const propTypes = {
	title: PropTypes.string,
	actions: PropTypes.array,
	navigator: PropTypes.object,
	onActionSelected: PropTypes.func,
	onIconClicked: PropTypes.func,
	navIcon: PropTypes.number
};

const Header = ({
	title,
	actions,
	navigator,
	onActionSelected,
	onIconClicked,
	navIcon
}) => {
	const handleIconClicked = () => {
		if (onIconClicked) {
			onIconClicked();
		} else if (navigator) {
			naviGoBack(navigator);
		}
	};

	const renderToolbarAndroid = () => (
		<ToolbarAndroid
      style={styles.toolbar}
      actions={actions}
      onActionSelected={onActionSelected}
      onIconClicked={handleIconClicked}
      navIcon={navIcon === undefined ? iconLeft : navIcon}
      titleColor="#fff"
      title={title}
    />
	);



	const Toolbar = Platform.select({
		android: () => renderToolbarAndroid()
	});

	return <Toolbar />;
};

const styles = StyleSheet.create({
	toolbar: {
		flexDirection: 'row',
		backgroundColor: '#3F51B5',
		alignItems: 'center',
		height: 58
	}
});

Header.propTypes = propTypes;

Header.defaultProps = {
	onActionSelected() {},
	title: '',
	actions: []
};

export default Header;