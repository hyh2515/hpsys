<template>
	<a-row :gutter="10">
		<a-col :span="16" :xs="24" :sm="24" :md="24" :lg="16" :xl="16">
			<userInfo class="mb-2" />
			<shortcut class="mb-2" />
		</a-col>
		<a-col :span="8" :xs="24" :sm="24" :md="24" :lg="8" :xl="8">
			<schedule class="mb-2" />
		</a-col>
	</a-row>
</template>

<script setup name="indexHome">
import UserInfo from "./components/userInfo.vue";
import Shortcut from "./components/shortcut.vue";
import Schedule from "./components/schedule.vue";
</script>
