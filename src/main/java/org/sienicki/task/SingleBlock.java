package org.sienicki.task;

import java.util.Objects;
import java.util.stream.Stream;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleBlock that = (SingleBlock) o;
        return Objects.equals(color, that.color) && Objects.equals(material, that.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, material);
    }

    @Override
    public String toString() {
        return "SingleBlock{" +
                "color='" + color + '\'' +
                ", material='" + material + '\'' +
                '}';
    }
}
