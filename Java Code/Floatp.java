
class Floatp extends Expr
{
	float val;

	Floatp(float f)
	{
		val = f;
	}
	
	void printParseTree(String indent)
	{
		LexAnalyzer.displayln(indent + indent.length() + " " + val);
	}
	
	String getArrayVar(){
		return "";
	}
	
	TypeVal typeEval()
	{
		return TypeChecker.float_Type;
	}
	void emitInstructions() {
		// TODO Auto-generated method stub
		LexAnalyzer.displayln(InterCodeGenerator.indent+ "push " + val);
	}
}