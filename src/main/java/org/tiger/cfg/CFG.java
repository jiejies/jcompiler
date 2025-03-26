package org.tiger.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CFG {
    private final String methodName;                    // 方法名
    private final List<BasicBlock> blocks;              // 所有基本块
    private final Map<String, BasicBlock> labelToBlock; // 标签到基本块的映射
    private BasicBlock entryBlock;                      // 入口基本块
    private BasicBlock exitBlock;                       // 出口基本块

    public CFG(String methodName) {
        this.methodName = methodName;
        this.blocks = new ArrayList<>();
        this.labelToBlock = new HashMap<>();
    }

    public void addBlock(BasicBlock block) {
        blocks.add(block);
        labelToBlock.put(block.getLabel(), block);
    }

    public void setEntryBlock(BasicBlock block) {
        this.entryBlock = block;
    }

    public void setExitBlock(BasicBlock block) {
        this.exitBlock = block;
    }

    public BasicBlock getBlockByLabel(String label) {
        return labelToBlock.get(label);
    }

    public String getMethodName() {
        return methodName;
    }

    public List<BasicBlock> getBlocks() {
        return blocks;
    }

    public BasicBlock getEntryBlock() {
        return entryBlock;
    }

    public BasicBlock getExitBlock() {
        return exitBlock;
    }

    public int getBlockCount() {
        return blocks.size();
    }

    public int getTotalStatementCount() {
        return blocks.stream()
                .mapToInt(BasicBlock::getStatementCount)
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CFG for method: ").append(methodName).append("\n");
        sb.append("Entry block: ").append(entryBlock.getLabel()).append("\n");
        sb.append("Exit block: ").append(exitBlock.getLabel()).append("\n");
        sb.append("Total blocks: ").append(getBlockCount()).append("\n");
        sb.append("Total statements: ").append(getTotalStatementCount()).append("\n\n");
        
        for (BasicBlock block : blocks) {
            sb.append(block.toString()).append("\n");
        }
        return sb.toString();
    }
} 