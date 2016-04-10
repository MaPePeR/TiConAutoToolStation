package mapeper.ticonautotoolstation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab extends CreativeTabs {
	public CreativeTab()
	{
		super(TiConAutoToolStation.MODID);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return TiConAutoToolStation.autoToolStationBlockItem;
	}
}
