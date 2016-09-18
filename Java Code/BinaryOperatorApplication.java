
abstract class BinaryOperatorApplication extends Expr
{
	Expr expr1;
	Expr expr2;

	abstract String getOp();

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";

		IO.displayln(indent + indent.length() + " " + getOp());
		expr1.printParseTree(indent1);
		expr2.printParseTree(indent1);
	}
}