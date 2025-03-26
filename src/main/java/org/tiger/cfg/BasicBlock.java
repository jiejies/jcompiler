package org.tiger.cfg;

import java.util.ArrayList;
import java.util.List;

public class BasicBlock {
    private final String label;           // 基本块的标签
    private final List<Statement> stms;   // 基本块中的语句
    private final List<BasicBlock> preds; // 前驱基本块
    private final List<BasicBlock> succs; // 后继基本块

    public BasicBlock(String label) {
        this.label = label;
        this.stms = new ArrayList<>();
        this.preds = new ArrayList<>();
        this.succs = new ArrayList<>();
    }

    public void addStatement(Statement stm) {
        stms.add(stm);
    }

    public void addPredecessor(BasicBlock pred) {
        preds.add(pred);
    }

    public void addSuccessor(BasicBlock succ) {
        succs.add(succ);
    }

    public String getLabel() {
        return label;
    }

    public List<Statement> getStatements() {
        return stms;
    }

    public List<BasicBlock> getPredecessors() {
        return preds;
    }

    public List<BasicBlock> getSuccessors() {
        return succs;
    }

    public int getStatementCount() {
        return stms.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(":\n");
        for (Statement stm : stms) {
            sb.append("  ").append(stm).append("\n");
        }
        return sb.toString();
    }
}