package com.aerobit.simplemenu.client;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "simplemenu", name = "Simple Menu", version = "1.0")
public class SimpleMenu {

    @Instance
    public SimpleMenu instance;
    public static GuiNewMainMenu gui;

    static boolean firstPersonEnabled;
    static boolean maintainRotationOnQuickload;
    public static float rotateRate;

    @EventHandler
    public void loadConfigs(FMLPreInitializationEvent ev) {
        Configuration config = new Configuration(ev.getSuggestedConfigurationFile());
        config.load();

        firstPersonEnabled = config.get(config.CATEGORY_GENERAL, "firstPersonEnabled", false).getBoolean(false);
        maintainRotationOnQuickload = config.get(config.CATEGORY_GENERAL, "maintainRotationOnQuickload", true).getBoolean(true);
        rotateRate = (float) config.get(config.CATEGORY_GENERAL, "rotateSpeed", 0.5D).getDouble(0.5D);

        config.save();

    }

    @EventHandler
    public void load(FMLInitializationEvent ev) {
        instance = this;
        gui = new GuiNewMainMenu();
        TickRegistry.registerTickHandler(new GuiOverride(), Side.CLIENT);
        MinecraftForge.EVENT_BUS.register(new GameOverlayRenderHandler());
    }

}
