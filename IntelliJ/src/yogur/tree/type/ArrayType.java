package yogur.tree.type;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

public class ArrayType extends Type {
	private Type internalType;
	private int size;

	public ArrayType(Type t, int size) {
		this.internalType = t;
		this.size = size;
	}

	public Type getInternalType() {
		return internalType;
	}

	public Type getBaseType() {
		return internalType.getBaseType();
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		internalType.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ArrayType)) {
			return false;
		}
		ArrayType other = (ArrayType)obj;
		return other.internalType.equals(internalType);
	}

	@Override
	public String toString() {
		return internalType.toString() + "[]";
	}
}