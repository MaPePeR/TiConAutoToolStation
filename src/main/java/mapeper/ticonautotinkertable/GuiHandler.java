package mapeper.ticonautotinkertable;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
									  int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof AutoToolStationTileEntity){
			return new AutoToolStationContainer(player.inventory, (AutoToolStationTileEntity) tileEntity);
		}
		return null;
	}

@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
									  int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof AutoToolStationTileEntity){
			return new AutoToolStationGUI(player.inventory, (AutoToolStationTileEntity) tileEntity);
		}
		return null;

	}
}
