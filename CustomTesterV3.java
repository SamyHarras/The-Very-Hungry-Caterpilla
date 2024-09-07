package assignment2;
import assignment2.food.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Iterator;

public class CustomTesterV3 {
    @Test @DisplayName("Empty + error handling MSLL Testing")
    @Tag("score:0")
    public void initialisation(){
        MySinglyLinkedList<Number> dummy = new MySinglyLinkedList<>();
        //start with checking everything is nominal right after construction!
        assertEquals(0, dummy.getSize(), "MSLL not initalised with 0 size");
        assertNull(dummy.head, "MSLL not initialised with null head");
        assertNull(dummy.tail, "MSLL not initialised with null tail");
        assertTrue(dummy.isEmpty(), "isEmpty returns false despite the list being brand new");
        assertThrows(java.util.NoSuchElementException.class, dummy::peekFirst, "peekFirst failed to throw the right exception.");
        assertThrows(java.util.NoSuchElementException.class, dummy::peekLast, "peekFirst failed to throw the right exception.");
        assertThrows(java.util.NoSuchElementException.class, dummy::removeFirst, "peekFirst failed to throw the right exception.");
        assertThrows(java.util.NoSuchElementException.class, dummy::removeLast, "peekFirst failed to throw the right exception.");
        Iterator<Number> myIterator = dummy.iterator();
        assertFalse(myIterator.hasNext(), "Iterator reports true for next element in empty MSLL");
    }

    @Test @DisplayName("MSLL Add/Remove Testing")
    @Tag("score:0")
    public void addRemove() {
        MySinglyLinkedList<Number> dummy = new MySinglyLinkedList<>();
        //now we test add and remove methosd
        for(int i = 0; i < 10; i++) switch (i%2) {case 0 -> dummy.addFirst(i); case 1 -> dummy.addLast(i);}
        int[] myInts = new int[]{8, 6, 4, 2, 0, 1, 3, 5, 7, 9}, LIST = new int[10];
        boolean temp = true;//should remain true throughout, if it ever goes false an assert will catch it
        int counter = 0;
        Iterator<Number> myIterator = dummy.iterator();//reset bc idk how it behaves after updating list after creation.
        while(myIterator.hasNext()){
            temp = temp && myIterator.next().intValue() == myInts[counter];
            counter++;
        }// temp = temp && dummy
        assertTrue(temp, "Elements were not added correctly using addLast and addFirst!");
        for(int i = 0; i < 10; i++) temp = temp && myInts[i] == dummy.removeFirst().intValue();
        assertTrue(temp, "Elements were not removed correctly using removeFirst()");
        assertEquals(0, dummy.getSize(), "List size is not 0 after removing all elements using removeFirst");
        assertNull(dummy.head, "head is not null after removing all elements using removeFirst");
        assertNull(dummy.tail, "tail is not null after removing all elements using removeFirst");

        for(int i = 0; i < 10; i++) switch (i%2) {case 0 -> dummy.addFirst(i); case 1 -> dummy.addLast(i);}//we refill the list to test with removeLast
        for(int i = 0; i < 10; i++) temp = temp && myInts[9-i] == dummy.removeLast().intValue();
        assertTrue(temp, "Elements were not removed correctly using removeLast()");

        assertEquals(0, dummy.getSize(), "List size is not 0 after removing all elements using removeLast");
        assertNull(dummy.head, "head is not null after removing all elements using removeLast");
        assertNull(dummy.tail, "tail is not null after removing all elements using removeLast");

        Number element1, element2;
        dummy.clear();
        for(int i = 0; i < 10; i++) switch (i%2) {case 0 -> dummy.addFirst(i); case 1 -> dummy.addLast(i);}
        while(!dummy.isEmpty()){
            element1 = dummy.peekFirst();
            element2 = dummy.removeFirst();
            temp = temp && element1.intValue() == element2.intValue();
        }
        assertTrue(temp, "peekFirst does not return what is about to be removeFirst'd!");
        //now for parallel trial
        dummy.clear();
        for(int i = 0; i < 10; i++) switch (i%2) {case 0 -> dummy.addFirst(i); case 1 -> dummy.addLast(i);}
        while(!dummy.isEmpty()){
            element1 = dummy.peekLast();
            element2 = dummy.removeLast();
            temp = temp && element1.intValue() == element2.intValue();
        }
        assertTrue(temp, "peekLast does not return what is about to be removeLast'd!");
    }

