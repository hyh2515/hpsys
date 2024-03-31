<template>
	<!-- 通用布局 -->
	<a-layout style="height: 100%">
		<a-layout-sider
			v-model:collapsed="menuIsCollapse"
			:trigger="null"
			collapsible
			width="210"
		>
			<header id="hpsysHeaderLogo" class="hpsys-header-logo">
				<div class="hpsys-header-left">
					<div class="logo-bar">
						<img
							class="logo"
							:src="sysBaseConfig.HPSYS_SYS_LOGO"
							alt="sysBaseConfig.HPSYS_SYS_NAME"
						/>
						<span>{{ sysBaseConfig.HPSYS_SYS_NAME }}</span>
					</div>
				</div>
			</header>
			<div
				:class="
					menuIsCollapse
						? 'admin-ui-side isCollapse'
						: 'admin-ui-side'
				"
			>
				<div class="admin-ui-side-scroll">
					<a-menu
						v-model:openKeys="openKeys"
						v-model:selectedKeys="selectedKeys"
						mode="inline"
						theme="dark"
						@select="onSelect"
						@openChange="onOpenChange"
					>
						<NavMenu :nav-menus="menu" />
					</a-menu>
				</div>
			</div>
		</a-layout-sider>
		<!-- 右侧布局 -->
		<a-layout>
			<div id="hpsysHeader" class="hpsys-header">
				<div class="hpsys-header-left" style="padding-left: 0px">
					<div
						class="panel-item hidden-sm-and-down"
						@click="menuIsCollapseClick"
					>
						<MenuUnfoldOutlined v-if="menuIsCollapse" />
						<MenuFoldOutlined v-else />
					</div>
					<moduleMenu
						v-if="moduleMenuShow"
						@switchModule="switchModule"
					/>
					<top-bar v-if="breadcrumbOpen" />
				</div>
				<div class="hpsys-header-right">
					<user-bar />
				</div>
			</div>
			<a-layout-content class="main-content-wrapper">
				<div id="admin-ui-main" class="admin-ui-main">
					<router-view v-slot="{ Component }">
						<keep-alive :include="kStore.keepLiveRoute">
							<component
								:is="Component"
								v-if="kStore.routeShow"
								:key="route.name"
							/>
						</keep-alive>
					</router-view>
					<iframe-view />
					<div class="main-bottom-wrapper">
						<a
							style="color: #a0a0a0"
							:href="sysBaseConfig.HPSYS_SYS_COPYRIGHT_URL"
							target="_blank"
							>{{ sysBaseConfig.HPSYS_SYS_COPYRIGHT }}</a
						>
					</div>
				</div>
			</a-layout-content>
		</a-layout>
	</a-layout>
	<!-- 退出最大化 -->
	<div class="main-maximize-exit" @click="exitMaximize">
		<fullscreen-exit-outlined style="color: #fff" />
	</div>
</template>

<script setup>
import UserBar from "@/layout/components/userbar.vue";
import { useGlobalStore, keepAliveStore } from "@/store";
import { useRoute, useRouter } from "vue-router";
import { message } from "ant-design-vue";
import tool from "@/utils/tool";
import NavMenu from "@/layout/components/navMenu.vue";
import ModuleMenu from "@/layout/components/moduleMenu.vue";
import IframeView from "@/layout/components/iframeView.vue";
import TopBar from "@/layout/components/topbar.vue";

const store = useGlobalStore();
const kStore = keepAliveStore();
const route = useRoute();
const router = useRouter();
const menu = ref([]);
const selectedKeys = ref([]);
const openKeys = ref([]);
const moduleMenu = ref([]);
const moduleMenuShow = ref(true);
const pMenu = ref({});

// computed计算方法 - start
const menuIsCollapse = computed(() => {
	return store.menuIsCollapse;
});
const breadcrumbOpen = computed(() => {
	return store.breadcrumbOpen;
});
const sysBaseConfig = computed(() => {
	return store.sysBaseConfig;
});
const sideUniqueOpen = computed(() => {
	return store.sideUniqueOpen;
});

