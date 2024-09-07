package assignment2;
import java.util.Iterator;
import java.util.Random;
import java.awt.Color;
import assignment2.food.*;

public class Caterpillar extends MySinglyLinkedList<Segment> {

	public EvolutionStage stage;
	public MyStack<Position> positionsPreviouslyOccupied;
	public int goal;
	public int turnsNeededToDigest;
	public static Random randNumGenerator = new Random(1);

	// Creates a Caterpillar with one Segment.  It is up to students to decide how to implement this. 
	public Caterpillar(Position p, Color c, int goal) {
		this.addFirst(new Segment(p, c));
		this.stage = EvolutionStage.FEEDING_STAGE;
		this.positionsPreviouslyOccupied = new MyStack<>();
		this.goal = goal;
		this.turnsNeededToDigest = 0;
	}

	public EvolutionStage getEvolutionStage() {
		return this.stage;
	}

	public Position getHeadPosition() {
		return ((Segment)this.head.element).getPosition() ;
	}

	// returns the color of the segment in position p. Returns null if such segment does not exist

	public Color getSegmentColor(Position p) {
		for (Segment seg : this) {
			if (seg.getPosition().equals(p)) {
				return seg.getColor();
			}
		}
		return null;
	}

	// shift all segments to the previous position while maintaining the old color
	public void move(Position p) {
		if (Position.getDistance(this.getHeadPosition(), p) != 1) {
			throw new IllegalArgumentException("Move is out of reach");
		}

		for (Segment seg : this) {
			if (seg.getPosition().equals(p)) {
				this.stage = EvolutionStage.ENTANGLED;
				return;
			}
		}

		Position previousPositionOccupied = this.getHeadPosition();
		Position temporaryPositionOccupied;
		boolean isFirstSegment = true;
		for (Segment seg : this) {
			if (isFirstSegment) {
				isFirstSegment = false;
				continue;
			}
			temporaryPositionOccupied = seg.getPosition();
			seg.setPosition(previousPositionOccupied);
			previousPositionOccupied = temporaryPositionOccupied;
		}

		// Set the new position for the head
		this.head.element.setPosition(p);

		// Push the previous tail position onto the stack
		this.positionsPreviouslyOccupied.push(previousPositionOccupied);

		// Handle growth if digesting a cake
		if (this.turnsNeededToDigest > 0) {
			growSegment();
			this.turnsNeededToDigest--;
			if (this.getSize() >= this.goal) {
				this.stage = EvolutionStage.BUTTERFLY;
			}
		} else if (this.stage == EvolutionStage.GROWING_STAGE && this.turnsNeededToDigest == 0) {
			this.stage = EvolutionStage.FEEDING_STAGE;
		}
	}

