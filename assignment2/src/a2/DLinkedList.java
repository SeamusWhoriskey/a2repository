package a2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * An instance is a doubly linked list. It provides much of the functionality
 * of Java class java.util.LinkedList.
 */
public class DLinkedList<E> extends java.util.AbstractList<E> {
    /** Number of nodes in the linked list. */
    private int size;

    /** first node of the linked list (null if the list is empty) */
    private Node head;

    /** last  node of the linked list (null if the list is empty) */
    private Node tail;
    
    /** Constructor: an empty linked list. */
    public DLinkedList() {
    	this.size = 0;
    	this.head = null;
    	this.tail = null;
    }

    /**
     * Return the number of elements in this list.
     * This operation must take constant time.
     */
    public @Override int size() {
    	return this.size;
    }

    /**
     * Return "[s0, s1, .., sn]" where (s0, s1, .., sn) are strings representing
     * the objects contained in this, in order.  Uses toString to convert the
     * objects to strings.
     *
     * For example, if this contains 6 3 8 in that order, the result is "[6, 3, 8]".
     */
    public @Override String toString() {
        String res= "[";
        // invariant: res = "[s0, s1, .., sk" where sk is the object before node n
        for (Node n = head; n != null; n= n.succ) {
            if (n != head)
                res= res + ", ";
            res= res + n.data;
        }
        return res + "]";
    }

    /**
     * Return "[sn, .., s1, s0]" where (s0, s1, .., sn) are strings representing
     * the objects contained in this, in order.  Uses toString to convert the
     * objects to strings.
     *
     * For example, if this contains 6 3 8 in that order, the result is "[8, 3, 6]".
     */
    public String toStringRev() {
    	String res= "[";
        // invariant: res = "[s0, s1, .., sk" where sk is the object after node n
        for (Node n = tail; n != null; n= n.pred) {
            if (n != tail)
                res= res + ", ";
            res= res + n.data;
        }
        return res + "]";
    }
    
    /**
     * Place element in a new node at the end of the list and return the new node.
     * This operation must take constant time.
     */
    private Node append(E element) {
        // TODO item #4
        // This mid-size helper function will be used by other methods
    	
    	// Initializes the new tail as new_tail with this.tail as the predecessor, data of element e,  and no successor.
    	Node new_tail = new Node(this.tail, element, null);
    	
    	if (this.size == 0) { 		// If the DLL is empty;
    		// Set this DLL's head to be the new_tail
    		this.head = new_tail;
    	} else {
    		// Sets the successor of the old tail to be the new tail
        	this.tail.succ = new_tail;
    	}
    	
    	// Sets the tail of the DLL as the new tail
    	this.tail = new_tail;
    	// Increases the size of the DLL by 1.
    	this.size += 1;
    	return new_tail;
    }
    
    /** Append element to the end of this list and return true. */
    public @Override boolean add(E element) {
        // TODO item #5
        // Rely on helper methods to keep this method small
        // This is THE MOST IMPORTANT method to get right because it will be used
        // in nearly every test
    	
    	// Use append() method to add element to the end of this list
    	append(element);
        return true;
    }
    
    /**
     * Return the Node at the given index of this list.
     * Takes time proportional to min(index, size - index).
     *
     * @param index the index of the node, in [0..size).
     *              0 is the first element, 1 is the second, etc.
     * @throws IndexOutOfBoundsException if index is not in [0..size)
     */
    private Node getNode(int index) {
        // TODO item #6
        // This large helper method is used more than any other helper method
        // It is used by other public methods or for testing.
        // Note that there are two ways to get to a node: from the head or from the tail.
        // This MUST use the fastest way for index.
        // (If h is exactly the middle, then either way is ok.)
    	
    	// Initialization of useful variable irev (used to determine which end node is closer to)
    	int irev = size - index - 1;
    	
    	// Assertion that there is a node in DLL of index index.
    	if (irev < 0){ 				
    		throw new IndexOutOfBoundsException();
    	} 
    	Node n = this.head;
    	if (irev > index) {  		// If index is closer to head, start from head
    		for (int j = 0; j < index; j++) {
    			n = n.succ;
    		}
    	} else { 					// Else, start from tail
			for (int j = 0; j < irev; j++) {
    			n = n.pred;
    		}
    	}
        return n;
    }
    
