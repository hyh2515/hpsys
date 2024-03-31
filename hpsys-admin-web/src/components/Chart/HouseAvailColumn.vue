<template>
	<div id="house-avail"></div>
</template>

<script setup>
import { onMounted } from "vue";
import { Pie } from "@antv/g2plot";
import houseInfoApi from "@/api/houseInfoApi";

onMounted(() => {
	houseInfoApi.availStat().then((data) => {
		const house_avail_col = new Pie("house-avail", {
			data,
			forceFit: true,
			radius: 0.8,
			angleField: "total",
			colorField: "division",
			label: {
				visible: true,
				type: "outer",
				style: {
					fill: "#000000",
				},
			},
			meta: {
				division: {
					alias: "地区",
				},
				total: {
					alias: "房源数",
				},
			},
		});

		house_avail_col.render();
	});
});
</script>
