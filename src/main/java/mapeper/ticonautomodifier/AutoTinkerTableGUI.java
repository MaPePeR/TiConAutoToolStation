package mapeper.ticonautomodifier;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class AutoTinkerTableGUI extends GuiContainer
{
	private static final ResourceLocation background = new ResourceLocation("tinker", "textures/gui/toolstation.png");
	private static final ResourceLocation icons = new ResourceLocation("tinker", "textures/gui/icons.png");
	private static final ResourceLocation description = new ResourceLocation("tinker", "textures/gui/description.png");

	public AutoTinkerTableGUI(InventoryPlayer inventoryPlayer,
					AutoTinkerTableTileEntity tileEntity) {
		super(new AutoTinkerTableContainer(inventoryPlayer, tileEntity));
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
		this.mc.fontRenderer.drawString("Auto Tinker Table", 116, 6, 0x000000);
		//draws "Inventory" or your regional equivalent
		this.mc.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 118, ySize - 96 + 2, 0x000000);

		TinkerUtils.drawToolStats(this.inventorySlots.getSlot(1).getStack(), 294, 0);
	}

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

		if (!this.inventorySlots.getSlot(AutoTinkerTableContainer.TOOLSLOT).getHasStack()) {
			this.drawTexturedModalRect(cornerX + (225 - 111), this.guiTop + (38 - 1), 18 * 0, 18 * 13, 18, 18);
		}

		// Draw the slots
		int slotX = 56, slotY = 37;
		this.drawTexturedModalRect(cornerX + slotX, this.guiTop + slotY, 144, 216, 18, 18);
		if (!this.inventorySlots.getSlot(AutoTinkerTableContainer.MODSLOT).getHasStack()) {
			this.drawTexturedModalRect(cornerX + slotX, this.guiTop + slotY, 18 * 2, 18 * 13, 18, 18);
		}

		// Draw description
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(description);
		this.drawTexturedModalRect(cornerX + 176, this.guiTop, 0, 0, 126, this.ySize + 30);
	}
}
