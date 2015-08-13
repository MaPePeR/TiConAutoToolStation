package mapeper.ticonautotoolstation;


import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ToolSlot extends Slot
{

	public ToolSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	public boolean isItemValid(ItemStack stack) {
		return TinkerUtils.isModifyableTool(stack);
	}
}
