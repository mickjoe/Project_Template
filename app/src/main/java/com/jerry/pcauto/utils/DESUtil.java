package com.jerry.pcauto.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES-Data Encryption Standard,即数据加密算法
 * @author jerry
 *
 */
public class DESUtil
{
	private static final String ALGORITHM = "DES";
	
	/**
	 * 生成Key，一般最后进行Base64编码
	 * @param seed
	 * @return
	 */
	private static String initKey(String seed)
	{
		SecureRandom secureRandom = null;
		if (seed != null)
		{
			secureRandom = new SecureRandom(seed.getBytes());
		}
		else
		{
			secureRandom = new SecureRandom();
		}
		
		try
		{
			KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
			
			keyGenerator.init(secureRandom);
			
			SecretKey key = keyGenerator.generateKey();
			
			byte[] keyBytes = key.getEncoded();
			
			return Base64Util.encode(keyBytes);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * DES加密
	 * @param src
	 * @return
	 * @throws Exception 
	 */
	public static String encrypt(String src,String keyString) throws Exception
	{
		// Base64编码 key - 转换为SecretKey对象
		Key key = getKey(keyString);
		
		Cipher cipher = Cipher.getInstance(ALGORITHM);

		// 设置模式 - 加密
		//Cipher.ENCRYPT_MODE
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		byte[] finalByte = cipher.doFinal(src.getBytes("utf-8"));
		
		return Base64Util.encode(finalByte);
	}
	
	/**
	 * DES解密
	 * @param encodeString
	 * @param keyString
	 * @return
	 * @throws Exception
	 */
	public static String decrypyt(String encodeString,String keyString) throws Exception
	{
		// Base64编码 key - 转换为SecretKey对象
		Key key = getKey(keyString);
		
		Cipher cipher = Cipher.getInstance(ALGORITHM);

		// 设置模式 - 解密
		//Cipher.DECRYPT_MODE
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		//String destStr = Base64Util.decode(encodeString);
		byte[] destBytes = Base64Util.decodeToBytes(encodeString);
		
		byte[] finalByte = cipher.doFinal(destBytes);
		
		return new String(finalByte,"utf-8");
		
	}
	
	/**
	 * Base64编码 key - 转换为SecretKey对象
	 * @param keyString
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
	 */
	public static Key getKey(String keyString) throws Exception
	{
		// 解码的字符
		String decodeString = Base64Util.decode(keyString);
		
		DESKeySpec spec = new DESKeySpec(decodeString.getBytes("utf-8"));
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
		
		return factory.generateSecret(spec);
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		// key
		// data
		// mode
		String src = "前锋深圳anroid1509 1507";
		
		String key = DESUtil.initKey("1509");
		
		String encryptString = DESUtil.encrypt(src, key);
		System.out.println("encryptString = "+encryptString);
		
		String decryptString = DESUtil.decrypyt(encryptString, key);
		System.out.println("decryptString = "+decryptString);
	}

}