    /**
     * Return the Node at the given index of this list.
     * Takes time proportional to min(index, size - index).
     *
     * @param index the index of the node, in [0..size).
     *              0 is the first element, 1 is the second, etc.
     * @throws IndexOutOfBoundsException if index is not in [0..size)
     */
    public @Override E get(int index) {
        // TODO item #7
        // Rely on helper methods to keep this method small.
        // Note that the helper method could throw the exception; doesn't
        // have to be done here.
    	return getNode(index).data;
    }
    
    /**
     * Replace the element at the given index of this list with e.
     * Takes time proportional to min(index, size - index).
     *
     * @param index the index of the node, in [0..size).
     *              0 is the first element, 1 is the second, etc.
     * @param e     the new value to place in the list
     * @return      the former value stored at the index
     * @throws IndexOutOfBoundsException if index is not in [0..size)
     */
    public @Override E set(int index, E element) {
        // TODO item #8
        // Rely on helper methods to keep this method small.
        // Note that a helper method could throw the exception; doesn't
        // have to be done here.
    	E out = getNode(index).data;
    	getNode(index).data = element;
    	return out;
    }
    
    /**
     * Insert element in a new node at the beginning of the list and return the
     * new node.
     * Runs in constant time.
     */
    private Node prepend(E element) {
        // TODO item #9
        // This mid-size helper function will be used by other methods
    	
    	// Initializes the new head as new_head with no predecessor, data of element e, and successor being the current head.
    	Node new_head = new Node(null, element, this.head);
    	
    	if (this.size == 0) { 		// If the DLL is empty;
    		// Set this DLL's tail to be new_head as well
    		this.tail = new_head;
    	}
    	// Sets the predecessor of the old head to be the new head
    	this.head.pred = new_head;
    	// Sets the head of the DLL as the new head
    	this.head = new_head;
    	
    	// Increases the size of the DLL by 1;
    	this.size += 1;
    	return new_head;
    }
    
    /**
     * Insert element into a new node before Node node and return the new node.
     * Takes constant time.
     *
     * @param element the element to insert
     * @param node a non-null Node that must be in this list
     */
    private Node insertBefore(E element, Node node) {
        // TODO item #10
        // This mid-size helper function will be used by other methods.
        // Do NOT test whether node is actually a Node of this list because
        // it will then not be a constant-time operation.
    	assert node != null;
    	Node node_before = node.pred;
    	Node new_node = new Node(node_before, element, node);
    	node_before.succ = new_node;
    	node.pred = new_node;
    	this.size += 1;
    	return node;
    }
    
    /**
     * Insert e into this list at position i.
     * The element currently at index i, as well as all later elements, are
     * shifted down to make room for element.
     * Takes time proportional to min (index, size - index).
     *
     * @param e the element to insert
     * @param i the place to put e, in [0..size] (note: i == size is allowed!)
     * @throws IndexOutOfBoundsException if i is not in [0..size]
     */
    public @Override void add(int index, E element) {
        // TODO item #11
        // Rely on helper methods to keep this method small.
        // Note that a helper method could throw the exception; doesn't
        // have to be done here.
    	
    	Node node_after = getNode(index);
    	insertBefore(element, node_after);

    }
    
    /**
     * Remove n from this list and return its data.
     *
     * @param  n the node to remove.  It must be in this list and non-null.
     * @return the data inside of n
     */
    private E removeNode(Node n) {
        // TODO item #12
        // This is a large helper method
        throw new NotImplementedError();  	    	
    }
    
