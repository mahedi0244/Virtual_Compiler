
/**

This class is a top-down, recursive-descent parser for the following syntactic categories:

<program> --> <var dec list> <statement>
<var dec list> --> <var dec> ";" | <var dec> ";" <var dec list>
<var dec> --> <type> <id list>
<type> --> <primitive type> | <array type>
<primitive type> --> "int" | "float" | "boolean"
<array type> --> <primitive type> <array index declaration>
<array index declaration> --> "[" <range list> "]"
<range list> --> <range> | <range> "," <range list>
<range> --> <int> ":" <int>
<id list> --> <id> | <id> "," <id list>
<statement> --> <assignment> | <block> | <if1> | <if2> | <while> | <print>
<assignment> --> <var> "=" <expr>;
<var> --> <id> | <array var>
<array var> --> <id> <array index>
<array index> --> "[" <e list> "]"
<e list> --> <expr> | <expr> "," <e list>
<block> --> "{" <s list> "}"
<s list> --> <statement> | <statement> <s list>
<if1> --> "if" <expr> <statement> 
<if2> --> "if" <expr> <statement> "else" <statement>
<while> --> "while" <expr> <statement>
<print> --> "print" <expr>;
<expr> --> <var> | <int> | <float> | <floatE> | <floatF> | "false" | "true" | "(" <operator application> ")"
<operator application> --> <binary operator application> | <unary operator application>
<binary operator application> --> <binary operator> <expr> <expr>
<unary operator application> --> <unary operator> <expr>
<binary operator> --> + | - | * | / | "|" | & | < | <= | > |>= | = | !=
<unary operator> --> - | !

Note: Each occurrence of "else" matches the closest unmatched "if".

The definitions of the tokens are given in the lexical analyzer class file "LexAnalyzer.java". 

The following variables/functions of "IO"/"LexAnalyzer" classes are used:

static void display(String s)
static void displayln(String s)
static void setIO(String inFile, String outFile)
static void closeIO()

static void setLex()
static String t // holds an extracted token
static State state // the current state of the finite automaton indicating the token category
static int getToken() // extracts the next token

An explicit parse tree is constructed in the form of nested class objects.

The main function will display the parse tree in linearly indented form.
Each syntactic category name labeling a node is displayed on a separate line, 
prefixed with the integer i representing the node's depth and indented by i blanks. 

**/


public abstract class Parser extends LexAnalyzer
{
	static boolean syntaxErrorFound = false;
	
	static final IntType int_type = new IntType();
	static final FloatType float_type = new FloatType();
	static final BooleanType boolean_type = new BooleanType();
	
	
	public static Program program()
	
	// <program> --> <var dec list> <statement>
	
	{
		VarDecList varDecList = varDecList();
		Statement statement = statement();
		return new Program(varDecList, statement);
	}
	
	public static VarDecList varDecList()
	
	// <var dec list> --> <var dec> ";" | <var dec> ";" <var dec list>
	
	{
		VarDec varDec = varDec();
		
		if ( state == State.Semicolon )
		{	
			getToken();
			if ( state == State.Keyword_int || state == State.Keyword_float || state == State.Keyword_boolean )
			{
				VarDecList varDecList = varDecList();
				return new MultipleVarDec(varDec, varDecList);
			}
			else
				return varDec;
		}
		else
		{
			errorMsg(4);
			return null;
		}
	}
	
	public static VarDec varDec()
	
	// <var dec> --> <type> <id list>
	
	{
		Type type = type();
		IdList idList = idList();
		return new VarDec(type, idList);
	}
	
	public static Type type()
	
	// <type> --> <primitive type> | <array type>
	// <primitive type> --> "int" | "float" | "boolean"
	// <array type> --> <primitive type> <array index declaration>
	
	{
		PrimitiveType primitiveType;

		switch( state )
		{
			case Keyword_int:     primitiveType = int_type;     getToken(); break;
			case Keyword_float:   primitiveType = float_type;   getToken(); break;
			case Keyword_boolean: primitiveType = boolean_type; getToken(); break;
			default: errorMsg(10); return null;
		}

		if ( state == State.LBracket )
		{
			ArrayIndexDeclaration arrayIndexDeclaration = arrayIndexDeclaration();
			return new ArrayType(primitiveType, arrayIndexDeclaration);
		}
		else
			return primitiveType;
	}

	public static ArrayIndexDeclaration arrayIndexDeclaration()

	// <array index declaration> --> "[" <range list> "]"

