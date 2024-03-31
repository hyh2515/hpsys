import { moduleRequest } from "@/utils/request.js";
const request = moduleRequest(`/houseInfo/`);
// 房屋信息
export default {
	availStat(data) {
		return request("availStat", data, "get");
	},
	averPrice(data) {
		return request("averPrice", data, "get");
	},
	listZipCode(data) {
		return request("listZipCode", data, "get");
	},
	getDistrictAverage(data) {
		return request("getDistrictAverage", data, "post");
	},
	listHouseInfo(data) {
		return request("listHouseInfo", data, "get");
	},
};
