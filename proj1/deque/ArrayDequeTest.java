package deque;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void addAndGet(){
        ArrayDeque<Integer> testArrayDeque = new ArrayDeque<>();
        for(int i = 0;i < 8;i++){
            testArrayDeque.addLast(i);
        }
        assertEquals(0,(int)testArrayDeque.get(0));
    }

    @Test
    public void testResize(){
        ArrayDeque<Integer> testArrayDeque = new ArrayDeque<>();
        for(int i = 0;i < 16;i++){
            testArrayDeque.addLast(i);
        }
        for(int i = 0;i < 16;i++){
            testArrayDeque.addFirst(i);
        }
        assertEquals(15,(int)testArrayDeque.removeFirst());
        assertEquals(15,(int)testArrayDeque.removeLast());
    }

    @Test
    public void testGet(){
        ArrayDeque<Integer> testArrayDeque = new ArrayDeque<>();
        testArrayDeque.addFirst(0);
        testArrayDeque.addLast(1);
        assertEquals(0,(int)testArrayDeque.removeFirst());
        assertEquals(1,(int)testArrayDeque.get(0));
        assertEquals(1,(int)testArrayDeque.removeLast());
        testArrayDeque.addLast(5);
        assertEquals(5,(int)testArrayDeque.removeLast());
        testArrayDeque.addLast(7);
        assertEquals(7,(int)testArrayDeque.removeFirst());
        testArrayDeque.addFirst(9);
        testArrayDeque.addLast(10);
        testArrayDeque.addFirst(11);
        testArrayDeque.addFirst(12);
        assertEquals(12,(int)testArrayDeque.get(0));
        assertEquals(12,(int)testArrayDeque.removeFirst());
        testArrayDeque.addFirst(15);
        assertEquals(10,(int)testArrayDeque.removeLast());
        testArrayDeque.addFirst(17);
        assertEquals(9,(int)testArrayDeque.removeLast());
        assertEquals(11,(int)testArrayDeque.get(2));
    }

    @Test
    public void FillEmptyFill(){
        ArrayDeque<Integer> testArrayDeque = new ArrayDeque<>();
        for(int i =0;i < 20;i++){
            testArrayDeque.addFirst(i);
        }
        for(int i = 19;i >=0;i--){
            assertEquals(i,(int)testArrayDeque.removeFirst());
        }

    }
}
