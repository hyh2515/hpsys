<template>
	<baidu-map
		ref="map"
		api-key="Kg2yEizFxUh0JhHI1tRXsCh4Ngk1Kiki"
		mapStyle="06f1ad30645a97a45aea73bde3c647fa"
		:center="[121.48941, 31.40527]"
		viewMode="2D"
		@complete="handleComplete"
		@marker-click="handleMarkerClick"
	/>
</template>

<script setup name="BaiduMap">
import BaiduMap from "@/components/baiduMap/index.vue";
const map = ref(null);

const handleComplete = () => {
	const result = props.data();
	result.then((res) => {
		if (res == null) return;
		map.value.renderMarker(res);
	});
};

const handleMarkerClick = (position) => {
	map.value.openInfoWindow(position);
};

const props = defineProps({
	data: {
		type: Function,
		required: true,
	},
});
</script>
