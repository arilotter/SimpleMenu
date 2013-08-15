package com.aerobit.simplemenu.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class GButton {

    private GuiNewMainMenu gui;
    private int x, y, width, height, hoverOffset;
    public int id;
    private String text;
    private FontRenderer fr;

    public GButton(GuiNewMainMenu gui, int x, int y, int width, int height, String text, int id) {
        this.gui = gui;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = id;
        hoverOffset = 0;

        fr = Minecraft.getMinecraft().fontRenderer;
    }

    public void drawButton(int mouseX, int mouseY) {
        boolean hovering = isHovering(mouseX, mouseY);
        if (hovering) {
            if (hoverOffset < 10) {
                hoverOffset += 1;
            }
        } else {
            if (hoverOffset > 0) {
                hoverOffset -= 1;
            }
        }

        Color colorBack = (new Color(0, 0, 0, 150));
        Color colorText = (new Color(255, 255, 255, 255));

        Gui.drawRect(x + hoverOffset, y, x + width + hoverOffset, y + height, colorBack.getRGB());
        fr.drawString(text, x + hoverOffset + 4, y + 4, colorText.getRGB(), false);
    }

    public void onMouseClick(int mouseX, int mouseY) {
        if (isHovering(mouseX, mouseY)) gui.buttonAction(this.id);
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;
    }

}
