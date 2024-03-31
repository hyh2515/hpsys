import axios from "axios";
import qs from "qs";
import { message, Modal, notification } from "ant-design-vue";
import sysConfig from "@/config/index.js";
import tool from "@/utils/tool.js";

// 以下这些code需要重新登录
const reloadCodes = [401, 1011007, 1011008];
const errorCodeMap = {
	400: "发出的请求有错误，服务器没有进行新建或修改数据的操作。",
	401: "用户没有权限（令牌、用户名、密码错误）。",
	403: "用户得到授权，但是访问是被禁止的。",
	404: "发出的请求针对的是不存在的记录，服务器没有进行操作。",
	406: "请求的格式不可得。",
	410: "请求的资源被永久删除，且不会再得到的。",
	422: "当创建一个对象时，发生一个验证错误。",
	500: "服务器发生错误，请检查服务器。",
	502: "网关错误。",
	503: "服务不可用，服务器暂时过载或维护。",
	504: "网关超时。",
};
// 定义一个重新登录弹出窗的变量
const loginBack = ref(false);
// 创建 axios 实例
const service = axios.create({
	baseURL: "/api", // api base_url
	timeout: sysConfig.TIMEOUT, // 请求超时时间
});

// HTTP request拦截器
service.interceptors.request.use(
	(config) => {
		const token = tool.data.get("TOKEN");
		if (token) {
			config.headers[sysConfig.TOKEN_NAME] =
				sysConfig.TOKEN_PREFIX + token;
		}
		if (!sysConfig.REQUEST_CACHE && config.method === "get") {
			config.params = config.params || {};
			config.params._ = new Date().getTime();
		}
		Object.assign(config.headers, sysConfig.HEADERS);
		return config;
	},
	(error) => {
		return Promise.reject(error);
	}
);

// 保持登录唯一性
const error = () => {
	loginBack.value = true;
	Modal.error({
		title: "提示：",
		okText: "重新登录",
		content: "登录已失效，请重新登录",
		onOK: () => {
			loginBack.value = false;
			tool.data.remove("TOKEN");
			tool.data.remove("USER_INFO"), window.location.reload();
		},
	});
};

// HTTP response拦截器
service.interceptors.response.use(
	(response) => {
		// 配置了blob，不处理直接返回文件流
		if (response.config.responseType === "blob") {
			if (response.status === 200) {
				return response;
			} else {
				message.warning("文件下载失败或此文件不存在");
				return;
			}
		}
		const data = response.data;
		const code = data.code;
		if (reloadCodes.includes(code)) {
			if (!loginBack.value) {
				error();
			}
			return;
		}
		if (code !== 200) {
			const customErrorMessage = response.config.customErrorMessage;
			message.error(customErrorMessage || data.msg);
			return Promise.reject(data);
		} else {
			// 成功提示
			const responseUrls = response.config.url.split("/");
			const apiNameArray = [
				// 统一接口，避免错误接口传输
				"add",
				"edit",
				"delete",
				"update",
			];
			apiNameArray.forEach((apiName) => {
				if (responseUrls[responseUrls.length - 1].includes(apiName)) {
					message.success(data.msg);
				}
			});
		}
		return Promise.resolve(data.data);
	},
	(error) => {
		if (error) {
			const status = 503;
			const description = errorCodeMap[status];
			notification.error({
				message: "请求错误",
				description,
			});
			return Promise.reject(status);
		}
	}
);

// 适配器, 用于适配不同的请求方式
export const baseRequest = (url, value = {}, method = "post", options = {}) => {
	url = sysConfig.API_URL + url;
	if (method === "post") {
		return service.post(url, value, options);
	} else if (method === "get") {
		return service.get(url, { params: value, ...options });
	} else if (method === "formdata") {
		// form-data表单提交的方式
		return service.post(url, qs.stringify(value), {
			headers: {
				"Content-Type": "multipart/form-data",
			},
			...options,
		});
	} else {
		// 其他请求方式，例如：put、delete
		return service({
			method: method,
			url: url,
			data: value,
			...options,
		});
	}
};

// 模块内的请求, 会自动加上模块的前缀
export const moduleRequest =
	(moduleUrl) =>
	(url, ...arg) => {
		return baseRequest(moduleUrl + url, ...arg);
	};

export default service;
