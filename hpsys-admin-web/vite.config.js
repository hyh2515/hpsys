import { defineConfig, loadEnv } from "vite";
import vue from "@vitejs/plugin-vue";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { VantResolver } from "unplugin-vue-components/resolvers";
import { resolve } from "path";
import viteCompression from "vite-plugin-compression";
import vueSetupExtend from "vite-plugin-vue-setup-extend";

export default defineConfig(({ command, mode }) => {
	const envConfig = loadEnv(mode, "./");
	return {
		base: "/hpsys-front/",
		plugins: [
			vue({
				script: {
					refTransform: true,
				},
			}),
			viteCompression(),
			vueSetupExtend(),
			Components({
				resolvers: [VantResolver()],
				dts: false,
				dirs: "src/components",
			}),
			AutoImport({
				imports: ["vue"],
				dirs: ["./src/utils/permission"],
				dts: "src/auto-imports.d.ts",
			}),
		],
		css: {
			preprocessorOptions: {
				less: {
					javascriptEnabled: true,
				},
			},
		},
		server: {
			port: envConfig.VITE_PORT,
			proxy: {
				"/api": {
					target: envConfig.VITE_API_BASEURL,
					ws: false,
					changeOrigin: true,
					rewrite: (path) => path.replace(/^\/api/, ""),
				},
			},
		},
		resolve: {
			alias: {
				"~": `${resolve(__dirname, "./")}`,
				"@/": `${resolve(__dirname, "src")}/`,
			},
		},
	};
});
