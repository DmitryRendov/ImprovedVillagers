package io.github.lukeeff.version.v1_15;

import io.github.lukeeff.version.VersionHandler;
import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.Villager;

public class Handler_1_15_R1 implements VersionHandler {

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
        Entity targetVillager = ((CraftEntity) villager).getHandle(); //Handle of villager object
        NBTTagCompound tag = targetVillager.save(new NBTTagCompound()); //NBT tag of target entity
        NBTTagList recipeData = (NBTTagList) tag.getCompound("Offers").get("Recipes");
        modifyNBT(recipeData);
        targetVillager.f(tag);
    }

    /**
     * Modify the NBT tag of a villager special price
     *
     * @param tagList the nbt tag list of the recipe data
     */
    private void modifyNBT(NBTTagList tagList) {
        final String SPECIALPRICE = "specialPrice";
        for (int i = 0; i < tagList.size(); i++) {
            int currentDiscount = tagList.getCompound(i).getInt(SPECIALPRICE);
            if (currentDiscount < maxDiscount) {
                tagList.getCompound(i).setInt(SPECIALPRICE, maxDiscount);
            }
        }
    }

}
