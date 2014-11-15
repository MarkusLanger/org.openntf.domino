package org.openntf.domino.nsfdata.structs.cd;

import java.nio.ByteBuffer;

import org.openntf.domino.nsfdata.structs.SIG;

/**
 * Text in a rich-text field can have the "Pass-Thru HTML" attribute. Pass-through HTML text is not translated to the Domino rich text
 * format. Pass-through HTML text is marked by CDHTMLBEGIN and CDHTMLEND records. (editods.h)
 * 
 * @since Lotus Notes/Domino 4.6
 *
 */
public class CDHTMLBEGIN extends CDRecord {

	public final Unsigned8[] Spared = array(new Unsigned8[4]);

	public CDHTMLBEGIN(final CDSignature cdSig) {
		super(cdSig);
	}

	public CDHTMLBEGIN(final SIG signature, final ByteBuffer data) {
		super(signature, data);
	}

}