	{
		getToken();  // flush "["
		RangeList rangeList = rangeList();

		if ( state == State.RBracket )
		{
			getToken();
			return new ArrayIndexDeclaration(rangeList);
		}
		else
		{
			errorMsg(11);
			return null;
		}
	}

	public static RangeList rangeList()

	// <range list> --> <range> | <range> "," <range list>

	{
		Range range = range();
		
		if ( state == State.Comma )
		{	
			getToken();
			RangeList rangeList = rangeList();
			return new MultipleRange(range, rangeList);
		}
		else
			return range;	
	}

	public static Range range()

	// <range> --> <int> ":" <int>

	{
		Int lowerBound = integer();
		if ( state == State.Colon )
		{
			getToken();
			Int upperBound = integer();
			return new Range(lowerBound, upperBound);
		}
		else
		{
			errorMsg(9);
			return null;
		}
	}

	public static Int integer()
	
	{
		if ( state == State.Int )
		{
			String integer = t;
			getToken();
			return new Int(Integer.parseInt(integer));
		}
		else
		{
			errorMsg(8);
			return null;
		}
	}
	
	public static IdList idList()
	
	// <id list> --> <id> | <id> "," <id list>
	
	{
		Id id = id();
		
		if ( state == State.Comma )
		{	
			getToken();
			IdList idList = idList();
			return new MultipleId(id, idList);
		}
		else
			return new SingleId(id);		
	}
	
	public static Id id()
	
	{
		if ( state == State.Id )
		{
			String id = t;
			getToken();
			return new Id(id);
		}
		else
		{
			errorMsg(12);
			return null;
		}
	}
	
	public static Statement statement()

	// <statement> --> <assignment> | <block> | <if1> | <if2> | <while> | <print>

	{
		switch ( state )
		{
		case Id: return assignment();
		case LBrace: return block();
		case Keyword_if: return if_1_2();
		case Keyword_while: return while_loop();
		case Keyword_print: return print();
		default: errorMsg(6); return null;
		}
	}

	public static Assignment assignment()

	// <assignment> --> <var> "=" <expr>;

	{
		Var var = var();
		if ( state == State.Eq )
		{
			getToken();
			Expr expr = expr();
			if ( state == State.Semicolon )
			{
				getToken();
				return new Assignment(var, expr);
			}
			else
			{
				errorMsg(4);
				return null;
			}
		}
		else
		{
			errorMsg(5);
			return null;
		}
	}

	public static Var var()

	// <var> --> <id> | <array var>
	// <array var> --> <id> <array index>
	// <array index> --> "[" <e list> "]"

	{
		Id id = id();
		if ( state == State.LBracket )
		{
			ArrayIndex arrayIndex = arrayIndex();
			return new ArrayVar(id, arrayIndex);
		}
		else
		{
			return id;
		}
	}

	public static ArrayIndex arrayIndex()

	// <array index> --> "[" <e list> "]"

	{
		getToken();  // flush "["
		EList eList = eList();
		if ( state == State.RBracket )
		{
			getToken();
			return new ArrayIndex(eList);
		}
		else
		{
			errorMsg(11);
			return null;
		}
	}

	public static EList eList()

	// <e list> --> <expr> | <expr> "," <e list>

	{
		Expr expr = expr();
		
		if ( state == State.Comma )
		{	
			getToken();
			EList eList = eList();
			return new MultipleExpr(expr, eList);
		}
		else
			return expr;	
	}

	public static Block block()

	// <block> --> "{" <s list> "}"

	{
		getToken();  // flush "{"
		SList sList = sList();
		if ( state == State.RBrace )
		{
			getToken();
			return new Block(sList);
		}
		else
		{
			errorMsg(3);
			return null;
		}
	}

	public static Statement if_1_2()

	// <if1> --> "if" <expr> <statement> 
	// <if2> --> "if" <expr> <statement> "else" <statement>

	{
		getToken();  // flush "if"
		Expr expr = expr();
		Statement statement1 = statement();
		if ( state == State.Keyword_else )
		{
			getToken();
			Statement statement2 = statement();
			return new If2(expr, statement1, statement2);
		}
		else
			return new If1(expr, statement1);
	}

	public static While while_loop()

	// <while> --> "while" <expr> <statement>

	{
		getToken();  // flush "while"
		Expr expr = expr();
		Statement statement = statement();
		return new While(expr, statement);		
	}

	public static Print print()

	// <print> --> "print" <expr>;

	{
		getToken();  // flush "print"
		Expr expr = expr();
		if ( state == State.Semicolon )
		{
			getToken();
			return new Print(expr);
		}
		else
		{
			errorMsg(4);
			return null;
		}
	}

