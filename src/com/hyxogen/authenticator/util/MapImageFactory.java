package com.hyxogen.authenticator.util;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

/**
 * 
 * @author Daan Meijer
 *
 */
public class MapImageFactory {

	/**
	 * Als een nieuwe map maken handmatig niet werkt gebruik dan
	 * {@link Bukkit#createMap(org.bukkit.World)}
	 * 
	 * @param name   the desired display name of the map item
	 * @param lore   the desired lore the map item has
	 * @param amount the number of map items
	 * @param image  the image to render onto the map
	 * @return a map {@link ItemStack} object with the desired image to be rendered
	 *         on
	 */
	public static ItemStack createMap(String name, Collection<? extends String> lore, int amount, Image image) {
		ItemStack item = new ItemStack(Material.MAP);
		item.setAmount(amount);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(new ArrayList<>(lore));
		item.setItemMeta(meta);

		MapView map = (MapView) item;

		List<MapRenderer> renderers = map.getRenderers();
		for (int i = 0; i < renderers.size(); i++) {
			map.removeRenderer(renderers.get(i));
		}

		map.addRenderer(new MapRenderer() {

			@Override
			public void render(MapView view, MapCanvas canvas, Player player) {
				canvas.drawImage(0, 0, image);
			}
		});

		return item;
	}

	/**
	 * Als een nieuwe map maken handmatig niet werkt gebruik dan
	 * {@link Bukkit#createMap(org.bukkit.World)}
	 * 
	 * @param name   the desired display name of the map item
	 * @param lore   the desired lore the map item has
	 * @param amount the number of map items
	 * @param image  the image to render onto the map
	 * @param player the player that can see the image
	 * @return a map {@link ItemStack} object with the desired image to be rendered
	 *         on with it only being rendered for a specified player
	 */
	public static ItemStack createMap(String name, Collection<? extends String> lore, int amount, Image image,
			Player player) {
		ItemStack item = new ItemStack(Material.MAP);
		item.setAmount(amount);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(new ArrayList<>(lore));
		item.setItemMeta(meta);

		MapView map = (MapView) item;

		List<MapRenderer> renderers = map.getRenderers();
		for (int i = 0; i < renderers.size(); i++) {
			map.removeRenderer(renderers.get(i));
		}

		map.addRenderer(new MapRenderer() {

			@Override
			public void render(MapView view, MapCanvas canvas, Player viewer) {
				if (viewer == player)
					canvas.drawImage(0, 0, image);
			}
		});

		return item;
	}
}