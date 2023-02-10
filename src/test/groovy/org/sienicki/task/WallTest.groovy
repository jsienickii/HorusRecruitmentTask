package org.sienicki.task

import spock.lang.Shared
import spock.lang.Specification

class WallTest extends Specification {

    @Shared
    Block BLOCK_RED_BRICK = new SingleBlock("red", "brick")
    @Shared
    Block BLOCK_YELLOW_BRICK = new SingleBlock("yellow", "brick")
    @Shared
    CompositeBlockImpl COMPOSITE_BLOCK1 = new CompositeBlockImpl("black", "brick")
    @Shared
    CompositeBlockImpl COMPOSITE_BLOCK2 = new CompositeBlockImpl("white", "hollow")
    @Shared
    Block BLOCK_WHITE_STONE = new SingleBlock("white", "stone")
    @Shared
    Wall wallDoesNotExist = new Wall()
    @Shared
    Wall wallWithSixBlocks = new Wall(BLOCK_YELLOW_BRICK, COMPOSITE_BLOCK1, BLOCK_RED_BRICK)
    @Shared
    Wall stoneAndHollowWall = new Wall()


    void setupSpec() {
        COMPOSITE_BLOCK1.addBlock(BLOCK_RED_BRICK)
        COMPOSITE_BLOCK2.addBlock(BLOCK_WHITE_STONE)
        COMPOSITE_BLOCK1.addBlock(COMPOSITE_BLOCK2)
        Block firstStoneBlock = new SingleBlock("dark ", "stone");
        CompositeBlock compositeBlock = new CompositeBlockImpl("yellow", "stone")
        Block secondStoneBlock = new SingleBlock("dark ", "stone");
        Block thirdStoneBlock = new SingleBlock("dark ", "stone");
        Block firstHollowBlock = new SingleBlock("white", "hollow");
        Block secondHollowBlock = new SingleBlock("white", "hollow");
        Block thirdHollowBlock = new SingleBlock("white", "hollow");
        compositeBlock.addBlock(thirdStoneBlock)
        stoneAndHollowWall.addBlock(firstStoneBlock)
        stoneAndHollowWall.addBlock(compositeBlock)
        stoneAndHollowWall.addBlock(secondStoneBlock)
        stoneAndHollowWall.addBlock(firstHollowBlock)
        stoneAndHollowWall.addBlock(secondHollowBlock)
        stoneAndHollowWall.addBlock(thirdHollowBlock)
    }

    def "should be able to instantiate class"() {
        expect:
        assert wallDoesNotExist != null
    }

    def "should throw illegal argument exception when wall does not exist and color is null()"() {
        when:
        wallDoesNotExist.findBlockByColor(null)
        then:
        thrown(IllegalArgumentException)
    }

    def "should find block with color #color"() {
        expect:
        wallWithSixBlocks.findBlockByColor(color) == Optional.of(expectedColor)
        where:
        color    | expectedColor
        "yellow" | BLOCK_YELLOW_BRICK
        "black"  | COMPOSITE_BLOCK1
        "red"    | BLOCK_RED_BRICK
    }

    def "should not find block with color #color "() {
        expect:
        Optional.empty() == wallWithSixBlocks.findBlockByColor(color)
        where:
        color << ["green", "pink", "orange"]
    }

    def "should throw illegal argument exception when wall does not exist and material is null"() {
        when:
        wallDoesNotExist.findBlockByMaterial(null)
        then:
        thrown(IllegalArgumentException)
    }

    def "should find list of block with material #material"() {
        given:
        Wall stoneAndHollowWall = new Wall()

        expect:
        stoneAndHollowWall.findBlockByMaterial(material).size() == sizeOfList
        where:
        material | sizeOfList
        "stone"  | 4
        "hollow" | 2
    }

    def "should not found wall with material #material"() {

        expect:
        wallWithSixBlocks.findBlockByMaterial("material").size() == result
        where:
        material | result
        "wood"   | 0
        "steel"  | 0
    }

    def "should return 0 when wall is empty "() {
        expect:
        assert wallDoesNotExist.count() == 0
    }

    def "should count numbers of block in wall #anyWall"() {
        expect:
        anyWall.count() == sizeOfWall

        where:
        anyWall            | sizeOfWall
        wallWithSixBlocks  | 6
        stoneAndHollowWall | 7
    }

    def "should add block to wall and count it"() {
        expect:
        Block exampleBlock1 = new SingleBlock("grey", "stone")
        Block exampleBlock2 = new SingleBlock("white", "hollow")
        wallWithSixBlocks.addBlock(exampleBlock1)
        stoneAndHollowWall.addBlock(exampleBlock1)
        stoneAndHollowWall.addBlock(exampleBlock2)

        anyWall.count() == resultat

        where:
        anyWall            | resultat
        wallWithSixBlocks  | 7
        stoneAndHollowWall | 9
    }

    private Wall createWall(List<Block> blockList) {

    }

    def "should add block to wall and count it properly"() {

        given:
        Wall newWall = new Wall()
        Wall newWall1 = new Wall()
        Block exampleBlock1 = new SingleBlock("grey", "stone")
        Block exampleBlock2 = new SingleBlock("white", "hollow")
        when:
        newWall.addBlock(exampleBlock2)
        newWall.addBlock(exampleBlock1)
        def numberOfBlocks = newWall.count()
        def numbersOfEmptyWall = newWall1.count()
        then:
        assert numberOfBlocks == 2
        assert numbersOfEmptyWall == 0
    }

}
