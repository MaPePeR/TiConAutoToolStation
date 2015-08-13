package mapeper.ticonautotinkertable;

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

@Mod(modid = TiConAutoTinkerTable.MODID, version = TiConAutoTinkerTable.VERSION)
public class TiConAutoTinkerTable
{

    public static final String MODID = "TiConAutoTinkerTable";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static TiConAutoTinkerTable instance;

    AutoTinkerTableBlock autoTinkerTableBlockBlock;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (!Loader.isModLoaded("TConstruct")) throw new RuntimeException("Need Tinkers Construct installed");
        autoTinkerTableBlockBlock = new AutoTinkerTableBlock();
        GameRegistry.registerBlock(autoTinkerTableBlockBlock, ItemBlock.class, "autotinkertable");
        GameRegistry.registerTileEntity(AutoTinkerTableTileEntity.class, "autotinkertableTile");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        GameRegistry.addShapedRecipe(new ItemStack(autoTinkerTableBlockBlock),
                "whw", "wtw", "wpw",
                'w', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE),
                'h', new ItemStack(Blocks.hopper),
                't', new ItemStack(TinkerUtils.getToolStation()),
                'p', new ItemStack(Blocks.piston)
        );
    }
}