	private void growSegment() {
		if (!this.positionsPreviouslyOccupied.isEmpty()) {
			Position newPos = this.positionsPreviouslyOccupied.peek();
			int colorIndex = randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length);
			Color newColor = GameColors.SEGMENT_COLORS[colorIndex];
			this.addLast(new Segment(newPos, newColor));
		}
	}

	// a segment of the fruit's color is added at the end
	public void eat(Fruit f) {
		if (this.stage != EvolutionStage.FEEDING_STAGE) {
			throw new IllegalArgumentException("Caterpillar can only eat in FEEDING_STAGE");
		}
		Position newPos = this.positionsPreviouslyOccupied.peek();
		this.addLast(new Segment(newPos, f.getColor()));
		if (this.length >= this.goal) {
			this.stage = EvolutionStage.BUTTERFLY;
		}
	}
 
	// the caterpillar moves one step backwards because of sourness
	public void eat(Pickle p) {
		if (this.stage != EvolutionStage.FEEDING_STAGE) {
			throw new IllegalArgumentException("Caterpillar can only eat in FEEDING_STAGE");
		}
		if (this.positionsPreviouslyOccupied.isEmpty()) {
			return;
		}

		Position newHeadPosition = this.positionsPreviouslyOccupied.pop();
		Segment oldHead = this.removeFirst();
		oldHead.setPosition(newHeadPosition);
		this.addLast(oldHead);

		if (this.turnsNeededToDigest > 0) {
			this.turnsNeededToDigest--;
		}
		if (this.turnsNeededToDigest == 0 && this.stage != EvolutionStage.BUTTERFLY) {
			this.stage = EvolutionStage.FEEDING_STAGE;
		}
	}

	// all the caterpillar's colors are shuffled around
	public void eat(Lollipop lolly) {
		if (this.stage != EvolutionStage.FEEDING_STAGE) {
			throw new IllegalArgumentException("Caterpillar can only eat in FEEDING_STAGE");
		}
		Color[] colors = new Color[this.length];
		int i = 0;
		for (Segment seg : this) {
			colors[i++] = seg.getColor();
		}
		for (i = colors.length - 1; i > 0; i--) {
			int j = randNumGenerator.nextInt(i + 1);
			Color temp = colors[i];
			colors[i] = colors[j];
			colors[j] = temp;
		}
		i = 0;
		for (Segment seg : this) {
			seg.setColor(colors[i++]);
		}
	}

	// brain freeze!!
	// It reverses and its (new) head turns blue
	public void eat(IceCream gelato) {
		if (this.stage != EvolutionStage.FEEDING_STAGE) {
			throw new IllegalArgumentException("Caterpillar can only eat in FEEDING_STAGE");
		}
		reverseList();
		this.head.element.setColor(GameColors.BLUE);
		this.positionsPreviouslyOccupied.clear();
	}
	private void reverseList() {
		SNode prev = null;
		SNode current = this.head;
		SNode next = null;
		while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		SNode temp = this.head;
		this.head = this.tail;
		this.tail = temp;
	}

	// the caterpillar embodies a slide of Swiss cheese loosing half of its segments. 
	public void eat(SwissCheese cheese) {
		if (this.stage != EvolutionStage.FEEDING_STAGE) {
			throw new IllegalArgumentException("Caterpillar can only eat in FEEDING_STAGE");
		}
		if (this.getSize() == 1){
			return;
		}
		Color[] colorsUsed = new Color[this.getSize()];
		int n = 0;
		for (Segment seg: this) {
			colorsUsed[n] = getSegmentColor(seg.getPosition());
			n++;
		}

		int keptPositions = (this.getSize()+2-1)/2;
		int removedPositionsNumber = this.getSize() - keptPositions;

		for(int j = 0; j < removedPositionsNumber; j++) {
			positionsPreviouslyOccupied.push(this.peekLast().getPosition());
			this.removeLast();
		}
		int m = 0;
		for (Segment seg: this) {
			seg.setColor(colorsUsed[m]);
			m+=2;
		}
	}

	// A big growing stage begins
	public void eat(Cake cake) {
		if (this.stage != EvolutionStage.FEEDING_STAGE) {
			throw new IllegalArgumentException("Caterpillar can only eat in FEEDING_STAGE");
		}
		int energy = cake.getEnergyProvided();
		while (energy > 0 && !this.positionsPreviouslyOccupied.isEmpty()) {
			Position newPos = this.positionsPreviouslyOccupied.pop();
			int colorIndex = randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length);
			Color newColor = GameColors.SEGMENT_COLORS[colorIndex];
			this.addLast(new Segment(newPos, newColor));
			energy--;
		}
		this.turnsNeededToDigest = cake.getEnergyProvided() - energy;
		if (this.turnsNeededToDigest == 0 && this.stage != EvolutionStage.BUTTERFLY) {
			this.stage = EvolutionStage.FEEDING_STAGE;
		}
	}

 	public String toString() {

 		String gus = "Gus: " ;
 		Iterator i = this.iterator() ;

 		while ( i.hasNext() ) {
			Segment s = (Segment) i.next() ;
			gus = s.toString() + gus ;
 		}
		return gus;
 	}
}