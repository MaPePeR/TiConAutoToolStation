package mapeper.ticonautotoolstation.modes;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;

public interface IATSMode
{
	public static final ImmutableList<IATSMode> modes = ImmutableList.<IATSMode>of(new ApplyUntilDoneMode(), new FillOneMode());
	public String getName();

	/**
	 * @return {@code null} when the {@code beforeModify} stack is done or the new modified item
	 */
	public ItemStack shouldMoveToOutput(ItemStack beforeModify, ItemStack afterModify);
}
