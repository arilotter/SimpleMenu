package com.aerobit.simplemenu.client;

import cpw.mods.fml.client.GuiModList;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeVersion;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;


public class GuiNewMainMenu extends GuiScreen {

    ArrayList<GButton> buttons;
    int startX, startY;
    ResourceLocation backgroundImageLocation = new ResourceLocation("simplemenu:textures/gui/MainMenuBackground.png");
    ResourceLocation brandingResourceLocation = new ResourceLocation("simplemenu:textures/gui/MainMenuBranding.png");
    int brandingWidth = 105;
    int brandingHeight = 19;

    int backgroundImageWidth = 1920;
    int backgroundImageHeight = 1080;

    int buttonSpacing = 0;
    int buttonHeight = 15;
    boolean renderWorld = true;
    public int closeTicks;
    private String mcVersion = "1.6.2";
    private float originalRotation = 0;

    public GuiNewMainMenu() {
        buttons = new ArrayList<GButton>();
    }

    public void initGui() {
        startX = width - 125;
        startY = height - 130;
        buttons.clear();

        if (renderWorld)
            addButton("Quickload");

        addButton(I18n.func_135053_a("menu.singleplayer"));
        if (mc.func_110432_I() != null) // Get session
            addButton(I18n.func_135053_a("menu.multiplayer"));
        addButton("Mods");
        addButton(I18n.func_135053_a("menu.options"));
        addButton("Language");
        addButton(I18n.func_135053_a("menu.quit"));
    }

    void loadMostRecentWorld() {
        //World loading!
        try {
            WorldLoader wl = new WorldLoader(0);
            if (!wl.canLoadWorld()) {
                renderWorld = false;
            } else {
                wl.loadWorld();
            }
        } catch (Exception e) { //This occurs when you have 0 worlds/can't load a world
            renderWorld = false;
        }
    }

    void addButton(String text) {
        int id = buttons.size();
        buttons.add(new GButton(this, startX, startY + (buttonSpacing + buttonHeight) * id, 105, buttonHeight, text, id));
    }


    @Override
    protected void keyTyped(char whatTheFuckDoesThisDo, int someRandomFuckingNumberThatDoesntMeanShit) {
        return;
        //stops ESC-ing into quickload (lol)
    }

    public void buttonAction(int buttonId) {
        if (!renderWorld)
            buttonId += 1; //This is so that quickload is removed if there's no world to quickload
        switch (buttonId) {
            case 0:
                mc.displayGuiScreen(null);
                mc.setIngameFocus();
                GuiOverride.world = Integer.MIN_VALUE;
                break;
            case 1:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 3:
                mc.displayGuiScreen(new GuiModList(this));
                break;
            case 4:
                mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 5:
                mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.func_135016_M()));
                break;
            case 6:
                mc.shutdown();
                break;
        }
    }


    @Override
    public void updateScreen() {
        if (renderWorld && mc.thePlayer != null && !mc.thePlayer.isDead) {
            GameOverlayRenderHandler.guiHidden = true;
            mc.gameSettings.hideGUI = true;
            mc.gameSettings.thirdPersonView = SimpleMenu.firstPersonEnabled ? 0 : 1; // 1 is thirdperson, 0 is firstperson
            if (SimpleMenu.maintainRotationOnQuickload && originalRotation == 0)
                originalRotation = mc.thePlayer.rotationYaw;
            mc.thePlayer.rotationYaw += SimpleMenu.rotateRate;
            mc.thePlayer.rotationPitch = 20.0F;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int whatDoWifVaar) {
        for (GButton btn : buttons) {
            btn.onMouseClick(mouseX, mouseY);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        if (!renderWorld) {
            drawDefaultBackground();
            this.mc.renderEngine.func_110577_a(backgroundImageLocation);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int[] d = getPositions();
            drawTexRect(d[0], d[1], d[2], d[3]);
        }

        this.mc.renderEngine.func_110577_a(brandingResourceLocation);
        drawTexRect(startX, startY - brandingHeight, brandingWidth, brandingHeight);

        for (GButton btn : buttons) {
            btn.drawButton(mouseX, mouseY);
        }

        Gui.drawRect(0, height - 12, fontRenderer.getStringWidth("Minecraft " + mcVersion + "/Forge " + ForgeVersion.getVersion()) + 5, height, new Color(0, 0, 0, 150).getRGB());
        fontRenderer.drawString("Minecraft " + mcVersion + "/Forge " + ForgeVersion.getVersion(), 2, height - 9, new Color(255, 255, 255, 255).getRGB());

    }

    @Override
    public boolean doesGuiPauseGame() {
        return closeTicks >= 30;
    }

    @Override
    public void onGuiClosed() {
        GameOverlayRenderHandler.shouldUnhideGui = true;
        mc.gameSettings.thirdPersonView = 0;
        mc.gameSettings.hideGUI = false;
        closeTicks = 0;
        if (SimpleMenu.maintainRotationOnQuickload && renderWorld) {
            mc.thePlayer.rotationYaw = originalRotation;
            originalRotation = 0F;
        }
    }

    int[] getPositions() {
        int[] dim = new int[4];
        float ratio = (float) backgroundImageWidth / (float) backgroundImageHeight;
        if ((float) width / (float) height < ratio) {

            dim[2] = (int) (height * ratio);
            dim[3] = height;
        } else {
            dim[2] = width;
            dim[3] = (int) (width / ratio);
        }
        dim[0] = width / 2 - dim[2] / 2;
        dim[1] = height / 2 - dim[3] / 2;
        return dim;
    }

    public void drawTexRect(int x, int y, int width, int height) {
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(x, y + height, 0, 0, 1);
        tess.addVertexWithUV(x + width, y + height, 0, 1, 1);
        tess.addVertexWithUV(x + width, y, 0, 1, 0);
        tess.addVertexWithUV(x, y, 0, 0.0, 0);
        tess.draw();
    }

}
