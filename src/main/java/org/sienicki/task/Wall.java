package org.sienicki.task;

import java.util.*;
import java.util.stream.Collectors;

public class Wall implements Structure {
    private final List<Block> blocks = new LinkedList<>();

    public Wall(Block ...blocks){
        Arrays.stream(blocks).forEach(this::addBlock);
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        if (color == null) {
            throw new IllegalArgumentException("Color could not be null");
        }
        return blocks.stream()
                .flatMap(Block::toStream)
                .filter(blocks -> blocks.getColor().equals(color))
                .findAny();
    }

    @Override
    public List<Block> findBlockByMaterial(String material) {
            if(material == null){
                throw new IllegalArgumentException("Material could not be null");
            }
        return blocks.stream()
                .flatMap(Block::toStream)
                .filter(block -> block.getMaterial().equals(material))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) blocks.stream()
                .flatMap(Block::toStream)
                .count();
    }

    public void addBlock(Block block){
        blocks.add(block);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return Objects.equals(blocks, wall.blocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks);
    }
}
