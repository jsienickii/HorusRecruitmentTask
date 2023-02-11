package org.sienicki.task

import spock.lang.Shared
import spock.lang.Specification

class WallTest extends Specification {
    //BLOCK FOR WALL_WITH_SIX_BLOCKS
    @Shared
    private final Block BLOCK_RED_BRICK = new SingleBlock("red", "brick")
    @Shared
    private final Block BLOCK_YELLOW_BRICK = new SingleBlock("yellow", "brick")
    @Shared
    private final CompositeBlockImpl COMPOSITE_BLOCK1 = new CompositeBlockImpl("black", "brick")
    @Shared
    private final CompositeBlockImpl COMPOSITE_BLOCK2 = new CompositeBlockImpl("white", "hollow")
    @Shared
    private final Block BLOCK_WHITE_STONE = new SingleBlock("white", "stone")
    @Shared
    private final Block STONE_BLOCK1 = new SingleBlock("dark ", "stone")

    //BLOCKS FOR STONE_AND_HOLLOW_WALL_WITH_SEVEN_BLOCKS
    @Shared
    private final CompositeBlock STONE_COMPOSITE_BLOCK = new CompositeBlockImpl("yellow", "stone")
    @Shared
    private final Block STONE_BLOCK2 = new SingleBlock("dark ", "stone")
    @Shared
    private final Block HOLLOW_BLOCK1 = new SingleBlock("white", "hollow")
    @Shared
    private final Block HOLLOW_BLOCK2 = new SingleBlock("white", "hollow")
    @Shared
    private final Block HOLLOW_BLOCK3 = new SingleBlock("white", "hollow")
    @Shared
    private final Block HOLLOW_BLOCK4 = new SingleBlock("white", "hollow")

    @Shared
    private final Wall EMPTY_WALL = new Wall()
    @Shared
    private final Wall WALL_WITH_SIX_BLOCKS = new Wall(BLOCK_YELLOW_BRICK, COMPOSITE_BLOCK1, BLOCK_RED_BRICK)
    @Shared
    private final Wall STONE_AND_HOLLOW_WALL_WITH_SEVEN_BLOCKS = new Wall(STONE_BLOCK1, STONE_COMPOSITE_BLOCK,
                                                                STONE_BLOCK2, HOLLOW_BLOCK1, HOLLOW_BLOCK2, HOLLOW_BLOCK3,
                                                                HOLLOW_BLOCK4)

    void setupSpec() {
        COMPOSITE_BLOCK1.addBlock(BLOCK_RED_BRICK)
        COMPOSITE_BLOCK2.addBlock(BLOCK_WHITE_STONE)
        COMPOSITE_BLOCK1.addBlock(COMPOSITE_BLOCK2)
    }

    def "should be able to instantiate class"() {
        expect:
        EMPTY_WALL != null
    }

    def "should throw illegal argument exception when wall does not exist and color is null()"() {
        when:
        EMPTY_WALL.findBlockByColor(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should find block with color #color"() {
        expect:
        WALL_WITH_SIX_BLOCKS.findBlockByColor(color) == Optional.of(expectedColor)

        where:
        color    | expectedColor
        "yellow" | BLOCK_YELLOW_BRICK
        "black"  | COMPOSITE_BLOCK1
        "red"    | BLOCK_RED_BRICK
    }

    def "should not find block with color #color "() {
        expect:
        Optional.empty() == WALL_WITH_SIX_BLOCKS.findBlockByColor(color)

        where:
        color << ["green", "pink", "orange"]
    }

    def "should throw illegal argument exception when wall does not exist and material is null"() {
        when:
        EMPTY_WALL.findBlockByMaterial(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should find list of block with material #material"() {
        expect:
        STONE_AND_HOLLOW_WALL_WITH_SEVEN_BLOCKS.findBlockByMaterial(material).size() == sizeOfList

        where:
        material | sizeOfList
        "stone"  | 3
        "hollow" | 4
    }

    def "should not found wall with material #material"() {
        expect:
        WALL_WITH_SIX_BLOCKS.findBlockByMaterial("material").size() == result

        where:
        material | result
        "wood"   | 0
        "steel"  | 0
    }

    def "should return 0 when wall is empty "() {
        expect:
        EMPTY_WALL.count() == 0
    }

    def "should count numbers of block in wall #anyWall"() {
        expect:
        anyWall.count() == sizeOfWall

        where:
        anyWall                                 | sizeOfWall
        WALL_WITH_SIX_BLOCKS                    | 6
        STONE_AND_HOLLOW_WALL_WITH_SEVEN_BLOCKS | 7
    }

    def "should add block to wall and count it "() {
        given:
        Wall newWall = new Wall()
        Block exampleBlock1 = new SingleBlock("grey", "stone")
        Block exampleBlock2 = new SingleBlock("white", "hollow")

        when:
        newWall.addBlock(exampleBlock2)
        newWall.addBlock(exampleBlock1)
        int numberOfBlocks = newWall.count()

        then:
        numberOfBlocks == 2
    }

}
