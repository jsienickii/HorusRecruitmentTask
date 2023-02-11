package org.sienicki.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CompositeBlockImpl extends SingleBlock implements CompositeBlock {
    private final List<Block> blockList = new ArrayList<>();

    public CompositeBlockImpl(String color, String material) {
        super(color, material);
    }

    @Override
    public List<Block> getBlocks() {
        return toStream().collect(Collectors.toList());
    }

    @Override
    public Stream<Block> toStream() {
        return Stream.concat(
                super.toStream(),
                blockList.stream().flatMap(Block::toStream)
        );
    }

    public void addBlock(Block block) {
        blockList.add(block);
    }
}
