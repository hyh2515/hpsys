<template>
	<xn-form-container
		title="预测房价"
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
			<a-row :gutter="20">
				<a-col :span="12">
					<a-form-item label="装饰状况：" name="decorationCondition">
						<a-select v-model:value="formData.decorationCondition">
							<a-select-option :value="0">毛坯</a-select-option>
							<a-select-option :value="1">简装</a-select-option>
							<a-select-option :value="2">精装</a-select-option>
						</a-select>
					</a-form-item>
					<a-form-item label="房本备件：" name="deed">
						<a-select v-model:value="formData.deed">
							<a-select-option :value="0"
								>未上传房本照片</a-select-option
							>
							<a-select-option :value="1"
								>已上传房本照片</a-select-option
							>
						</a-select>
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="配备电梯：" name="elevator">
						<a-radio-group v-model:value="formData.elevator">
							<a-radio :value="0">是</a-radio>
							<a-radio :value="1">否</a-radio>
						</a-radio-group>
					</a-form-item>
				</a-col>
				<a-col :span="24">
					<a-form-item label="周边配置：" name="aroundCondition">
						<a-checkbox-group
							v-model:value="aroundCondition"
							style="width: 100%; display: flex"
						>
							<a-checkbox :value="0">购物</a-checkbox>
							<a-checkbox :value="1">教育</a-checkbox>
							<a-checkbox :value="2">交通</a-checkbox>
							<a-checkbox :value="3">健身</a-checkbox>
							<a-checkbox :value="4">环境</a-checkbox>
							<a-checkbox :value="5">医疗</a-checkbox>
						</a-checkbox-group>
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="所在楼层" name="level">
						<a-select v-model:value="formData.level">
							<a-select-option :value="0">低</a-select-option>
							<a-select-option :value="1">中</a-select-option>
							<a-select-option :value="2">高</a-select-option>
							<a-select-option :value="4">地下室</a-select-option>
						</a-select>
					</a-form-item>
					<a-form-item label="楼层数" name="total">
						<a-input-number
							v-model:value="formData.total"
							:min="0"
							:max="300"
							:precision="0"
							allow-clear
						>
						</a-input-number>
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="建筑结构" name="framework">
						<a-select v-model:value="formData.framework">
							<a-select-option :value="0"
								>砖混结构</a-select-option
							>
							<a-select-option :value="1"
								>钢混结构</a-select-option
							>
						</a-select>
					</a-form-item>
					<a-form-item label="房屋年限" name="houseTerm">
						<a-select v-model:value="formData.houseTerm">
							<a-select-option :value="0"
								>未满两年</a-select-option
							>
							<a-select-option :value="1">满两年</a-select-option>
							<a-select-option :value="2">满五年</a-select-option>
						</a-select>
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="产权所属" name="ownership">
						<a-select v-model:value="formData.ownership">
							<a-select-option :value="0">非共有</a-select-option>
							<a-select-option :value="1">共有</a-select-option>
						</a-select>
					</a-form-item>
					<a-form-item label="房屋用途" name="purpose">
						<a-select v-model:value="formData.purpose">
							<a-select-option :value="0">别墅</a-select-option>
							<a-select-option :value="1"
								>新式里弄</a-select-option
							>
							<a-select-option :value="2"
								>旧式里弄</a-select-option
							>
							<a-select-option :value="3"
								>普通住宅</a-select-option
							>
							<a-select-option :value="4">老公寓</a-select-option>
							<a-select-option :value="5"
								>花园洋房</a-select-option
							>
						</a-select>
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="每层楼住户数" name="apt">
						<a-input-number
							v-model:value="formData.apt"
							:min="0"
							:max="50"
							:precision="0"
							allow-clear
						>
						</a-input-number>
					</a-form-item>
					<a-form-item label="电梯数" name="lift">
						<a-input-number
							v-model:value="formData.lift"
							:min="0"
							:max="20"
							:precision="0"
							allow-clear
						>
						</a-input-number>
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="所在区域" name="district">
						<a-select
							v-model:value="formData.district"
							:options="zipCodes"
						>
						</a-select>
					</a-form-item>
					<a-form-item label="交易权属" name="rights">
						<a-select v-model:value="formData.rights">
							<a-select-option :value="0"
								>动迁安置房</a-select-option
							>
							<a-select-option :value="1"
								>售后公房</a-select-option
							>
							<a-select-option :value="2">商品房</a-select-option>
						</a-select>
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="建筑面积/㎡" name="scale">
						<a-input-number
							v-model:value="formData.scale"
							:min="0"
							allow-clear
						>
						</a-input-number>
					</a-form-item>
					<a-form-item label="户型结构" name="structure">
						<a-select v-model:value="formData.structure">
							<a-select-option :value="0">复式</a-select-option>
							<a-select-option :value="1">平层</a-select-option>
							<a-select-option :value="2">跃层</a-select-option>
							<a-select-option :value="3">错层</a-select-option>
						</a-select>
					</a-form-item>
				</a-col>
				<a-col :span="24">
					<a-row :span="6">
						<a-form-item label="浴室数" name="bath">
							<a-input-number
								v-model:value="formData.bath"
								:min="0"
								:max="20"
								allow-clear
							>
							</a-input-number>
						</a-form-item>
						&nbsp; &nbsp;
						<a-form-item label="厨房数" name="kitchen">
							<a-input-number
								v-model:value="formData.kitchen"
								:min="0"
								:max="20"
								allow-clear
							>
							</a-input-number>
						</a-form-item>
						&nbsp; &nbsp;
						<a-form-item label="卧室数" name="room">
							<a-input-number
								v-model:value="formData.room"
								:min="0"
								:max="20"
								allow-clear
							>
							</a-input-number>
						</a-form-item>
						&nbsp; &nbsp;
						<a-form-item label="客厅数" name="saloon">
							<a-input-number
								v-model:value="formData.saloon"
								:min="0"
								:max="20"
								allow-clear
							>
							</a-input-number>
						</a-form-item>
					</a-row>
				</a-col>
			</a-row>
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
import { required } from "@/utils/formRules";
import tool from "@/utils/tool";
import houseInfoApi from "@/api/houseInfoApi";
import predictApi from "@/api/predictApi";
// 默认是关闭状态
const visible = ref(false);
const formRef = ref();
const emit = defineEmits({ successful: null });
const formLoading = ref(false);
// 表单数据
const formData = ref({});
const aroundCondition = ref([]);
const zipCodes = ref([]);
const predValue = ref(0);

