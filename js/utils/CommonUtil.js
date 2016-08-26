'use strict'
import {
	BackAndroid
} from 'react-native';
export const naviGoBack = (navigator) => {
	if (navigator && navigator.getCurrentRoutes().length > 1) {
		navigator.pop();
		return true;
	} else {
		BackAndroid.exitApp();
		return false;
	}

};