	public static SList sList()

	// <s list> --> <statement> | <statement> <s list>

	{
		Statement statement = statement();

		if ( state == State.Id || state == State.LBrace ||
		     state == State.Keyword_if || state == State.Keyword_while || state == State.Keyword_print )
		{
			SList sList = sList();
			return new MultipleStatement(statement, sList);
		}
		else
			return statement;
	}

	public static Expr expr()

	// <expr> --> <var> | <int> | <float> | <floatE> | <floatF> | "false" | "true" | "(" <operator application> ")"

	{
		switch ( state )
		{
			case Id:
				
				return var();

			case Int:

				Int intElem = new Int(Integer.parseInt(t));
				getToken();
				return intElem;

			case Float: case FloatE: case FloatF:

				Floatp floatElem = new Floatp(Float.parseFloat(t));
				getToken();
				return floatElem;

			case Keyword_false:

				getToken();
				return new Bool(false);

			case Keyword_true:

				getToken();
				return new Bool(true);

			case LParen:
				
				getToken();
				Expr operatorApplication = operatorApplication();
				if ( state == State.RParen )
				{
					getToken();
					return operatorApplication;
				}
				else
				{
					errorMsg(7);
					return null;
				}

			default:

				errorMsg(2);
				return null;
		}
	}

	public static Expr operatorApplication()

	// <operator application> --> <binary operator application> | <unary operator application>
	// <binary operator application> --> <binary operator> <expr> <expr>
	// <unary operator application> --> <unary operator> <expr>
	// <binary operator> --> + | - | * | / | "|" | & | < | <= | > |>= | = | !=
	// <unary operator> --> - | !

	{
		Expr expr1, expr2;

		switch( state )
		{
			case Add: getToken(); expr1 = expr(); expr2 = expr(); return new Add(expr1, expr2);
			case Mul: getToken(); expr1 = expr(); expr2 = expr(); return new Mul(expr1, expr2);
			case Div: getToken(); expr1 = expr(); expr2 = expr(); return new Div(expr1, expr2);
			case Or : getToken(); expr1 = expr(); expr2 = expr(); return new Or (expr1, expr2);
			case And: getToken(); expr1 = expr(); expr2 = expr(); return new And(expr1, expr2);
			case Lt : getToken(); expr1 = expr(); expr2 = expr(); return new Lt (expr1, expr2);
			case Le : getToken(); expr1 = expr(); expr2 = expr(); return new Le (expr1, expr2);
			case Gt : getToken(); expr1 = expr(); expr2 = expr(); return new Gt (expr1, expr2);
			case Ge : getToken(); expr1 = expr(); expr2 = expr(); return new Ge (expr1, expr2);
			case Eq : getToken(); expr1 = expr(); expr2 = expr(); return new Eq (expr1, expr2);
			case Neq: getToken(); expr1 = expr(); expr2 = expr(); return new Neq(expr1, expr2);
			case Inv: getToken(); expr1 = expr(); return new Inv(expr1);
			case Sub: getToken();
				  expr1 = expr();
				  if ( state == State.RParen )
					return new Neg(expr1);
				  else
				  {
				  	expr2 = expr();
					return new Sub(expr1, expr2);
				  }
			default:  errorMsg(1); return null;
		}
	}

	public static void errorMsg(int i)
	{
		syntaxErrorFound = true;
		
		display(t + " : Syntax Error, unexpected symbol where");

		switch( i )
		{
		case 1:	 displayln(" arithmetic, boolean, or comparison operator expected"); return;
		case 2:  displayln(" id, int, float, false, true, or ( expected"); return;
		case 3:	 displayln(" } expected"); return;
		case 4:	 displayln(" ; expected"); return;
		case 5:	 displayln(" = expected"); return;
		case 6:	 displayln(" id, {, if, while, or print expected"); return;
		case 7:	 displayln(" ) expected"); return;
		case 8:  displayln(" int expected"); return;
		case 9:  displayln(" : expected"); return;
		case 10: displayln(" type name expected"); return;
		case 11: displayln(" ] expected"); return;
		case 12: displayln(" id expected"); return;
		}
	}

	public static void main(String argv[])
	{
		// argv[0]: input file containing a program
		// argv[1]: output file displaying the parse tree 

		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		Program program = program(); // build a parse tree
		if ( ! t.isEmpty() )
			displayln(t + "  -- unexpected symbol");
		else if ( ! syntaxErrorFound )
			program.printParseTree("");

		closeIO();
	}
}
