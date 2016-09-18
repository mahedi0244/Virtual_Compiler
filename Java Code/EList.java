abstract class EList
{
	void printParseTree(String indent)
	{
		EList p = this;
		while ( p.getClass() == MultipleExpr.class )
		{
			((MultipleExpr)p).expr.printParseTree(indent);
			p = ((MultipleExpr)p).eList;
		}
		p.printParseTree(indent); // p is the last Expr
	}
	
	abstract void emitInstructions();
	abstract String getArrayVar();
}