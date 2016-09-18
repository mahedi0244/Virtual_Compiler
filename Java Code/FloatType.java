class FloatType extends PrimitiveType
{
	String getType()
	{
		return "float";
	}
	
	Float_Type typeVal()
	{
		return TypeChecker.float_Type;
	}
	
	int getRangeValue(){
		return 0;
	}
}