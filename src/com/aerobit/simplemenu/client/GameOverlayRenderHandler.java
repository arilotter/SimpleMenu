package com.aerobit.simplemenu.client;// Time Created: 8/6/13 12:12 AM

import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class GameOverlayRenderHandler {
    static boolean guiHidden;
    static boolean shouldUnhideGui;

    @ForgeSubscribe
    public void renderOverlay(RenderGameOverlayEvent ev) {
        if (guiHidden) {
            GuiIngameForge.renderHelmet = false;
            GuiIngameForge.renderPortal = false;
            GuiIngameForge.renderHotbar = false;
            GuiIngameForge.renderCrosshairs = false;
            GuiIngameForge.renderBossHealth = false;
            GuiIngameForge.renderHealth = false;
            GuiIngameForge.renderArmor = false;
            GuiIngameForge.renderFood = false;
            GuiIngameForge.renderHealthMount = false;
            GuiIngameForge.renderAir = false;
            GuiIngameForge.renderExperiance = false;
            GuiIngameForge.renderJumpBar = false;
            GuiIngameForge.renderObjective = false;
        }
        if (shouldUnhideGui) {
            GuiIngameForge.renderHelmet = true;
            GuiIngameForge.renderPortal = true;
            GuiIngameForge.renderHotbar = true;
            GuiIngameForge.renderCrosshairs = true;
            GuiIngameForge.renderBossHealth = true;
            GuiIngameForge.renderHealth = true;
            GuiIngameForge.renderArmor = true;
            GuiIngameForge.renderFood = true;
            GuiIngameForge.renderHealthMount = true;
            GuiIngameForge.renderAir = true;
            GuiIngameForge.renderExperiance = true;
            GuiIngameForge.renderJumpBar = true;
            GuiIngameForge.renderObjective = true;
            shouldUnhideGui = false;
            guiHidden = false;
        }
    }
}
