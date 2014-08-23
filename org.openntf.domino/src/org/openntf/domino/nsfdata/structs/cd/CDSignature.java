package org.openntf.domino.nsfdata.structs.cd;

import static org.openntf.domino.nsfdata.structs.ODSConstants.RecordLength.BYTE;
import static org.openntf.domino.nsfdata.structs.ODSConstants.RecordLength.LONG;
import static org.openntf.domino.nsfdata.structs.ODSConstants.RecordLength.WORD;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.openntf.domino.nsfdata.structs.BSIG;
import org.openntf.domino.nsfdata.structs.LSIG;
import org.openntf.domino.nsfdata.structs.ODSConstants.RecordLength;
import org.openntf.domino.nsfdata.structs.SIG;
import org.openntf.domino.nsfdata.structs.WSIG;

public enum CDSignature {
	PDEF_MAIN(WORD, 83), PDEF_TYPE(WORD, 84), PDEF_PROPERTY(WORD, 85), PDEF_ACTION(WORD, 86), TABLECELL_DATAFLAGS(BYTE, 87),
	EMBEDDEDCONTACTLIST(WORD, 88), IGNORE(BYTE, 89), TABLECELL_HREF2(WORD, 90), HREFBORDER(WORD, 91), TABLEDATAEXTENSION(WORD, 92),
	EMBEDDEDCALCTL(WORD, 93), ACTIONTEXT(WORD, 94), EVENT_LANGUAGE_ENTRY(WORD, 95), FILESEGMENT(LONG, 96), FILEHEADER(LONG, 97),
	DATAFLAGS(BYTE, 98), BACKGROUNDPROPERTIES(BYTE, 98), EMBEDEXTRA_INFO(WORD, 100), CLIENT_BLOBPART(WORD, 101), CLIENT_EVENT(WORD, 102),
	BORDERINFO_HS(WORD, 103), LARGE_PARAGRAPH(WORD, 104), EXT_EMBEDDEDSCHED(WORD, 105), BOXSIZE(BYTE, 106), POSITIONING(BYTE, 107),
	LAYER(BYTE, 108), DECSFIELD(WORD, 109), SPAN_END(BYTE, 110), SPAN_BEGIN(BYTE, 111), TEXTPROPERTIESTABLE(WORD, 112), HREF2(WORD, 113),
	BACKGROUNDCOLOR(BYTE, 114), INLINE(WORD, 115), V6HOTSPOTBEGIN_CONTINUATION(WORD, 116), TARGET_DBLCLK(WORD, 117), CAPTION(WORD, 118),
	LINKCOLORS(WORD, 119), TABELCELL_HREF(WORD, 120), ACTIONBAREXT(WORD, 121), IDNAME(WORD, 122), TABLECELL_IDNAME(WORD, 123),
	IMAGESEGMENT(LONG, 124), IMAGEHEADER(LONG, 125), V5HOTSPOTBEGIN(WORD, 126), V5HOTSPOTEND(BYTE, 127), TEXTPROPERTY(WORD, 128),
	PARAGRAPH(BYTE, 129), PABDEFINITION(WORD, 130), PABREFERENCE(BYTE, 131), TEXT(WORD, 133), HEADER(WORD, 142), LINKEXPORT2(WORD, 146),
	BITMAPHEADER(LONG, 149), BITMAPSEGMENT(LONG, 150), COLORTABLE(LONG, 151), GRAPHIC(LONG, 153), PMMETASEC(LONG, 154),
	WINMETASEG(LONG, 155), MACMETASEG(LONG, 156), CGMETA(LONG, 157), PMMETAHEADER(LONG, 158), WINMETAHEADER(LONG, 159),
	MACMETAHEADER(LONG, 160), TABLEBEGIN(BYTE, 163), TABLECELL(BYTE, 164), TABLEEND(BYTE, 165), STYLENAME(BYTE, 166),
	STORAGELINK(WORD, 196), TRANSPARENTTABLE(LONG, 197), HORIZONTALRULE(WORD, 201), ALTTEXT(WORD, 202), ANCHOR(WORD, 203),
	HTMLBEGIN(WORD, 204), HTMLEND(WORD, 205), HTMLFORMULA(WORD, 206), NESTEDTABLEBEGIN(BYTE, 207), NESTEDTABLECELL(BYTE, 208),
	NESTEDTABLEEND(BYTE, 209), COLOR(BYTE, 210), TABLECELL_COLOR(BYTE, 211), BLOBPART(WORD, 220), BEGIN(BYTE, 221), END(BYTE, 222),
	VERTICALALIGN(BYTE, 223), FLOATPOSITION(BYTE, 224), TIMERINFO(BYTE, 225), TABLEROWHEIGHT(BYTE, 226), TABLELABEL(WORD, 227),
	BIDI_TEXT(WORD, 228), BIDI_TEXTEFFECT(WORD, 229), REGIONBEGIN(WORD, 230), REGIONEND(WORD, 231), TRANSITION(WORD, 232),
	FIELDHINT(WORD, 233), PLACEHOLDER(WORD, 234), EMBEDDEDOUTLINE(WORD, 236), EMBEDDEDVIEW(WORD, 237), CELLBACKGROUNDDATA(WORD, 238),
	FRAMESETHEADER(WORD, 239), FRAMESET(WORD, 240), FRAME(WORD, 241), TARGET(WORD, 242), MAPELEMENT(WORD, 244), AREAELEMENT(WORD, 245),
	HREF(WORD, 246), EMBEDDEDCTL(WORD, 247), HTML_ALTTEXT(WORD, 248), EVENT(WORD, 249), PRETABLEBEGIN(WORD, 251), BORDERINFO(WORD, 252),
	EMBEDDEDSCHEDCTL(WORD, 253), EXT2_FIELD(WORD, 254), EMBEDDEDEDITCTL(WORD, 255),

