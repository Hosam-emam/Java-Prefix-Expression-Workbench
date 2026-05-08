package AST;
public class BinaryOpNode extends ASTNode {
    public String operator;
    public ASTNode left;
    public ASTNode right;

    public BinaryOpNode(String operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public String prettyPrint(String indent) {
        return indent + "BinaryOp(" + operator + ")\n"
                + left.prettyPrint(indent + "├── ") + "\n"
                + right.prettyPrint(indent + "└── ");
    }
}