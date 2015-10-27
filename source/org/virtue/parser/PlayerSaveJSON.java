package org.virtue.parser;

import org.virtue.model.entity.player.Player;

import com.google.gson.JsonObject;

public class PlayerSaveJSON {

	public void savePlayer(Player player) {
		JsonObject obj = new JsonObject();
		obj.addProperty("username", player.getUsername());
		obj.addProperty("display", player.getUsername());
		obj.addProperty("password", player.getPassword());
		obj.addProperty("rank", player.getRights());
	}

}
