package mapeper.ticonautotoolstation;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TinkerUtils
{
	//
	static Method modifyItemMethod;
	static Object modifyBuilderInstance;
	static Class iModifyable;
	static Method getBaseTagNameMethod;
	//drawToolStats (ItemStack stack, int x, int y)
	static Method drawToolStatsMethod;
	static Field toolStationField;
	static {
		try
		{
			Class clazz = Class.forName("tconstruct.library.crafting.ModifyBuilder");
			Field instanceField = clazz.getField("instance");
			modifyBuilderInstance = instanceField.get(null);
			modifyItemMethod = clazz.getMethod("modifyItem", ItemStack.class, ItemStack[].class);

			iModifyable = Class.forName("tconstruct.library.modifier.IModifyable");
			getBaseTagNameMethod = iModifyable.getMethod("getBaseTagName");

			clazz = Class.forName("tconstruct.tools.gui.ToolStationGuiHelper");
			drawToolStatsMethod = clazz.getMethod("drawToolStats", ItemStack.class, int.class, int.class);

			clazz = Class.forName("tconstruct.tools.TinkerTools");
			toolStationField = clazz.getField("toolStationWood");
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
	}

	public static ItemStack modifyItem(ItemStack item, ItemStack[] modifiers) {
		if (modifyItemMethod != null && item != null && modifiers != null) {
			try
			{
				Object o = modifyItemMethod.invoke(modifyBuilderInstance, item, modifiers);
				if (o == null || o instanceof ItemStack)
					return (ItemStack)o;
				else {
					throw new RuntimeException("ModifyBuilder.modifyItem did not return ItemStack");
				}
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			} catch (RuntimeException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean isModifyableTool(ItemStack stack) {
		return stack != null && stack.getItem() != null && iModifyable.isInstance(stack.getItem());
	}

	public static void drawToolStats(ItemStack item, int x, int y) {
		if (drawToolStatsMethod != null && isModifyableTool(item)) {
			try
			{
				drawToolStatsMethod.invoke(null, item, x, y);
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static Block getToolStation() {
		if (toolStationField != null) {
			Object o = null;
			try
			{
				o = toolStationField.get(null);
			} catch (IllegalAccessException e)
			{
				new RuntimeException("Could not get toolStationWood from tconstruct.tools.TinkerTools", e);
			}
			if (o instanceof Block) {
				return (Block) o;
			}
		}
		throw new RuntimeException("Could not get toolStationWood from tconstruct.tools.TinkerTools");
	}

	public static String getBaseTagName(ItemStack itemStack) {
		if (!isModifyableTool(itemStack)) throw new IllegalArgumentException("ItemStack does not contain IModifiyable");
		try
		{
			Object o  = getBaseTagNameMethod.invoke(itemStack.getItem());
			if (o instanceof String) {
				return (String) o;
			} else {
				throw new IllegalArgumentException("getBaseTagName did return " + o.getClass().getName() + " instead of String");
			}
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
