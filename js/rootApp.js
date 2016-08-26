import React, {
	Component
} from 'react';
import {
	Provider
} from 'react-redux'
import configureStore from './store/store.js'

import App from './containes/App.js'

const store = configureStore();

class rootApp extends Component {
	render() {
		return (
			<Provider store={store}>
               <App/>
            </Provider>
		)
	}
}
export default rootApp;