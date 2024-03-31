import { baseRequest } from "@/utils/request";

const request = (url, ...arg) => baseRequest(`/userCenter/` + url, ...arg);

// 用户控制器
export default {
	// 修改用户密码
	userUpdatePassword(data) {
		return request("updatePassword", data);
	},
	// 修改用户头像
	userUpdateAvatar(data) {
		return request("updateAvatar", data);
	},
	// 获取登录用户的菜单
	userLoginMenu(data) {
		return request("loginMenu", data, "get");
	},
	// 编辑个人信息
	userUpdateUserInfo(data) {
		return request("updateUserInfo", data);
	},
	// 根据id集合获取角色集合
	userCenterGetRoleListByIdList(data) {
		return request("getRoleListByIdList", data);
	},
};
