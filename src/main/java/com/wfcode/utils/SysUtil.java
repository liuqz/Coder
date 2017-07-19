package com.wfcode.utils;

import org.apache.commons.lang3.SystemUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

/**
 * 系统硬件信息工具类
 *
 * Created by Liuqz on 2017-6-30.
 */
public abstract class SysUtil {
    private static final int OS_WINDOWS = 1;
    private static final int OS_LINUX = 2;
    private static final int OS_MAC = 3;

    /**
     * 取得当前OS类型
     * @return
     */
    private static int getCurrentOS() {
        try {
            if(SystemUtils.IS_OS_WINDOWS) return OS_WINDOWS;
            if(SystemUtils.IS_OS_LINUX) return OS_LINUX;
            if(SystemUtils.IS_OS_MAC) return OS_MAC;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获得系统信息
     *
     * @return
     * @throws Exception
     */
    public static String getSysInfo() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("Host: " + getHostName() + "\n");
        builder.append("OS: " + getOS() + "\n");
        builder.append("MAC: ");
        List<String> macs = getMacIds();
        for(String mac : macs){
            builder.append(mac + "\n");
        }
        return builder.toString();
    }

    /**
     * 获得MAC列表
     *
     * @return
     * @throws Exception
     */
    private static List<String> getMacIds() throws Exception{
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

        Collections.sort(macList);
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

    /**
     * 获得机器名
     *
     * @return
     */
    private static String getHostName() {
        return SystemUtils.getHostName();
    }

    /**
     * 获得操作系统名称
     *
     * @return
     */
    private static String getOS() {
       return SystemUtils.OS_NAME;
    }

    /**
     * 获得操作系统版本
     *
     * @return
     */
    private static String getOSVersion() {
        return SystemUtils.OS_VERSION;
    }



    // =========== Linux平台可以使用的方法 ==========



    // =========== Windows平台可以使用的方法 ==========

    /**
     * 获得CpuID
     *
     * @return
     * @throws Exception
     */
    private static String getCpuIDAtWin() throws Exception {
        long start = System.currentTimeMillis();
        Process process = Runtime.getRuntime().exec(
                new String[] { "wmic", "cpu", "get", "ProcessorId" });
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        return serial;
    }

    // =========== Mac平台可以使用的方法 ==========

}
