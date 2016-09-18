
abstract class Type
{
	abstract void printParseTree(String indent);
	abstract Valid_Type typeVal(); // converts a Type object to the corresponding Valid_Type object
	abstract int getRangeValue();
}