import { createApp } from "vue";
import Antd from "ant-design-vue";
import { createPinia } from "pinia";
import App from "./App.vue";

import "./style/index.less";
import hpsys from "./hpsys";
import router from "./router";

const app = createApp(App);
app.use(createPinia());
app.use(router);
app.use(Antd);
app.use(hpsys);

// 挂在app
app.mount("#app");
