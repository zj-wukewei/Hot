const HOST = "http://apis.baidu.com/";
import FetchHttpClient, {
	query,
	header
} from 'fetch-http-client';
import {
	toastShort
} from "./ToastUtil.js";

let client = new FetchHttpClient(HOST);

export function getNewList(url, pnSize) {

	return getClient().get(url, {
			query: {
				num: 10,
				pn: pnSize
			},
		}).then(filterJSON)
		.then(filterStatus)
		.catch((error) => {
			toastShort('网络发生错误,请重试!')
		});
}

function getClient() {
	client.addMiddleware(query());
	client.addMiddleware(request => {
		request.options.headers['apikey'] = 'ff27ef67506b2c0738a3252b01f8d564';
	});
	return client;
}

function filterStatus(res) {
	if (res.code === 200) {
		return res.newslist;
	} else {
		toastShort(res.msg);
	}
}

function filterJSON(res) {
	return res.json();
}