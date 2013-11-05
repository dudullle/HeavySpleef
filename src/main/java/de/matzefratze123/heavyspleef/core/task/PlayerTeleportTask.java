package de.matzefratze123.heavyspleef.core.task;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import de.matzefratze123.heavyspleef.core.Game;
import de.matzefratze123.heavyspleef.core.flag.FlagType;
import de.matzefratze123.heavyspleef.core.flag.ListFlagLocation.SerializeableLocation;
import de.matzefratze123.heavyspleef.objects.SimpleBlockData;
import de.matzefratze123.heavyspleef.objects.SpleefPlayer;

public class PlayerTeleportTask implements Runnable {

	private Game game;
	private List<SimpleBlockData> changedBlocks;

	public PlayerTeleportTask(Game game) {
		this.game = game;
		this.changedBlocks = new ArrayList<SimpleBlockData>();
	}

	@Override
	public void run() {
		Location defaultSpawnpoint = game.getFlag(FlagType.SPAWNPOINT);
		List<SerializeableLocation> spawnpoints = game.getFlag(FlagType.NEXTSPAWNPOINT);
		
		List<SpleefPlayer> players = game.getIngamePlayers();
		
		for (int i = 0; i < players.size(); i++) {
			SpleefPlayer player = players.get(i);
			Location teleportTo;
			
			if (spawnpoints != null && i < spawnpoints.size()) {
				Location bukkitLocation = spawnpoints.get(i).getBukkitLocation();
				
				teleportTo = bukkitLocation.clone();
			} else if (defaultSpawnpoint != null) {
				teleportTo = defaultSpawnpoint.clone();
			} else {
				Location randomLocation = game.getRandomLocation();
				
				teleportTo = randomLocation;
			}
			
			if (game.getFlag(FlagType.BOXES) && game.getFlag(FlagType.ONEVSONE)) {
				generateBox(teleportTo);
				
				//Add a half block to prevent box glitches
				teleportTo = new Location(teleportTo.getWorld(), teleportTo.getBlockX(), teleportTo.getBlockY(), teleportTo.getBlockZ());
				teleportTo.add(0.5, 0, 0.5);
			}
			
			//We have to teleport the player after the boxes were build. Reason: Otherwise players can glitch out
			player.getBukkitPlayer().teleport(teleportTo);
		}
	}

	private void generateBox(Location location) {
		if (location == null)
			return;
		
		//List all blockfaces which are relevant to change
		BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH,
				BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH_EAST,
				BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
				BlockFace.SOUTH_WEST, BlockFace.SELF };
		
		Location loc = location.clone();

		for (int i = 0; i < 3; i++) {
			for (BlockFace face : faces) {
				//Do not generate blocks where the player stands
				if (i < 2 && face == BlockFace.SELF)
					continue;
				
				Block block = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + i, loc.getBlockZ()).getRelative(face);
				
				changedBlocks.add(new SimpleBlockData(block));
				block.setType(Material.GLASS);
			}
		}
	}
	
	/**
	 * This method is called on the start() in Game.java
	 */
	public void removeBoxes() {
		for (SimpleBlockData data : changedBlocks) {
			Block block = data.getWorld().getBlockAt(data.getLocation());
			block.setTypeIdAndData(data.getMaterial().getId(), data.getData(), false);
		}
		
		changedBlocks.clear();
	}

}
