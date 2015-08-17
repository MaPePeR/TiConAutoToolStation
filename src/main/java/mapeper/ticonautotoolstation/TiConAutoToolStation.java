package mapeper.ticonautotoolstation;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = TiConAutoToolStation.MODID, version = TiConAutoToolStation.VERSION)
public class TiConAutoToolStation
{

    public static final String MODID = "TiConAutoToolStation";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance(MODID)
    public static TiConAutoToolStation instance;

    AutoToolStationBlock autoToolStationBlockBlock;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (!Loader.isModLoaded("TConstruct")) throw new RuntimeException("Need Tinkers Construct installed");
        autoToolStationBlockBlock = new AutoToolStationBlock();
        GameRegistry.registerBlock(autoToolStationBlockBlock, ItemBlock.class, "ats_autotoolstation");
        GameRegistry.registerTileEntity(AutoToolStationTileEntity.class, "ats_autotoolstationTile");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        GameRegistry.addShapedRecipe(new ItemStack(autoToolStationBlockBlock),
                "whw", "wtw", "wpw",
                'w', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE),
                'h', new ItemStack(Blocks.hopper),
                't', new ItemStack(TinkerUtils.getToolStation()),
                'p', new ItemStack(Blocks.piston)
        );
    }
}
