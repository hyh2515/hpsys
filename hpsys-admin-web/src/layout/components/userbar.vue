<template>
	<div class="user-bar">
		<div class="screen panel-item hidden-sm-and-down" @click="fullscreen">
			<fullscreen-outlined />
		</div>
		<a-dropdown class="user panel-item">
			<div class="user-avatar">
				<a-avatar :src="userInfo.avatar" />
				<label>{{ userName }}</label>
			</div>
			<template #overlay>
				<a-menu>
					<a-menu-item key="uc" @click="handleUser('uc')">
						<UserOutlined style="margin-right: 8px" />
						<span>个人中心</span>
					</a-menu-item>
					<a-menu-item
						key="clearCache"
						@click="handleUser('clearCache')"
					>
						<loading3-quarters-outlined style="margin-right: 8px" />
						<span>清理缓存</span>
					</a-menu-item>
					<a-menu-divider />
					<a-menu-item key="outLogin" @click="handleUser('outLogin')">
						<export-outlined style="margin-right: 8px" />
						<span>退出登录</span>
					</a-menu-item>
				</a-menu>
			</template>
		</a-dropdown>
		<div
			v-if="setDrawer === 'true'"
			class="setting panel-item"
			@click="openSetting"
		>
			<layout-outlined />
		</div>
	</div>

	<!-- 整体风格设置抽屉 -->
	<a-drawer v-model:visible="settingDialog" :closable="false" width="300">
		<setting />
	</a-drawer>
</template>

<script setup name="layoutUserBar">
import { createVNode } from "vue";
import { ExclamationCircleOutlined } from "@ant-design/icons-vue";
import { Modal } from "ant-design-vue";
import screenFull from "screenfull";
import { message } from "ant-design-vue";
import router from "@/router";
import tool from "@/utils/tool";
import config from "@/config";
import loginApi from "@/api/loginApi";
import { useGlobalStore } from "@/store";
import Setting from "./setting.vue";

const settingDialog = ref(false);
const setDrawer = ref(import.meta.env.VITE_SET_DRAWER);
const store = useGlobalStore();
const userInfo = computed(() => {
	return store.userInfo;
});
const userName = ref(userInfo.value?.userName || "");

// 个人信息
const handleUser = (key) => {
	if (key === "uc") {
		router.push({ path: "/usercenter" });
	}
	if (key === "outLogin") {
		Modal.confirm({
			title: "提示",
			content: "确认退出当前用户？",
			icon: createVNode(ExclamationCircleOutlined),
			maskClosable: false,
			onOk() {
				// 取得缓存中的token
				const token = tool.data.get("TOKEN");
				const param = {
					token: token,
				};
				message.loading("退出中...", 1);
				loginApi
					.logout(param)
					.then(() => {
						// 清理掉个人的一些信息
						tool.data.remove("TOKEN");
						tool.data.remove("USER_INFO");
						tool.data.remove("MENU");
						tool.data.remove("HPSYS_MENU_MODULE_ID");
						router.replace({ path: "/login" });
					})
					.catch(() => {
						tool.data.clear();
						router.replace({ path: "/login" });
						location.reload();
					});
			},
			onCancel() {},
		});
	}
};
// 设置抽屉
const openSetting = () => {
	settingDialog.value = true;
};
// 全屏
const fullscreen = () => {
	const element = document.documentElement;
	if (screenFull.isEnabled) {
		screenFull.toggle(element);
	}
};
</script>

<style lang="less" scoped>
:deep(.ant-modal) {
	top: 20px;
}
:deep(.ant-modal-content) {
	border-radius: 10px;
}
.user-bar {
	display: flex;
	align-items: center;
	height: 100%;
}
.user-bar .user-avatar {
	height: 49px;
	display: flex;
	align-items: center;
}
.user-bar .user-avatar label {
	display: inline-block;
	margin-left: 5px;
	cursor: pointer;
}
.setting {
	margin-right: 10px;
}
</style>
