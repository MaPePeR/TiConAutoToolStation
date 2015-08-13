package mapeper.ticonautotoolstation;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

//Thanks to http://www.minecraftforge.net/wiki/Containers_and_GUIs
public class AutoToolStationBlock extends Block implements ITileEntityProvider
{
	protected AutoToolStationBlock()
	{
		super(Material.rock);
		this.setBlockName("autotinkertable");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new AutoToolStationTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(TiConAutoTinkerTable.instance, 0, world, x, y, z);
		}

		return true;
	}
}
