
class Block extends Statement
{
	SList sList;

	Block(SList s)
	{
		sList = s;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent + "  ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <block>");
		sList.printParseTree(indent2);
	}
	
	TypeVal typeEval()
	{
		return sList.typeEval();
	}
	
	void emitInstructions(){
		sList.emitInstructions();
	}
}