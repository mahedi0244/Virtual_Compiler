
abstract class PrimitiveType extends Type
{
	abstract Primitive_Type typeVal();
	abstract String getType();

	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " " + getType());
	}
}