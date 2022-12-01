package com.spring.mod;

import com.spring.mod.chatGamesSolver.ChatGameSolver;
import com.spring.mod.commands.toggle;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import tv.twitch.chat.Chat;

@Mod(modid = GlobalVars.MOD_ID, version = GlobalVars.MOD_VERSION, name = GlobalVars.MOD_NAME)
public class Main {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

        ChatGameSolver.createConfigFile();
        MinecraftForge.EVENT_BUS.register(new ChatGameSolver());
    }



}
