package com.learning.base.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Static functions to simplifiy common {@link MessageDigest}
 * tasks. This class is thread safe.
 *
 * @author 99bill
 *
 */
public class MD5Util {

	private static Logger logger = LoggerFactory.getLogger(MD5Util.class);

	private MD5Util() {
	}

	/**
	 * Returns a MessageDigest for the given <code>algorithm</code>.
	 *
	 *            The MessageDigest algorithm name.
	 * @return An MD5 digest instance.
	 * @throws RuntimeException
	 *             when a {@link NoSuchAlgorithmException} is
	 *             caught
	 */

	static MessageDigest getDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	public static byte[] md5(byte[] data) {
		return getDigest().digest(data);
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	public static byte[] md5(String data, String charset) {
	    if ( charset == null ) {
	        return md5(data.getBytes());
	    } else {
	        try {
                return md5(data.getBytes(charset));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
            }
	        
	        return null;
	    }
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String md5Hex(byte[] data) {
		return HexUtil.toHexString(md5(data));
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String md5Hex(String data) {
		return HexUtil.toHexString(md5(data, null));
	}
	
	public static String md5Hex(String data, String charset) {
        return HexUtil.toHexString(md5(data, charset));
    }

	public  static String encode(String myinfo){
		MessageDigest messageDigest = getDigest();
		try {
			messageDigest.update(myinfo.getBytes("utf-8"));
			byte[] digesta = messageDigest.digest();
			return HexUtil.byte2hex(digesta);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static void main(String[] args){
		String opt = "";
		String uid = "";
		System.out.println(md5Hex("11111111"));
	}
}
