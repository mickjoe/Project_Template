package com.jerry.pcauto.utils;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Base64Util
{

	/**
	 * base64加密算法 把字符转换为 - base64的字符
	 * 
	 * @param src
	 * @return
	 */
	public static String encode(String src)
	{
		if (src == null)
		{
			return null;
		}

		try
		{
			return Base64.encodeToString(src.getBytes("utf-8"), Base64.NO_WRAP);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * base64加密算法 把字节转换为 - base64的字符
	 * 
	 * @param src
	 * @return
	 */
	public static String encode(byte[] src)
	{
		if (src == null)
		{
			return null;
		}

		BASE64Encoder encoder = new BASE64Encoder();

		return encoder.encode(src);
	}
	
	/**
	 * Base64解码
	 * 把编码的字符进行解码成明文
	 * @param dest
	 */
	public static String decode(String dest)
	{
		if (dest == null)
		{
			return null;
		}
		try
		{
			byte[] buffer = Base64.decode(dest, Base64.NO_WRAP);
			
			return new String(buffer, "utf-8");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Base64解码
	 * 
	 * @param dest
	 */
	public static byte[] decodeToBytes(String dest)
	{
		if (dest == null)
		{
			return null;
		}
		try
		{
			byte[] buffer = Base64.decode(dest,Base64.NO_WRAP);
			
			return buffer;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Base64解码
	 * 把编码的字符进行解码成明文
	 * @param dest
	 */
	public static String decode(byte[] destBytes)
	{
		if (destBytes == null)
		{
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		
		try
		{
			byte[] buffer = Base64.decode(new String(destBytes, "utf-8"),Base64.NO_WRAP);
			
			return new String(buffer, "utf-8");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		// 
		String string = "前锋深圳anroid1509 1507";
		
		String result = Base64Util.encode(string);
		System.out.println("result = "+result);
		
		//result = 5YmN6ZSL5rex5ZyzYW5yb2lkMTUwOSAxNTA3
		
		//String decodeStr = Base64Util.decode(result);
		//System.out.println("decodeStr = "+decodeStr);
		
		String decodeStr = Base64Util.decode(result.getBytes("utf-8"));
		System.out.println("decodeStr = "+decodeStr);
			
	}

}