const loadData = async () => {
	const value = await houseInfoApi.listZipCode();
	value.forEach((item) => {
		const obj = {
			label: item.division,
			value: item.code,
		};
		zipCodes.value.push(obj);
	});
	zipCodes.value.push({ label: "暂无数据", value: "310000" });
};

// 打开抽屉
const onOpen = () => {
	formData.value = {
		coordinateX: 121,
		coordinateY: 31,
		decorationCondition: 0,
		deed: 0,
		elevator: 0,
		facility0: 0,
		facility1: 0,
		facility2: 0,
		facility3: 0,
		facility4: 0,
		facility5: 0,
		level: 4,
		total: 1,
		framework: 0,
		houseTerm: 0,
		ownership: 0,
		purpose: 5,
		apt: 1,
		lift: 1,
		district: "310000",
		rights: 0,
		scale: 80,
		structure: 0,
		bath: 0,
		kitchen: 0,
		room: 0,
		saloon: 0,
	};
	loadData();
	visible.value = true;
};
// 关闭抽屉
const onClose = () => {
	visible.value = false;
};

// 默认要校验的
const formRules = {
	scale: [required("请输入规模")],
};
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		// 因为不切断，我下面转换数据格式，影响上面表单会报错
		formLoading.value = true;
		aroundCondition.value.forEach((item) => {
			if (item === 0) formData.facility0 = 1;
			if (item === 1) formData.facility1 = 1;
			if (item === 2) formData.facility2 = 1;
			if (item === 3) formData.facility3 = 1;
			if (item === 4) formData.facility4 = 1;
			if (item === 5) formData.facility5 = 1;
		});
		let formDatas = JSON.parse(JSON.stringify(formData.value));
		console.log(formDatas);
		predictApi
			.getPredictPrice(formDatas)
			.then((data) => {
				predValue.value = data;
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
	predValue,
});
</script>
