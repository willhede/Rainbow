package com.whede1601.Rainbow;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class giveitems {

	private static Rainbow plugin;
	public static ArrayList<ItemStack> armoritems = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> weaponitems = new ArrayList<ItemStack>();
	public static void run()
	{
		armoritems.add(new ItemStack(Material.DIAMOND_BOOTS, 1));
		armoritems.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
		armoritems.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
		armoritems.add(new ItemStack(Material.DIAMOND_HELMET, 1));
		weaponitems.add(new ItemStack(Material.DIAMOND_SWORD, 1));
		weaponitems.add(new ItemStack(Material.BOW, 1));
	}
	public giveitems(Rainbow plugin)
	{
		ArrayList<ItemStack> armoritems = new ArrayList<ItemStack>();
		this.plugin = plugin;
	}

	public static void weed(Player p) {
		run();
		for(ItemStack is : armoritems)
		{
			System.out.println(is);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 8);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 8);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 8);
			is.addUnsafeEnchantment(Enchantment.THORNS, 8);
			is.getItemMeta().setDisplayName(ChatColor.GOLD + "Weed's armor");
			if (is.getData().getItemType() == Material.DIAMOND_HELMET)
			{
				is.addUnsafeEnchantment(Enchantment.OXYGEN, 8);
				is.addUnsafeEnchantment(Enchantment.WATER_WORKER, 8);
			}
			if (is.getData().getItemType() == Material.DIAMOND_BOOTS)
			{
				is.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 8);
				is.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 8);
			}
			p.getInventory().addItem(is);
		}
		for(ItemStack is : weaponitems)
		{
			if (is.getData().getItemType() == Material.DIAMOND_SWORD)
			{
				is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 8);
				is.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 8);
				is.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 8);
				is.addUnsafeEnchantment(Enchantment.DURABILITY, 8);
				is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 8);
				is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 8);
				is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 8);
				is.getItemMeta().setDisplayName(ChatColor.GOLD + "Weed's sword");
			}
			if (is.getData().getItemType() == Material.BOW)
			{
				is.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 8);
				is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 8);
				is.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 8);
				is.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 8);
				is.addUnsafeEnchantment(Enchantment.DURABILITY, 8);
				is.getItemMeta().setDisplayName(ChatColor.GOLD + "Weed's bow");
			}
			p.getInventory().addItem(is);
		}
	}
}
