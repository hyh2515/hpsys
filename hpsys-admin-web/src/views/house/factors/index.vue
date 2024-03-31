<template>
	<a-tabs v-model:activeKey="activeKey">
		<a-tab-pane key="map" tab="房源均价地图">
			<BaiduMap :data="mapData" />
		</a-tab-pane>
		<a-tab-pane key="condition" tab="房源配置">
			<a-row :gutter="[10, 10]">
				<a-col :span="12">
					<a-card :bordered="false">
						<ConditionFactorsBar :data="loadCondition" />
					</a-card>
				</a-col>
				<a-col :span="12">
					<a-card :bordered="false">
						<ConditionFactorsColumn :data="loadCondition" />
					</a-card>
				</a-col>
			</a-row>
		</a-tab-pane>
	</a-tabs>
</template>

<script setup name="factors">
import BaiduMap from "./baiduMap.vue";
import houseInfoApi from "@/api/houseInfoApi";

const activeKey = ref("map");
const mapData = async () => {
	let data = [];
	await houseInfoApi.listHouseInfo().then((res) => {
		res.forEach((item) => {
			data.push({
				title: "价格: " + item.average + "元每平",
				position: [item.coordinateX, item.coordinateY],
			});
		});
	});
	return data;
};
const loadCondition = async () => {
	let data = new Array(6);
	let data2 = new Array(6);
	let count = new Array(6);
	for (let i = 0; i < 6; ++i) {
		data[i] = new Object({ condition: "", averPrice: 0 });
		data2[i] = new Object({
			condition: "",
			total: 0,
		});
		count[i] = 0;
	}
	data[0].condition = "购物";
	data[1].condition = "教育";
	data[2].condition = "交通";
	data[3].condition = "健身";
	data[4].condition = "环境";
	data[5].condition = "医疗";
	await houseInfoApi.listHouseInfo().then((res) => {
		res.forEach((item) => {
			if (item.facility0 === "1") {
				++count[0];
				data[0].averPrice += item.average;
			} else if (item.facility1 === "1") {
				++count[1];
				data[1].averPrice += item.average;
			} else if (item.facility2 === "1") {
				++count[2];
				data[2].averPrice += item.average;
			} else if (item.facility3 === "1") {
				++count[3];
				data[3].averPrice += item.average;
			} else if (item.facility4 === "1") {
				++count[4];
				data[4].averPrice += item.average;
			} else if (item.facility5 === "1") {
				++count[5];
				data[5].averPrice += item.average;
			}
		});
		for (let i = 0; i < 6; ++i) {
			data2[i].condition = data[i].condition;
			data2[i].total = count[i];
		}
		for (let i = 0; i < 6; ++i) {
			data[i].averPrice = parseFloat(
				(data[i].averPrice / count[i]).toFixed(2)
			);
		}
	});
	return [data, data2];
};
</script>
