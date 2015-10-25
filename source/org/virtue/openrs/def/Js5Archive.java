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
package org.virtue.openrs.def;

/**
 * 
 * @author Sundays211
 * @since Oct 21, 2014
 */
public enum Js5Archive {
	SKELETONS(0, "", false, false, false),
	SKINS(1, "", false, false, false),
	CONFIG(2, "client.config", true, false, false),
	INTERFACES(3, "", true, false, false),
	LANDSCAPES(5, "", false, false, false),
	MESHES(7, "", false, false, false),
	SPRITES(8, "client.sprites", false, false, false),
	TEXTURES(9, "", false, false, false),
	BINARY(10, "client.binary", false, false, false),
	SCRIPTS(12, "", true, false, false),
	FONTMETRICS(13, "client.fontmetrics", false, false, false),
	VORBIS(14, "client.vorbis", false, false, false),
	CONFIG_LOC(16, "", true, false, false),
	CONFIG_ENUM(17, "", true, false, false),
	CONFIG_NPC(18, "", true, false, false),
	CONFIG_ITEM(19, "", true, false, false),
	CONFIG_ANIM(20, "", true, false, false),
	CONFIG_SPOT(21, "", true, false, false),
	CONFIG_STRUCT(22, "", true, false, false),
	UNKNOWN_23(23, "", true, false, false),
	QUICKCHAT(24, "", true, false, false),
	QUICKCHAT_GLOBAL(25, "", true, false, false),
	UNKNOWN_26(26, "", false, false, false),
	PARTICLES(27, "", false, false, false),
	DEFAULTS(28, "client.defaults", false, false, false),
	BILLBORDS(29, "", false, false, false),
	DLLS(30, "", false, false, false),
	SHADERS(31, "client.hlsl", false, false, false),
	UNKNOWN_32(32, "", false, false, false),
	UNKNOWN_33(33, "", true, false, false),
	UNKNOWN_34(34, "", false, false, false),
	CUTSCENES(35, "", true, false, false),
	UNKNOWN_37(37, "", false, false, false),
	AUDIO(40, "", false, false, true),
	WORLDMAP_DATA(41, "", false, false, false),
	UNKNOWN_42(42, "", false, false, false);
	
	int archiveId;
	boolean aBool8184;
    
    public boolean method1461(byte i) {
    	return aBool8184;
    }
    
    Js5Archive(int id, String string, boolean bool, boolean bool_0_, boolean bool_1_) {
		archiveId = id;
		aBool8184 = bool_1_;
    }
    
    public int getArchiveId() {
    	return archiveId;
    }
}
