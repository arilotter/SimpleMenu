package com.aerobit.simplemenu.client;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.SaveFormatComparator;

import java.util.Collections;
import java.util.List;

public class WorldLoader {

    private final List saveList;
    private final Minecraft mc;
    private final boolean canLoadWorld;
    private final String saveName;
    private final String saveFolder;

    public WorldLoader(int worldNum) throws AnvilConverterException {
        // loads the saves
        mc = Minecraft.getMinecraft();
        this.saveList = this.mc.getSaveLoader().getSaveList();
        Collections.sort(this.saveList);
        saveFolder = getSaveFileName(worldNum);
        saveName = getSaveName(worldNum);
        canLoadWorld = (saveFolder != null && saveName != null);
    }

    protected String getSaveFileName(int i) {
        if (saveList.size() < i + 1) {
            return null;
        } else {
            return ((SaveFormatComparator) saveList.get(i)).getFileName();
        }
    }

    /**
     * returns the name of the saved game
     */
    protected String getSaveName(int par1) {
        String s = ((SaveFormatComparator) this.saveList.get(par1)).getDisplayName();

        if (s == null || MathHelper.stringNullOrLengthZero(s)) {
            s = I18n.func_135053_a("selectWorld.world") + " " + (par1 + 1);
        }

        return s;
    }

    public void loadWorld() {
        mc.launchIntegratedServer(saveFolder, saveName, null); // this is how GuiSelectWorld does it.
    }

    public boolean canLoadWorld() {
        return canLoadWorld;
    }
}
