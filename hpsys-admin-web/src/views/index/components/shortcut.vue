<template>
	<a-card :bordered="false" style="margin-top: 10px">
		<template #title> 快捷方式 </template>
		<a-row :gutter="10">
			<a-col
				:span="6"
				:key="shortcut.id"
				v-for="shortcut in shortcutList"
				:xs="12"
				:sm="8"
				:md="6"
				:lg="8"
				:xl="6"
			>
				<shortcutCard
					:icon="shortcut.icon ? shortcut.icon : 'menu-outlined'"
					:label="shortcut.title"
					@click="leaveFor(shortcut.path)"
				/>
			</a-col>
		</a-row>
	</a-card>
</template>

<script setup name="shortcut">
import router from "@/router";
import userCenterApi from "@/api/userCenterApi";
import ShortcutCard from "@/components/ShortcutCard/index.vue";
import { onMounted } from "vue";
const shortcutList = ref([]);

onMounted(() => {
	// 进来后执行查询
	getUserLoginWorkbench();
});

const getUserLoginWorkbench = () => {
	shortcutList.value = [
		{
			id: "1548901111999773979",
			title: "个人中心",
			icon: "user-outlined",
			path: "/user",
		},
		{
			id: "1548901111999773980",
			title: "基本信息",
			icon: "picLeft-outlined",
			path: "/houseBasicInfo",
		},
		{
			id: "1548901111999773980",
			title: "影响因素",
			icon: "aim-outlined",
			path: "/houseFactors",
		},
		{
			id: "1548901111999773980",
			title: "房价预测",
			icon: "branches-outlined",
			path: "/housePrediction",
		},
	];
};

const leaveFor = (url = "/") => {
	router.replace({
		path: url,
	});
};
</script>

<style scoped>
.ant-list-item {
	padding: 8px 0px !important;
}
</style>
