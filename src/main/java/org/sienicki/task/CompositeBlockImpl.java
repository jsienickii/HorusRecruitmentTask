package org.sienicki.task;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class CompositeBlockImpl extends SingleBlock implements CompositeBlock{
    private final List<Block> blockList = new LinkedList<>();

    public CompositeBlockImpl(String color, String material) {
        super(color, material);
    }

    @Override
    public List<Block> getBlocks() {
        return  Collections.unmodifiableList(blockList);
    }


    @Override
    public Stream<Block> toStream() {
        return Stream.concat(
                super.toStream(),
                blockList.stream().flatMap(Block::toStream)
        );
    }
    public void addBlock(Block block){
        blockList.add(block);
    }
}
