import loginApi from "@/api/loginApi";
import router from "@/router";
import tool from "@/utils/tool";
import { message } from "ant-design-vue";
import { useGlobalStore } from "@/store";
import routerUtil from "@/utils/routerUtil";
import userCenterApi from "@/api/userCenterApi";

export const afterLogin = async (loginToken) => {
	tool.data.set("TOKEN", loginToken);
	// 获取登录的用户信息
	const loginUser = await loginApi.getLoginUser();
	const globalStore = useGlobalStore();
	globalStore.setUserInfo(loginUser);
	tool.data.set("USER_INFO", loginUser);

	// 获取用户的菜单
	const menu = await userCenterApi.userLoginMenu();
	let indexMenu = routerUtil.getIndexMenu(menu).path;
	tool.data.set("MENU", menu);

	// 重置系统默认应用
	tool.data.set("HPSYS_MENU_MODULE_ID", globalStore.module);
	message.success("登录成功");
	if (menu) {
		let routerTag = 0;
		menu.forEach((item) => {
			if (item.children) {
				if (JSON.stringify(item.children).indexOf(indexMenu) > -1) {
					++routerTag;
				}
			}
		});
		if (routerTag === 0) {
			// 取首页
			indexMenu = routerUtil.getIndexMenu(menu).path;
		}
	}
	await router.replace({
		path: indexMenu,
	});
};
