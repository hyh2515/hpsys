<template>
	<div id="conditionFactorsColumn"></div>
</template>

<script setup>
import { onMounted } from "vue";
import { Bar } from "@antv/g2plot";

onMounted(() => {
	const result = props.data();
	result.then((res) => {
		const data = res[1];
		const bar = new Bar("conditionFactorsColumn", {
			title: {
				visible: true,
				text: "平均每1000套房周边配置数",
			},
			data,
			xField: "total",
			yField: "condition",
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
				total: {
					alias: "总计",
				},
			},
		});

		bar.render();
	});
});

const props = defineProps({
	data: {
		type: Function,
		required: true,
	},
});
</script>
