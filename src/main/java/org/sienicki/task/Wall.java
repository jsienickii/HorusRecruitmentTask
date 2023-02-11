package org.sienicki.task;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
public class Wall implements Structure {
    private final List<Block> blocks = new LinkedList<>();

    public Wall(Block... blocks) {
        Arrays.stream(blocks).forEach(this::addBlock);
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        Optional.ofNullable(color).orElseThrow(() -> new IllegalArgumentException("Color could not be null"));
        return blocks.stream()
                .flatMap(Block::toStream)
                .filter(blocks -> blocks.getColor().equals(color))
                .findAny();
    }

    @Override
    public List<Block> findBlockByMaterial(String material) {
        Optional.ofNullable(material).orElseThrow(() -> new IllegalArgumentException("Material could not be null"));
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

    public void addBlock(Block block) {
        blocks.add(block);
    }

}
