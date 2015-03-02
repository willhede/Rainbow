package com.whede1601.Rainbow;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class giveitems {

	private static Rainbow plugin;
	public static ArrayList<ItemStack> armoritems = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> weaponitems = new ArrayList<ItemStack>();
	public static void run()
	{
		if (armoritems.isEmpty())
		{
		armoritems.add(new ItemStack(Material.DIAMOND_BOOTS, 1));
		armoritems.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
		armoritems.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
		armoritems.add(new ItemStack(Material.DIAMOND_HELMET, 1));
		}
		if (weaponitems.isEmpty())
		{
		weaponitems.add(new ItemStack(Material.DIAMOND_SWORD, 1));
		weaponitems.add(new ItemStack(Material.BOW, 1));
		}
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
			ItemMeta ism = is.getItemMeta();
			ism.setDisplayName(ChatColor.GOLD + "Weed's armor");
			is.setItemMeta(ism);
			System.out.println(is);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 8);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 8);
			is.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 8);
			is.addUnsafeEnchantment(Enchantment.THORNS, 8);
			is.addUnsafeEnchantment(Enchantment.DURABILITY, 127);
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
			ItemMeta ism = is.getItemMeta();
			if (is.getData().getItemType() == Material.DIAMOND_SWORD)
			{
				ism.setDisplayName(ChatColor.GOLD + "Holy Sword Excalibur");
				is.setItemMeta(ism);
				is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 8);
				is.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 8);
				is.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 8);
				is.addUnsafeEnchantment(Enchantment.DURABILITY, 127);
				is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 8);
				is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 8);
				is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 8);
			}
			if (is.getData().getItemType() == Material.BOW)
			{
				ism.setDisplayName(ChatColor.GOLD + "Weed's bow");
				is.setItemMeta(ism);
				is.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 8);
				is.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 8);
				is.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 8);
				is.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 8);
				is.addUnsafeEnchantment(Enchantment.DURABILITY, 127);
			}
			p.getInventory().addItem(is);
		}
		armoritems.clear();
		weaponitems.clear();
	}
	public static void god(Player p) {
		run();
		for(ItemStack is : armoritems)
		{
			System.out.println(is);
			is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			is.addEnchantment(Enchantment.PROTECTION_FIRE, 4);
			is.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
			is.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
			if (is.getData().getItemType() == Material.DIAMOND_HELMET)
			{
				is.addEnchantment(Enchantment.OXYGEN, 3);
				is.addEnchantment(Enchantment.WATER_WORKER, 1);
			}
			if (is.getData().getItemType() == Material.DIAMOND_BOOTS)
			{
				is.addEnchantment(Enchantment.PROTECTION_FALL, 4);
				is.addEnchantment(Enchantment.DEPTH_STRIDER, 3);

			}
			p.getInventory().addItem(is);
		}
		for(ItemStack is : weaponitems)
		{
			if (is.getData().getItemType() == Material.DIAMOND_SWORD)
			{
				is.addEnchantment(Enchantment.DAMAGE_ALL, 5);
				is.addEnchantment(Enchantment.FIRE_ASPECT, 2);

			}
			if (is.getData().getItemType() == Material.BOW)
			{
				is.addEnchantment(Enchantment.ARROW_INFINITE, 1);
				is.addEnchantment(Enchantment.ARROW_FIRE, 1);
				is.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
				is.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
			}
			p.getInventory().addItem(is);
		}
		armoritems.clear();
		weaponitems.clear();
	}
	public static void king(Player p) {
		run();
		for(ItemStack is : armoritems)
		{
			System.out.println(is);
			is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
			is.addEnchantment(Enchantment.PROTECTION_FIRE, 3);
			is.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 3);
			is.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3);
			if (is.getData().getItemType() == Material.DIAMOND_HELMET)
			{
				is.addEnchantment(Enchantment.OXYGEN, 2);
				is.addEnchantment(Enchantment.WATER_WORKER, 1);
			}
			if (is.getData().getItemType() == Material.DIAMOND_BOOTS)
			{
				is.addEnchantment(Enchantment.PROTECTION_FALL, 3);
				is.addEnchantment(Enchantment.DEPTH_STRIDER, 2);

			}
			p.getInventory().addItem(is);
		}
		for(ItemStack is : weaponitems)
		{
			if (is.getData().getItemType() == Material.DIAMOND_SWORD)
			{
				is.addEnchantment(Enchantment.DAMAGE_ALL, 4);
				is.addEnchantment(Enchantment.FIRE_ASPECT, 2);

			}
			if (is.getData().getItemType() == Material.BOW)
			{
				is.addEnchantment(Enchantment.ARROW_INFINITE, 1);
				is.addEnchantment(Enchantment.ARROW_FIRE, 1);
				is.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
				is.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			}
			p.getInventory().addItem(is);
		}
		armoritems.clear();
		weaponitems.clear();
	}
	public static void queen(Player p) {
		run();
		for(ItemStack is : armoritems)
		{
			System.out.println(is);
			is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			is.addEnchantment(Enchantment.PROTECTION_FIRE, 2);
			is.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 2);
			is.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
			if (is.getData().getItemType() == Material.DIAMOND_HELMET)
			{
				is.addEnchantment(Enchantment.OXYGEN, 2);
				is.addEnchantment(Enchantment.WATER_WORKER, 1);
			}
			if (is.getData().getItemType() == Material.DIAMOND_BOOTS)
			{
				is.addEnchantment(Enchantment.PROTECTION_FALL, 2);
				is.addEnchantment(Enchantment.DEPTH_STRIDER, 1);

			}
			p.getInventory().addItem(is);
		}
		for(ItemStack is : weaponitems)
		{
			if (is.getData().getItemType() == Material.DIAMOND_SWORD)
			{
				is.addEnchantment(Enchantment.DAMAGE_ALL, 3);
				is.addEnchantment(Enchantment.FIRE_ASPECT, 1);

			}
			if (is.getData().getItemType() == Material.BOW)
			{
			}
			p.getInventory().addItem(is);
		}
		armoritems.clear();
		weaponitems.clear();
	}
	public static void prince(Player p) {
		run();
		for(ItemStack is : armoritems)
		{
			System.out.println(is);
			is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			is.addEnchantment(Enchantment.PROTECTION_FIRE, 1);
			is.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
			is.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
			if (is.getData().getItemType() == Material.DIAMOND_HELMET)
			{
				is.addEnchantment(Enchantment.OXYGEN, 1);
				is.addEnchantment(Enchantment.WATER_WORKER, 1);
			}
			if (is.getData().getItemType() == Material.DIAMOND_BOOTS)
			{
				is.addEnchantment(Enchantment.PROTECTION_FALL, 1);
			}
			p.getInventory().addItem(is);
		}
		for(ItemStack is : weaponitems)
		{
			if (is.getData().getItemType() == Material.DIAMOND_SWORD)
			{
				is.addEnchantment(Enchantment.DAMAGE_ALL, 2);
				is.addEnchantment(Enchantment.FIRE_ASPECT, 1);

			}
			if (is.getData().getItemType() == Material.BOW)
			{
			}
			p.getInventory().addItem(is);
		}
		armoritems.clear();
		weaponitems.clear();
	}
}
