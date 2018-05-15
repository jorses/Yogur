package yogur.tree.expression.identifier;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.declaration.Argument;
import yogur.tree.type.ArrayType;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;
import yogur.tree.type.ClassType;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class DotIdentifier extends VarIdentifier {
	private Expression expression;
	private String identifier;

	private Declaration declaration;

	public DotIdentifier(Expression left, String right) {
		this.expression = left;
		this.identifier = right;
	}

	@Override
	public Declaration getDeclaration() {
		return declaration;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		expression.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType left = expression.performTypeAnalysis();
		if (left instanceof ClassType) {
			ClassType classT = (ClassType) left;
			declaration = classT.getDeclaration().getDeclaration(identifier);
			return declaration.performTypeAnalysis();
		}

		throw new CompilationException("Trying to member access (." + identifier + ") on a non-class type " + left,
				getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		expression.performMemoryAssignment(currentOffset, nestingDepth);
	}

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		expression.generateCodeR(stream);
		if (declaration instanceof Argument) {
			stream.appendInstruction("inc", ((Argument)declaration).getOffset());
		} else {
			// For a function, generate just the code of the class
		}
	}
}
