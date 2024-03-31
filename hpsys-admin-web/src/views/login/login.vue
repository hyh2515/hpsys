<template>
	<div class="login-bgc">
		<div class="login-main">
			<div class="login-form">
				<a-card>
					<div class="login-header">
						<div class="logo">
							<img
								:src="sysBaseConfig.HPSYS_SYS_LOGO"
								:alt="sysBaseConfig.HPSYS_SYS_NAME"
							/>
							<label>{{ sysBaseConfig.HPSYS_SYS_NAME }}</label>
						</div>
					</div>
					<a-tabs v-model:activeKey="activeKey">
						<a-tab-pane key="userAccount" tab="账号密码">
							<a-form
								ref="loginForm"
								:model="ruleForm"
								:rule="rules"
							>
								<a-form-item name="account">
									<a-input
										v-model:value="ruleForm.account"
										placeholder="请输入账号"
										size="large"
										@keyup.enter="login"
									>
										<template #prefix>
											<UserOutlined
												class="login-icon-gray"
											/>
										</template>
									</a-input>
								</a-form-item>
								<a-form-item name="password">
									<a-input-password
										v-model:value="ruleForm.password"
										placeholder="请输入密码"
										size="large"
										autocomplete="off"
										@keyup.enter="login"
									>
										<template #prefix>
											<LockOutlined
												class="login-icon-gray"
											/>
										</template>
									</a-input-password>
								</a-form-item>
								<a-form-item>
									<a href="#" style="color: #0d84ff"
										>忘记密码？</a
									>
								</a-form-item>
								<a-form-item>
									<a-button
										type="primary"
										class="w-full"
										:loading="loading"
										round
										size="large"
										@click="login"
										>登录</a-button
									>
								</a-form-item>
							</a-form>
						</a-tab-pane>
					</a-tabs>
				</a-card>
			</div>
		</div>
	</div>
</template>

<script setup>
import { required } from "@/utils/formRules";
import configDate from "@/config";
import smCrypto from "@/utils/smCrypto";
import { afterLogin } from "./utils";
import loginApi from "@/api/loginApi";
import { useGlobalStore, keepAliveStore, iframeStore } from "@/store";

const activeKey = ref("userAccount");
const loading = ref(false);

const ruleForm = reactive({
	account: "Admin",
	password: "123456",
});

const rules = reactive({
	account: [required("请输入账号", "blur")],
	password: [required("请输入密码", "blur")],
});

const store = useGlobalStore();
const kStore = keepAliveStore();
const iStore = iframeStore();

const setSysBaseConfig = store.setSysBaseConfig;
const clearKeepLive = kStore.clearKeepLive;
const clearIframeList = iStore.clearIframeList;

const sysBaseConfig = computed(() => {
	return store.sysBaseConfig;
});

onBeforeMount(() => {
	clearKeepLive();
});

// 登录
const loginForm = ref();
const login = async () => {
	loginForm.value.validate().then(async () => {
		loading.value = true;
		const loginData = {
			account: ruleForm.account,
			// 密码使用sm2加密，传输过程只看到密文，后端存储使用hash
			password: smCrypto.doSm2Encrypt(ruleForm.password),
		};
		// 获取Token
		try {
			const loginToken = await loginApi.login(loginData);
			afterLogin(loginToken);
		} catch (err) {
			loading.value = false;
		}
	});
};
</script>

<style lang="less" scoped>
@import "./login";
</style>
