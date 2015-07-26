/*
 * This file is part of HeavySpleef.
 * Copyright (c) 2014-2015 matzefratze123
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.matzefratze123.heavyspleef.core.event;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Location;

import de.matzefratze123.heavyspleef.core.game.Game;
import de.matzefratze123.heavyspleef.core.game.QuitCause;
import de.matzefratze123.heavyspleef.core.player.SpleefPlayer;

@Getter
public class PlayerLeaveGameEvent extends PlayerGameEvent implements Cancellable {

	private @Setter boolean cancelled;
	private @Setter Location teleportationLocation;
	private @Setter boolean sendMessages = true;
	private @Setter String playerMessage;
	private @Setter String broadcastMessage;
	private QuitCause cause;
	private SpleefPlayer killer;
	
	public PlayerLeaveGameEvent(Game game, SpleefPlayer player, SpleefPlayer killer, QuitCause cause) {
		super(game, player);
		
		this.killer = killer;
		this.cause = cause;
	}
	
}