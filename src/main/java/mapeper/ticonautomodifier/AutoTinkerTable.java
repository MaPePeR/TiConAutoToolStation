package mapeper.ticonautomodifier;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AutoTinkerTable extends Block implements ITileEntityProvider
{
	protected AutoTinkerTable()
	{
		super(Material.rock);
		this.setBlockName("autotinkertable");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new AutoTinkerTableTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(TiConAutoModifier.instance, 0, world, x, y, z);
		}

		return true;
	}
}
