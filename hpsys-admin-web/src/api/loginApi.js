import { moduleRequest } from "@/utils/request.js";
const request = moduleRequest(`/auth/`);
// 登录
export default {
	// 账号密码登录
	login(data) {
		return request("doLogin", data, "post", false);
	},
	logout(data) {
		return request("doLogout", data, "get");
	},
	// 获取用户信息
	getLoginUser(data) {
		return request("getLoginUser", data, "get");
	},
};
