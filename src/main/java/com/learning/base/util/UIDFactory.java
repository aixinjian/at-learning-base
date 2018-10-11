package com.learning.base.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Random;

/**
 * <p>
 * </p>
 *
 * @version 1.0
 */
@Slf4j
public abstract class UIDFactory {


    protected static final int NUM0 = 0;
    protected static final int NUM1 = 1;
    protected static final int NUM2 = 2;
    protected static final int NUM3 = 3;
    protected static final int NUM4 = 4;
    protected static final int NUM5 = 5;
    protected static final int NUM6 = 6;
    protected static final int NUM26 = 26;
    protected static final int NUM52 = 52;
    protected static final int NUM62 = 62;
    protected static final int NUM63 = 63;
    protected static final int NUM25 = 25;
    protected static final int NUM51 = 51;
    protected static final int NUM61 = 61;
    protected static final byte NUM0X0F = 0x0f;
    protected static final byte NUM0X03 = 0x03;
    protected static final int NUM0XC0 = 0xc0;
    protected static final int NUM0XF0 = 0xf0;
    protected static final int NUM0XFC = 0xfc;
    protected static final byte NUM0X3F = 0x3f;
    protected static final byte NUM0XF = 0xf;
    protected static final int NUM32 = 32;
    protected static final int NUM16 = 16;
    protected static final int NUM10 = 10;
    protected static final long NUM4294967296L = 4294967296L;
    protected static final int NUM15 = 15;
    protected static final int NUM8 = 8;
    protected static final int NUM255 = 255;
    protected static final int NUM60 = 60;
    protected static final int NUM24 = 24;
    protected static final long NUM30L = 30L;
    protected static final int NUM1000 = 1000;
    protected static final int NUM23 = 23;
    protected static final int NUM59 = 59;
    protected static final int NUM0XFF000000 = 0xff000000;
    protected static final int NUM0XFF0000 = 0xff0000;
    protected static final int NUM0XFF00 = 0xff00;
    protected static final int NUM0XFF = 0xff;
    protected static final int NUM30 = 30;
    protected static final int NUM9 = 9;
    protected static final int NUM11 = 11;
    protected static final int NUM20 = 20;
    protected static final int NUM50 = 50;
    protected static final int NUM100 = 100;
    protected static final int NUM240 = 240;
    protected static final int NUM1024 = 1024;
    protected static final int NUM200 = 200;
    protected static final int NUM127 = 127;

    protected static final int SECOND_PER_MINUTE = 60;
    protected static final int SECOND_PER_HOUR = 3600;
    protected static final int SECOND_PER_DAY = 86400;
    protected static final int SECOND_PER_WEEK = 604800;

    /**
     * Global Unified Identifier
     */
    protected static final String UID_GUID = "GUID";

    /**
     * United Unified Identifier
     */
    protected static final String UID_UUID = "UUID";

    /**
     * Current Epoch millis SEED
     */
    protected static final long EPOCH = System.currentTimeMillis();

    /**
     * JVM Hashcode
     */
    protected static final long JVMHASH = Integer.MIN_VALUE;

    /**
     * Epoch has millisecond
     */
    protected static final long MACHINEID = getMachineID();

    /**
     * Random by seed
     */
    protected static final Random M_RANDOM = new Random(EPOCH);

    /**
     * MD5 Instance
     */
    private static MessageDigest md5;

    /* Initialize MD5 factory */
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * MD5 flag
     */
    private boolean isMd5 = false;

    /**
     * Get Default UIDFactory.
     *
     * @return UIDFactory UID manager object
     */
    public static UIDFactory getDefault() {
        return UUID.getInstance();
    }

    /**
     * Get Specified UIDFactory.
     *
     * @param uidfactory Description of the Parameter
     * @return UIDFactory
     * @throws Exception Description of the Exception
     */
    public static UIDFactory getInstance(String uidfactory) throws Exception {
        if (uidfactory.equalsIgnoreCase(UID_UUID)) {
            return UUID.getInstance();
        }

        throw new Exception(uidfactory + " Not Found!");
    }

    /**
     * Get next UID.
     *
     * @return String Storagable UID
     */
    public abstract String getNextUID();

    /**
     * Get current UID.
     *
     * @return String Storagable UID
     */
    public abstract String getUID();

    /**
     * Is MD5 switch ON.
     *
     * @return ON is true.
     */
    public boolean isMD5() {
        return isMd5;
    }

    /**
     * Set MD5 output.
     *
     * @param flag MD5 switch
     */
    public void setMD5(boolean flag) {
        isMd5 = flag;
    }

    /**
     * Set current UID.
     *
     * @param uid Object uid
     * @throws Exception Description of the Exception
     */
    public abstract void setUID(String uid) throws Exception;

    /**
     * Return Printable ID String.
     *
     * @return String
     */
    public abstract String toPrintableString();

    /**
     * Convert bytes to MD5 bytes.
     *
     * @param bytes Description of the Parameter
     * @return
     */
    protected static byte[] toMD5(byte[] bytes) {
        return md5.digest(bytes);
    }

    /**
     * Gets the machineID attribute of the GUID class
     *
     * @return The machineID value
     */
    private static long getMachineID() {
        long i = 0;

        try {
            InetAddress inetaddress = InetAddress.getLocalHost();
            byte[] abyte0 = inetaddress.getAddress();

            i = toInt(abyte0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return i;
    }

    /**
     * Convert bytes to int utils.
     *
     * @param abyte0 Object bytes array
     * @return Result int
     */
    private static int toInt(byte[] abyte0) {
        int i = ((abyte0[0] << NUM24) & NUM0XFF000000)
                | ((abyte0[NUM1] << NUM16) & NUM0XFF0000)
                | ((abyte0[NUM2] << NUM8) & NUM0XFF00)
                | (abyte0[NUM3] & NUM0XFF);

        return i;
    }

}