    /**
     * Remove and return the element at index i.
     * Takes time proportional to min(i, size - i).
     *
     * @param i the index of the element to remove, in [0..size).
     *          0 is the first element, 1 is the second, etc.
     * @return the removed element
     * @throws IndexOutOfBoundsException if i is not in [0..size)
     */
    public @Override E remove(int i) {
        // TODO item #13
        // Rely on helper methods to keep this method small.
        // Note that a helper method could throw the exception; doesn't
        // have to be done here.
        throw new NotImplementedError();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    /** An instance is a node of this list. */
    private class Node {
        /** Predecessor of this node on list (null if this is the first node). */
        private Node pred;
        
        /** The data in this element. */
        private E data;
        
        /** Successor of this node on list. (null if this is the last node). */
        private Node succ;
        
        /** Constructor: an instance with predecessor node p (can be null),
          * successor node s (can be null), and data e. */
        private Node(Node p, E e, Node s) {
            this.pred = p;
            this.succ = s;
            this.data = e;
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * Glass-box tests for DLinkedList.  Since this is an inner
     * class, it has access to DLinkedList's private types, fields, and methods.
     */
    public static class Tests {

        /**
         * Asserts that list satisfies its invariants.  It is useful to call
         * this after any tests that modify a linked list to ensure that the
         * list remains well-formed.
         *
         * @throws AssertionFailedError if the list is not well-formed
         */
        private static void assertInvariants(DLinkedList<?> list) {
            throw new NotImplementedError();
        }

        @Test
        public void testAppend() {
            DLinkedList<String> ll     = new DLinkedList<String>();
            DLinkedList<String>.Node n = ll.append("Mike");
            assertEquals("[Mike]", ll.toString());
            assertEquals("[Mike]", ll.toStringRev());
            assertEquals(1, ll.size());
            assertEquals(ll.tail, n);   
        }

        /** Compare DLinkedList to standard library list */
        @Test
        public void testToString() {
            List<Integer>  ll = new java.util.LinkedList<Integer>();
            List<Integer> dll = new DLinkedList<Integer>();
            
            assertEquals(dll.toString(), ll.toString());

            dll.add(5); ll.add(5);
            assertEquals(dll.toString(), ll.toString());
            
            dll.add(4); ll.add(4);
            assertEquals(dll.toString(), ll.toString());
        }
        
        /** Testing toStringRev method */
        @Test
        public void testToStringRev() {
            DLinkedList<Integer> dll = new DLinkedList<Integer>();
            dll.add(4); 
            dll.add(5);
            assertEquals(dll.toStringRev(), "[5, 4]");
        }
        
        /** Testing get method */
        @Test
        public void testGet() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	dll.add(4);
        	assertEquals(dll.get(0), 4);
        	try {
        		dll.get(1);
        	} catch (IndexOutOfBoundsException e) {
        		System.out.println("testGet index out of range");
        	}
        }
        
        /** Testing the set method */
        @Test
        public void testSet() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	dll.add(4);
        	dll.set(0,8);
        	assertEquals(dll.get(0), 8);
        	try {
        		dll.set(1,2);
        	} catch (IndexOutOfBoundsException e) {
        		System.out.println("testSet index out of range");
        	}
        }
 
        /** Testing the add method */
        @Test
        public void testAdd() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	//Add one number to the DLinkedList
        	dll.add(3);
        	//Assert that adding a 3 to an empty DLinkedList returns [3] 
        	assertEquals(dll.toString(), "[3]");
        	//Check if add will extend the DLinkedList as specified
        	dll.add(9);
        	dll.add(11);
        	assertEquals(dll.toString(), "[3, 9, 11]");
        }        
        
        /** Testing the prepend method */
        @Test
        public void testPrepend() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	//Add some indexes to the DLinkedList
        	dll.add(3); dll.add(9); dll.add(-4); dll.add(7);
        	//Assert that putting a 4 in front of the DLinkedList will return [4, 3, 9, -4, 7]
        	assertEquals(dll.prepend(4), "[4, 3, 9, -4, 7]");
        	//Try-catch to test error cases
        	try {
        		dll = null;
        		dll.prepend(-1);
        	} catch (IndexOutOfBoundsException e) {
        		System.out.println("testPrepend index out of range");
        	}
        }
        
        /** Testing the insertBefore method */
        @Test
        public void testInsertBefore() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	//Add some indexes to the DLinkedList
        	dll.add(3); dll.add(9); dll.add(-4); dll.add(7);
        	//Assert that putting a 5 before index 3 will return [3, 9, -4, 5, 7] 
        	System.out.println(dll);
        	//assertEquals(dll.insertBefore(3), "[3, 9, -4, 5, 7]");
        	//Try-catch to test error cases
        	try {
        	//	dll.insertBefore(,);
        	} catch (IndexOutOfBoundsException e) {
        		System.out.println("testSet index out of range");
        	}
        }
       
        
        
        
    }
}
