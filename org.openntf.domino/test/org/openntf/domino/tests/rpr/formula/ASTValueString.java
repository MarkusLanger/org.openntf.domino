/* Generated By:JJTree: Do not edit this line. ASTValueString.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.openntf.domino.tests.rpr.formula;

public class ASTValueString extends SimpleNode {
	String value;

	public ASTValueString(final int id) {
		super(id);
	}

	public ASTValueString(final AtFormulaParser p, final int id) {
		super(p, id);
	}

	public void parseString(final String image, final char c) {
		value = image;
	}

	@Override
	public String toString() {
		return super.toString() + ": " + value;
	}

	@Override
	public Value evaluate(final FormulaContext ctx) {
		return new Value(value);
	}

}
/* JavaCC - OriginalChecksum=1d8b3742054260502a01c39f4a41131e (do not edit this line) */
