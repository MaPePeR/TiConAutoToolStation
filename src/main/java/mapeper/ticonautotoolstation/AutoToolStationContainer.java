package mapeper.ticonautotoolstation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

//Thanks to http://www.minecraftforge.net/wiki/Containers_and_GUIs
public class AutoToolStationContainer extends Container
{
	public static final int MODSLOT = 0;
	public static final int TOOLSLOT = 1;
	AutoToolStationTileEntity tileEntity;
	public AutoToolStationContainer(InventoryPlayer inventoryPlayer, AutoToolStationTileEntity te){
		tileEntity = te;

		addSlotToContainer(new Slot(tileEntity, MODSLOT, 56 + 111, 37 + 1));
		addSlotToContainer(new ToolSlot(tileEntity, TOOLSLOT, 225, 38));

		//commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);
}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}


	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						118 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 118 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		//null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			//merges the item into player inventory since its in the tileEntity
			if (slot < tileEntity.getSizeInventory()) {
				if (!this.mergeItemStack(stackInSlot, tileEntity.getSizeInventory(), 36+tileEntity.getSizeInventory(), true)) {
					return null;
				}
			}
			else if (TinkerUtils.isModifyableTool(stack)) {
				if (!this.mergeItemStack(stackInSlot, TOOLSLOT, TOOLSLOT + 1, false)) {
					return null;
				}
			}
			//places it into the tileEntity is possible since its in the player inventory
			else if (!this.mergeItemStack(stackInSlot, MODSLOT, MODSLOT + 1, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}
}
