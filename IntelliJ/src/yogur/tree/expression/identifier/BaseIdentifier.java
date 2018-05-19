package yogur.tree.expression.identifier;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.declaration.FuncDeclaration;
import yogur.tree.statement.VarDeclaration;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.Declaration;
import yogur.typeidentification.MetaType;
import yogur.utils.Log;

import java.io.IOException;

public class BaseIdentifier extends VarIdentifier {
	private String name;

	private Declaration declaration;
	private int nestingDepth;

	public BaseIdentifier(String name) {
		this.name = name;
	}

	/**
	 * To be used internally to mock a base identifier. A node created with this constructor is not
	 * expected to be inserted in the tree.
	 * @param argument the declaration associated to this identifier.
	 */
	public BaseIdentifier(Argument argument) {
		this.name = argument.getDeclarator().getName();
		this.declaration = argument;
	}

	@Override
	public boolean isAssignable() {
		return declaration instanceof Argument;
	}

	public Declaration getDeclaration() {
		return declaration;
	}

	public String getName() {
		return name;
	}

	@Override
	public int getDepthOnStack() {
		return 2;	// At most 2, may be less
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declaration = table.searchId(name, getLine(), getColumn());
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		return declaration.performTypeAnalysis();
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		this.nestingDepth = nestingDepth.getValue();
		Log.debug("Assigned USE nesting depth " + this.nestingDepth + " for identifier " + name + " in line " + getLine());
	}

	@Override
	public void generateCodeL(PMachineOutputStream stream) throws IOException {
		Argument arg = (Argument)declaration;
		if (arg.isDeclaredOnClass()) {
			// We are accessing a class field within a function
			// We have to load the class (which is the first parameter), and then get the argument on it
			stream.appendInstruction("lod", 0, FuncDeclaration.START_PARAMETER_INDEX);
			stream.appendInstruction("lda", nestingDepth - arg.getNestingDepth(), arg.getOffset());
		} else if (arg.isPassedByReference()) {
			stream.appendInstruction("lod", 0, arg.getOffset());
		} else {
			stream.appendInstruction("lda", nestingDepth - arg.getNestingDepth(), arg.getOffset());
		}
	}
}
