import { baseRequest } from "@/utils/request";

const request = (url, ...arg) => baseRequest(`/user/` + url, ...arg);
export default {
	// 获取用户分页
	userPage(data) {
		return request("page", data, "get");
	},
	// 提交表单 edit为true时为编辑，默认为新增
	submitForm(data, edit = false) {
		return request(edit ? "edit" : "add", data);
	},
	// 删除用户
	userDelete(data) {
		return request("delete", data);
	},
	// 获取用户详情
	userDetail(data) {
		return request("detail", data, "get");
	},
	// 用户拥有角色
	userOwnRole(data) {
		return request("ownRole", data, "get");
	},
};
