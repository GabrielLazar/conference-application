package com.gabriellazar.conferenceapp.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonUtilsTest {

    @Test
    public void testGetHostNameFromSystem(){
       String hostName = CommonUtils.getHostNameFromSystem();
       assertNotNull(hostName);
    }

}