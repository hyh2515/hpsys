<template>
	<a-row :gutter="20">
		<a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<a-card :bordered="false" style="margin-bottom: 10px">
				<a-form
					ref="searchFormRef"
					name="advanced_search"
					class="ant-advanced-search-form"
					:model="searchFormState"
				>
					<a-row :gutter="24">
						<a-col :span="8">
							<a-form-item name="searchKey" label="名称关键词">
								<a-input
									v-model:value="searchFormState.searchKey"
									placeholder="请输入用户名称关键词"
								></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="8">
							<a-button
								type="primary"
								@click="tableRef.refresh(true)"
							>
								<template #icon><SearchOutlined /></template>
								查询
							</a-button>
							<a-button class="snowy-buttom-left" @click="reset">
								<template #icon><redo-outlined /></template>
								重置
							</a-button>
						</a-col>
					</a-row>
				</a-form>
			</a-card>
			<a-card :bordered="false">
				<s-table
					ref="tableRef"
					:columns="columns"
					:data="loadData"
					:expand-row-by-click="true"
					:alert="options.alert.show"
					bordered
					:row-key="(record) => record.id"
					:row-selection="options.rowSelection"
					@resizeColumn="handleResizeColumn"
				>
					<template #operator class="table-operator">
						<a-space>
							<a-button
								type="primary"
								@click="formRef.onOpen(undefined)"
							>
								<template #icon><plus-outlined /></template>
								新增用户
							</a-button>
						</a-space>
					</template>
					<template #bodyCell="{ column, record }">
						<template v-if="column.dataIndex === 'action'">
							<a @click="formRef.onOpen(record)">编辑</a>
							<a-divider type="vertical" />
							<a-popconfirm
								title="确定删除此用户？"
								@confirm="removeOrg(record)"
							>
								<a-button type="link" danger size="small"
									>删除</a-button
								>
							</a-popconfirm>
							<a-divider type="vertical" />
						</template>
					</template>
				</s-table>
			</a-card>
		</a-col>
	</a-row>
	<Form ref="formRef" @successful="tableRef.refresh()" />
</template>

<script setup name="sysRole">
import userApi from "@/api/userApi";
import Form from "./form.vue";

const columns = [
	{
		title: "用户名称",
		dataIndex: "name",
		resizable: true,
		width: 150,
	},
	{
		title: "账号",
		dataIndex: "account",
		resizable: true,
		width: 150,
	},
	{
		title: "通信地址",
		dataIndex: "mailingAddress",
		width: 150,
	},
	{
		title: "电话",
		dataIndex: "phone",
		width: 100,
	},
	{
		title: "排序",
		dataIndex: "sortCode",
		width: 100,
	},
	{
		title: "操作",
		dataIndex: "action",
		align: "center",
		width: "200px",
	},
];
const selectedRowKeys = ref([]);
// 列表选择配置
const options = {
	alert: {
		show: false,
		clear: () => {
			selectedRowKeys.value = ref([]);
		},
	},
	rowSelection: {
		onChange: (selectedRowKey, selectedRows) => {
			selectedRowKeys.value = selectedRowKey;
		},
	},
};
// 定义tableDOM
const tableRef = ref();
const formRef = ref();
const searchFormRef = ref();
const searchFormState = ref({});

// 表格查询 返回 Promise 对象
const loadData = (parameter) => {
	let param = Object.assign(parameter, searchFormState.value);
	return userApi.userPage(param).then((res) => {
		return res;
	});
};
// 重置
const reset = () => {
	searchFormRef.value.resetFields();
	tableRef.value.refresh(true);
};
// 可伸缩列
const handleResizeColumn = (w, col) => {
	col.width = w;
};
// 删除
const removeOrg = (record) => {
	let params = [
		{
			id: record.id,
		},
	];
	userApi.userDelete(params).then(() => {
		tableRef.value.refresh(true);
	});
};
</script>

<style scoped>
.ant-form-item {
	margin-bottom: 0 !important;
}
.primaryAdd {
	margin-right: 10px;
}
.snowy-buttom-left {
	margin-left: 8px;
}
</style>
