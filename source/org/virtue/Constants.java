/**
 * Copyright (c) 2014 Launcher Studios
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
package org.virtue;

import java.math.BigInteger;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Aug 8, 2014
 */
public class Constants {

	/**
	 * The Framework Name
	 */
	public static String FRAME_NAME = new String("Virue");

	/**
	 * The Framework Version
	 */
	public static double FRAME_VERSION = new Double(1.0);

	/**
	 * The Framework Major Revision
	 */
	public static int FRAME_MAJOR = new Integer(857);

	/**
	 * The Framework Minor Revision
	 */
	public static int FRAME_MINOR = new Integer(1);

	/**
	 * The Cache Repository
	 */
	public static String CACHE_REPOSITORY = new String(System.getProperty("user.home") + "/Desktop/data");

	/**
	 * The OnDemand Delta Keys
	 */
	public static int[] ONDEMAND_DELTA = { 2670, 69795, 41651, 35866, 358716, 44375, 18239, 20373, 166001, 1088825,
			411951, 550452, 797793, 1136601, 34434, 806685, 20238, 1244, 63343, 2077, 119, 1336070, 4122245, 8991,
			22747 };

	/**
	 * The OnDemand Session Token
	 */
	public static String ONDEMAND_TOKEN = new String("9Jxi3d4cJufWHwLrj5jkNFd39Sj2EwIC");

	/**
	 * The Login Session Token
	 */
	public static String LOGIN_TOKEN = new String("wwGlrZHF5gKN6D3mDdihco3oPeYN2KFybL9hUUFqOvk");

	public static final BigInteger ONDEMAND_MODULUS = new BigInteger(
			"98211207632598216632574352276587654819496020913869702903448724788350559669040381099689433546005648568991958649010862475246873121007363571159669265702608212758143918882703473897766071783000131116001085824455220579801507062679473481088296966063849543105128735425596828953836442410238368447510940422228092046399");

	public static final BigInteger ONDEMAND_EXPONENT = new BigInteger(
			"2686920293654708064485799069715148161975012061866859595432863322176977180624523602113968511649726534388247583161824258329152135525980787693810931129053458197938193280389942772139436687693023983166032921888325402839230392027216446884787251394559750301348715366832024005762803436354261693243594354998405575569");

	public static final BigInteger LOGIN_MODULUS = new BigInteger(
			"138718966591926402240609335038565799859156602937713938209763325395555080762444598079660934938517430369118865489923659886390602958347623030251868030359873987405390259532388986368523547845025346632739421612694993566724069297087976819701549055297312367387391613235040762537185443316785190737623106429614907247041");

	public static final BigInteger LOGIN_EXPONENT = new BigInteger(
			"3253292821639545298744473319716734583266302984806541694438351330286222425986501476241494987571925637080362181027704430251344381753517808222792028360515835059057030752444093239389317429399843643961115130397653101151833776609050826691227515914961024667776002683694553943777284564680176918457637862600601991045");
	/**
	 * The 826 Packet Sizes
	 */
	public static final int[] PACKET_SIZES = new int[120];

