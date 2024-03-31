<template>
	<div class="setting-drawer-index-content">
		<div class="scrollbar">
			<h3>整体界面布局</h3>
			<a-divider />
			<div class="mb-4 layout-slide">
				<h4 class="">顶栏应用主题色：</h4>
				<a-switch
					:checked="topHeaderThemeColorOpen"
					@change="changeTopHanderThemeColorOpen"
				/>
			</div>
			<div class="mb-4 layout-slide">
				<h4>顶栏主题色通栏：</h4>
				<a-switch
					style="float: right"
					:checked="topHeaderThemeColorSpread"
					:disabled="!topHeaderThemeColorOpen"
					@change="changeTopHanderThemeColorSpread"
				/>
			</div>
			<a-divider />
			<a-form ref="formRef" class="text-right">
				<a-form-item label="模块坞">
					<a-switch
						:checked="moduleUnfoldOpen"
						@change="toggleState('moduleUnfoldOpen')"
					/>
				</a-form-item>
				<a-form-item label="面包屑">
					<a-switch
						:checked="breadcrumbOpen"
						@change="toggleState('breadcrumbOpen')"
					/>
				</a-form-item>
				<a-form-item label="折叠菜单">
					<a-switch
						:checked="menuIsCollapse"
						@change="toggleState('menuIsCollapse')"
					/>
				</a-form-item>
				<a-form-item label="菜单排他展开">
					<a-switch
						:checked="sideUniqueOpen"
						@change="toggleState('sideUniqueOpen')"
					/>
				</a-form-item>
				<a-form-item label="表单风格">
					<a-select
						:value="formStyle"
						class="!w-[80px]"
						size="small"
						:options="xnFormStyleOptions"
						@change="formStyleChange"
					/>
				</a-form-item>
			</a-form>
			<a-alert
				message="以上配置可实时预览，开发者可在 config/index.js 中配置默认值，不建议在生产环境下开放布局设置"
				type="warning"
			/>
		</div>
	</div>
</template>
<script setup>
import { useGlobalStore } from "@/store";
import tool from "@/utils/tool";
const store = useGlobalStore();
const toolDataNameMap = {
	menuIsCollapse: "MENU_COLLAPSE",
	sideUniqueOpen: "SIDE_UNIQUE_OPEN",
	breadcrumbOpen: "BREADCRUMD_OPEN",
	topHeaderThemeColorOpen: "TOP_HEADER_THEME_COLOR_OPEN",
	topHeaderThemeColorSpread: "TOP_HEADER_THEME_COLOR_SPREAD",
	moduleUnfoldOpen: "MODULE_UNFOLD_OPEN",
};

const xnFormStyleOptions = ref([
	{
		label: "抽屉",
		value: "drawer",
	},
	{
		label: "对话框",
		value: "modal",
	},
]);

const menuIsCollapse = computed(() => {
	return store.menuIsCollapse;
});
const sideUniqueOpen = computed(() => {
	return store.sideUniqueOpen;
});
const breadcrumbOpen = computed(() => {
	return store.breadcrumbOpen;
});
const moduleUnfoldOpen = computed(() => {
	return store.moduleUnfoldOpen;
});
const topHeaderThemeColorOpen = computed(() => {
	return store.topHeaderThemeColorOpen;
});
const topHeaderThemeColorSpread = computed(() => {
	return store.topHeaderThemeColorSpread;
});
const formStyle = computed(() => {
	return store.formStyle;
});

const changeTopHanderThemeColorOpen = () => {
	toggleState("topHeaderThemeColorOpen");
	if (!topHeaderThemeColorOpen) {
		store.topHeaderThemeColorSpread = false;
		tool.data.set("HPSYS_TOP_HEADER_THEME_COLOR_SPREAD", false);
	}
};

const changeTopHanderThemeColorSpread = () => {
	toggleState("topHeaderThemeColorSpread");
};
const toggleState = (stateName) => {
	store.toggleConfig(stateName);
	const toolDataName = toolDataNameMap[stateName];
	tool.data.set(`HPSYS${toolDataName}`, store[stateName]);
};
// 切换表单风格
const formStyleChange = (value) => {
	tool.data.set("HPSYS_FORM_STYLE", value);
	store.setFormStyle(value);
};
</script>

<style lang="less" scoped>
.hpsys-setting-checkbox {
	display: flex;
	margin-bottom: 20px;
}
.hpsys-setting-checkbox-item {
	position: relative;
	width: 44px;
	height: 36px;
	margin-right: 16px;
	overflow: hidden;
	background-color: #ebeef1;
	border-radius: 2px;
	box-shadow: 0 1px 2.5px 0 rgb(0 0 0 / 18%);
	cursor: pointer;
}
.hpsys-setting-checkbox-item::before {
	position: absolute;
	top: 0;
	left: 0;
	width: 33%;
	height: 100%;
	background-color: #fff;
	content: "";
}
.hpsys-setting-checkbox-item::after {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 25%;
	background-color: #fff;
	content: "";
}
.hpsys-setting-checkbox-item-light {
	background-color: #ebeef1;
	content: "";
}
.hpsys-setting-checkbox-item-light::before {
	background-color: #fff;
	content: "";
}
.hpsys-setting-checkbox-item-light::after {
	background-color: #fff;
}
.hpsys-setting-checkbox-item-dark {
	z-index: 1;
	background-color: #ebeef1;
	content: "";
}
.hpsys-setting-checkbox-item-dark::before {
	z-index: 1;
	background-color: #001529;
	content: "";
}
.hpsys-setting-checkbox-item-dark::after {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 25%;
	background-color: #fff;
	content: "";
}
.hpsys-setting-checkbox-item-realdark {
	background-color: rgba(0, 21, 41, 0.85);
}
.hpsys-setting-checkbox-item-realdark::before {
	z-index: 1;
	background-color: rgba(0, 21, 41, 0.65);
	content: "";
}
.hpsys-setting-checkbox-item-realdark::after {
	background-color: rgba(0, 21, 41, 0.85);
}
.hpsys-setting-checkbox-item-select-icon {
	position: absolute;
	right: 8px;
	bottom: 8px;
	color: #1890ff;
	font-weight: 700;
	font-size: 14px;
	pointer-events: none;
}

.hpsys-setting-theme-color-colorBlock {
	margin-top: 8px;
	width: 20px;
	height: 20px;
	border-radius: 2px;
	float: left;
	cursor: pointer;
	margin-right: 8px;
	padding-left: 0px;
	padding-right: 0px;
	text-align: center;
	color: #fff;
	font-weight: 700;
}

.hpsys-setting-layout-menu-classical {
	z-index: 1;
	background-color: #ebeef1;
	content: "";
}
.hpsys-setting-layout-menu-classical::before {
	z-index: 1;
	background-color: #001529;
	content: "";
}
.hpsys-setting-layout-menu-classical::after {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 25%;
	background-color: #fff;
	content: "";
}

.scrollbar {
	margin: 0 auto;
}
</style>
