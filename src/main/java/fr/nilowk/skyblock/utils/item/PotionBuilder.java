package fr.nilowk.skyblock.utils.item;

import fr.nilowk.skyblock.utils.TriMap;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotionBuilder {

    private PotionType potionType;
    private String name;
    private List<String> description;
    private Color color;
    private String localizedName;
    private Integer customModelData;
    private Map<PotionEffect, Boolean> potionEffects;
    private TriMap<Enchantment, Integer, Boolean> enchantments;
    private ItemFlag[] itemFlags;
    private HashMap<Attribute, AttributeModifier> attributes;
    public boolean unbreakable;

    public PotionBuilder(PotionType potionType, boolean unbreakable) {
        this.potionType = potionType;
        this.unbreakable = unbreakable;
        this.potionEffects = new HashMap<>();
    }

    public PotionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PotionBuilder setDescription(String... lines) {
        List<String> description = new ArrayList<>();
        for (String str : lines) {
            description.add(str);
        }
        this.description = description;
        return this;
    }

    public PotionBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public PotionBuilder setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
        return this;
    }

    public PotionBuilder setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public PotionBuilder addEffect(PotionEffect potionEffect, boolean overwrite) {
        potionEffects.put(potionEffect, overwrite);
        return this;
    }

    public PotionBuilder addEffects(HashMap<PotionEffect, Boolean> potionEffects) {
        for (Map.Entry<PotionEffect, Boolean> effect : potionEffects.entrySet()) {
            this.potionEffects.put(effect.getKey(), effect.getValue());
        }
        return this;
    }

    public PotionBuilder addEnchantment(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        enchantments.put(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    public PotionBuilder addEnchantments(TriMap<Enchantment, Integer, Boolean> enchantments) {
        enchantments.forEachKeys(k -> {
            this.enchantments.put(k, enchantments.getValue(k), enchantments.getSecondValue(k));
        });
        return this;
    }

    public PotionBuilder addItemFlags(ItemFlag... itemFlags) {
        this.itemFlags = itemFlags;
        return this;
    }


    public PotionBuilder addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        this.attributes.put(attribute, attributeModifier);
        return this;
    }

    public PotionBuilder addAttributeModifiers(HashMap<Attribute, AttributeModifier> attributes) {
        for (Map.Entry<Attribute, AttributeModifier> attribute : attributes.entrySet()) {
            this.attributes.put(attribute.getKey(), attribute.getValue());
        }
        return this;
    }

    public ItemStack build() {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta pM = (PotionMeta) potion.getItemMeta();
        pM.setBasePotionData(new PotionData(potionType));
        if (name != null) pM.setDisplayName(name);
        if (description != null) pM.setLore(description);
        if (color != null) pM.setColor(color);
        if (localizedName != null) pM.setLocalizedName(localizedName);
        if (customModelData != null) pM.setCustomModelData(customModelData);
        for (Map.Entry<PotionEffect, Boolean> effect : potionEffects.entrySet()) {
            pM.addCustomEffect(effect.getKey(), effect.getValue());
        }
        if (itemFlags != null) pM.addItemFlags(itemFlags);
        if (attributes != null) {
            for (Map.Entry<Attribute, AttributeModifier> attribute : attributes.entrySet()) {
                pM.addAttributeModifier(attribute.getKey(), attribute.getValue());
            }
        }
        pM.setUnbreakable(unbreakable);
        potion.setItemMeta(pM);
        return potion;
    }

}
