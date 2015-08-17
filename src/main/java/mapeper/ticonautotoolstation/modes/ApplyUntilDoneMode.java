package mapeper.ticonautotoolstation.modes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ApplyUntilDoneMode implements IATSMode {
	@Override
	public String getName() {
		return StatCollector.translateToLocal("ats.autotoolstation.mode.applyUntilDone");
	}

	@Override
	public ItemStack shouldMoveToOutput(ItemStack beforeModify, ItemStack afterModify) {
		return afterModify;
	}
}
