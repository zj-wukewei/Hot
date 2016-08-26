'use strict'
import {
	combineReducers
} from 'redux';
import news from './newsList.js';
const rootReduer = combineReducers({
	news,
});

export default rootReduer;