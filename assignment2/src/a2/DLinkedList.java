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
    	// TODO item #1
        // Look at the class invariant to determine how to implement this.
    	
    	// Initialization of int size, Node head and Node tail.
    	this.size = 0;
    	this.head = null;
    	this.tail = null;
    }

    /**
     * Return the number of elements in this list.
     * This operation must take constant time.
     */
    public @Override int size() {
    	// TODO item #2
        // This is an extremely small method
    	
    	// returns the private int size
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
    	// TODO item #3
        // This should use field tail and the pred fields in nodes.
        // Do NOT use field size.
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
    	
    	if (this.size == 0) { 			// If the DLL is empty,
    		this.head = new_tail;		// Set this DLL's head to be the new_tail
    	} else {						// Otherwise, we then know that this DLL is not empty, and we can then
        	this.tail.succ = new_tail;	// set the successor of the old tail to be the new tail
    	}
    	
    	// Sets the tail of this DLL as the new tail
    	this.tail = new_tail;
    	// Increases the size of this DLL by 1.
    	this.size += 1;
    	return new_tail;
    }
    
    /** Append element to the end of this list and return true. */
    public @Override boolean add(E element) {
        // TODO item #5
        // Rely on helper methods to keep this method small
        // This is THE MOST IMPORTANT method to get right because it will be used
        // in nearly every test
    	
    	// Use append() method to add element to the right end of this list
    	this.append(element);
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
    	
    	// Initialization of useful variable irev (used to determine which end the index is closer to)
    	int irev = size - index - 1;
    	// Assertion that there is a node in DLL with the index of int index.
    	if (irev < 0 || index < 0){ 				
    		throw new IndexOutOfBoundsException();	// Throw an exception if index is not inbounds
    	} 
    	Node node_out = null;
    	if (irev > index) {  					// If index is closer to head, start from head
    		Node n = this.head;
    		for (int j = 0; j < index; j++) {	// Cycle through each Node until the Node of index index is reached
    			n = n.succ;
    		}
    		node_out = n;
    	} else { 								// Else, start from tail
    		Node n = this.tail;
			for (int j = 0; j < irev; j++) {	// Cycle through each Node until the Node of index index is reached
    			n = n.pred;
    		}
			node_out = n;
    	}
    	
    	// Returns the Node at index index.
        return node_out;
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
    	
    	// Uses the getNode method to return the Node at the given index.
    	return this.getNode(index).data;
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
    	
    	// Uses get method to find the data in the desired node
    	E out = this.get(index);
    	// Uses getNode method to change the data in the desired node
    	this.getNode(index).data = element;
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
    		this.tail = new_head;	// Set this DLL's tail to be new_head as well
    	} else {					// Otherwise, we know this DLL is not empty, and therefore we can then
    	this.head.pred = new_head;	// set the predecessor of the old head to be the new head
    	}
    	
    	// Sets the head of this DLL as the new head
    	this.head = new_head;
    	
    	// Increases the size of this DLL by 1;
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
    	
    	// Assert that the node to add is not null
    	assert node != null;
    	// Create a new node, new_node with the predecessor of new_node being the predecessor of node, data is element,
    	// successor is node.
    	Node new_node = new Node(node.pred, element, node);
    	// Set the successor of the Node preceding node to be new_node
    	node.pred.succ = new_node;
    	// Set the preceding Node of node to be new_node
    	node.pred = new_node;
    	// Increase the size of this DLL by 1.
    	this.size += 1;
    	return new_node;
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
    	
    	// Create a node node_after to be used in the insertBefore method.
    	Node node_after = this.getNode(index);
    	// Use node_after to insert a new node of data element into this DLL.
    	this.insertBefore(element, node_after);

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
    	
    	
    	// One side note: the next 14 lines of code are unnecessary if removeNode is used exclusively as a
    	// helper method for the below remove method, but in the case that one wants to use this method  
    	// directly, these 14 lines ensure that there are no errors.
    	
    	// Asserting that the node n is non-null
        assert n != null;
        // Determining if the node n is in this DLL
        boolean nodeInL = false; 		
        for (int i =0; i < size; i++) {	// For each possible index of this DLL,
        	if (n == this.getNode(i)) {	// If Node n is equal to this.getNode for a specific index,
        		nodeInL = true;			// change nodeInL to be true.
        	}
        }
        if (nodeInL == false) {			// If Node n is not in the DLL, 
        	// Print that the given node for removeNode is not in this DLL
        	System.out.println("Node is not in DLL for removeNode");
        	// Throw an IndexOutOfBoundsException
        	throw new IndexOutOfBoundsException();
        }
        
        // We now know that the node is non-null and in the DLL at index n_loc;
        // Find the preceding and succeeding node using 
       
        if (n.pred != null) {			// If n is not the first node in the list, 
        	n.pred.succ = n.succ;		// Set the preceding node's successor to be the successor of n.
        } else {						// If n is the first node in the list (i.e. n is the head of this list),
        	this.head = n.succ;			// Set the successor of n as the head of this list.
        }
        
        if (n.succ != null) {			// If n is not the last node in the list,
        	n.succ.pred = n.pred;		// Set the succeeding node's predecessor to be the predecessor of n.
        } else {						// If n is the last node in the list (i.e. n is the tail of this list),
        	this.tail = n.pred;			// Set the tail of this list as the predecessor of n.
        }
        
        // Set the predecessor and successor of n to be null to remove it from the list fully (ensuring no errors).
        n.succ = null; n.pred = null;
        
        // Returns the element stored in Node n.
        return n.data;
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
    	
    	// Use getNode method to determine what Node to remove
    	Node n = this.getNode(i);
    	// Remove Node n from the DLL, set E to be the data of node.
    	E data = this.removeNode(n);
    	// Return the data in the removed node.
        return data;
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
            // Testing that the invariant type remains the same for each node.
            for (int k = 1; k < list.size; k++) {
            	assertEquals(list.get(k).getClass(), list.get(k-1).getClass());
            }
        }

        @Test
        public void testAppend() {
            DLinkedList<String> ll     = new DLinkedList<String>();
            DLinkedList<String>.Node n = ll.append("Mike");
            assertEquals("[Mike]", ll.toString());
            assertEquals("[Mike]", ll.toStringRev());
            assertEquals(1, ll.size());
            assertEquals(ll.tail, n);   
            assertInvariants(ll);
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
            assertInvariants(dll);
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
        	assertInvariants(dll);
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
        	assertInvariants(dll);
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
        	assertInvariants(dll);
        }
        
        /** Testing the getNode method */
        @Test
        public void testGetNode() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	dll.add(3);
        	// Asserting that getNode method gives the correct node added
        	assertEquals(dll.getNode(0).data, 3);
        	assertEquals(dll.getNode(0).pred, null);
        	assertEquals(dll.getNode(0).succ, null);
        	dll.add(5); dll.add(9); dll.add(-4); dll.add(7);
        	// Asserting that getNode method gives the correct node when starting from the head
        	assertEquals(dll.getNode(1).data, 5);
        	assertEquals(dll.getNode(1).pred, dll.getNode(0));
        	assertEquals(dll.getNode(1).succ, dll.getNode(2));
        	// Asserting that getNode method gives the correct node when starting from the tail
        	assertEquals(dll.getNode(3).data, -4);
        	assertEquals(dll.getNode(3).pred, dll.getNode(2));
        	assertEquals(dll.getNode(3).succ, dll.getNode(4));
        	// Asserting that an index out of bounds will throw an error
        	try {
        		dll.getNode(6);
        	} catch (IndexOutOfBoundsException e) {
        		System.out.println("testGetNode index out of bounds exception");
        	}
        	assertInvariants(dll);
        }
        
        /** Testing the prepend method */
        @Test
        public void testPrepend() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	// Test that prepend method works on an empty dll
        	dll.prepend(2);
        	//Add some integers to the DLinkedList
        	dll.add(3); dll.add(9); dll.add(-4); dll.add(7);
        	//Assert that using prepend(4) will insert a 4 at the front of the DLinkedList, which will then return [4, 2, 3, 9, -4, 7]
        	dll.prepend(4);
        	assertEquals(dll.toString(), "[4, 2, 3, 9, -4, 7]");
        	assertInvariants(dll);
        }
        
        /** Testing the add method override */
        @Test
        public void testAddOverride() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	//Add some integers to the DLinkedList
        	dll.add(3); dll.add(9); dll.add(-4); dll.add(7);
        	//Assert that putting a 5 before index 3 will return [3, 9, -4, 5, 7] 
        	dll.add(3, 5);
        	assertEquals(dll.toString(), "[3, 9, -4, 5, 7]");
        	//Try-catch to test error cases
        	try {
        		dll.add(6, 5);
        	} catch (IndexOutOfBoundsException e) {
        		System.out.println("testAddOverride index out of range");
        	}
        	assertInvariants(dll);
        }
        
        /** Testing the insertBefore method */
        @Test
        public void testInsertBefore() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	//Add some integers to the DLinkedList
        	dll.add(3); dll.add(9); dll.add(-4); dll.add(7);
        	dll.insertBefore(2, dll.getNode(2));
        	assertEquals(dll.toString(), "[3, 9, 2, -4, 7]");
        	assertEquals(dll.get(2), 2);
        	// Asserting that a null node will return an error
        	try {
        		dll.insertBefore(2,  null);
        	} catch (AssertionError e) {
        		System.out.println("insertBefore cannot insert before a null node");
        	}
        	assertInvariants(dll);
        }
        
        /** Testing the removeNode method */
        @Test
        public void testRemoveNode() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	// Add some integers to the DLinkedList
        	dll.add(3); dll.add(9); dll.add(-4); dll.add(7);
        	DLinkedList<Integer>.Node n = dll.getNode(2);
        	// Asserting that removeNode removes the correct node.
        	int data = dll.removeNode(n);
        	assertEquals(dll.toString(), "[3, 9, 7]");
        	assertEquals(data, -4);
        	// Asserting that removeNode will not run on a null node
        	try {
        		dll.removeNode(null);
        	} catch (AssertionError e) {
        		System.out.println("removeNode will not remove a null node");
        	}
        	// Asserting that removeNode will not run on a node not in dll
        	try {
        		dll.removeNode(n);
        	} catch (IndexOutOfBoundsException e) {
        		System.out.println("removeNode node given is not in the dll");
        	}
        	assertInvariants(dll);
        }
        
        /** Testing the remove method */
        @Test
        public void testRemove() {
        	DLinkedList<Integer> dll = new DLinkedList<Integer>();
        	// Add some integers to the DLinkedList
        	dll.add(3); dll.add(9); dll.add(-4); dll.add(7);
        	dll.remove(0);
        	assertEquals(dll.toString(), "[9, -4, 7]");
        	// Asserting that remove will not run if index is out of bounds of dll
        	try {
        		dll.remove(-1);
        	} catch (IndexOutOfBoundsException e) {
        		System.out.println("remove index is out of bounds");
        	}
        	assertInvariants(dll);
        }
        
    }

}
