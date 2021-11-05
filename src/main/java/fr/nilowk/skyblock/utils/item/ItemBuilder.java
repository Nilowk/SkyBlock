package fr.nilowk.skyblock.utils.item;

import fr.nilowk.skyblock.utils.TriMap;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private Material material;
    private String name;
    private List<String> description;
    private String localizedName;
    private Integer customModelData;
    private TriMap<Enchantment, Integer, Boolean> enchantments;
    private ItemFlag[] itemFlags;
    private HashMap<Attribute, AttributeModifier> attributes;
    private boolean unbreakable;

    public ItemBuilder(Material material, boolean unbreakable) {
        this.material = material;
        this.unbreakable = unbreakable;
        this.enchantments = new TriMap<>();
        this.attributes = new HashMap<>();
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setDescription(String... lines) {
        List<String> description = new ArrayList<>();
        for (String str : lines) {
            description.add(str);
        }
        this.description = description;
        return this;
    }

    public ItemBuilder setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        enchantments.put(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    public ItemBuilder addEnchantments(TriMap<Enchantment, Integer, Boolean> enchantments) {
        enchantments.forEachKeys(k -> {
            this.enchantments.put(k, enchantments.getValue(k), enchantments.getSecondValue(k));
        });
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        this.itemFlags = itemFlags;
        return this;
    }

    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        this.attributes.put(attribute, attributeModifier);
        return this;
    }

    public ItemBuilder addAttributeModifiers(HashMap<Attribute, AttributeModifier> attributes) {
        for (Map.Entry<Attribute, AttributeModifier> attribute : attributes.entrySet()) {
            this.attributes.put(attribute.getKey(), attribute.getValue());
        }
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material);
        ItemMeta itM = item.getItemMeta();
        if (name != null) itM.setDisplayName(name);
        if (description != null) itM.setLore(description);
        if (localizedName != null) itM.setLocalizedName(localizedName);
        if (customModelData != null) itM.setCustomModelData(customModelData);
        if (enchantments != null) {
            enchantments.forEachKeys(key -> {
                itM.addEnchant(key, enchantments.getValue(key), enchantments.getSecondValue(key));
            });
        }
        if (itemFlags != null) itM.addItemFlags(itemFlags);
        if (attributes != null) {
            for (Map.Entry<Attribute, AttributeModifier> attribute : attributes.entrySet()) {
                itM.addAttributeModifier(attribute.getKey(), attribute.getValue());
            }
        }
        itM.setUnbreakable(unbreakable);
        item.setItemMeta(itM);
        return item;
    }
}
