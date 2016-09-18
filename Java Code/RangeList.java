
abstract class RangeList
{
	void printParseTree(String indent)
	{
		RangeList p = this;
		while ( p.getClass() == MultipleRange.class )
		{
			((MultipleRange)p).range.printParseTree(indent);
			p = ((MultipleRange)p).rangeList;
		}
		p.printParseTree(indent); // p is the last Range
	}
	
	abstract int getRangeValue();
}