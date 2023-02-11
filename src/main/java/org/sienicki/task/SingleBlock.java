package org.sienicki.task;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class SingleBlock implements Block {
    private final String color;
    private final String material;

    public SingleBlock(String color, String material) {
        this.color = color;
        this.material = material;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getMaterial() {
        return material;
    }

    @Override
    public Stream<Block> toStream() {
        return Stream.of(this);
    }


}
