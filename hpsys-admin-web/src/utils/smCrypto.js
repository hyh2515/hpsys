import smCrypto from "sm-crypto";

const sm2 = smCrypto.sm2;
const cipherMode = 1; // 1 - C1C3C2, 0 - C1C2C3, 默认为1
const publicKey =
	"045274c63e9feb5db589a141b78c16bf122172932b71cf45f8c467113ca9e87f9aa66522e539bc832e867303b818fd3435973613067f403c0fde6d44a27797f790";

/**
 * 国密加解密工具类
 */
export default {
	// SM2加密
	doSm2Encrypt(msgString) {
		return sm2.doEncrypt(msgString, publicKey, cipherMode);
	},
	// SM2数组加密
	doSm2ArrayEncrypt(msgString) {
		return sm2.doEncrypt(msgString, publicKey, cipherMode);
	},
};
