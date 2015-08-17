package mapeper.ticonautotoolstation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.Level;


//Thanks to http://www.minecraftforge.net/wiki/Containers_and_GUIs
public class AutoToolStationTileEntity extends TileEntity implements ISidedInventory
{
	ItemStack[] inventory;

	public AutoToolStationTileEntity() {
		inventory = new ItemStack[3];
	}

	public static final int[] accessibleSlots = new int[]{C.MODSLOT, C.TOOLSLOT};

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return accessibleSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
	{
		if (TinkerUtils.isModifyableTool(stack)) {
			return slot == C.TOOLSLOT && (inventory[C.TOOLOUTSLOT] == null || inventory[C.TOOLOUTSLOT].stackSize == 0);
		} else {
			return slot == C.MODSLOT;
		}
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		return slot == C.TOOLOUTSLOT;
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName()
	{
		return "tile.ats_autotoolstation.name";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
				player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openInventory()
	{

	}

	@Override
	public void closeInventory()
	{

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (slot == C.TOOLSLOT) return TinkerUtils.isModifyableTool(stack) && (inventory[C.TOOLOUTSLOT] == null || inventory[C.TOOLOUTSLOT].stackSize == 0);
		return true;
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			if (inventory[C.MODSLOT] != null && inventory[C.MODSLOT].stackSize >= 1 && inventory[C.TOOLSLOT] != null) {
				ItemStack modifierCopy = inventory[C.MODSLOT].copy();
				modifierCopy.stackSize = 1;

				ItemStack modifyResult = TinkerUtils.modifyItem(inventory[C.TOOLSLOT], new ItemStack[]{modifierCopy.copy()});
				if (modifyResult == null) {
					//Could not apply more modifiers
					if (inventory[C.TOOLOUTSLOT] == null || inventory[C.TOOLOUTSLOT].stackSize == 0)
					{
						inventory[C.TOOLOUTSLOT] = inventory[C.TOOLSLOT];
						inventory[C.TOOLSLOT] = null;
						this.markDirty();
					} else {
						TiConAutoToolStation.LOGGER.log(Level.ERROR, "Auto Tool Station output slot was not empty, but we want to move a tool there!");
					}
				}
				else
				{
					decrStackSize(C.MODSLOT, 1);
					inventory[C.TOOLSLOT] = modifyResult;
					this.markDirty();
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}
}