// 路由监听高亮
const showThis = () => {
	pMenu.value = route.meta.breadcrumb ? route.meta.breadcrumb[0] : {};
	// 展开的
	nextTick(() => {
		// 取得默认路由地址并设置展开
		let active = route.meta.active || route.path;
		// 如果是目录，必须往下找
		if (route.meta.type === "catalog") {
			active = traverseChild(pMenu.value.children, active).path;
		}
		selectedKeys.value = new Array(active);
		const pidKey = getParentKeys(pMenu.value.children, active);
		// 判断是隐藏的路由，找其上级
		if (route.meta.hidden && pidKey) {
			if (pidKey.length > 1) {
				selectedKeys.value = new Array(pidKey[1]);
			}
		}
		const nextTickMenu = pMenu.value.children;
		if (pidKey) {
			const modelPidKey = getParentKeys(moduleMenu.value, route.path);
			const parentPath = pidKey[pidKey.length - 1];
		}
		openKeys.value = pidKey;
	});
};

// 执行-start
moduleMenu.value = router.getMenu();
// 获取缓存中的菜单模块是哪个
const menuModuleId = tool.data.get("HPSYS_MENU_MODULE_ID");
if (menuModuleId) {
	// 防止切换一个无此应用的人
	const module = router.getMenu().filter((item) => item.id === menuModuleId);
	if (module.length > 0) {
		menu.value = module[0].children;
	} else {
		menu.value = router.getMenu()[0].children;
	}
} else {
	menu.value = router.getMenu()[0].children;
}
showThis();

onMounted(() => {
	onLayoutResize();
	window.addEventListener("resize", onLayoutResize);
});
watch(route, (newValue) => {
	// 清理选中的
	selectedKeys.value = [];
	showThis();
});

const menuIsCollapseClick = () => {
	store.toggleConfig("menuIsCollapse");
};

// 菜单展开/关闭的回调
const onOpenChange = (keys) => {
	if (sideUniqueOpen.value) {
		// 获取最新的
		const openKey = keys[keys.length - 1];
		if (keys.length > 1) {
			// 获取上级
			openKeys.value = getParentKeys(menu.value, openKey);
		} else {
			openKeys.value = Array.of(openKey); // new Array(openKey);
		}
	} else {
		openKeys.value = keys;
	}
};

// 获取上级keys
const getParentKeys = (data, val) => {
	const traverse = (array, val) => {
		// 递归父级key
		for (const element of array) {
			if (element.path === val) {
				return [element.path];
			}
			if (element.children) {
				const far = traverse(element.children, val);
				if (far) {
					return far.concat(element.path);
				}
			}
		}
	};
	return traverse(data, val);
};
// 当菜单被选中时
const onSelect = (obj) => {
	const pathLength = obj.keyPath.length;
	const path = obj.keyPath[pathLength - 1];
	router.push({ path });
	// 设置选中
	selectedKeys.value = obj.selectedKeys;
};
const onLayoutResize = () => {
	const clientWidth = document.body.clientWidth;
};
// 切换应用
const switchModule = (id) => {
	if (moduleMenu.value.length > 0) {
		showThis();
		const menus = moduleMenu.value.filter((item) => item.id === id)[0]
			.children;
		if (menus.length > 0) {
			// 正儿八百的菜单
			menu.value = menus;
			const firstMenu = traverseChild(menu.value);
			const path = firstMenu.path;
			// 如果是外链
			if (firstMenu.menuType === "LINK") {
				window.open(path);
			} else {
				// 将此模块的唯一值加入缓存
				tool.data.set("HPSYS_MENU_MODULE_ID", id);
				// 然后将其跳转至指定界面，默认始终取排序第一的
				router.push({ path });
			}
		} else {
			message.warning("该模块下无任何菜单");
		}
	}
};

// 遍历获取子集
const traverseChild = (menu) => {
	if (menu[0] && menu[0].children !== undefined) {
		if (menu[0].children.length > 0) {
			if (
				menu[0].children[0] &&
				menu[0].children[0].meta.hidden &&
				menu[0].children[0].meta.hidden === true
			) {
				return menu[0];
			} else {
				return traverseChild(menu[0].children);
			}
		}
	} else {
		return menu[0];
	}
};

// 退出最大化
const exitMaximize = () => {
	document.getElementById("app").classList.remove("main-maximize");
	moduleMenuShow.value = false;
	nextTick(() => {
		moduleMenuShow.value = true;
	});
};
</script>
