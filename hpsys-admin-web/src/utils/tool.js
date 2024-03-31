// import { UUID } from "https://unpkg.com/uuidjs@^5";

const tool = {};

// localStorage
tool.data = {
	set(name, value) {
		const _value = JSON.stringify(value);
		return localStorage.setItem(name, _value);
	},
	get(name) {
		let data = localStorage.getItem(name);
		try {
			data = JSON.parse(data);
		} catch (err) {
			return null;
		}
		return data;
	},
	remove(name) {
		return localStorage.removeItem(name);
	},
	clear() {
		return localStorage.clear();
	},
};

// sessionStorage: 在会话结束时，存储在sessionStorage的数据会被清除
tool.session = {
	set(name, value) {
		const _value = JSON.stringify(value);
		return sessionStorage.setItem(name, _value);
	},
	get(name) {
		let data = sessionStorage.getItem(name);
		try {
			data = JSON.parse(data);
		} catch (err) {
			return null;
		}
		return data;
	},
	remove(name) {
		return sessionStorage.removeItem(name);
	},
	clear() {
		return sessionStorage.clear();
	},
};

// 千分符
tool.groupSeparator = (num) => {
	num = `${num}`;
	if (!num.includes(".")) num += ".";

	return num
		.replace(/(\d)(?=(\d{3})+\.)/g, (_, $1) => {
			return `${$1},`;
		})
		.replace(/\.$/, "");
};

// 获取所有字典数组
tool.dictDataAll = () => {
	return tool.data.get("DICT_TYPE_TREE_DATA");
};

// tool.hpsysUuid = () => {
// 	return "hp" + UUID.generate().slice(2);
// };

export default tool;
