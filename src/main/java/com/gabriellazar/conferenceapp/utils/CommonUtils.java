package com.gabriellazar.conferenceapp.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * The purpose of this class acts as a Utility class contains the generic methods to be used across the module.
 */
@Slf4j
public class CommonUtils {

    public static String getHostNameFromSystem() {
        String result = "Unknown";
        try {
            InetAddress ia = InetAddress.getLocalHost();
            result = ia.getHostName();
            int indx = result.indexOf('.');
            if (indx > 0) {
                result = result.substring(0, indx);
            }
        } catch (Exception e) {
            log.error("Could not get hostname. Exception:: {}", e);
        }
        return result;
    }
}
