package yogur.typeidentification;

import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.Program;

public class TypeAnalyzer {
    private Program program;

    public TypeAnalyzer(Program program) {
        this.program = program;
    }

    public Program decorateTree() throws CompilationException {
		program.performTypeAnalysis();

        return program;
    }
}
