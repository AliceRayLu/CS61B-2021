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
}
