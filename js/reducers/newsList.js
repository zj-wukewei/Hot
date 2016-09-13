import * as types from '../constants/ActionTypes';

const initialState = {
	loading: true,
	isRefresh: false,
	isLoadMore: false,
	newsList: []
}

export default function revicesNewsList(state = initialState, action) {
	switch (action.type) {
		case types.FE_NEWLIST:
			return Object.assign({}, state, {
				loading: action.loading,
				isRefresh: action.isRefresh,
				isLoadMore: action.isLoadMore,

			});
		case types.RE_NEWLIST:
			return Object.assign({}, state, {
				loading: false,
				isRefresh: false,
				isLoadMore: false,
				newsList: state.isLoadMore ? state.newsList.concat(action.newsList) : action.newsList,
			});
		default:
			return state;
	}
}