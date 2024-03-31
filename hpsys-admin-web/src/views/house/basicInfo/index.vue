<template>
	<a-row :gutter="[10, 10]">
		<a-col :span="12">
			<a-card title="房源分布" :bordered="false">
				<HouseAvailColumn />
			</a-card>
		</a-col>
		<a-col :span="12">
			<a-card title="价格分布" :bordered="false">
				<AverPriceColumn />
			</a-card>
		</a-col>
	</a-row>
</template>

<script setup name="basicInfo"></script>
