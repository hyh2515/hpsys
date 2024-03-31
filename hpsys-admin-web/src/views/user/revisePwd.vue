<template>
	<a-form
		ref="formRef"
		:model="formData"
		:rules="formRules"
		v-bind="layout"
		:label-col="{ ...layout.labelCol, offset: 0 }"
		:wrapper-col="{ ...layout.wrapperCol, offset: 0 }"
	>
		<a-form-item label="原密码：" name="password">
			<a-input
				v-model:value="formData.password"
				placeholder="请输入原密码"
				allow-clear
			/>
		</a-form-item>
		<a-form-item label="新密码：" name="newPassword">
			<a-input
				v-model:value="formData.newPassword"
				placeholder="请输入新密码"
				allow-clear
			/>
		</a-form-item>

		<a-form-item :wrapper-col="{ ...layout.wrapperCol, offset: 4 }">
			<a-button type="primary" :loading="submitLoading" @click="onSubmit"
				>确认修改</a-button
			>
		</a-form-item>
	</a-form>
</template>

<script setup name="RevisePwd">
import { required, rules } from "@/utils/formRules";
import userCenterApi from "@/api/userCenterApi";

const formRef = ref();
let formData = ref({});
const submitLoading = ref(false);
// 默认要校验的
const formRules = {
	password: [required("请输入原密码")],
	newPassword: [required("请输入新密码")],
};
// 验证并提交数据
const onSubmit = () => {
	formRef.value
		.validate()
		.then(() => {
			submitLoading.value = true;
			userCenterApi.userUpdatePassword(formData.value).then(() => {
				submitLoading.value = false;
			});
		})
		.catch(() => {
			submitLoading.value = false;
			emit("successful");
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
