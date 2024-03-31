<template>
	<div>
		<a-calendar
			v-model:value="calendarValue"
			:fullscreen="false"
			@select="onPanelSelect"
		/>
	</div>
</template>

<script setup name="schedule">
import dayjs from "dayjs";
import weekday from "dayjs/plugin/weekday";
import localeData from "dayjs/plugin/localeData";
dayjs.extend(weekday);
dayjs.extend(localeData);

import { required } from "@/utils/formRules";
import { onMounted } from "vue";
const scheduleList = ref([]);
const calendarValue = ref(dayjs());

onMounted(() => {
	// 进来后执行查询
	seleScheduleList();
});

const seleScheduleList = () => {
	const param = {
		scheduleDate: calendarValue.value.format("YYYY-MM-DD"),
	};
};

// 点击某一天
const onPanelSelect = () => {
	seleScheduleList();
};
</script>
