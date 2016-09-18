
abstract class UnaryOperatorApplication extends Expr
{
	Expr expr;

	abstract String getOp();

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";

		IO.displayln(indent + indent.length() + " " + getOp());
		expr.printParseTree(indent1);
	}
}