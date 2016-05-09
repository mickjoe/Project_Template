package com.jerry.pcauto.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util
{
	private static final String ALGORITHM = "MD5";

	/**
	 * MD5加密
	 * @param src
	 * @return
	 */
	public static byte[] encrypt(String src)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			
			byte[] input = src.getBytes("utf-8");
			
			return md.digest(input);
			
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * MD5 - BASE64编码
	 * @param src
	 * @return
	 */
	public static String encryptWithBase64(String src)
	{
		// MD5加密
		byte[] result = encrypt(src);
		
		// result字节转换为 16 进制内容
		//Integer.toHexString(int)
		String hexString = getHexString(result);
		
		// Base64编码
		return Base64Util.encode(hexString);
	}
	
	private final static String[] HEX_DIGITS = 
		{"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
	
	// bAse64 - a-z A-Z 0-9 + /
	
	/**
	 * 
	 * @return
	 */
	private static String getHexString(byte[] result)
	{
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < result.length; i++)
		{
//			System.out.println(Integer.toHexString(result[i]));
//				buffer.append(Integer.toHexString(result[i]));
			int iResult = result[i];
			
			if(iResult < 0)
			{
				iResult += 256;
			}
			
			int id1 = iResult / 16;
			int id2 = iResult % 16;
			
			// 5 d
			System.out.println(HEX_DIGITS[id1] + HEX_DIGITS[id2]);
			buffer.append(HEX_DIGITS[id1] + HEX_DIGITS[id2]);
		}
		System.out.println(buffer);
		return buffer.toString();
	}
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException
	{
			String src = "前锋深圳anroid1509 1507";
			
			byte[] result = MD5Util.encrypt(src);
			String encrptStr = new String(result,"utf-8");
			System.out.println("encrptStr = "+encrptStr);
			
			String encrptStr2 =  encryptWithBase64(src);
			System.out.println("encrptStr2 = "+encrptStr2);
	}

}





