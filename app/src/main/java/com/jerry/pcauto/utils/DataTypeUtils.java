package com.jerry.pcauto.utils;

import java.util.Arrays;

public class DataTypeUtils
{
    public static int unsignedByteToInt(byte b)
    {
        return (int) b & 0xFF;
    }

    public static String byteToHex(byte b)
    {
        int i = b & 0xFF;
        return Integer.toHexString(i);
    }

    public static long unsigned4BytesToInt(byte[] buf, int pos)
    {
        int firstByte = 0;
        int secondByte = 0;
        int thirdByte = 0;
        int fourthByte = 0;
        int index = pos;
        firstByte = (0x000000FF & ((int) buf[index]));
        secondByte = (0x000000FF & ((int) buf[index + 1]));
        thirdByte = (0x000000FF & ((int) buf[index + 2]));
        fourthByte = (0x000000FF & ((int) buf[index + 3]));
        index = index + 4;
        return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
    }

    public static byte[] shortToByteArray(short s)
    {
        byte[] targets = new byte[2];
        for (int i = 0;
             i < 2;
             i++)
        {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }


    public static byte[] intToByteArray(int s)
    {
        byte[] targets = new byte[2];
        for (int i = 0;
             i < 4;
             i++)
        {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }

    /**
     * long to byte[]
     *
     * @param s long
     * @return byte[]
     */
    public static byte[] longToByteArray(long s)
    {
        byte[] targets = new byte[2];
        for (int i = 0;
             i < 8;
             i++)
        {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }

    /**
     * 32λintתbyte[]
     */
    public static byte[] int2byte(int res)
    {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);
        targets[1] = (byte) ((res >> 8) & 0xff);
        targets[2] = (byte) ((res >> 16) & 0xff);
        targets[3] = (byte) (res >>> 24);
        return targets;
    }


    public static int byte2int(byte[] res)
    {
        // res = InversionByte(res);
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00);
        return targets;
    }


    public static short get2BytesToShort(byte[] body, int position)
    {
        byte[] bytes = new byte[2];
        //      copyBytesDown(body, position, bytes, 2);
        return (short) (((short) bytes[0] << 8) | (0xff & bytes[1]));
    }

    public static int get4BytesToInt(byte[] body, int position)
    {
        byte[] bytes = Arrays.copyOfRange(body, position, position + 4);
        //copyBytesDown(body, position, bytes, 4);
        return (int) bytes[0] << 24 |
                (0xff & bytes[1]) << 16 |
                (0xff & bytes[2]) << 8 |
                (0xff & bytes[3]);
    }


    public static long get8BytesToLong(byte[] body, int position)
    {
        byte[] bytes = new byte[8];
        //      copyBytesDown(body, position, bytes, 8);
        return (long) bytes[0] << 56 |
                (long) (0xff & bytes[1]) << 48 |
                (long) (0xff & bytes[2]) << 40 |
                (long) (0xff & bytes[3]) << 32 |
                (long) (0xff & bytes[4]) << 24 |
                (0xff & bytes[5]) << 16 |
                (0xff & bytes[6]) << 8 |
                (0xff & bytes[7]);
    }

    public static void main(String[] args)
    {

    }

    public static byte[] int2ByteArray(int target)
    {
        byte[] array = new byte[4];
        for (int i = 0;
             i < 4;
             i++)
        {
            int offSet = array.length - i - 1;
            array[i] = (byte) (target >> 8 * offSet & 0xFF);
        }
        return array;
    }

    public static int byteArray2Int(byte[] array)
    {
        int result = 0;
        byte loop;

        for (int i = 0;
             i < 4;
             i++)
        {
            loop = array[i];
            int offSet = array.length - i - 1;
            result += (loop & 0xFF) << (8 * offSet);

        }
        return result;
    }

    public static String toHexString(String s)
    {
        String str = "";
        for (int i = 0;
             i < s.length();
             i++)
        {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

}
