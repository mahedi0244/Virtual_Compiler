
class ArrayIndexDeclaration
{
	RangeList rangeList;

	ArrayIndexDeclaration(RangeList r)
	{
		rangeList = r;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";

		IO.displayln(indent + indent.length() + " <array index declaration>");
		rangeList.printParseTree(indent1);
	}

	int getRangeValue(){
		return rangeList.getRangeValue();
	}
}