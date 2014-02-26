/* Generated By:JJTree: Do not edit this line. ASTValueDouble.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.openntf.domino.tests.rpr.formula.parse;

import org.openntf.domino.tests.rpr.formula.eval.FormulaContext;
import org.openntf.domino.tests.rpr.formula.eval.Value;

public class ASTValueDouble extends SimpleNode {
	private Double value;

	public ASTValueDouble(final int id) {
		super(id);
	}

	public ASTValueDouble(final AtFormulaParser p, final int id) {
		super(p, id);
	}

	public void parseDouble(final String image, final char decSep) {
		value = Double.valueOf(image.replace(decSep, '.'));
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
/* JavaCC - OriginalChecksum=caf90bb805413c9a270cb62ab4d79699 (do not edit this line) */