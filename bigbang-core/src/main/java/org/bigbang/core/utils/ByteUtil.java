package org.bigbang.core.utils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @Title ByteUtil.java
 * @Description Byte 转换工具类
 * @Author yzh yingzh@getui.com
 * @Date 05.04.2016
 */
public class ByteUtil{
    /**
     * Get bytes byte [ ].
     *
     * @param data the data
     * @return the byte [ ]
     */
    public static byte[] getBytes(short data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    /**
     * Get bytes byte [ ].
     *
     * @param data the data
     * @return the byte [ ]
     */
    public static byte[] getBytes(char data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data);
        bytes[1] = (byte) (data >> 8);
        return bytes;
    }

    /**
     * Get bytes byte [ ].
     *
     * @param data the data
     * @return the byte [ ]
     */
    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }

    /**
     * Get bytes byte [ ].
     *
     * @param data the data
     * @return the byte [ ]
     */
    public static byte[] getBytes(long data) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        bytes[4] = (byte) ((data >> 32) & 0xff);
        bytes[5] = (byte) ((data >> 40) & 0xff);
        bytes[6] = (byte) ((data >> 48) & 0xff);
        bytes[7] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }

    /**
     * Get bytes byte [ ].
     *
     * @param data the data
     * @return the byte [ ]
     */
    public static byte[] getBytes(float data) {
        int intBits = Float.floatToIntBits(data);
        return getBytes(intBits);
    }

    /**
     * Get bytes byte [ ].
     *
     * @param data the data
     * @return the byte [ ]
     */
    public static byte[] getBytes(double data) {
        long intBits = Double.doubleToLongBits(data);
        return getBytes(intBits);
    }

    /**
     * Get bytes byte [ ].
     *
     * @param data        the data
     * @param charsetName the charset name
     * @return the byte [ ]
     */
    private static byte[] getBytes(String data, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    /**
     * Get bytes byte [ ].
     *
     * @param data the data
     * @return the byte [ ]
     */
    public static byte[] getBytes(String data) {
        return getBytes(data, "GBK");
    }


    /**
     * Gets short.
     *
     * @param bytes the bytes
     * @return the short
     */
    public static short getShort(byte[] bytes) {
        return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    /**
     * Gets char.
     *
     * @param bytes the bytes
     * @return the char
     */
    public static char getChar(byte[] bytes) {
        return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    /**
     * Gets int.
     *
     * @param bytes the bytes
     * @return the int
     */
    public static int getInt(byte[] bytes) {
        return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
    }

    /**
     * Gets long.
     *
     * @param bytes the bytes
     * @return the long
     */
    public static long getLong(byte[] bytes) {
        return (0xffL & (long) bytes[0]) | (0xff00L & ((long) bytes[1] << 8)) | (0xff0000L & ((long) bytes[2] << 16)) | (0xff000000L & ((long) bytes[3] << 24))
                | (0xff00000000L & ((long) bytes[4] << 32)) | (0xff0000000000L & ((long) bytes[5] << 40)) | (0xff000000000000L & ((long) bytes[6] << 48)) | (0xff00000000000000L & ((long) bytes[7] << 56));
    }

    /**
     * Gets float.
     *
     * @param bytes the bytes
     * @return the float
     */
    public static float getFloat(byte[] bytes) {
        return Float.intBitsToFloat(getInt(bytes));
    }

    /**
     * Gets double.
     *
     * @param bytes the bytes
     * @return the double
     */
    public static double getDouble(byte[] bytes) {
        long l = getLong(bytes);
        return Double.longBitsToDouble(l);
    }

    /**
     * Gets string.
     *
     * @param bytes       the bytes
     * @param charsetName the charset name
     * @return the string
     */
    public static String getString(byte[] bytes, String charsetName) {
        return new String(bytes, Charset.forName(charsetName));
    }

    /**
     * Gets string.
     *
     * @param bytes the bytes
     * @return the string
     */
    public static String getString(byte[] bytes) {
        return getString(bytes, "GBK");
    }


    public static <T> T get(Class<T> t, byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return (T) obj;
    }

    public static byte[] getBytes(Serializable obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        short s = 122;
        int i = 122;
        long l = 1222222;

        char c = 'a';

        float f = 122.22f;
        double d = 122.22;

        Man man = new Man("helloworld");


        String string = "helloworld";
        System.out.println(s);
        System.out.println(i);
        System.out.println(l);
        System.out.println(c);
        System.out.println(f);
        System.out.println(d);
        System.out.println(string);

        System.out.println("**************");

        System.out.println(getShort(getBytes(s)));
        System.out.println(getInt(getBytes(i)));
        System.out.println(getLong(getBytes(l)));
        System.out.println(getChar(getBytes(c)));
        System.out.println(getFloat(getBytes(f)));
        System.out.println(getDouble(getBytes(d)));
        System.out.println(getString(getBytes(string)));

        System.out.println(get(Man.class,getBytes(man)));
    }

    public static class Man implements Serializable{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public Man(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Man{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}