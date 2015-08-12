package mapeper.ticonautomodifier;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class AutoTinkerTableGUI extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation("minecraft", "textures/gui/demo_background.png");
	public AutoTinkerTableGUI (InventoryPlayer inventoryPlayer,
					AutoTinkerTableTileEntity tileEntity) {
		super(new AutoTinkerTableContainer(inventoryPlayer, tileEntity));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		//draw text and stuff here
		//the parameters for drawString are: string, x, y, color
		this.mc.fontRenderer.drawString("Tiny", 8, 6, 4210752);
		//draws "Inventory" or your regional equivalent
		this.mc.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
												   int par3) {
		//draw your Gui here, only thing you need to chage is the path
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
