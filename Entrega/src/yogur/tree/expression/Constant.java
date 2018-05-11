package yogur.tree.expression;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

public class Constant extends Expression {
	Object value;

	public Constant(Object value) {
		this.value = value;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		// Do nothing
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		if (value instanceof Integer) {
			return new BaseType(BaseType.PredefinedType.Int);
		} else if (value instanceof Boolean) {
			return new BaseType(BaseType.PredefinedType.Bool);
		} else {
			throw new CompilationException("Invalid constant value: " + value,  getLine(), getColumn(),
					CompilationException.Scope.TypeAnalyzer);
		}
	}
}