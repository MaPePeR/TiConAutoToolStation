package mapeper.ticonautotoolstation;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

//Thanks to http://www.minecraftforge.net/wiki/Containers_and_GUIs
public class AutoToolStationGUI extends GuiContainer
{
	private static final ResourceLocation background = new ResourceLocation("autotoolstation", "textures/gui/toolstation.png");
	private static final ResourceLocation icons = new ResourceLocation("tinker", "textures/gui/icons.png");
	private static final ResourceLocation description = new ResourceLocation("tinker", "textures/gui/description.png");

	public AutoToolStationGUI(InventoryPlayer inventoryPlayer,
							  AutoToolStationTileEntity tileEntity) {
		super(new AutoToolStationContainer(inventoryPlayer, tileEntity));
	}

	@Override
	public void initGui() {
		super.initGui();
		this.xSize = 176 + 110;
		this.guiLeft = (this.width - 176) / 2 - 110;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		//draw text and stuff here
		//the parameters for drawString are: string, x, y, color
		this.mc.fontRenderer.drawString(StatCollector.translateToLocal("tile.ats_autotoolstation.name"), 116, 6, 4210752);
		//draws "Inventory" or your regional equivalent
		this.mc.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 118, ySize - 96 + 2, 4210752);

		if (this.inventorySlots.getSlot(C.TOOLSLOT).getHasStack())
		{
			TinkerUtils.drawToolStats(this.inventorySlots.getSlot(C.TOOLSLOT).getStack(), 294, 0);
		} else {
			//From https://github.com/SlimeKnights/TinkersConstruct/blob/a7405a3d10318bb5c486ec75fb62897a8149d1a6/src/main/java/tconstruct/tools/gui/ToolStationGui.java#L187-L191
			this.drawCenteredString(fontRendererObj, "\u00A7n" + StatCollector.translateToLocal("tile.ats_autotoolstation.name"), 349, 8, 0xffffff);
			fontRendererObj.drawSplitString(
					StatCollector.translateToLocal("ats.autotoolstation.description1") + "\n\n" +
					StatCollector.translateToLocal("ats.autotoolstation.description2") + "\n\n" +
					"\u00a78" + StatCollector.translateToLocal("ats.autotoolstation.thanksticon")+ "\u00a7r", 294, 24, 115, 0xffffff);
		}
	}

	//Code based on https://github.com/SlimeKnights/TinkersConstruct/blob/a7405a3d10318bb5c486ec75fb62897a8149d1a6/src/main/java/tconstruct/tools/gui/ToolStationGui.java#L201-L231
	//Modified to only use 2 Slots and show the icons at different places
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
												   int par3)
	{
		//Slot1: 56 37
		//Slot2: 38 28

		// Draw the background
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);
		final int cornerX = this.guiLeft + 110;
		this.drawTexturedModalRect(cornerX, this.guiTop, 0, 0, 176, this.ySize);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(icons);

		if (!this.inventorySlots.getSlot(C.TOOLSLOT).getHasStack()) {
			this.drawTexturedModalRect(cornerX + (225 - 111), this.guiTop + (38 - 1), 18 * 0, 18 * 13, 18, 18);
		}

		// Draw the slots
		int slotX = 56, slotY = 37;
		this.drawTexturedModalRect(cornerX + slotX, this.guiTop + slotY, 144, 216, 18, 18);
		if (!this.inventorySlots.getSlot(C.MODSLOT).getHasStack()) {
			this.drawTexturedModalRect(cornerX + slotX, this.guiTop + slotY, 18 * 2, 18 * 13, 18, 18);
		}

		// Draw description
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(description);
		this.drawTexturedModalRect(cornerX + 176, this.guiTop, 0, 0, 126, this.ySize + 30);
	}
}
