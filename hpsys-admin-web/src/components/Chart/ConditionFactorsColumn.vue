<template>
	<div id="conditionFactorsBar"></div>
</template>

<script setup>
import { onMounted } from "vue";
import { Column } from "@antv/g2plot";

onMounted(() => {
	const result = props.data();
	result.then((res) => {
		const data = res[0];
		const column = new Column("conditionFactorsBar", {
			title: {
				visible: true,
				alignTo: "left",
				text: "不同条件下房屋均价",
				style: {
					fontSize: 18,
					fill: "black",
				},
			},
			data,
			forceFit: true,
			xField: "condition",
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
					autoRotate: true,
				},
			},
			meta: {
				condition: {
					alias: "条件",
				},
				averPrice: {
					alias: "均价（元/平）",
				},
			},
		});

		column.render();
	});
});

const props = defineProps({
	data: {
		type: Function,
		required: true,
	},
});
</script>
