const DEFAULT_CONFIG = {
	// 首页地址
	HOMEPAGE_URL: "/",

	// 接口地址
	API_URL: import.meta.env.VITE_API_BASEURL,

	// 请求超时
	TIMEOUT: 60000,

	// TokenName Authorization
	TOKEN_NAME: "token",

	// Token前缀，注意最后有个空格，如不需要需设置空字符串
	TOKEN_PREFIX: "",

	// 追加其他头
	HEADERS: {},

	// 菜单是否折叠
	HPSYS_MENU_COLLAPSE: false,

	// 模块坞
	HPSYS_MODULE_UNFOLD_OPEN: true,

	// 是否开启展示面包屑
	HPSYS_BREADCRUMD_OPEN: false,

	// 顶栏是否应用主题色
	HPSYS_TOP_HEADER_THEME_COLOR_OPEN: false,

	// 顶栏主题色通栏
	HPSYS_TOP_HEADER_THEME_COLOR_SPREAD: false,

	// 系统菜单编号
	HPSYS_MENU_MODULE_ID: "0123456789",

	// 侧边菜单是否排他展开
	HPSYS_SIDE_UNIQUE_OPEN: true,

	// 主题颜色
	COLOR: "#1890FF",

	// 请求是否开启缓存
	REQUEST_CACHE: false,

	SYS_BASE_CONFIG: {
		// 默认logo
		HPSYS_SYS_LOGO: "/src/assets/img/logo.png",
		// 背景图
		HPSYS_SYS_BACK_IMAGE: "",
		// 系统名称
		HPSYS_SYS_NAME: "HPSYS",
		// 版本
		HPSYS_SYS_VERSION: "1.0",
		// 版权
		HPSYS_SYS_COPYRIGHT: "Hpsys ©2024 by Yaohui Hu",
		// 默认文件存储
		HPSYS_SYS_DEFAULT_FILE_ENGINE: "LOCAL",
	},
};

export default DEFAULT_CONFIG;
