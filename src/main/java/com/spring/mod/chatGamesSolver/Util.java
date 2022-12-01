package com.spring.mod.chatGamesSolver;

import net.minecraft.client.Minecraft;

public class Util {

    public static void sendMCMessage(String msg){
        sendMCMessage(msg, 2900, 2250);
    }
    public static void sendMCMessage(String msg, int max, int min) {
        try {
            double timeout = Math.random() * (max - min) + min;
            Thread.sleep((long) timeout);
            Minecraft.getMinecraft().thePlayer.sendChatMessage(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
