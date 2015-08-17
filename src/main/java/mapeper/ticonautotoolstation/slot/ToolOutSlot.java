package mapeper.ticonautotoolstation.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ToolOutSlot extends Slot
{
	public ToolOutSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	public boolean isItemValid(ItemStack stack) {
		return false;
	}

}
