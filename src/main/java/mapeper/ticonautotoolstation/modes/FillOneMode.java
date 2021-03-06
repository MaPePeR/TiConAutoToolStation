package mapeper.ticonautotoolstation.modes;

import mapeper.ticonautotoolstation.TinkerUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class FillOneMode implements IATSMode {
	@Override
	public String getName() {
		return StatCollector.translateToLocal("ats.autotoolstation.mode.fillOne");
	}

	@Override
	public ItemStack shouldMoveToOutput(ItemStack beforeModify, ItemStack afterModify) {
		if (afterModify == null) return null;
		if (getModifierCount(beforeModify) > getModifierCount(afterModify)) {
			return null;
		}
		return afterModify;
	}

	private int getModifierCount(ItemStack itemStack) {
		return itemStack.getTagCompound().getCompoundTag(TinkerUtils.getBaseTagName(itemStack)).getInteger("Modifiers");
	}
}
