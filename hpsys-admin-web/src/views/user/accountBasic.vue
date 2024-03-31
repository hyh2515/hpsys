<template>
	<a-form
		ref="formRef"
		:model="formData"
		:rules="formRules"
		v-bind="layout"
		:label-col="{ ...layout.labelCol, offset: 0 }"
		:wrapper-col="{ ...layout.wrapperCol, offset: 0 }"
	>
		<a-form-item label="账号：" name="account">
			<span>{{ formData.account }}</span>
		</a-form-item>
		<a-form-item label="姓名：" name="name">
			<a-input
				v-model:value="formData.name"
				placeholder="请输入姓名"
				allow-clear
			/>
		</a-form-item>
		<a-form-item label="手机：" name="phone">
			<a-input
				v-model:value="formData.phone"
				placeholder="请输入手机"
				allow-clear
			/>
		</a-form-item>
		<a-form-item label="年龄：" name="age">
			<a-input-number
				v-model:value="formData.age"
				placeholder="请输入你的年龄"
				allow-clear
				:precision="0"
				:min="1"
				:max="150"
			/>
		</a-form-item>

		<a-form-item :wrapper-col="{ ...layout.wrapperCol, offset: 4 }">
			<a-button type="primary" :loading="submitLoading" @click="onSubmit"
				>保存基本信息</a-button
			>
		</a-form-item>
	</a-form>
</template>

<script setup name="AccountBasic">
import { required, rules } from "@/utils/formRules";
import userCenterApi from "@/api/userCenterApi";
import tool from "@/utils/tool";
import { cloneDeep } from "lodash-es";
import { useGlobalStore } from "@/store";

const store = useGlobalStore();

const formRef = ref();
let formData = ref({});
formData.value = cloneDeep(store.userInfo);
const submitLoading = ref(false);
// 默认要校验的
const formRules = {
	name: [required("请输入姓名")],
	age: [required("请输入年龄")],
};
// 验证并提交数据
const onSubmit = () => {
	formRef.value
		.validate()
		.then(() => {
			submitLoading.value = true;
			userCenterApi.userUpdateUserInfo(formData.value).then(() => {
				submitLoading.value = false;
				// 更新前端缓存
				store.setUserInfo(cloneDeep(formData.value));
				tool.data.set("USER_INFO", formData.value);
			});
		})
		.catch(() => {
			submitLoading.value = false;
		});
};
const layout = {
	labelCol: {
		span: 4,
	},
	wrapperCol: {
		span: 12,
	},
};
</script>
