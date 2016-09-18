
class While extends Statement
{
	Expr expr;
	Statement statement;

	While(Expr e, Statement s)
	{
		expr = e;
		statement = s;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent + "  ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <while>");
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
			IO.displayln("Type Error: non-boolean expression found in while-statement");
			return TypeChecker.typeError;
		}
	}
	
	void emitInstructions() {
		
		int x = ++InterCodeGenerator.jump;
		IO.displayln(x + ":");
		expr.emitInstructions();
		IO.displayln(InterCodeGenerator.indent+ "ifF goto " + ++InterCodeGenerator.jump);
		statement.emitInstructions();
		IO.displayln(InterCodeGenerator.indent+ "goto "+ x);
		IO.displayln(x+1 + ":");
	}
}
