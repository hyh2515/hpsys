<template>
	<div id="districtAverPriceLinear"></div>
</template>

<script setup>
import { onMounted } from "vue";
import * as echarts from "echarts";
import houseInfoApi from "@/api/houseInfoApi";

const Echarts = ref();

const loadData2 = async (district, area) => {
	const maxArea = area + 50;
	const minArea = area - 50;
	const value = await houseInfoApi.getDistrictAverage(district);
	const data2 = [];
	value.forEach((item) => {
		if (item.scale <= maxArea && item.scale >= minArea) {
			data2.push([item.scale, item.price]);
		}
	});
	return data2;
};

onMounted(() => {
	Echarts.value = echarts.init(
		document.getElementById("districtAverPriceLinear")
	);
	const option = {
		title: {
			text: "该地区在售房源价格分布",
			left: "center",
		},
		xAxis: {
			scale: true,
			name: "房屋面积/m2",
		},
		yAxis: {
			scale: true,
			name: "房价/元",
		},
		tooltip: {
			trigger: "item",
		},
		series: [
			{
				type: "effectScatter",
				symbolSize: 20,
				data: [],
			},
			{
				type: "scatter",
				data: [],
			},
		],
	};
	// 绘制图表
	Echarts.value.setOption(option);
	// 自适应大小
	window.onresize = () => {
		Echarts.value.resize();
	};
});

const refreshData = async (data1, zipCode) => {
	const district = { zipCode: zipCode };
	//刷新数据

	var option = Echarts.value.getOption();
	option.series[0].data = data1;
	option.series[1].data = await loadData2(district, data1[0][0]);
	Echarts.value.setOption(option);
};

defineExpose({
	refreshData,
});
</script>