    @Test @DisplayName("Misc MSLL Testing")
    @Tag("score:0")
    public void miscMSLL(){
        MySinglyLinkedList<Number> dummy = new MySinglyLinkedList<>();
        assertTrue(dummy.addFirst(-1), "addFirst does not return true.");
        assertTrue(dummy.addLast(-2), "addFirst does not return true.");
        assertFalse(dummy.isEmpty(), "isEmpty does not return false after adding two elements");
        assertEquals(2, dummy.getSize(), "Somehow you failed this case (getSize()) but not earlier ones");
        dummy.clear();
        assertEquals(0, dummy.getSize(), "List size is not 0 after clear()");
        assertNull(dummy.head, "head is not null after clear");
        assertNull(dummy.tail, "tail is not null after clear");
        assertTrue(dummy.isEmpty(), "isEmpty does not return true after clear()");
        dummy.clear();//just to see if for some reason you throw an error

        dummy.addFirst(33.625);
        assertEquals(dummy.head, dummy.tail, "head and tail are not equivalent when size = 1");
        dummy.addFirst(-1.75);
        assertEquals(dummy.head.next, dummy.tail, "head.next and tail are not equivalent when size = 2");
        assertTrue(Math.pow((double)dummy.peekFirst() + 1.75, 2) < 10E-3, "peekFirst returns the wrong element");
        assertTrue(Math.pow((double)dummy.peekLast() - 33.625, 2) < 10E-3, "peekLast returns the wrong element");

        //last edge cases I can think of
        dummy.removeLast();
        assertEquals(dummy.head, dummy.tail, "head and tail are not equivalent when size = 1 after removeLast");
        dummy.addFirst(33);
        dummy.removeFirst();
        assertEquals(dummy.head, dummy.tail, "head and tail are not equivalent when size = 1 after removeFirst");

        dummy.clear();
        for(int i = 0; i < 1000; i++) dummy.addLast(i);
        assertEquals(0, dummy.peekFirst(), "peekFirst doesn't return head after 1000 elements added total");
        assertEquals(999, dummy.peekLast(), "peekLast doesn't return tail after 1000 elements added total");
    }

    @Test @DisplayName("Stack Testing")
    @Tag("score:0")
    public void stack(){
        MyStack<Number> myStack = new MyStack<>();
        assertEquals(0, myStack.getSize(), "MyStack is not initialised to 0 size.");
        assertTrue(myStack.isEmpty(), "MyStack.isEmpty() is false on empty stack at initialisation.");
        assertThrows(java.util.NoSuchElementException.class, myStack::pop, "pop does not throw the correct Exception when empty Stack.");
        boolean temp = true;
        for(int i = 0; i < 2000; i++) temp = temp && myStack.push(i);
        assertTrue(temp, "MyStack.push does not return true.");
        assertEquals(2000, myStack.getSize(), "MyStack.getSize() is not returning the correct size!");
        //for(int i = 0; Math.random() < 0.98; i++) System.out.println(i);//OK then... forgot how loops worked for a second there
        for(int i = 0; i < 2000; i++) temp = temp && myStack.pop().intValue() == (1999 - i);
        assertTrue(temp, "myStack does not return items in the correct order!");
        assertEquals(0, myStack.getSize(), "MyStack is not 0 size when 0 elements.");
        assertTrue(myStack.isEmpty(), "MyStack.isEmpty() is false on empty stack.");

        for(int i = 0; i < 100; i++) myStack.push((i*i)%(i/2 + 3));
        int[] myInts = new int[]{0, 1, 0, 1, 1, 0, 0, 1, 1, 4, 4, 1, 0, 7, 6, 5, 3, 3, 0, 1, 10, 12, 8, 11, 6, 10, 4, 9, 2, 8, 0, 7, 17, 6, 16, 5, 15, 4, 14, 3, 13, 2, 12, 1, 11, 0, 10, 25, 9, 25, 8, 25, 7, 25, 6, 25, 5, 25, 4, 25, 3, 25, 2, 25, 1, 25, 0, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25, 36, 25};
        //for(int i = 0; i < 100; i++) System.out.print((i*i)%(i/2 + 3) + ", ");
        //one last test for pop, but mainly for peek();
        Number element1, element2;
        while(!myStack.isEmpty()){
            element1 = myStack.peek();
            element2 = myStack.pop();
            temp = temp && element1.intValue() == element2.intValue();
        }
        assertTrue(temp, "peek does not return what is about to be popped!");

        myStack.clear();
        assertEquals(0, myStack.getSize(), "MyStack not 0 size after clear()");
        assertTrue(myStack.isEmpty(), "MyStack.isEmpty() is false on empty stack after clear().");
        assertThrows(java.util.NoSuchElementException.class, myStack::pop, "pop does not throw the correct Exception when empty Stack.");
    }

