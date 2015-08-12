package mapeper.ticonautomodifier;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.ItemBlock;

@Mod(modid = TiConAutoModifier.MODID, version = TiConAutoModifier.VERSION)
public class TiConAutoModifier
{

    public static final String MODID = "TiConAutoModifier";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static TiConAutoModifier instance;

    AutoTinkerTable autoTinkerTableBlock;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (!Loader.isModLoaded("TConstruct")) throw new RuntimeException("Need Tinkers Construct installed");
        autoTinkerTableBlock = new AutoTinkerTable();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        GameRegistry.registerBlock(autoTinkerTableBlock, ItemBlock.class, "autotinkertable");
        GameRegistry.registerTileEntity(AutoTinkerTableTileEntity.class, "autotinkertableTile");
    }
}
