import config from "./config";
import tool from "./utils/tool";
import STable from "./components/Table/index.vue";
import Ellipsis from "./components/Ellipsis/index.vue";
import DragModal from "./components/DragModal/index.vue";
import * as antdvIcons from "@ant-design/icons-vue";

export default {
	install(app) {
		// 挂载全局对象
		app.config.globalProperties.$CONFIG = config;
		app.config.globalProperties.$TOOL = tool;

		// 注册常用组件
		app.component("STable", STable);
		app.component("Ellipsis", Ellipsis);
		app.component("DragModal", DragModal);

		// 统一注册antdv图案
		for (const icon in antdvIcons) {
			app.component(icon, antdvIcons[icon]);
		}
	},
};
