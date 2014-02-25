/* Generated By:JJTree: Do not edit this line. ASTAssignment.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.openntf.domino.tests.rpr.formula;

/**
 * ASTAssignment
 * 
 * @author Roland Praml, Foconis AG
 * 
 */
public class ASTAssignment extends TreeNode {
	public static final int FIELD = 1;
	public static final int VAR = 2;
	public static final int ENV = 3;
	public static final int DEFAULT = 4;

	private int type;
	private String varName;

	public ASTAssignment(final int id) {
		super(id);
	}

	public ASTAssignment(final AtFormulaParser p, final int id) {
		super(p, id);
	}

	public void init(final String _varName, final int _type) {
		varName = _varName;
		type = _type;

	}

	/* (non-Javadoc)
	 * @see org.openntf.domino.tests.rpr.formula.SimpleNode#toString(java.lang.String)
	 */
	@Override
	public String toString() {
		switch (type) {
		case FIELD:
			return super.toString() + ": FIELD " + varName;

		case VAR:
			return super.toString() + ": VAR " + varName;

		case ENV:
			return super.toString() + ": ENV " + varName;

		case DEFAULT:
			return super.toString() + ": DEFAULT " + varName;
		}
		return super.toString() + ": ? " + varName;
	}

	@Override
	public Value evaluate(final FormulaContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
/* JavaCC - OriginalChecksum=c689d3838a28b0c3613968dcbc8bc341 (do not edit this line) */
