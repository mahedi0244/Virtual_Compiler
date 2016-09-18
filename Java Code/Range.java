
class Range extends RangeList
{
	Int lowerBound;
	Int upperBound;

	Range(Int lb, Int ub)
	{
		lowerBound = lb;
		upperBound = ub;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent + indent.length() + " <range>");
		lowerBound.printParseTree(indent1);
		upperBound.printParseTree(indent1);
	}
	
	int getRangeValue(){
		return upperBound.val-lowerBound.val+1;
	}
}