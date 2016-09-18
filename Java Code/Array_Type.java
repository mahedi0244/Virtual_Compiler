
final class Array_Type extends Valid_Type
{
	Primitive_Type elemType;
	int dimension;
	int[] rangeList;

	Array_Type(Primitive_Type et, int d, int[] rl)
	{
		elemType = et;
		dimension = d;
		rangeList = rl;
	}

	String rangeListToString()
	{
		String rangeListString = rangeList[0]+"";
		for ( int i=1; i < rangeList.length; i++ )
			rangeListString = rangeListString+", "+rangeList[i];
		return rangeListString;
	}

	public String toString()
	{
		return "["+elemType.toString()+", "+dimension+", "+rangeListToString()+"]";
	}

	boolean isNumberType()
	{
		return false;
	}
}