	/**
	 * Loads the 826 Packet Sizes
	 */
	static {
		/*
		 * PACKET_SIZES[0] = 7; PACKET_SIZES[1] = 9; PACKET_SIZES[2] = 6;
		 * PACKET_SIZES[3] = 8; PACKET_SIZES[4] = -2; PACKET_SIZES[5] = -1;
		 * PACKET_SIZES[6] = 3; PACKET_SIZES[7] = 4; PACKET_SIZES[8] = -1;
		 * PACKET_SIZES[9] = 8; PACKET_SIZES[10] = 5; PACKET_SIZES[11] = -2;
		 * PACKET_SIZES[12] = -1; PACKET_SIZES[13] = 15; PACKET_SIZES[14] = 3;
		 * PACKET_SIZES[15] = 8; PACKET_SIZES[16] = -1; PACKET_SIZES[17] = 18;
		 * PACKET_SIZES[18] = 8; PACKET_SIZES[19] = 1; PACKET_SIZES[20] = 7;
		 * PACKET_SIZES[21] = -1; PACKET_SIZES[22] = 9; PACKET_SIZES[23] = 4;
		 * PACKET_SIZES[24] = 7; PACKET_SIZES[25] = -1; PACKET_SIZES[26] = 8;
		 * PACKET_SIZES[27] = 16; PACKET_SIZES[28] = 9; PACKET_SIZES[29] = 7;
		 * PACKET_SIZES[30] = -1; PACKET_SIZES[31] = -2; PACKET_SIZES[32] = 3;
		 * PACKET_SIZES[33] = 3; PACKET_SIZES[34] = 1; PACKET_SIZES[35] = -1;
		 * PACKET_SIZES[36] = -1; PACKET_SIZES[37] = -2; PACKET_SIZES[38] = 17;
		 * PACKET_SIZES[39] = -1; PACKET_SIZES[40] = -2; PACKET_SIZES[41] = -1;
		 * PACKET_SIZES[42] = -2; PACKET_SIZES[43] = -2; PACKET_SIZES[44] = 16;
		 * PACKET_SIZES[45] = 18; PACKET_SIZES[46] = 4; PACKET_SIZES[47] = 9;
		 * PACKET_SIZES[48] = 3; PACKET_SIZES[49] = 3; PACKET_SIZES[50] = 8;
		 * PACKET_SIZES[51] = 3; PACKET_SIZES[52] = 3; PACKET_SIZES[53] = 3;
		 * PACKET_SIZES[54] = 3; PACKET_SIZES[55] = 2; PACKET_SIZES[56] = -1;
		 * PACKET_SIZES[57] = -1; PACKET_SIZES[58] = 8; PACKET_SIZES[59] = 0;
		 * PACKET_SIZES[60] = 4; PACKET_SIZES[61] = 0; PACKET_SIZES[62] = -1;
		 * PACKET_SIZES[63] = 4; PACKET_SIZES[64] = 9; PACKET_SIZES[65] = 7;
		 * PACKET_SIZES[66] = 3; PACKET_SIZES[67] = 1; PACKET_SIZES[68] = 4;
		 * PACKET_SIZES[69] = 9; PACKET_SIZES[70] = -1; PACKET_SIZES[71] = 15;
		 * PACKET_SIZES[72] = -1; PACKET_SIZES[73] = 1; PACKET_SIZES[74] = 4;
		 * PACKET_SIZES[75] = 0; PACKET_SIZES[76] = 3; PACKET_SIZES[77] = -1;
		 * PACKET_SIZES[78] = 7; PACKET_SIZES[79] = -1; PACKET_SIZES[80] = 6;
		 * PACKET_SIZES[81] = -1; PACKET_SIZES[82] = 11; PACKET_SIZES[83] = 2;
		 * PACKET_SIZES[84] = 4; PACKET_SIZES[85] = -2; PACKET_SIZES[86] = 6;
		 * PACKET_SIZES[87] = 8; PACKET_SIZES[88] = -1; PACKET_SIZES[89] = -1;
		 * PACKET_SIZES[90] = 8; PACKET_SIZES[91] = 0; PACKET_SIZES[92] = 8;
		 * PACKET_SIZES[93] = -1; PACKET_SIZES[94] = 4; PACKET_SIZES[95] = 3;
		 * PACKET_SIZES[96] = 4; PACKET_SIZES[97] = 2; PACKET_SIZES[98] = -1;
		 * PACKET_SIZES[99] = 1; PACKET_SIZES[100] = -1; PACKET_SIZES[101] = 9;
		 * PACKET_SIZES[102] = 7; PACKET_SIZES[103] = 3; PACKET_SIZES[104] = -1;
		 * PACKET_SIZES[105] = 0; PACKET_SIZES[106] = 3; PACKET_SIZES[107] = 0;
		 * PACKET_SIZES[108] = 3; PACKET_SIZES[109] = -1; PACKET_SIZES[110] = 9;
		 * PACKET_SIZES[111] = -1; PACKET_SIZES[112] = -2; PACKET_SIZES[113] =
		 * -1; PACKET_SIZES[114] = 3; PACKET_SIZES[115] = 1; PACKET_SIZES[116] =
		 * -2; PACKET_SIZES[117] = -2; PACKET_SIZES[118] = 11; PACKET_SIZES[119]
		 * = 12;
		 */
	}
}
