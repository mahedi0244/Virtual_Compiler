
class If1 extends Statement
{
	Expr expr;
	Statement statement;

	If1(Expr e, Statement s)
	{
		expr = e;
		statement = s;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent + "  ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <if1>");
		expr.printParseTree(indent2);
		statement.printParseTree(indent2);
	}
	
	TypeVal typeEval()
	{
		TypeVal eType = expr.typeEval();
		TypeVal stType = statement.typeEval();
		if ( eType == TypeChecker.typeError || stType == TypeChecker.typeError )
			return TypeChecker.typeError;
		else if ( eType == TypeChecker.boolean_Type )
			return TypeChecker.typeCorrect;
		else
		{
			IO.displayln("Type Error: non-boolean expression found in if-statement");
			return TypeChecker.typeError;
		}
	}
	
	void emitInstructions() {
		
		int x= ++InterCodeGenerator.jump;
		expr.emitInstructions();
		IO.displayln(InterCodeGenerator.indent+ "ifF goto " + x);

		statement.emitInstructions();
		IO.displayln(x+ ":");
	}
}