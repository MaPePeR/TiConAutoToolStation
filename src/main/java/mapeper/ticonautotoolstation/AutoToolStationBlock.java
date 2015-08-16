package mapeper.ticonautotoolstation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

//Thanks to http://www.minecraftforge.net/wiki/Containers_and_GUIs
public class AutoToolStationBlock extends Block implements ITileEntityProvider
{
	protected AutoToolStationBlock()
	{
		super(Material.rock);
		this.setHardness(1.0F);
		this.setBlockName("ats_autotoolstation");
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
			player.openGui(TiConAutoToolStation.instance, 0, world, x, y, z);
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 1) {
			//Top
			return icon[1];
		}
		return icon[0];
	}

	IIcon[] icon;
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		icon = new IIcon[2];
		icon[0] = register.registerIcon("autotoolstation:autotoolstation/sides");
		icon[1] = register.registerIcon("tinker:toolforge_top");
		//https://github.com/SlimeKnights/TinkersConstruct/blob/master/resources/assets/tinker/textures/blocks/toolforge_top.png
	}
}