	DOCUMENT_PRE_26(BYTE, 128), FIELD_PRE_36(WORD, 132), FIELD(WORD, 138), DOCUMENT(BYTE, 134), METAFILE(WORD, 135), BITMAP(WORD, 136),
	FONTTABLE(WORD, 139), LINK(BYTE, 140), LINKEXPORT(BYTE, 141), KEYWORD(WORD, 143), LINK2(WORD, 145), CGM(WORD, 147), TIFF(LONG, 148),
	PATTERNTABLE(LONG, 152), DDEBEGIN(WORD, 161), DDEEND(WORD, 162), OLEBEGIN(WORD, 167), OLEEND(WORD, 168), HOTSPOTBEGIN(WORD, 169),
	HOTSPOTEND(BYTE, 170), BUTTON(WORD, 171), BAR(WORD, 172), V4HOTSPOTBEGIN(WORD, 173), V4HOTSPOTEND(BYTE, 170), EXT_FIELD(WORD, 176),
	LSOBJECT(WORD, 177), HTMLHEADER(WORD, 178), HTMLSEGMENT(WORD, 179), LAYOUT(BYTE, 183), LAYOUTTEXT(BYTE, 184), LAYOUTEND(BYTE, 185),
	LAYOUTFIELD(BYTE, 186), PABHIDE(WORD, 187), PABFORMREF(BYTE, 188), ACTIONBAR(BYTE, 189), ACTION(WORD, 190, LONG),
	DOCAUTOLAUNCH(WORD, 191), LAYOUTGRAPHIC(BYTE, 192), OLEOBJINFO(WORD, 193), LAYOUTBUTTON(BYTE, 194), TEXTEFFECT(WORD, 195),

	VMHEADER(BYTE, 175), VMBITMAP(BYTE, 176), BMRECT(BYTE, 177), VMPOLYGON_BYTE(BYTE, 178), VMPOLYLINE_BYTE(BYTE, 179),
	VMREGION(BYTE, 180), VMACTION(BYTE, 181), VMELLIPSE(BYTE, 182), VMRNDRECT(BYTE, 184), VMBUTTON(BYTE, 185), VMACTION_2(WORD, 186),
	VMTEXTBOX(WORD, 197), VMPOLYGON(WORD, 188), VMPOLYLINE(WORD, 190), VMCIRCLE(BYTE, 191), VMPOLYRGN_BYTE(BYTE, 192),

	ALTERNATEBEGIN(WORD, 198), ALTERNATEEND(BYTE, 199), OLERTMARKER(WORD, 200);

	private final RecordLength recordLength_;
	private final int baseValue_;
	private final RecordLength trueRecordLength_;

	private CDSignature(final RecordLength recordLength, final int baseValue) {
		recordLength_ = recordLength;
		baseValue_ = baseValue;
		trueRecordLength_ = null;
	}

	/**
	 * This exists because the ACTION record type has a signature defined with WORD but actually uses an LSIG
	 */
	private CDSignature(final RecordLength recordLength, final int baseValue, final RecordLength trueRecordLength) {
		recordLength_ = recordLength;
		baseValue_ = baseValue;
		trueRecordLength_ = trueRecordLength;
	}

	public int getBaseValue() {
		return baseValue_;
	}

	public RecordLength getRecordLength() {
		return recordLength_;
	}

	public RecordLength getEffectiveRecordLength() {
		return trueRecordLength_ != null ? trueRecordLength_ : recordLength_;
	}

	public int getSize() {
		if (trueRecordLength_ != null) {
			switch (trueRecordLength_) {
			case BYTE:
				return 2;
			case LONG:
				return 6;
			case WORD:
				return 4;
			default:
				return 0;
			}
		} else {
			switch (recordLength_) {
			case BYTE:
				return 2;
			case LONG:
				return 6;
			case WORD:
				return 4;
			default:
				return 0;
			}
		}
	}

	public static SIG sigForData(final ByteBuffer data) {
		data.order(ByteOrder.LITTLE_ENDIAN);
		//		System.out.println("reading sig at position: " + data.position());
		byte lowOrderByte = data.get(data.position());
		int lowOrder = lowOrderByte & 0xFF;
		int highOrder = data.get(data.position() + 1) & 0xFF;
		//		System.out.println("low order: " + lowOrder);
		//		System.out.println("low order byte: " + lowOrderByte);
		//		System.out.println("high order: " + highOrder);

		RecordLength length = RecordLength.valueOf(highOrder);

		//		int value = lowOrder | highOrder;
		for (CDSignature cdSig : values()) {
			if (lowOrder == cdSig.getBaseValue() && length == cdSig.getRecordLength()) {
				//				System.out.println("matched signature " + cdSig);
				// Now determine which SIG to return based on the record length
				switch (cdSig.getEffectiveRecordLength()) {
				case BYTE:
					return new BSIG(cdSig, highOrder);
				case WORD:
					return new WSIG(cdSig, data.getShort(data.position() + 2));
				case LONG:
					return new LSIG(cdSig, data.getInt(data.position() + 2));
				default:
					break;
				}
			}
		}
		throw new IllegalArgumentException("Unknown sig value " + lowOrder + " for length " + length);
	}
}