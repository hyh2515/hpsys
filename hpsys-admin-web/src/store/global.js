import { defineStore } from "pinia";
import config from "@/config";
import tool from "@/utils/tool";

const toolDataGet = (key) => {
	return tool.data.get(key);
};

// 获取缓存中的，如果取不到那就用配置的
const getCacheConfig = (value) => {
	const data = toolDataGet(value);
	if (data === null) {
		return config[value];
	}
	return data;
};

export const useGlobalStore = defineStore("global", () => {
	// 利用Vue3组合式API，ref()定义state的属性
	// function() 定义actions
	// computed 定义getters
	// 定义state
	// 菜单是否折叠 toggle
	const menuIsCollapse = ref(getCacheConfig("HPSYS_MENU_COLLAPSE"));
	// 侧边菜单是否排他展开
	const sideUniqueOpen = ref(getCacheConfig("HPSYS_SIDE_UNIQUE_OPEN"));
	// 是否展示面包屑
	const breadcrumbOpen = ref(getCacheConfig("HPSYS_BREADCRUMD_OPEN"));
	// 模块坞
	const moduleUnfoldOpen = ref(getCacheConfig("HPSYS_MODULE_UNFOLD_OPEN"));
	// 顶栏是否应用主题色
	const topHeaderThemeColorOpen = ref(
		getCacheConfig("HPSYS_TOP_HEADER_THEME_COLOR_OPEN")
	);
	// 顶栏主题色通栏
	const topHeaderThemeColorSpread = ref(
		getCacheConfig("HPSYS_TOP_HEADER_THEME_COLOR_SPREAD")
	);

	// 用户信息
	const userInfo = ref(toolDataGet("USER_INFO") || {});
	// 系统配置
	const sysBaseConfig = ref(
		toolDataGet("SYS_BASE_CONFIG") || config.SYS_BASE_CONFIG
	);

	// 默认应用
	const module = ref(getCacheConfig("HPSYS_MENU_MODULE_ID"));

	// 定义action
	const toggleConfig = (key) => {
		switch (key) {
			case "menuIsCollapse":
				menuIsCollapse.value = !menuIsCollapse.value;
				break;
			case "topHeaderThemeColorSpread":
				topHeaderThemeColorSpread.value =
					!topHeaderThemeColorSpread.value;
				break;
			case "sideUniqueOpen":
				sideUniqueOpen.value = !sideUniqueOpen.value;
				break;
			case "breadcrumbOpen":
				breadcrumbOpen.value = !breadcrumbOpen.value;
				break;
			case "topHeaderThemeColorOpen":
				topHeaderThemeColorOpen.value = !topHeaderThemeColorOpen.value;
				topHeaderThemeColorSpread.value = topHeaderThemeColorOpen.value
					? topHeaderThemeColorSpread.value
					: topHeaderThemeColorOpen.value;
				break;
			case "moduleUnfoldOpen":
				moduleUnfoldOpen.value = !moduleUnfoldOpen.value;
				break;
		}
	};

	const setUserInfo = (key) => {
		userInfo.value = key;
	};
	const setSysBaseConfig = (key) => {
		sysBaseConfig.value = key;
	};
	const setModule = (key) => {
		module.value = key;
	};
	return {
		breadcrumbOpen,
		sideUniqueOpen,
		topHeaderThemeColorOpen,
		topHeaderThemeColorSpread,
		menuIsCollapse,
		moduleUnfoldOpen,
		module,
		toggleConfig,
		userInfo,
		sysBaseConfig,
		setUserInfo,
		setSysBaseConfig,
		setModule,
	};
});
