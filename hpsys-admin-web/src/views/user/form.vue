<template>
	<xn-form-container
		:title="formData.id ? '编辑用户' : '增加用户'"
		:width="500"
		:visible="visible"
		:destroy-on-close="true"
		:body-style="{ 'padding-top': '0px' }"
		@close="onClose"
	>
		<a-form
			ref="formRef"
			:model="formData"
			:rules="formRules"
			layout="vertical"
		>
			<a-tabs v-model:activeKey="activeTabsKey">
				<a-tab-pane key="1" tab="基础信息" force-render>
					<a-row :gutter="20">
						<a-col :span="12">
							<a-form-item label="账号：" name="account">
								<a-input
									v-model:value="formData.account"
									placeholder="请输入账号"
									allow-clear
								/>
							</a-form-item>
						</a-col>
					</a-row>
					<a-row :gutter="20">
						<a-col :span="16">
							<a-form-item label="姓名：" name="name">
								<a-input
									v-model:value="formData.name"
									placeholder="请输入姓名"
									allow-clear
								/>
							</a-form-item>
						</a-col>
					</a-row>
					<a-row :gutter="20">
						<a-col :span="16">
							<a-form-item label="手机号：" name="phone">
								<a-input
									v-model:value="formData.phone"
									placeholder="请输入手机"
									allow-clear
								/>
							</a-form-item>
						</a-col>
					</a-row>
					<a-row :gutter="20">
						<a-col :span="20">
							<a-form-item label="年龄：" name="age">
								<a-input-number
									v-model:value="formData.age"
									:min="0"
									:max="150"
									:precision="0"
									allow-clear
								/>
							</a-form-item>
						</a-col>
					</a-row>
					<a-row :gutter="20">
						<a-col :span="20">
							<a-form-item label="排序码：" name="sortCode">
								<a-input-number
									v-model:value="formData.sortCode"
									:min="1"
									:max="100"
									:precision="0"
									allow-clear
								/>
							</a-form-item>
						</a-col>
					</a-row>
					<a-row :gutter="20">
						<a-col :span="16">
							<a-form-item
								label="通信地址："
								name="mailingAddress"
							>
								<a-textarea
									v-model:value="formData.mailingAddress"
									placeholder="请输入通信地址"
									:auto-size="{ minRows: 2, maxRows: 5 }"
									allow-clear
								/>
							</a-form-item>
						</a-col>
					</a-row>
				</a-tab-pane>
			</a-tabs>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" :loading="formLoading" @click="onSubmit"
				>保存</a-button
			>
		</template>
	</xn-form-container>
</template>

<script setup>
import userApi from "@/api/userApi";
import userCenterApi from "@/api/userCenterApi";
import { required } from "@/utils/formRules";
import tool from "@/utils/tool";
// 默认是关闭状态
const visible = ref(false);
const formRef = ref();
const activeTabsKey = ref("1");
const emit = defineEmits({ successful: null });
const formLoading = ref(false);
// 表单数据
const formData = ref({});

// 打开抽屉
const onOpen = (record) => {
	visible.value = true;
	formData.value = {};
	if (record) {
		convertFormData(record);
	}
};
// 关闭抽屉
const onClose = () => {
	visible.value = false;
};
// 回显数据
const convertFormData = (record) => {
	const param = {
		id: record.id,
	};
	// 查询详情
	userApi.userDetail(param).then((data) => {
		formData.value = Object.assign(formData.value, data);
	});
};

// 默认要校验的
const formRules = {
	account: [required("请输入账号")],
	name: [required("请输入姓名")],
};
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		// 因为不切断，我下面转换数据格式，影响上面表单会报错
		let formDatas = JSON.parse(JSON.stringify(formData.value));
		formLoading.value = true;
		userApi
			.submitForm(formDatas, formDatas.id)
			.then(() => {
				onClose();
				emit("successful");
			})
			.finally(() => {
				formLoading.value = false;
			});
	});
};
defineExpose({
	onOpen,
});
</script>

<style scoped type="less">
.childAddButton {
	margin-bottom: 10px;
}
.form-row {
	background-color: var(--item-hover-bg);
	margin-left: 0px !important;
}
.form-row-con {
	padding-bottom: 5px;
	padding-top: 5px;
	padding-left: 15px;
}
.form-div {
	padding-top: 10px;
}
</style>
