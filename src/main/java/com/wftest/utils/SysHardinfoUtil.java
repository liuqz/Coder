package com.wftest.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * 系统硬件信息工具类
 *
 * Created by Liuqz on 2017-6-30.
 */
public class SysHardinfoUtil {

    /**
     * 获得MAC列表
     *
     * @return
     * @throws Exception
     */
    public static List<String> getMacIds() throws Exception{
        InetAddress ip = null;
        NetworkInterface ni = null;
        List<String> macList = new Vector<String>();

        Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            ni = (NetworkInterface) netInterfaces.nextElement();
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                ip = (InetAddress) ips.nextElement();
                if (!ip.isLoopbackAddress() && ip.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
                    macList.add(formatMacFromBytes(ni.getHardwareAddress()));
                }
            }
        }

        return macList;
    }

    /**
     * 格式化MAC地址
     *
     * @param bytes
     * @return
     */
    private static String formatMacFromBytes(byte[] bytes) {
        StringBuffer mac = new StringBuffer();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append("-");
            }
            currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }
}
