package AST;

public class LetExprNode extends ASTNode {
    public String varName;
    public ASTNode value;

    public LetExprNode(String varName, ASTNode value) {
        this.varName = varName;
        this.value = value;
    }

    @Override
    public String prettyPrint(String indent) {
        return indent + "LetExpr\n"
                + indent + "├── Identifier(" + varName + ")\n"
                + value.prettyPrint(indent + "└── ");
    }
}