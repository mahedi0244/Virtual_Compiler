
class Int extends Expr
{
	int val;

	Int(int i)
	{
		val = i;
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
		return TypeChecker.int_Type;
	}
	void emitInstructions() {
		// TODO Auto-generated method stub
		LexAnalyzer.displayln(InterCodeGenerator.indent+ "push " + val);
	}
}
