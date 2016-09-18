
class ArrayIndex
{
	EList eList;

	ArrayIndex(EList el)
	{
		eList = el;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";

		IO.displayln(indent + indent.length() + " <array index>");
		eList.printParseTree(indent1);
	}
	
	void emitInstructions(){
		eList.emitInstructions();
	}
}