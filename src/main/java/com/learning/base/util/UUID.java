package com.learning.base.util;

/**
 * <p>
 * </p>
 *
 * @version 1.0
 */
public class UUID extends UIDFactory {
    /**
     * Length bits
     */
    protected static final int BITS8 = 8;

    /**
     * Length byte
     */
    protected static final int BYTELEN = 16;

    /**
     * High order mask
     */
    protected static final int HIMASK = 240;

    /**
     * Low order 8bits mask
     */
    protected static final int LO8BITMASK = 255;

    /**
     * Low order mask
     */
    protected static final int LOMASK = 15;

    /**
     * Upper limit Short
     */
    protected static final long MAX_INT = 32767;

    /**
     * Upper limit Integer
     */
    protected static final long MAX_LONG = 2147483647;

    /** Epoch has millisecond */
    /**
     * High order tag
     */
    private long mHiTag;

    /**
     * Low order tag
     */
    private long mLoTag;

    /**
     * UUID Cache
     */
    private String mUuid = null;

    /**
     * Construct overpass user data.
     *
     * @param highTag High order tag
     * @param loTag   Low order tag
     */
    protected UUID(long highTag, long loTag) {
        mHiTag = highTag;
        mLoTag = loTag;
        mUuid = toString(this.toByteArray());
    }

    /**
     * Construct default.
     */
    protected UUID() {
        next();
        mUuid = toString(this.toByteArray());
    }

    /**
     * Return high order byte.
     *
     * @param b Object byte
     * @return Result byte
     */
    private static byte hiNibble(byte b) {
        return (byte) ((b >> NUM4) & NUM0XF);
    }

    /**
     * Return low order byte.
     *
     * @param b Object byte
     * @return Result byte
     */
    private static byte loNibble(byte b) {
        return (byte) (b & NUM0XF);
    }

    /**
     * Get back next new uid.
     *
     * @return java.lang.String
     */
    public String getNextUID() {
        next();

        return mUuid;
    }

    /**
     * Get back current uid.
     *
     * @return java.lang.String
     */
    public String getUID() {
        return mUuid;
    }

    /**
     * Set current UID.
     *
     * @param uidStr The new uID value
     * @throws Exception Bad string format
     */
    public void setUID(String uidStr) throws Exception {
        long loTag = 0L;
        long hiTag = 0L;
        int len = uidStr.length();

        if (NUM32 != len) {
            throw new Exception("bad string format");
        }

        int i = 0;
        int idx = 0;

        for (; i < 2; i++) {
            loTag = 0L;

            for (int j = 0; j < (len / 2); j++) {
                String s = uidStr.substring(idx++, idx);
                int val = Integer.parseInt(s, NUM16);

                loTag <<= NUM4;
                loTag |= val;
            }

            if (i == 0) {
                hiTag = loTag;
            }
        }

        mHiTag = hiTag;
        mLoTag = loTag;
        mUuid = toString(this.toByteArray());
    }

    /**
     * Get printable String.
     *
     * @return java.lang.String
     */
    public String toPrintableString() {
        byte[] bytes = toByteArray();

        if (NUM16 != bytes.length) {
            return "** Bad UUID Format/Value **";
        }

        StringBuffer buf = new StringBuffer();
        int i;

        for (i = 0; i < NUM4; i++) {
            buf.append(Integer.toHexString(hiNibble(bytes[i])));
            buf.append(Integer.toHexString(loNibble(bytes[i])));
        }

        while (i < NUM10) {
            buf.append('-');

            int j = 0;

            while (j < 2) {
                buf.append(Integer.toHexString(hiNibble(bytes[i])));
                buf.append(Integer.toHexString(loNibble(bytes[i++])));
                j++;
            }
        }

        buf.append('-');

        for (; i < NUM16; i++) {
            buf.append(Integer.toHexString(hiNibble(bytes[i])));
            buf.append(Integer.toHexString(loNibble(bytes[i])));
        }

        return buf.toString();
    }

    /**
     * Return UID String.
     *
     * @return UID String
     */
    public String toString() {
        return mUuid;
    }

    /**
     * Get new UUID instance.
     *
     * @return UUID
     */
    protected static UIDFactory getInstance() {
        return new UUID();
    }

    /**
     * Overpass a bytes array generator UID String.
     *
     * @param bytes Object bytes array
     * @return UID String
     */
    protected static String toString(byte[] bytes) {
        if (NUM16 != bytes.length) {
            return "** Bad UUID Format/Value **";
        }

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < NUM16; i++) {
            buf.append(Integer.toHexString(hiNibble(bytes[i])));
            buf.append(Integer.toHexString(loNibble(bytes[i])));
        }

        return buf.toString();
    }

    /**
     * Generator & get back a UUID & cache String.
     */
    protected void next() {
        mHiTag = (System.currentTimeMillis() + (JVMHASH * NUM4294967296L)) ^ MACHINEID;
        mLoTag = EPOCH + Math.abs(M_RANDOM.nextLong());
        mUuid = toString(this.toByteArray());
    }

    /**
     * Overpass high order tag & low order tag convert to array bytes.
     *
     * @return Array bytes
     */
    protected byte[] toByteArray() {
        byte[] bytes = new byte[NUM16];
        int idx = NUM15;
        long val = mLoTag;

        for (int i = 0; i < NUM8; i++) {
            bytes[idx--] = (byte) (int) (val & (long) NUM255);
            val >>= NUM8;
        }

        val = mHiTag;

        for (int i = 0; i < NUM8; i++) {
            bytes[idx--] = (byte) (int) (val & (long) NUM255);
            val >>= NUM8;
        }

        if (!this.isMD5()) {
            return bytes;
        } else {
            return toMD5(bytes);
        }
    }
}
