package com.van.pcurrency.quests;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class QuestVillagerScreen extends AbstractContainerScreen<QuestVillagerMenu> {

    public QuestVillagerScreen(QuestVillagerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);

        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            Quest quest = PlayerQuestData.getCurrentQuest(player);
            if (quest != null) {
                String info = quest.title + ": Bring " + quest.amount + " " + quest.type;
                graphics.drawString(this.font, info, this.leftPos + 10, this.topPos + 10, 0xFFFFFF);
            }
        }
    }
}
