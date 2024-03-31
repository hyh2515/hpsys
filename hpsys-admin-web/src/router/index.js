import nProgress from "nprogress";
import "nprogress/nprogress.css";
import systemRouter from "./systemRouter";
import { notification } from "ant-design-vue";
import { createRouter, createWebHistory } from "vue-router";
import whiteListRouters from "./whiteList";
import { useGlobalStore, searchStore } from "@/store";
import tool from "@/utils/tool";
import userRoutes from "@/config/route";
const modules = import.meta.glob("/src/views/**/**.vue");
import { cloneDeep } from "lodash-es";

// 进度条配置
nProgress.configure({ showSpinner: false, speed: 500 });

// 系统特殊路由
const routes_404 = [
	{
		path: "/:pathMatch(.*)*",
		hidden: true,
		component: () => import("@/layout/404.vue"),
	},
];
// 系统路由
const routes = [...systemRouter, ...whiteListRouters, ...routes_404];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

// 判断是否已经加载过动态/静态路由
const isGetRouter = ref(false);

// 白名单校验
const exportWhiteListFromRouter = (router) => {
	const res = [];
	for (const item of router) res.push(item.path);
	return res;
};
const whiteList = exportWhiteListFromRouter(whiteListRouters);

router.beforeEach(async (to, from, next) => {
	nProgress.start();
	const store = useGlobalStore();

	const sysBaseConfig =
		tool.data.get("HPSYS_SYS_BASE_CONFIG") || store.sysBaseConfig;
	// 动态标题
	document.title = to.meta.title
		? `${to.meta.title} - ${sysBaseConfig.HPSYS_SYS_NAME}`
		: `${sysBaseConfig.HPSYS_SYS_NAME}`;
	// 过滤白名单
	if (whiteList.includes(to.path)) {
		next();
		return false;
	}

	const token = tool.data.get("TOKEN");
	if (to.path === "/login") {
		// 当用户输入了login路由，将其跳转首页即可
		if (token) {
			next({
				path: "/",
			});
			return false;
		}
		// 删除路由(替换当前layout路由)
		router.addRoute(routes[0]);
		isGetRouter.value = false;
		next();
		return false;
	}
	if (!token) {
		next({
			path: "/login",
		});
		return false;
	}
	// 整页路由处理
	if (to.meta.fullpage) {
		to.matched = [to.matched[to.matched.length - 1]];
	}
	// 加载动态/静态路由
	if (!isGetRouter.value) {
		const apiMenu = [
			{ id: store.module, children: [], name: "房价预测", path: "/hhy" },
		];
		apiMenu[0].children = tool.data.get("MENU") || [];
		if (apiMenu[0].children.length === 0) {
			// 创建默认模块，显示默认菜单
			apiMenu[0] = cloneDeep(userRoutes.module[0]);
		}
		apiMenu[0].children = [
			...(apiMenu[0].children ? apiMenu[0].children : []),
			...userRoutes.menu,
		];
		let menuRouter = filterAsyncRouter(apiMenu);
		menuRouter = flatAsyncRoutes(menuRouter);
		menuRouter.forEach((item) => {
			router.addRoute("layout", item);
		});

		const search_store = searchStore();
		search_store.init(menuRouter);
		isGetRouter.value = true;
		next({ ...to, replace: true });
		return false;
	}
	next();
});

router.afterEach(() => {
	nProgress.done();
});

router.onError((error) => {
	nProgress.done();
	notification.error({
		message: "路由错误",
		description: error.message,
	});
});

// 入侵追加自定义方法、对象
router.getMenu = () => {
	const store = useGlobalStore();

	const apiMenu = [
		{ id: store.module, children: [], name: "房价预测", path: "/hhy" },
	];
	apiMenu[0].children = tool.data.get("MENU") || [];
	// 增加固定路由
	if (apiMenu.length === 0) {
		// 创建默认模块，显示默认菜单
		apiMenu[0] = cloneDeep(userRoutes.module[0]);
	}
	const childrenApiMenu = apiMenu[0].children;
	apiMenu[0].children = [
		...(childrenApiMenu ? childrenApiMenu : []),
		...userRoutes.menu,
	];
	return filterUrl(apiMenu);
};

const filterUrl = (map) => {
	const newMap = [];
	const traverse = (maps) => {
		maps &&
			maps.forEach((item) => {
				item.meta = item.meta ? item.meta : {};
				// 处理iframe
				if (item.meta.type === "iframe") {
					item.path = `/${item.name}`;
				}
				// 递归循环
				if (item.children && item.children.length > 0) {
					item.children = filterUrl(item.children);
				}
				newMap.push(item);
			});
	};
	traverse(map);
	return newMap;
};

// 转换
const filterAsyncRouter = (routerMap) => {
	const accessedRouters = [];
	routerMap.forEach((item) => {
		item.meta = item.meta ? item.meta : {};
		// MAP转路由对象
		const route = {
			path: item.path,
			name: item.name,
			meta: item.meta,
			redirect: item.redirect,
			children: item.children ? filterAsyncRouter(item.children) : null,
			component: loadComponent(item.component),
		};
		accessedRouters.push(route);
	});
	return accessedRouters;
};
const loadComponent = (component) => {
	if (component) {
		if (component.includes("/")) {
			return modules[`/src/views/${component}.vue`];
		}
		return modules[`/src/views/${component}/index.vue`];
	} else {
		return () => import(/* @vite-ignore */ `/src/layout/empty.vue`);
	}
};

// 路由扁平化
const flatAsyncRoutes = (routes, breadcrumb = []) => {
	const res = [];
	routes.forEach((route) => {
		const tmp = { ...route };
		if (tmp.children) {
			const childrenBreadcrumb = [...breadcrumb];
			childrenBreadcrumb.push(route);
			const tmpRoute = { ...route };
			tmpRoute.meta.breadcrumb = childrenBreadcrumb;
			delete tmpRoute.children;
			res.push(tmpRoute);
			const childrenRoutes = flatAsyncRoutes(
				tmp.children,
				childrenBreadcrumb
			);
			childrenRoutes.map((item) => {
				res.push(item);
			});
		} else {
			const tmpBreadcrumb = [...breadcrumb];
			tmpBreadcrumb.push(tmp);
			tmp.meta.breadcrumb = tmpBreadcrumb;
			res.push(tmp);
		}
	});
	return res;
};

export default router;
