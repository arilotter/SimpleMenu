package com.aerobit.simplemenu.client;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiYesNo;

import java.util.EnumSet;

public class GuiOverride implements ITickHandler {

    static int world;
    boolean flag;
    private boolean shouldUnloadWorld;
    private boolean shouldTryToEnableRenderWorld;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen instanceof GuiMainMenu && flag == false) {
            SimpleMenu.gui.loadMostRecentWorld();
            if (SimpleMenu.gui.renderWorld) {
                world = mc.getIntegratedServer().hashCode();
            } else {
                mc.displayGuiScreen(SimpleMenu.gui);
            }
            flag = true;
        }

        if (mc.getIntegratedServer() != null && mc.getIntegratedServer().hashCode() == world) {
            if (mc.thePlayer != null && (mc.currentScreen == null || mc.currentScreen instanceof GuiIngameMenu)) {
                mc.displayGuiScreen(SimpleMenu.gui);
            }
        } else {
            flag = false;
        }

        if (mc.currentScreen instanceof GuiNewMainMenu) {
            SimpleMenu.gui.closeTicks++;
            if (shouldUnloadWorld) {
                mc.loadWorld(null);
                shouldUnloadWorld = false;
            }
            if (shouldTryToEnableRenderWorld) {
                flag = false;
                SimpleMenu.gui.renderWorld = true;
                shouldTryToEnableRenderWorld = false;
            }
        }

        if (mc.currentScreen instanceof GuiYesNo && SimpleMenu.gui.renderWorld) {
            shouldUnloadWorld = true;
            SimpleMenu.gui.renderWorld = false;
        }

        if (mc.currentScreen instanceof GuiCreateWorld) {
            shouldTryToEnableRenderWorld = true;
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {

    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT, TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return "MainMenuOverride";
    }

}
