<template>
	<a-row>
		<a-col :span="24">
			<a-card>
				<a-row :gutter="[10, 10]">
					<div class="pred-header">
						<div class="pred-header-left" style="padding-left: 0px">
							预测价格为：{{ showPrice }}
						</div>
						<div class="pred-header-right">
							<a-button
								type="primary"
								@click="formRef.onOpen(activeKey)"
							>
								<template #icon
									><radarChart-outlined
								/></template>
								输入住房信息
							</a-button>
						</div>
					</div>
				</a-row>
				<a-row>
					<a-col span="24">
						<a-tabs v-model:activeKey="activeKey">
							<a-tab-pane key="LINEAR" tab="线性回归预测">
								<districtAverPriceLinear
									ref="linearRef"
									class="showSandianTU"
								/>
							</a-tab-pane>
							<a-tab-pane key="RIDGE" tab="岭回归预测">
								<DistrictAverPriceRidge
									ref="ridgeRef"
									class="showSandianTU"
								/>
							</a-tab-pane>
							<a-tab-pane key="LASSO" tab="Lasso预测">
								<DistrictAverPriceLasso
									ref="lassoRef"
									class="showSandianTU"
								/>
							</a-tab-pane>
						</a-tabs>
					</a-col>
				</a-row>
			</a-card>
		</a-col>
	</a-row>
	<Form ref="formRef" @successful="prediction"></Form>
</template>

<script setup name="prediction">
import Form from "./form.vue";
import { onMounted } from "vue";

// 定义tableDOM
const formRef = ref();
const zipCode = ref(310000);
const area = ref(80);
const linearRef = ref();
const ridgeRef = ref();
const lassoRef = ref();
const activeKey = ref("LINEAR");
const showPrice = computed(() => {
	if (formRef.value == undefined) {
		return 0;
	} else if (activeKey.value == "LINEAR") {
		return formRef.value.predValue[0];
	} else if (activeKey.value == "RIDGE") {
		return formRef.value.predValue[1];
	} else {
		return formRef.value.predValue[2];
	}
});

const prediction = () => {
	const district = {
		zipCode: zipCode.value,
	};
	if (activeKey.value === "LINEAR") {
		linearRef.value.refreshData(
			[
				[
					area.value,
					formRef.value.predValue == undefined
						? 0
						: formRef.value.predValue[0],
				],
			],
			zipCode.value
		);
	} else if (activeKey.value == "RIDGE") {
		ridgeRef.value.refreshData(
			[
				[
					area.value,
					formRef.value.predValue == undefined
						? 0
						: formRef.value.predValue[1],
				],
			],
			zipCode.value
		);
	} else {
		lassoRef.value.refreshData(
			[
				[
					area.value,
					formRef.value.predValue == undefined
						? 0
						: formRef.value.predValue[2],
				],
			],
			zipCode.value
		);
	}
};
</script>

<style scoped lang="less">
.pred-title {
	align-items: center;
	font-size: 24px;
	font-weight: bold;
	margin-bottom: 4px;
}
.pred-header {
	width: 100%;
	display: flex;
	justify-content: space-between;
}
.pred-header-left {
	display: flex;
	align-items: center;
	padding-left: 20px;
	font-size: 18px;
	font-weight: 600;
}
.pred-header-right {
	display: flex;
	align-items: center;
}
.showSandianTU {
	width: 100%;
	min-height: 60vh;
}
</style>
