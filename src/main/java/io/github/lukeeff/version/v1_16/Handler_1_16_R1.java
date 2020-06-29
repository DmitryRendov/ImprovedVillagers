package io.github.lukeeff.version.v1_16;

import io.github.lukeeff.version.VersionHandler;
import net.minecraft.server.v1_16_R1.Entity;
import net.minecraft.server.v1_16_R1.NBTBase;
import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity;
import org.bukkit.entity.Villager;

public class Handler_1_16_R1 implements VersionHandler {

    private int maxDiscount;

    /**
     * Sets the max discount of a villager trade
     *
     * @param maxDiscount the maximum amount of emeralds that can be discounted
     * @param villager    the villager trading with the player
     */
    @Override
    public void setMaxDiscount(int maxDiscount, Villager villager) {
        this.maxDiscount = maxDiscount;
        Entity targetVillager = ((CraftEntity) villager).getHandle();
        NBTTagCompound tag = targetVillager.save(new NBTTagCompound());
        if (tag.hasKeyOfType("Offers", 10)) {
            NBTTagList recipeData = (NBTTagList) tag.getCompound("Offers").get("Recipes");
            modifyNBT(recipeData);
        }
        targetVillager.a_(tag);
    }

    /**
     * Modify the NBT tag of a villager special price
     *
     * @param tagList the nbt tag list of the recipe data
     */
    private void modifyNBT(NBTTagList tagList) {
        final String SPECIALPRICE = "specialPrice";

        if (tagList == null || tagList.size() == 0) return;
        for (int i = 0; i < tagList.size(); i++) {
            int currentDiscount = tagList.getCompound(i).getInt(SPECIALPRICE);

            if (currentDiscount < 0) {
                NBTBase buy = tagList.getCompound(i).get("buy");
                NBTBase sell = tagList.getCompound(i).get("sell");
                if (buy != null && sell != null && buy.getTypeId() == 10) {
                    NBTTagCompound nbtBuy = (NBTTagCompound) buy;
                    if (nbtBuy.hasKey("Count")) {
                        int buyCount = nbtBuy.getInt("Count");
                        // Holy shit, Mojang! a discount might be two or even three times bigger than the original price
                        if (Math.abs(currentDiscount) < buyCount) {
                            maxDiscount = -1 * (int) Math.floor(buyCount * 0.15 + Math.abs(currentDiscount) * 0.1);
                        } else {
                            maxDiscount = -1 * (int) Math.floor(buyCount * 0.15 + Math.abs(currentDiscount % buyCount) * 0.2);
                        }
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[ImprovedVillagers] " + ChatColor.AQUA + "Orig=" + buyCount + " Cur=" + currentDiscount + " Calc=" + maxDiscount + "       Trades: " + buy.asString() + " " + sell.asString());
                    }
                }
            }

            if (currentDiscount < maxDiscount) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[ImprovedVillagers] " + ChatColor.AQUA + "Adjusting discount from " + currentDiscount + " to " + maxDiscount);
                tagList.getCompound(i).setInt(SPECIALPRICE, maxDiscount);
            }
        }
    }

}
