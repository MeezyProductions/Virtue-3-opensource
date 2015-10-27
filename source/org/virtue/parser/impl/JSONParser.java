/**
 * Copyright (c) 2014 Virtue Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.virtue.parser.impl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtue.model.entity.player.Player;
import org.virtue.network.protocol.message.login.LoginRequestMessage;
import org.virtue.parser.Parser;
import org.virtue.parser.ParserDataType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Nov 18, 2014
 */
public class JSONParser implements Parser {

	/**
	 * The {@link Logger} instance
	 */
	private static Logger logger = LoggerFactory.getLogger(JSONParser.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.virtue.parser.Parser#saveObjectDefinition(java.lang.Object,
	 * java.lang.String, org.virtue.parser.ParserType)
	 */
	@Override
	public void saveObjectDefinition(Object object, String file,
			ParserDataType type) {
		JsonObject obj = new JsonObject();
		int ordinal = 0;
		switch (type) {
		case CHARACTER:
			Player player = (Player) object;
			obj.addProperty("username", player.getUsername());
			obj.addProperty("display", player.getUsername());
			obj.addProperty("password", player.getPassword());
			obj.addProperty("rank", player.getRights());

			JsonArray location = new JsonArray();
			JsonObject coords = new JsonObject();
			break;
		case FRIEND:
			break;
		case IGNORE:
			break;
		case INV:
			break;
		case SKILL:
			break;
		case VAR:
			break;
		case EXCHANGE:
			break;
		case LAYOUT:
			@SuppressWarnings("unchecked")
			Map<Integer, Integer> layout = (Map<Integer, Integer>) object;
			JsonArray layoutVars = new JsonArray();
			JsonObject layoutVar = new JsonObject();
			for (Map.Entry<Integer, Integer> var : layout.entrySet()) {
				layoutVar.addProperty("key", var.getKey());
				layoutVar.addProperty("value", var.getValue());
				layoutVars.add(layoutVar);
			}
			obj.add("layout", layoutVars);
			break;
		case CLAN_SETTINGS:
			break;
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			FileWriter writer = new FileWriter(new File(type.getPath(), file
					+ ".json"));
			writer.write(gson.toJson(obj));
			writer.close();
		} catch (JsonIOException | IOException ex) {
			logger.error("Failed to save file: " + file, ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.virtue.parser.Parser#loadObjectDefinition(java.lang.Object,
	 * org.virtue.parser.ParserType)
	 */
	@Override
	public Object loadObjectDefinition(Object object, ParserDataType type) {
		JsonParser parser = new JsonParser();
		JsonObject obj;
		switch (type) {
		case CHARACTER:
			LoginRequestMessage request = (LoginRequestMessage) object;
			try {
				obj = parser.parse(new FileReader(new File(type.getPath(), request.getUsername() + ".json")))
						.getAsJsonObject();
				String display = obj.get("display").getAsString();
				String password = obj.get("password").getAsString();
				int privilege = obj.get("privilege").getAsInt();
			} catch (Exception e) {

			}
			break;
		case FRIEND:
			break;
		case IGNORE:
			break;
		case INV:
			break;
		case LAYOUT:
			break;
		case SKILL:
			break;
		case VAR:
			break;
		case CLAN_SETTINGS:
			break;
		case EXCHANGE:
			break;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.virtue.parser.Parser#objectFileExists(java.lang.String,
	 * org.virtue.parser.ParserType)
	 */
	@Override
	public boolean objectFileExists(String name, ParserDataType type) {
		// TODO Auto-generated method stub
		return false;
	}

}