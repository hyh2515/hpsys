<template>
	<div id="aver-price"></div>
</template>

<script setup>
import { onMounted } from "vue";
import { Column } from "@antv/g2plot";
import houseInfoApi from "@/api/houseInfoApi";

onMounted(() => {
	houseInfoApi.averPrice().then((data) => {
		const aver_price_col = new Column("aver-price", {
			data,
			xField: "division",
			yField: "averPrice",
			label: {
				position: "middle",
				style: {
					fill: "#FFFFFF",
					opacity: "0.6",
				},
			},
			xAxis: {
				label: {
					autoHide: true,
					autoRotate: true,
				},
			},
			meta: {
				division: {
					alias: "地区",
				},
				averPrice: {
					alias: "均价",
				},
			},
		});

		aver_price_col.render();
	});
});
</script>
