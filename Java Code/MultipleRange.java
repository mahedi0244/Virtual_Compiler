
class MultipleRange extends RangeList
{
	Range range;
	RangeList rangeList;

	MultipleRange(Range r, RangeList rl)
	{
		range = r;
		rangeList = rl;
	}
	
	int getRangeValue(){
		return range.getRangeValue()*rangeList.getRangeValue();
	}
}