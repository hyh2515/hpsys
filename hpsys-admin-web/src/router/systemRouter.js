import tool from "@/utils/tool";
import routerUtil from "@/utils/routerUtil";
import config from "@/config";

// 系统路由
const routes = [
	{
		name: "layout",
		path: "/",
		component: () => import("@/layout/index.vue"),
		redirect: tool.data.get("MENU")
			? routerUtil.getIndexMenu(tool.data.get("MENU")).path
			: config.DASHBOARD_URL,
		children: [],
	},
	{
		path: "/login",
		component: () => import("@/views/login/login.vue"),
		meta: {
			title: "登录",
		},
	},
];

export default routes;
