package org.sienicki.task;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    private static final Block BLOCK_RED_BRICK = new SingleBlock("red", "brick");
    private static final Block BLOCK_YELLOW_BRICK = new SingleBlock("yellow", "brick");
    private static final CompositeBlockImpl COMPOSITE_BLOCK1 = new CompositeBlockImpl("yellow", "brick");
    private static final CompositeBlockImpl COMPOSITE_BLOCK2 = new CompositeBlockImpl("white", "hollow");
    private static final Block BLOCK_WHITE_STONE = new SingleBlock("white", "stone");

    private Structure wallDoesNotExist;
    private Structure createdWall;

    @BeforeAll
    static void wallSetup(){
        COMPOSITE_BLOCK1.addBlock(BLOCK_RED_BRICK);
        COMPOSITE_BLOCK2.addBlock(BLOCK_WHITE_STONE);
        COMPOSITE_BLOCK1.addBlock(COMPOSITE_BLOCK2);
    }
    @BeforeEach
    void setUp() {
        wallDoesNotExist = new Wall();
        createdWall = new Wall(BLOCK_YELLOW_BRICK, COMPOSITE_BLOCK1, BLOCK_RED_BRICK);
    }

    @Test
    void shouldBeAbleToInstantiateClass(){
       assertNotNull(wallDoesNotExist);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenWallDoesNotExistAndColorIsNull(){
        assertThrows(IllegalArgumentException.class, () -> wallDoesNotExist.findBlockByColor(null));
    }
    @Test
    void shouldFindBlockWithColor(){
        Optional<Block> redBlock = createdWall.findBlockByColor("white");
        assertTrue(redBlock.isPresent());
    }
    @Test
    void shouldNotFindBlockWithColor(){
        Optional<Block> blackBlock = createdWall.findBlockByColor("black");
        assertFalse(blackBlock.isPresent());
    }
    @Test
    void shouldThrowIllegalArgumentExceptionWhenWallDoesNotExistAndMaterialIsNull(){
        assertThrows(IllegalArgumentException.class, () -> wallDoesNotExist.findBlockByMaterial(null));
    }

    @Test
    void shouldFoundListOfBlocksWithMaterial(){
        List<Block> block = createdWall.findBlockByMaterial("stone");
        assertEquals(List.of(BLOCK_WHITE_STONE) , block);
    }
    @Test
    void shouldNotFindBlockWithMaterial(){
        List<Block> block = createdWall.findBlockByMaterial("wood");
        assertTrue(block.isEmpty());
    }
    @Test
    void shouldReturn0WhenWallIsEmpty(){
        assertEquals(0, wallDoesNotExist.count() );
    }
    @Test
    void shouldCountBlocksForCreatedWall(){
        assertEquals(6, createdWall.count() );
    }
    @Test
    void shouldAddBlockToWallAndCountIt(){
        //given
        Wall anotherWall = new Wall();
        SingleBlock greenSingleBlock = new SingleBlock("green", "stone");
        SingleBlock blackSingleBlock = new SingleBlock("black", "stone");
        //when
        anotherWall.addBlock(greenSingleBlock);
        anotherWall.addBlock(blackSingleBlock);
        //then
        assertEquals(2,anotherWall.count());
    }
    @Test
    void shouldCountNestedBlocks(){
        //given
        Wall nestedWall = new Wall();
        CompositeBlockImpl compositeBlock = new CompositeBlockImpl("orange", "wood");
        SingleBlock blackSingleBlock = new SingleBlock("black", "stone");
        SingleBlock anotherSingleBlock = new SingleBlock("purple", "steel");
        SingleBlock singleBlock = new SingleBlock("green", "steel");
        //when
        compositeBlock.addBlock(blackSingleBlock);
        compositeBlock.addBlock(anotherSingleBlock);
        nestedWall.addBlock(compositeBlock);
        nestedWall.addBlock(singleBlock);
        //then
        assertEquals(4, nestedWall.count());
    }
    @Test
    void shouldFindNestedBlockByColor(){
        //given
        Wall nestedWall = new Wall();
        CompositeBlockImpl compositeBlock = new CompositeBlockImpl("orange", "wood");
        SingleBlock blackSingleBlock = new SingleBlock("black", "stone");
        SingleBlock anotherSingleBlock = new SingleBlock("purple", "steel");
        //when
        compositeBlock.addBlock(blackSingleBlock);
        compositeBlock.addBlock(anotherSingleBlock);
        nestedWall.addBlock(compositeBlock);
        //then
        assertTrue(nestedWall.findBlockByColor("purple").isPresent());
    }
    @Test
    void shouldDisplayAllBlockCreatingCompositeBlock(){
        //given
        CompositeBlockImpl compositeBlock = new CompositeBlockImpl("orange", "wood");
        SingleBlock blackSingleBlock = new SingleBlock("black", "stone");
        SingleBlock anotherSingleBlock = new SingleBlock("purple", "steel");
        //when
        compositeBlock.addBlock(blackSingleBlock);
        compositeBlock.addBlock(anotherSingleBlock);
        List<Block> blocks = compositeBlock.getBlocks();
        //then
        assertEquals(List.of(blackSingleBlock, anotherSingleBlock), blocks);
    }

}