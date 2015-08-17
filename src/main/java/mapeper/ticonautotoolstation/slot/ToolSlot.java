package mapeper.ticonautotoolstation.slot;


import mapeper.ticonautotoolstation.C;
import mapeper.ticonautotoolstation.TinkerUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ToolSlot extends Slot {

	public ToolSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) {
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	public boolean isItemValid(ItemStack stack) {
		ItemStack toolOutSlotContent = inventory.getStackInSlot(C.TOOLOUTSLOT);
		return TinkerUtils.isModifyableTool(stack) && (toolOutSlotContent == null || toolOutSlotContent.stackSize == 0);
	}
}