    //TODO: The caterpillar will always move before attempting to eat.
    // The caterpillar will eat only when it is in a feeding stage.
    // The methods will be tested only on valid inputs: null is not a valid input for any of the
    // methods!
    private static final class c {
        public static String b = "\u001B[34m", g = "\u001B[32m", y = "\u001B[33m", r = "\u001B[31m", o = "\u001B[38;5;208m", w = "\u001B[0m";
    }
    @Test @DisplayName("Caterpillar Basics")//basic movement, initialisation, getSegmentColor
    @Tag("score:0")
    public void caterpillarBasics(){
        Caterpillar gus = new Caterpillar(new Position(-1, 2), GameColors.ORANGE, 8){
            public String toString() {
                StringBuilder gus = new StringBuilder();
                for (Segment s : this) gus.insert(0, s.toString());
                return gus.toString();
            }
        };//for comparison purposes
        assertEquals(8, gus.goal, "Goal is not initialised properly?");
        assertTrue(gus instanceof MySinglyLinkedList);
        assertEquals(1, gus.length, "Caterpillar length is not 1 on initialisation.");
        assertEquals(EvolutionStage.FEEDING_STAGE, gus.getEvolutionStage(), "Caterpillar is not initialised at the Feeding Stage");
        assertEquals("\u001B[38;5;208m(-1,2)\u001B[0m", gus.head.element.toString(), "Caterpillar is not initialised with proper segment as head.");
        assertThrows(IllegalArgumentException.class, () -> gus.move(new Position(0, 1)), "Caterpillar does not throw IllegalArgumentException after failing to move out of range.");
        gus.move(new Position (-1, 3));
        gus.eat(new Fruit(GameColors.RED));
        gus.move(new Position (-1, 4));
        gus.eat(new Fruit(GameColors.BLUE));
        gus.move(new Position (-1, 5));
        gus.eat(new Fruit(GameColors.GREEN));
        assertEquals(4, gus.length, "Caterpillar length does not increase properly.");
        final String target = SITS(-1, 2, c.g) + SITS(-1, 3, c.b) + SITS(-1, 4, c.r) + SITS(-1, 5, c.o);
        //System.out.println(target);
        assertEquals(target, gus.toString(), "Caterpillar fails eating Fruits with pre-movement as specified.");
        boolean temp = true;
        final Color[] answers = new Color[]{GameColors.GREEN, GameColors.BLUE, GameColors.RED, GameColors.ORANGE, GameColors.YELLOW};
        for(int i = -1; i < 7; i++)
            if (i < 2 || i > 5) temp = temp && null == gus.getSegmentColor(new Position(-1, i));
            else temp = temp && gus.getSegmentColor(new Position(-1, i)).equals(answers[i - 2]);
        assertTrue(temp, "getSegmentColor does not return null when Position is not in Caterpillar, or just returns the wrong Color.");
        EvolutionStage[] STAGES = new EvolutionStage[]{EvolutionStage.GROWING_STAGE, EvolutionStage.ENTANGLED, EvolutionStage.BUTTERFLY};
        for (EvolutionStage stage : STAGES) {
            gus.stage = stage;
            assertThrows(IllegalArgumentException.class, () -> gus.eat(new Fruit(GameColors.GREEN)), "Caterpillar will only eat in FEEDING_STAGE !");
            assertThrows(IllegalArgumentException.class, () -> gus.eat(new Pickle()), "Caterpillar will only eat in FEEDING_STAGE !");
            assertThrows(IllegalArgumentException.class, () -> gus.eat(new IceCream()), "Caterpillar will only eat in FEEDING_STAGE !");
            assertThrows(IllegalArgumentException.class, () -> gus.eat(new SwissCheese()), "Caterpillar will only eat in FEEDING_STAGE !");
            assertThrows(IllegalArgumentException.class, () -> gus.eat(new Lollipop()), "Caterpillar will only eat in FEEDING_STAGE !");
            assertThrows(IllegalArgumentException.class, () -> gus.eat(new Cake(10000)), "Caterpillar will only eat in FEEDING_STAGE !");
        }
    }

    @Test @DisplayName("Caterpillar Movement")
    @Tag("score:0")
    public void caterpillarMoves() {//note we move/update body ONLY IF in bounds AND no collision!
        Caterpillar gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 30);
        //basic "does it move?" testing
        gus.move(new Position(0, 1));
        gus.eat(new Fruit(GameColors.GREEN));
        assertEquals(new Position(0, 1), gus.getHeadPosition(), "move does not move head to new position!");
        gus.move(new Position(0, 2));
        assertEquals(new Position(0, 2), gus.getHeadPosition(), "move does not move head to new position!");
        assertEquals(new Position(0, 1), gus.tail.element.getPosition(), "move did not move gus' second segment to where its head used to be!");

        gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 88);
        boolean temp = true;
        for(int i = -2; i < 3; i++) for (int j = -2; j < 3; j++)
            if(i*i + j*j > 1) {
                temp = false;
                try{
                    gus.move(new Position(i, j));
                } catch (IllegalArgumentException e){
                    temp = true;
                } catch (Error e) {
                    fail("Strange error from out of bounds move.");
                }
                assertTrue(temp, "No IllegalArgumentException thrown for attempting to move out of bounds!");
            }
        Position[] positions = {new Position(-1, 0), new Position(1, 0), new Position(0, 1), new Position(0, 1), new Position(0, 0)};
        for (int i = 0; i < 5; i++) {
            gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 18);
            gus.move(positions[i]);
            gus.move(positions[4]);//back to start
            if (EvolutionStage.ENTANGLED == gus.getEvolutionStage()) break;//if entangled after 0, 0 movement
            gus.eat(new Fruit(GameColors.BLUE));
            gus.move(positions[i]);
            assertEquals(EvolutionStage.ENTANGLED, gus.getEvolutionStage(), "Failed to go to ENTANGLED stage after moving head from (0,0) to " + positions[i]);
        }// test entanglement (5 possible locations), error thrown elsewise
        //longer ENTANGLED test now
        for (int K = -1; K < 2; K++){
            gus = new Caterpillar(new Position(0, -1), GameColors.GREEN, 1800);
            for (int i = 0; i < 100; i++) {
                gus.move(new Position(0, i));
                gus.eat(new Fruit((i%2==0)?GameColors.ORANGE:GameColors.RED));
            }//101 segments long now, stretching from 0, -1 to 0, 99
            gus.move(new Position(1, 99));
            gus.move(new Position(2, 99));//+2 pos in stack
            for (int i = 1; i < 40; i++) gus.move(new Position(2, 99 - i));//ends up at 2,60, +39 positions in stack
            gus.move(new Position(1, 60)); //+ 1 pos in stack
            for (int i = 0; i < 20; i++) gus.move(new Position(1, 60 + i + 1));//ends at 1,80, +20 positions in stack (total of 62)
            assertEquals(EvolutionStage.FEEDING_STAGE, gus.getEvolutionStage(), "Not in FEEDING_STAGE for some reason, after lots of non-colliding movement.");
            gus.move(new Position(1 + K, 80));//+1 position in stack, total of 63
            assertEquals(EvolutionStage.ENTANGLED, gus.getEvolutionStage(), "Not ENTANGLED searching far back into the list.");
        }
        for(int i = 60; i > -2; i--) temp = temp && gus.positionsPreviouslyOccupied.pop().equals(new Position(0, i));
        //issues here with checking, bc previously I did not return after ENTANGLING so I moved, which screwed up my test
        assertTrue(temp, "positionsPreviouslyOccupied did not update properly...");
        gus = new Caterpillar(new Position(0,0), GameColors.GREEN, 180);
        positions = new Position[]{new Position(0, 1), new Position(1, 1), new Position(1, 0), new Position(1, -1), new Position(0, -1), new Position(-1, -1), new Position(-1, 0), new Position(-1, 1), new Position(0, 1), new Position(0, 0)};
        for (Position position : positions) gus.move(position);
        for(int i = positions.length - 2; i > -1; i--) temp = temp && positions[i].equals(gus.positionsPreviouslyOccupied.pop());
        assertTrue(temp, "positionsPreviouslyOccupied did not update properly...");
