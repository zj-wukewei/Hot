import {
	ToastAndroid
} from 'react-native';

export const toastShort = (content) => {
	if (content != undefined) {
		ToastAndroid.show(content, ToastAndroid.SHORT);
	}
}

export const toastLong = (content) => {
	if (content != undefined) {
		ToastAndroid.show(content, ToastAndroid.LONG);
	}
}