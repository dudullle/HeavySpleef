/**
 *   HeavySpleef - The simple spleef plugin for bukkit
 *   
 *   Copyright (C) 2013 matzefratze123
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package me.matzefratze123.heavyspleef.core.flag;

import me.matzefratze123.heavyspleef.database.DatabaseSerializeable;

import org.bukkit.entity.Player;

public abstract class Flag<T> implements DatabaseSerializeable<T> {

	protected String name;
	
	public Flag(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public abstract T parse(Player player, String input);
	
	public abstract String toInfo(Object value);
	
	public abstract String getHelp();
	
	public abstract FlagType getType();
	
	@Override
	public String toString() {
		return name;
	}
}