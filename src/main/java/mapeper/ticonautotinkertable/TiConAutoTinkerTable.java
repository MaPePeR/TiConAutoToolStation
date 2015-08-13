package mapeper.ticonautotinkertable;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.ItemBlock;

@Mod(modid = TiConAutoTinkerTable.MODID, version = TiConAutoTinkerTable.VERSION)
public class TiConAutoTinkerTable
{

    public static final String MODID = "TiConAutoTinkerTable";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static TiConAutoTinkerTable instance;

    AutoTinkerTableBlock autoTinkerTableBlockBlock;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (!Loader.isModLoaded("TConstruct")) throw new RuntimeException("Need Tinkers Construct installed");
        autoTinkerTableBlockBlock = new AutoTinkerTableBlock();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        GameRegistry.registerBlock(autoTinkerTableBlockBlock, ItemBlock.class, "autotinkertable");
        GameRegistry.registerTileEntity(AutoTinkerTableTileEntity.class, "autotinkertableTile");
    }
}
