import * as types from "../constants/ActionTypes.js";
import * as Services from "../utils/Services.js";
import {
	NEW_LIST
} from "../constants/Urls.js";


export function fetchNews(loading, isRefresh, isLoadMore, pn) {
	return dispatch => {
		dispatch(fetchNewsList(loading, isRefresh, isLoadMore));
		Services.getNewList(NEW_LIST, pn)
			.then(result => {
				dispatch(receiveNewsList(result));
			});
	}
}

function fetchNewsList(loading, isRefresh, isLoadMore) {
	return {
		type: types.FE_NEWLIST,
		loading: loading,
		isRefresh: isRefresh,
		isLoadMore: isLoadMore
	};
}

function receiveNewsList(newsList) {
	return {
		type: types.RE_NEWLIST,
		newsList: newsList
	}
}