//testing moving into where the tail currently is — SHOULD ENTANGLE!!!
        gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 898);
        gus.move(new Position(1, 0));
        gus.eat(new Fruit(GameColors.RED));
        gus.move(new Position(1, 1));
        gus.eat(new Fruit(GameColors.ORANGE));
        gus.move(new Position(0, 1));
        gus.eat(new Fruit(GameColors.RED));
        assertEquals(EvolutionStage.FEEDING_STAGE, gus.getEvolutionStage(), "Somehow entangled despite never overlapping any square ever");
        gus.move(new Position(0, 0));
        assertEquals(EvolutionStage.ENTANGLED, gus.getEvolutionStage(), "Moving into your tail should create an ENTANGLED state.");
    }

    @Test @DisplayName("eat Methods")
    @Tag("score:0")
    public void eating(){
        //fruit has already been tested but we're testing it again!
        Caterpillar gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 100){
            public String toString() {
                StringBuilder gus = new StringBuilder();
                for (Segment s : this) gus.insert(0, s.toString());
                return gus.toString();
            }
        };
        gus.move(new Position(1, 0));//new head position
        gus.eat(new Fruit(GameColors.ORANGE));//goes to tail
        gus.move(new Position(1, 1));//new head position
        gus.eat(new Fruit(GameColors.BLUE));//goes to tail
        String target = SITS(0, 0, c.b) + SITS(1, 0, c.o) + SITS(1, 1, c.g);
        assertEquals(target, gus.toString(), "something is wrong with your colors after eating fruits!");

        gus.positionsPreviouslyOccupied.clear();
        gus.move(new Position(2, 1));
        gus.eat(new Pickle());
        assertEquals(target, gus.toString(), "Eating a pickle after 1 movement does not put you back exactly how you were.");
        //pickle testing done! not much to do here... technically we just compared whole caterpillars though so eh

        for(int i = 1; i < 9; i++) gus.move(new Position(2, i));//ends up at 2,8
        Position oldhead = new Position(gus.getHeadPosition().getX(), gus.getHeadPosition().getY()),
                oldtail = new Position(gus.tail.element.getPosition().getX(), gus.tail.element.getPosition().getY());
        String[] segments = new String[gus.length];
        Iterator<Segment> iter = gus.iterator();
        Segment TEMP = iter.next();//head position
        segments[0] = SITS(TEMP.getPosition().getX(), TEMP.getPosition().getY(), c.g);//we know head is green because I wrote this program...
        int index = 1;
        while(iter.hasNext()) segments[index++] = iter.next().toString();
        target = "";
        for(int i = 0; i < segments.length - 1; i++) target += segments[i];
        target += (new Segment(new Position(gus.tail.element.getPosition().getX(), gus.tail.element.getPosition().getY()), GameColors.BLUE)).toString();

        gus.eat(new IceCream());

        assertEquals(target, gus.toString(), "gus was not reversed properly!");
        assertTrue(gus.positionsPreviouslyOccupied.isEmpty(), "positionsPreviouslyOccupied does not get cleared on eating IceCream!");
        assertEquals(oldtail, gus.getHeadPosition(), "New head is not where the tail used to be!");
        assertEquals(oldhead, gus.tail.element.getPosition(), "New tail is not where old head used to be!");
        //IceCream testing done!
        for(int K = 0; K < 2; K++) {//test two cases, odd length even length
            gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 100) {
                public String toString() {
                    StringBuilder gus = new StringBuilder();
                    for (Segment s : this) gus.insert(0, s.toString());
                    return gus.toString();
                }
            };
            gus.move(new Position(0, 1));
            gus.eat(new Fruit(GameColors.YELLOW));
            gus.move(new Position(0, 2));
            gus.eat(new Fruit(GameColors.RED));
            gus.move(new Position(0, 3));
            gus.eat(new Fruit(GameColors.BLUE));
            gus.move(new Position(1, 3));
            gus.eat(new Fruit(GameColors.ORANGE));
            gus.move(new Position(2, 3));
            gus.eat(new Fruit(GameColors.YELLOW));
            gus.move(new Position(2, 2));
            if(K == 1) gus.eat(new Fruit(GameColors.RED));
            gus.positionsPreviouslyOccupied.clear();//to allow reuse of tests, so they are aligned at the head (allows for "addition" of a trailing segment in one but not the other)
            //first half ROUNDED UP of segments will be kept
            MyStack<Position> key = new MyStack<>();
            for(int i = 1; i < 4; i++) key.push(new Position(0, i - K));
            target = SITS(1, 3, c.o) + SITS(2, 3, c.r) + SITS(2, 2, c.g);
            if(K == 1) target = SITS(0, 3, c.r) + target;

            gus.eat(new SwissCheese());

            assertEquals(target, gus.toString(), "SwissCheese consumption is not correct! Expected \n" + target + "\nbut got\n" + gus.toString());
            assertEquals(key.toString(), gus.positionsPreviouslyOccupied.toString(), "positionsPreviouslyOccupied not updated after SwissCheese! Expected\n" + key.toString() + "\nbut got\n" + gus.positionsPreviouslyOccupied.toString());
            //SwissCheese testing done!
        }//check colors, positions, and previousPositions SwissCheese testing

        //now lollipop testing begins
        gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 100) {
            public String toString() {
                StringBuilder gus = new StringBuilder();
                for (Segment s : this) gus.insert(0, s.toString());
                return gus.toString();
            }
        };
        gus.move(new Position(0, 1));
        gus.eat(new Fruit(GameColors.YELLOW));
        gus.move(new Position(0, 2));
        gus.eat(new Fruit(GameColors.RED));
        gus.move(new Position(0, 3));
        gus.eat(new Fruit(GameColors.BLUE));
        gus.move(new Position(1, 3));
        gus.eat(new Fruit(GameColors.ORANGE));
        gus.move(new Position(2, 3));
        gus.eat(new Fruit(GameColors.YELLOW));
        gus.move(new Position(2, 2));
        final Color[] cArray = new Color[]{GameColors.GREEN, GameColors.RED, GameColors.BLUE, GameColors.ORANGE, GameColors.YELLOW};
        int[] key = new int[]{1, 1, 1, 1, 2};
        target = SITS(0, 1, c.b) + SITS(0, 2, c.y) + SITS(0, 3, c.y) + SITS(1,3, c.g) + SITS(2, 3, c.r) + SITS(2, 2, c.o);

        gus.eat(new Lollipop());

        iter = gus.iterator();
        Color tempc;
        while(iter.hasNext()) {
            tempc = iter.next().getColor();
            for(int i = 0; i < 5; i++) if(cArray[i].equals(tempc)){
                key[i]--;
                break;
            }
        }
        boolean temp = true;
        for(int i = 0; i < 5; i++) if(key[i] != 0) temp = false;
        assertTrue(temp, "Number of colors is not maintained! (ie started with 3 blues, now have 2 blues after eating Lollipop)");
        assertEquals(target, gus.toString(), "Order of colors after eating Lollipop does not match key! Expected\n" + target + "\nbut got\n" + gus.toString());
        //assuming my version of eat(Lollipop) is correct, since it does match with the minitester... follow the instructions word for word on how to do the color mixing algorithm!!


        //to do NO LONGER Cake test previous position list... if growing from cake it does NOT update bc your segment is there rn, if you can't grow into a segment, etc...
        String[] mk = new String[]{c.r, c.g, c.b, c.y, c.o};
        for(int K = 0; K < 4; K++) {//4 tests—all energy consumed butterfly, all energy consumed no butterfly, not enough positions left, segment in the way
            gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 20){
                public String toString() {
                    StringBuilder gus = new StringBuilder();
                    for (Segment s : this) gus.insert(0, s.toString());
                    return gus.toString();
                }
            };
            gus.move(new Position(0, 1));
            gus.eat(new Fruit(GameColors.YELLOW));
            gus.move(new Position(0, 2));
            gus.eat(new Fruit(GameColors.RED));
            gus.move(new Position(0, 3));
            gus.eat(new Fruit(GameColors.BLUE));
            gus.move(new Position(1, 3));
            gus.eat(new Fruit(GameColors.ORANGE));
            gus.move(new Position(2, 3));
            gus.eat(new Fruit(GameColors.YELLOW));
            gus.move(new Position(2, 2));
            if(K <= 1){//all energy consumed, butterfly or not
                for(int i = 3; i < 101; i++) gus.move(new Position(i, 2));//rgbyo
                if(K == 0) key = new int[]{2, 2, 4, 2, 3,2 ,3, 4, 3, 3, 1, 4, 4, 3, 4, 2, 0, 3, 1};
                else key = new int[]{4, 0, 2, 2, 3, 3, 4, 4, 4, 0, 2, 1, 4, 1, 3, 4, 2, 0, 3, 1};
                target = "";
                for(int i = 0; i < key.length; i++) target += SITS(82 + i - K, 2, mk[key[i]]);
                gus.eat(new Cake(13 + K));//6 + 13 or 14

                assertEquals((K==0)?EvolutionStage.FEEDING_STAGE:EvolutionStage.BUTTERFLY, gus.getEvolutionStage(), "Not at Butterfly stage after meeting goal, or not at feeding stage after not meeting goal! (after eating Cake)");
                assertEquals(target, gus.toString(), "Gus does not match after eating cake! Expected\n" + target + "\nbut got\n" + gus.toString());
                assertEquals(0, gus.turnsNeededToDigest, "turnsNeededToDigest is not 0 after digesting all energy!");
            } else if (K == 2){//not enough positions left so can't consume all energy
                for(int i = 3; i < 7; i++) gus.move(new Position(i, 2));//head at 6, 2
                assertEquals(5, gus.positionsPreviouslyOccupied.getSize(), "prevPositions Stack wrong size (somehow slipped through earlier tests)!");
                final int OLD = gus.getSize();
                key = new int[]{4, 0, 3, 1, 0, 3, 4, 2, 0, 3, 1};
                int[]   X = new int[]{0, 0, 0, 0, 1, 2, 2, 3, 4, 5, 6},
                        Y = new int[]{0, 1, 2, 3, 3, 3, 2, 2, 2, 2, 2};
                target = "";
                for(int i = 0; i < key.length; i++) target += SITS(X[i], Y[i], mk[key[i]]);

                gus.eat(new Cake(7));

                assertEquals(target, gus.toString(), "Incorrect Caterpillar after eating Cake (test for when not enough prevPositions to consume all energy). Expected\n" + target + "\nbut got\n" + gus.toString());
                assertEquals(5, gus.getSize() - OLD, "Somehow you added more or less segments than in the previous positions stack (test is when not enough positions to consume enough energy)!");
                assertEquals(2, gus.turnsNeededToDigest, "Wrong turnsNeededToDigest after running out of positions in stack");
                assertTrue(gus.positionsPreviouslyOccupied.isEmpty(), "prevPositions MyStack isEmpty() is not true after running out of positions after eating Cake");
                assertEquals(EvolutionStage.GROWING_STAGE, gus.getEvolutionStage(), "Caterpillar not in GROWING_STAGE after not fully digesting Cake!");
            } else {//K == 3
                gus.move(new Position(2, 1));
                gus.move(new Position(1, 1));
                gus.move(new Position(0, 1));
                gus.move(new Position(-1, 1));
                key = new int[]{0, 0, 4, 3, 4, 2, 0, 3, 1};
                int[]   X = new int[]{0, 0, 1, 2, 2, 2, 1, 0, -1},
                        Y = new int[]{2, 3, 3, 3, 2, 1, 1, 1,  1};
                target = "";
                for(int i = 0; i < key.length; i++) target += SITS(X[i], Y[i], mk[key[i]]);

                gus.eat(new Cake(6));

                assertEquals(3, gus.turnsNeededToDigest, "Wrong number of turnsNeededToDigest after previousPositions would conflict with body (eat(Cake) method)");
                assertEquals(9, gus.getSize(), "Gus is the wrong size after eating a Cake (growth cut off by overlap with body test)");
                assertEquals(target, gus.toString(), "Gus does not match target (growth cut off by self after eating Cake test). Expected\n" + target + "\nbut got\n" + gus.toString());
                assertEquals(EvolutionStage.GROWING_STAGE, gus.getEvolutionStage(), "Caterpillar not in GROWING_STAGE after not fully digesting Cake!");
            }
            //now we test what happens when still in growing stage!
            if(K == 2){ //K == 2 tests growing stage ends and still no butterfly
                temp = temp && gus.getSize() == 11;
                gus.move(new Position(7, 2));
                temp = temp && gus.getSize() == 12;
                assertEquals(EvolutionStage.GROWING_STAGE, gus.getEvolutionStage(), "Caterpillar exists GROWING_STAGE early!");
                gus.move(new Position(8, 2));
                temp = temp && gus.getSize() == 13;

                target = "";
                key = new int[]{0, 0, 4, 0, 3, 1, 0, 3, 4, 2, 0, 3, 1};
                int[]   X = new int[]{0, 0, 0, 0, 1, 2, 2, 3, 4, 5, 6, 7, 8},
                        Y = new int[]{0, 1, 2, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2};
                for(int i = 0; i < key.length; i++) target += SITS(X[i], Y[i], mk[key[i]]);

                assertTrue(temp, "Gus did not grow properly while moving in GROWING_STAGE!");
                assertEquals(EvolutionStage.FEEDING_STAGE, gus.getEvolutionStage(), "Caterpillar does not return to FEEDING_STAGE after exhausting all excess energy to grow whilst moving after eating cake!");
                assertEquals(target, gus.toString(), "Caterpillar did not grow properly after 3 movements after eating Cake and getting cut off early (digestion). Expected\n" + target + "\nbut got\n" + gus.toString());
                assertTrue(gus.positionsPreviouslyOccupied.isEmpty(), "previousPositions not empty (likely added to Stack despite growing a segment there anyways)  (GROWING_STAGE)");

                //check it does NOT continue growing
                gus.move(new Position(8, 3));
                assertEquals(key.length, gus.getSize(), "Caterpillar kept growing after exhausting all energy from Cake!");
            } else if (K == 3){//K == 3 tests reaching butterfly stage while in growing stage
                gus.goal = 12;
                target = "";
                key = new int[]      {2, 2, 2, 0, 0, 4, 3, 4, 2, 0, 3, 1};
                int[]   X = new int[]{0, 0, 1, 2, 2, 2, 1, 0, -1, -2, -3, -4},
                        Y = new int[]{2, 3, 3, 3, 2, 1, 1, 1,  1,  1,  1,  1};
                for(int i = 0; i < key.length; i++) target += SITS(X[i], Y[i], mk[key[i]]);
                final String INIT = gus.positionsPreviouslyOccupied.toString();

                temp = temp && gus.getSize() == 9;
                gus.move(new Position(-2, 1));
                temp = temp && gus.getSize() == 10;
                gus.move(new Position(-3, 1));
                temp = temp && gus.getSize() == 11;
                assertEquals(EvolutionStage.GROWING_STAGE, gus.getEvolutionStage(), "Caterpillar exists GROWING_STAGE early!");
                gus.move(new Position(-4, 1));
                temp = temp && gus.getSize() == 12;
                assertEquals(target, gus.toString(), "Gus did not grow properly after 3 movements after eating Cake and getting cut off early (digestion). Expected\n" + target + "\nbut got\n" + gus.toString());
                assertTrue(temp, "Gus did not grow once per movement while digesting!");
                assertEquals(EvolutionStage.BUTTERFLY, gus.getEvolutionStage(), "Caterpillar not in BUTTERFLY stage after digesting remainder of energy and reaching goal after eating Cake!");
                assertEquals(INIT, gus.positionsPreviouslyOccupied.toString(), "Stack of previous positions changed (GROWING_STAGE), likely means you added to stack while growing a segment there anyways");
            }
        }
        //SO close....
        gus = new Caterpillar(new Position(0, 0), GameColors.BLUE, 2);
        gus.move(new Position(0, 1));
        assertEquals(EvolutionStage.FEEDING_STAGE, gus.getEvolutionStage(), "Gus is not initialised at FEEDING_STAGE (goal=2)");
        gus.eat(new Fruit(GameColors.RED));
        assertEquals(EvolutionStage.BUTTERFLY, gus.getEvolutionStage(), "Gus does not become a BUTTERFLY after eating Fruit (goal = 2, 2 segments total)");

        //length 1 assertions
        gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 100){
            public String toString() {
                StringBuilder gus = new StringBuilder();
                for (Segment s : this) gus.insert(0, s.toString());
                return gus.toString();
            }
        };

        gus.move(new Position(0, 1));
        gus.eat(new Pickle());
        target = SITS(0, 0, c.g);
        assertEquals(target, gus.toString(), "Caterpillar fails eat Pickle when length 1: Expected\n" + target + "\nbut got\n" + gus);

        gus.move(new Position(0, 1));
        gus.eat(new IceCream());
        target = SITS(0, 1, c.b);
        assertEquals(target, gus.toString(), "Caterpillar fails eat IceCream when length 1: Expected\n" + target + "\nbut got\n" + gus);

        gus.move(new Position(0, 0));
        gus.eat(new SwissCheese());
        target = SITS(0, 0, c.b);
        assertEquals(target, gus.toString(), "Caterpillar fails SwissCheese when length 1: Expected\n" + target + "\nbut got\n" + gus);

        gus.move(new Position(0, 1));
        gus.eat(new Lollipop());
        target = SITS(0, 1, c.b);
        assertEquals(target, gus.toString(), "Caterpillar fails Lollipop when length 1: Expected\n" + target + "\nbut got\n" + gus);

        //length 2 tests
        gus = new Caterpillar(new Position(0, 0), GameColors.GREEN, 100){
            public String toString() {
                StringBuilder gus = new StringBuilder();
                for (Segment s : this) gus.insert(0, s.toString());
                return gus.toString();
            }
        };

        gus.move(new Position(0, 1));
        gus.eat(new Fruit(GameColors.ORANGE));
        gus.move(new Position(0, 2));

        gus.eat(new Pickle());
        target = SITS(0, 0, c.o) + SITS(0, 1, c.g);
        assertEquals(target, gus.toString(), "Caterpillar fails eat Pickle when length 2: Expected\n" + target + "\nbut got\n" + gus);

        gus.move(new Position(0, 2));
        gus.eat(new IceCream());
        target = SITS(0, 2, c.g) + SITS(0, 1, c.b);
        assertEquals(target, gus.toString(), "Caterpillar fails eat IceCream when length 2: Expected\n" + target + "\nbut got\n" + gus);

        gus.move(new Position(1, 1));
        gus.eat(new Lollipop());
        target = SITS(0, 1, c.g) + SITS(1, 1, c.b);
        assertEquals(target, gus.toString(), "Caterpillar fails eat Lollipop when length 2: Expected\n" + target + "\nbut got\n" + gus);

        gus.positionsPreviouslyOccupied.clear();
        gus.move(new Position(1, 2));
        gus.eat(new SwissCheese());
        target = SITS(1, 2, c.b);
        assertEquals(target, gus.toString(), "Caterpillar fails eat SwissCheese when length 2: Expected\n" + target + "\nbut got\n" + gus);
        MyStack<Position> testing = new MyStack<>();
        testing.push(new Position(0, 1));
        testing.push(new Position(1, 1));
        assertEquals(testing.toString(), gus.positionsPreviouslyOccupied.toString(), "Caterpillar's previousPositions stack does not match key aftering eating SwissCheese length 2: Expected\n" + testing + "\nbut got\n" + gus.positionsPreviouslyOccupied);

        gus.move(new Position(2, 2));
        target = SITS(2, 2, c.b);
        gus.eat(new Cake(0));
        assertEquals(1, gus.getSize(), "Caterpillar grows upon consuming Cake with 0 energy!");
        assertEquals(target, gus.toString(), "Caterpillar changes upon eating Cake with 0 energy!\n" + target + "\nbut got\n" + gus);
        assertEquals(EvolutionStage.FEEDING_STAGE, gus.getEvolutionStage(), "Caterpillar stage is not FEEDING_STAGE after eating new Cake(0)!");
        System.out.println("Passed at least 120 assertions!");
    }

    private String SITS(int x, int y, String clr){//segment info to string
        return clr + "(" + x + "," + y + ")" + c.w;
    }
    //NOTE can't eat more while digesting a cake, only eat in feeding stage!
}
