package com.baimao.bmapisdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @author baimao
 * @title SignUtil
 *
 * api 签名加密
 */
public class SignUtil {

    /**
     * 将用户参数和密钥拼接加密
     * @param body 用户参数
     * @param accessSecret 用户密钥
     * @return
     */
   public static String Sign(String body,String accessSecret) {
       Digester md5 = new Digester(DigestAlgorithm.SHA256);
       String digestHex = md5.digestHex(body + accessSecret);
       return digestHex;
   }

}
