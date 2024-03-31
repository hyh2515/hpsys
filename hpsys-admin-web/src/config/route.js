// 静态路由配置
const routes = {
	// 默认路由, 仅限于后端未添加任何单页配置
	module: [
		{
			id: "01",
			name: "homeModule",
			path: "/homeModule",
			component: "",
			meta: {
				title: "默认",
				type: "module",
				icon: "bank-outlined",
			},
			chirdren: [],
		},
	],
	// 个人中心
	menu: [
		{
			id: "001",
			name: "usercenter",
			path: "/usercenter",
			component: "user/index",
			meta: {
				title: "用户中心",
				type: "menu",
				hidden: true,
			},
		},
	],
};

export default routes;
