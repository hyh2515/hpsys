import { baseRequest } from "@/utils/request.js";

const request = (url, ...arg) => baseRequest(`/menu/` + url, ...arg);

export default {
	// 获取菜单树
	menuTree(data) {
		return request("tree", data, "get");
	},
	// 获取菜单详情
	menuDetail(data) {
		return request("detail", data, "get");
	},
};
