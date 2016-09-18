
class Bool extends Expr
{
	boolean val;

	Bool(boolean b)
	{
		val = b;
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " " + val);
	}
	
	String getArrayVar(){
		return "";
	}
	
	TypeVal typeEval()
	{
		return TypeChecker.boolean_Type;
	}
	
	void emitInstructions() {
		// TODO Auto-generated method stub
		IO.displayln(InterCodeGenerator.indent+ "push " + val);
	}
}