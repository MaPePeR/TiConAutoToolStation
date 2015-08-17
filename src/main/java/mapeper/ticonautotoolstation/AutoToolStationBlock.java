package mapeper.ticonautotoolstation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mapeper.ticonautotoolstation.modes.IATSMode;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

//Thanks to http://www.minecraftforge.net/wiki/Containers_and_GUIs
public class AutoToolStationBlock extends Block implements ITileEntityProvider {
	protected AutoToolStationBlock() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setBlockName("ats_autotoolstation");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new AutoToolStationTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof AutoToolStationTileEntity) {
				AutoToolStationTileEntity atsTile = (AutoToolStationTileEntity) tile;
				atsTile.mode = (atsTile.mode + 1) % IATSMode.modes.size();
				if (world.isRemote) {
					player.addChatComponentMessage(new ChatComponentTranslation("ats.autotoolstation.modechange", atsTile.getMode().getName()));
				}
			}
		} else if (!world.isRemote) {
			player.openGui(TiConAutoToolStation.instance, 0, world, x, y, z);
		}

		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	private void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {

				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world,
						x + rx, y + ry, z + rz,
						item.copy());

				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side == 1) {
			//Top
			return icon[1];
		}
		return icon[0];
	}

	IIcon[] icon;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icon = new IIcon[2];
		icon[0] = register.registerIcon("autotoolstation:autotoolstation/sides");
		icon[1] = register.registerIcon("tinker:toolforge_top");
		//https://github.com/SlimeKnights/TinkersConstruct/blob/master/resources/assets/tinker/textures/blocks/toolforge_top.png
	}
}
