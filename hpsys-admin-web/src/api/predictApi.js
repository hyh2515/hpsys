import { moduleRequest } from "@/utils/request.js";
const request = moduleRequest(`/predict/`);
// 房屋信息
export default {
	getPredictPrice(data) {
		return request("getPredictPrice", data, "post");
	},
};
