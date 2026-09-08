# DSA Revision — Stack, Queue, Deque & Priority Queue

> **Language:** Java  
> **Format:** Pattern → problem → solution → explanation → time/space complexity  
> **Goal:** Interview revision. Focus on recognizing the pattern first.

---

# 7. Stack

## 7.1 Classic Stack

### Pattern 1 — Implement Stack Using Array

**Idea:** Stack follows **LIFO** — Last In, First Out.

```java
class ArrayStack {
    private int[] arr;
    private int top = -1;

    public ArrayStack(int capacity) {
        arr = new int[capacity];
    }

    public void push(int value) {
        if (top == arr.length - 1) {
            throw new RuntimeException("Stack Overflow");
        }
        arr[++top] = value;
    }

    public int pop() {
        if (top == -1) {
            throw new RuntimeException("Stack Underflow");
        }
        return arr[top--];
    }

    public int peek() {
        if (top == -1) {
            throw new RuntimeException("Stack is empty");
        }
        return arr[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
```

**Remember:** `top` points to the current top element.

**Time:** `push O(1)`, `pop O(1)`, `peek O(1)`  
**Space:** `O(n)`

---

### Pattern 2 — Implement Stack Using Linked List

**Idea:** Insert and delete from the head so both operations are `O(1)`.

```java
class LinkedStack {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node top;

    public void push(int value) {
        Node node = new Node(value);
        node.next = top;
        top = node;
    }

    public int pop() {
        if (top == null) {
            throw new RuntimeException("Stack Underflow");
        }

        int value = top.value;
        top = top.next;
        return value;
    }

    public int peek() {
        if (top == null) {
            throw new RuntimeException("Stack is empty");
        }

        return top.value;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
```

**Time:** `push O(1)`, `pop O(1)`, `peek O(1)`  
**Space:** `O(n)`

---

### Pattern 3 — Balanced Parentheses

**Problem:** Check whether brackets are balanced.

Example:

```text
Input:  ({[]})
Output: true

Input:  ([)]
Output: false
```

**Pattern:** Opening bracket → push.  
Closing bracket → top must contain its matching opening bracket.

```java
import java.util.*;

class BalancedParentheses {

    public static boolean isBalanced(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : s.toCharArray()) {

            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            }

            else if (ch == ')' || ch == '}' || ch == ']') {

                if (stack.isEmpty()) {
                    return false;
                }

                char open = stack.pop();

                if (!matches(open, ch)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    private static boolean matches(char open, char close) {
        return (open == '(' && close == ')')
            || (open == '{' && close == '}')
            || (open == '[' && close == ']');
    }
}
```

**Time:** `O(n)`  
**Space:** `O(n)`

---

### Pattern 4 — Reverse String Using Stack

**Idea:** Push characters left-to-right, then pop them. Because stack is LIFO, the string becomes reversed.

```java
import java.util.*;

class ReverseString {

    public static String reverse(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : s.toCharArray()) {
            stack.push(ch);
        }

        StringBuilder result = new StringBuilder();

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }
}
```

**Time:** `O(n)`  
**Space:** `O(n)`

---

# 7.2 Monotonic Stack

> **Monotonic stack = a stack maintained in increasing or decreasing order.**

Most common use:

```text
"Find the next greater/smaller element"
```

---

### Pattern 5 — Next Greater Element

**Problem:**

```text
Input:
[2, 1, 5, 3, 4]

Output:
[5, 5, -1, 4, -1]
```

For each element, find the first greater element on its right.

**Pattern:** Traverse from right to left and maintain a decreasing stack of useful candidates.

```java
import java.util.*;

class NextGreaterElement {

    public static int[] nextGreater(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = n - 1; i >= 0; i--) {

            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                result[i] = stack.peek();
            }

            stack.push(nums[i]);
        }

        return result;
    }
}
```

**Why pop smaller/equal?**

They can never be the next greater element for the current value.

**Time:** `O(n)` amortized  
**Space:** `O(n)`

---

### Pattern 6 — Next Smaller Element

**Problem:** Find the first smaller element on the right.

```text
Input:
[4, 5, 2, 10, 8]

Output:
[2, 2, -1, 8, -1]
```

```java
import java.util.*;

class NextSmallerElement {

    public static int[] nextSmaller(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = n - 1; i >= 0; i--) {

            while (!stack.isEmpty() && stack.peek() >= nums[i]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                result[i] = stack.peek();
            }

            stack.push(nums[i]);
        }

        return result;
    }
}
```

**Time:** `O(n)` amortized  
**Space:** `O(n)`

---

### Pattern 7 — Largest Rectangle in Histogram

**Problem:**

```text
heights = [2, 1, 5, 6, 2, 3]

Answer = 10
```

The largest rectangle is formed by heights `5` and `6`:

```text
min(5, 6) * 2 = 10
```

**Pattern:** Maintain an increasing stack of indices. When a smaller height arrives, calculate rectangles whose right boundary is now known.

```java
import java.util.*;

class LargestRectangleHistogram {

    public static int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        for (int i = 0; i <= heights.length; i++) {

            int currentHeight =
                (i == heights.length) ? 0 : heights[i];

            while (!stack.isEmpty()
                    && currentHeight < heights[stack.peek()]) {

                int height = heights[stack.pop()];

                int width = stack.isEmpty()
                        ? i
                        : i - stack.peek() - 1;

                maxArea = Math.max(
                    maxArea,
                    height * width
                );
            }

            stack.push(i);
        }

        return maxArea;
    }
}
```

**Key formula:**

```text
width = rightBoundary - leftBoundary - 1
area  = height × width
```

**Time:** `O(n)`  
**Space:** `O(n)`

---

### Pattern 8 — Stock Span

**Problem:**

For each day, find how many consecutive previous days had a price less than or equal to today's price.

```text
Prices:
[100, 80, 60, 70, 60, 75, 85]

Span:
[1,   1,  1,  2,  1,  4,  6]
```

**Pattern:** Maintain indices of previous greater prices.

```java
import java.util.*;

class StockSpan {

    public static int[] calculateSpan(int[] prices) {
        int n = prices.length;
        int[] span = new int[n];

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {

            while (!stack.isEmpty()
                    && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }

            span[i] = stack.isEmpty()
                    ? i + 1
                    : i - stack.peek();

            stack.push(i);
        }

        return span;
    }
}
```

**Time:** `O(n)` amortized  
**Space:** `O(n)`

---

# 7.3 Expression Problems

## Pattern 9 — Infix → Postfix

### Infix

```text
A + B * C
```

### Postfix

```text
A B C * +
```

**Idea:**

- Operand → directly output
- `(` → push
- `)` → pop until `(`
- Operator → pop higher/equal precedence operators, then push current operator
- At the end → pop remaining operators

```java
import java.util.*;

class InfixToPostfix {

    public static String convert(String expression) {
        StringBuilder result = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : expression.toCharArray()) {

            if (Character.isLetterOrDigit(ch)) {
                result.append(ch);
            }

            else if (ch == '(') {
                stack.push(ch);
            }

            else if (ch == ')') {

                while (!stack.isEmpty()
                        && stack.peek() != '(') {
                    result.append(stack.pop());
                }

                stack.pop();
            }

            else if (isOperator(ch)) {

                while (!stack.isEmpty()
                        && stack.peek() != '('
                        && precedence(stack.peek()) >= precedence(ch)) {
                    result.append(stack.pop());
                }

                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-'
            || ch == '*' || ch == '/';
    }

    private static int precedence(char ch) {
        if (ch == '+' || ch == '-') return 1;
        if (ch == '*' || ch == '/') return 2;
        return 0;
    }
}
```

**Time:** `O(n)`  
**Space:** `O(n)`

> This version assumes single-character operands and the operators `+ - * /`.

---

## Pattern 10 — Evaluate Postfix

Example:

```text
2 3 * 4 +
```

Calculation:

```text
2 × 3 + 4
= 10
```

**Pattern:** Operand → push. Operator → pop two operands, calculate, push result.

```java
import java.util.*;

class EvaluatePostfix {

    public static int evaluate(String expression) {
        Deque<Integer> stack = new ArrayDeque<>();

        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {

            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int b = stack.pop();
                int a = stack.pop();

                int result = apply(a, b, token.charAt(0));

                stack.push(result);
            }
        }

        return stack.pop();
    }

    private static boolean isNumber(String token) {
        return token.matches("-?\\d+");
    }

    private static int apply(int a, int b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            default: throw new IllegalArgumentException("Invalid operator");
        }
    }
}
```

**Important:**

```text
b = first pop
a = second pop

a - b
a / b
```

Order matters for `-` and `/`.

**Time:** `O(n)`  
**Space:** `O(n)`

---

## Pattern 11 — Evaluate Prefix

Example:

```text
+ * 2 3 4
```

Calculation:

```text
(2 × 3) + 4
= 10
```

**Pattern:** Scan from right to left.

```java
import java.util.*;

class EvaluatePrefix {

    public static int evaluate(String expression) {
        Deque<Integer> stack = new ArrayDeque<>();

        String[] tokens = expression.split("\\s+");

        for (int i = tokens.length - 1; i >= 0; i--) {

            String token = tokens[i];

            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int a = stack.pop();
                int b = stack.pop();

                int result = apply(a, b, token.charAt(0));

                stack.push(result);
            }
        }

        return stack.pop();
    }

    private static boolean isNumber(String token) {
        return token.matches("-?\\d+");
    }

    private static int apply(int a, int b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            default: throw new IllegalArgumentException("Invalid operator");
        }
    }
}
```

**Time:** `O(n)`  
**Space:** `O(n)`

---

# 8. Queue / Deque / Priority Queue

## 8.1 Queue Basics

### Pattern 12 — Implement Queue Using Array

Queue follows **FIFO**:

```text
First In → First Out
```

```java
class ArrayQueue {

    private int[] arr;
    private int front = 0;
    private int rear = 0;

    public ArrayQueue(int capacity) {
        arr = new int[capacity];
    }

    public void enqueue(int value) {
        if (rear == arr.length) {
            throw new RuntimeException("Queue is full");
        }

        arr[rear++] = value;
    }

    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        return arr[front++];
    }

    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        return arr[front];
    }

    public boolean isEmpty() {
        return front == rear;
    }
}
```

**Time:** enqueue `O(1)`, dequeue `O(1)`, peek `O(1)`  
**Space:** `O(n)`

> This simple implementation does not reuse slots after dequeue. A circular queue solves that limitation.

---

### Pattern 13 — Implement Queue Using Linked List

Use two pointers:

```text
front → first element
rear  → last element
```

```java
class LinkedQueue {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node front;
    private Node rear;

    public void enqueue(int value) {
        Node node = new Node(value);

        if (rear == null) {
            front = rear = node;
            return;
        }

        rear.next = node;
        rear = node;
    }

    public int dequeue() {
        if (front == null) {
            throw new RuntimeException("Queue is empty");
        }

        int value = front.value;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return value;
    }

    public int peek() {
        if (front == null) {
            throw new RuntimeException("Queue is empty");
        }

        return front.value;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
```

**Time:** enqueue `O(1)`, dequeue `O(1)`, peek `O(1)`  
**Space:** `O(n)`

---

### Pattern 14 — Circular Queue

**Why circular queue?**

A normal array queue may waste empty slots at the beginning.

Circular queue reuses them using modulo:

```text
nextIndex = (index + 1) % capacity
```

```java
class CircularQueue {

    private int[] arr;
    private int front = 0;
    private int rear = 0;
    private int size = 0;

    public CircularQueue(int capacity) {
        arr = new int[capacity];
    }

    public void enqueue(int value) {
        if (isFull()) {
            throw new RuntimeException("Queue is full");
        }

        arr[rear] = value;
        rear = (rear + 1) % arr.length;
        size++;
    }

    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        int value = arr[front];
        front = (front + 1) % arr.length;
        size--;

        return value;
    }

    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        return arr[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == arr.length;
    }
}
```

**Time:** enqueue `O(1)`, dequeue `O(1)`, peek `O(1)`  
**Space:** `O(n)`

---

### Pattern 15 — Queue Using Two Stacks

**Idea:**

```text
Stack 1 → incoming elements
Stack 2 → outgoing elements
```

When `stack2` is empty, transfer everything from `stack1` to `stack2`.

```java
import java.util.*;

class QueueUsingTwoStacks {

    private Deque<Integer> in = new ArrayDeque<>();
    private Deque<Integer> out = new ArrayDeque<>();

    public void enqueue(int value) {
        in.push(value);
    }

    public int dequeue() {
        moveIfNeeded();

        if (out.isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        return out.pop();
    }

    public int peek() {
        moveIfNeeded();

        if (out.isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        return out.peek();
    }

    private void moveIfNeeded() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
    }
}
```

**Time:** enqueue `O(1)` amortized, dequeue `O(1)` amortized  
**Worst-case single dequeue:** `O(n)` when transferring elements  
**Space:** `O(n)`

**Interview phrase:** "Each element moves from `in` to `out` at most once, so the amortized cost is O(1)."

---

### Pattern 16 — Stack Using Two Queues

**Idea:** Make `push()` expensive so `pop()` remains `O(1)`.

```java
import java.util.*;

class StackUsingTwoQueues {

    private Queue<Integer> q1 = new ArrayDeque<>();
    private Queue<Integer> q2 = new ArrayDeque<>();

    public void push(int value) {

        q2.offer(value);

        while (!q1.isEmpty()) {
            q2.offer(q1.poll());
        }

        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }

    public int pop() {
        if (q1.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        return q1.poll();
    }

    public int peek() {
        if (q1.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        return q1.peek();
    }

    public boolean isEmpty() {
        return q1.isEmpty();
    }
}
```

**Time:** push `O(n)`, pop `O(1)`, peek `O(1)`  
**Space:** `O(n)`

---

# 8.2 Deque / Sliding Window

## Pattern 17 — Deque Operations

A **Deque** (Double Ended Queue) supports insertion/removal from both ends.

Java:

```java
Deque<Integer> deque = new ArrayDeque<>();

deque.addFirst(10);
deque.addLast(20);

int first = deque.peekFirst();
int last = deque.peekLast();

deque.removeFirst();
deque.removeLast();
```

Typical operations:

```text
addFirst()    → O(1)
addLast()     → O(1)
removeFirst() → O(1)
removeLast()  → O(1)
peekFirst()   → O(1)
peekLast()    → O(1)
```

**Space:** `O(n)`

---

## Pattern 18 — Sliding Window Maximum

**Problem:**

```text
nums = [1, 3, -1, -3, 5, 3, 6, 7]
k = 3

Output:
[3, 3, 5, 5, 6, 7]
```

**Idea:** Maintain a deque of **indices**.

The deque is decreasing by value:

```text
front = largest value in current window
```

```java
import java.util.*;

class SlidingWindowMaximum {

    public static int[] maxSlidingWindow(int[] nums, int k) {

        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - k + 1];

        Deque<Integer> deque = new ArrayDeque<>();
        int resultIndex = 0;

        for (int i = 0; i < n; i++) {

            // Remove indices outside the window
            while (!deque.isEmpty()
                    && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // Remove smaller values from the back
            while (!deque.isEmpty()
                    && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            // Window is ready
            if (i >= k - 1) {
                result[resultIndex++] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
```

**Why store indices instead of values?**

Because we need to know whether an element has left the current window.

**Time:** `O(n)` amortized  
**Space:** `O(k)`

---

# 8.3 PriorityQueue / Heap

## Pattern 19 — PriorityQueue with Natural Ordering

Java's `PriorityQueue` is a **min-heap by default**.

```java
import java.util.*;

PriorityQueue<Integer> pq = new PriorityQueue<>();

pq.offer(30);
pq.offer(10);
pq.offer(20);

System.out.println(pq.peek()); // 10

System.out.println(pq.poll()); // 10
System.out.println(pq.poll()); // 20
System.out.println(pq.poll()); // 30
```

Important:

```text
offer() → O(log n)
poll()  → O(log n)
peek()  → O(1)
```

**Space:** `O(n)`

---

## Pattern 20 — Custom Comparator PriorityQueue

Example: highest salary first.

```java
import java.util.*;

class Employee {
    String name;
    int salary;

    Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }
}

PriorityQueue<Employee> pq =
    new PriorityQueue<>(
        (a, b) -> Integer.compare(b.salary, a.salary)
    );

pq.offer(new Employee("Alice", 90000));
pq.offer(new Employee("Bob", 70000));
pq.offer(new Employee("Charlie", 80000));

Employee top = pq.poll();

System.out.println(top.name);   // Alice
System.out.println(top.salary); // 90000
```

**Important comparator rule:**

```java
(a, b) -> Integer.compare(b.salary, a.salary)
```

means larger salary gets higher priority.

**Time:** offer/poll `O(log n)`, peek `O(1)`  
**Space:** `O(n)`

---

## Pattern 21 — Top K Elements

**Problem:** Find the K largest elements.

```text
nums = [3, 2, 1, 5, 6, 4]
k = 2

Answer:
[5, 6]
```

**Pattern:** Maintain a **min-heap of size K**.

```java
import java.util.*;

class TopKElements {

    public static int[] topK(int[] nums, int k) {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {

            minHeap.offer(num);

            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] result = new int[minHeap.size()];

        int i = 0;

        while (!minHeap.isEmpty()) {
            result[i++] = minHeap.poll();
        }

        return result;
    }
}
```

### Why min-heap?

We want the **K largest**.

The smallest among our current K elements sits at the top. If a new element is larger, it can replace that smallest element.

**Time:** `O(n log k)`  
**Space:** `O(k)`

### For Top K smallest

Use a **max-heap of size K**.

---

## Pattern 22 — Merge K Sorted Lists

**Problem:**

```text
List 1: 1 → 4 → 5
List 2: 1 → 3 → 4
List 3: 2 → 6

Output:
1 → 1 → 2 → 3 → 4 → 4 → 5 → 6
```

**Pattern:** Put the first node of every list into a min-heap.

Then repeatedly:

```text
remove smallest
add it to answer
insert that node's next node
```

```java
import java.util.*;

class MergeKSortedLists {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {

        PriorityQueue<ListNode> minHeap =
            new PriorityQueue<>(
                (a, b) -> Integer.compare(a.val, b.val)
            );

        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!minHeap.isEmpty()) {

            ListNode node = minHeap.poll();

            tail.next = node;
            tail = tail.next;

            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }
}
```

Let:

```text
N = total number of nodes
K = number of lists
```

**Time:** `O(N log K)`  
**Space:** `O(K)`

---

# Pattern 23 — Dijkstra's Algorithm

**Problem:** Find the shortest distance from a source node to every other node in a graph with **non-negative edge weights**.

Example graph:

```text
0 --4--> 1
|        |
1        2
|        |
v        v
2 --1--> 1
```

More generally, represent the graph as:

```java
List<List<int[]>> graph
```

where:

```text
edge = {neighbor, weight}
```

**Pattern:**

```text
Min Heap
   ↓
Take node with smallest known distance
   ↓
Relax all outgoing edges
   ↓
Update distance if shorter
   ↓
Push updated distance into heap
```

```java
import java.util.*;

class Dijkstra {

    static class State {
        int node;
        int distance;

        State(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static int[] shortestPath(
            List<List<int[]>> graph,
            int source) {

        int n = graph.size();
        int[] dist = new int[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        PriorityQueue<State> minHeap =
            new PriorityQueue<>(
                (a, b) -> Integer.compare(a.distance, b.distance)
            );

        minHeap.offer(new State(source, 0));

        while (!minHeap.isEmpty()) {

            State current = minHeap.poll();

            int node = current.node;
            int distance = current.distance;

            // Ignore stale heap entries
            if (distance != dist[node]) {
                continue;
            }

            for (int[] edge : graph.get(node)) {

                int nextNode = edge[0];
                int weight = edge[1];

                if (dist[node] != Integer.MAX_VALUE
                        && dist[node] + weight < dist[nextNode]) {

                    dist[nextNode] =
                        dist[node] + weight;

                    minHeap.offer(
                        new State(nextNode, dist[nextNode])
                    );
                }
            }
        }

        return dist;
    }
}
```

### Relaxation

The most important Dijkstra line is:

```java
if (dist[node] + weight < dist[nextNode]) {
    dist[nextNode] = dist[node] + weight;
}
```

Meaning:

> "Can I reach `nextNode` through the current node with a shorter distance?"

**Time:** `O((V + E) log V)` with a binary heap  
**Space:** `O(V + E)`

> Dijkstra does **not** work correctly with negative edge weights.

---

# Stack / Queue / Heap Pattern Recognition

## Stack

Think **LIFO**:

```text
Last In → First Out
```

Use stack when the problem involves:

- Matching brackets
- Undo
- Reverse
- Previous/next greater/smaller
- Nested expressions
- Monotonic stack

### Stack triggers

```text
"Next greater"
"Next smaller"
"Largest rectangle"
"Stock span"
"Balanced parentheses"
"Previous greater"
"Previous smaller"
```

Think:

```text
MONOTONIC STACK
```

---

# Queue

Think **FIFO**:

```text
First In → First Out
```

Use queue for:

- BFS
- Scheduling
- Processing in arrival order
- Sliding-window structures

---

# Deque

Think:

```text
Both ends
```

Especially:

```text
Sliding Window Maximum
Sliding Window Minimum
```

For sliding-window maximum:

```text
Deque stores indices
Deque values are decreasing
Front = maximum
```

---

# PriorityQueue / Heap

Think:

```text
"I repeatedly need the smallest/largest item."
```

Typical problems:

```text
Top K
Kth largest/smallest
Merge K sorted lists
Dijkstra
Scheduling
Median / two heaps
```

### Java defaults

```java
PriorityQueue<Integer> minHeap =
    new PriorityQueue<>();
```

For max heap:

```java
PriorityQueue<Integer> maxHeap =
    new PriorityQueue<>(Collections.reverseOrder());
```

---

# Complexity Cheat Sheet

| Pattern | Time | Space |
|---|---:|---:|
| Array Stack push/pop/peek | O(1) | O(n) |
| Linked Stack push/pop/peek | O(1) | O(n) |
| Balanced Parentheses | O(n) | O(n) |
| Reverse String | O(n) | O(n) |
| Next Greater Element | O(n) | O(n) |
| Next Smaller Element | O(n) | O(n) |
| Largest Rectangle | O(n) | O(n) |
| Stock Span | O(n) | O(n) |
| Infix → Postfix | O(n) | O(n) |
| Evaluate Postfix | O(n) | O(n) |
| Evaluate Prefix | O(n) | O(n) |
| Array Queue | O(1) operations | O(n) |
| Linked Queue | O(1) operations | O(n) |
| Circular Queue | O(1) operations | O(n) |
| Queue using 2 Stacks | O(1) amortized | O(n) |
| Stack using 2 Queues | push O(n), pop O(1) | O(n) |
| Deque operations | O(1) | O(n) |
| Sliding Window Maximum | O(n) | O(k) |
| PriorityQueue offer/poll | O(log n) | O(n) |
| PriorityQueue peek | O(1) | O(n) |
| Top K | O(n log k) | O(k) |
| Merge K Lists | O(N log K) | O(K) |
| Dijkstra | O((V+E) log V) | O(V+E) |

---

# One-Page Memory Map

```text
STACK
│
├── LIFO
│   ├── Implement Stack
│   ├── Reverse String
│   └── Balanced Parentheses
│
├── MONOTONIC STACK
│   ├── Next Greater
│   ├── Next Smaller
│   ├── Largest Rectangle
│   └── Stock Span
│
└── EXPRESSION
    ├── Infix → Postfix
    ├── Evaluate Postfix
    └── Evaluate Prefix


QUEUE
│
├── FIFO
│   ├── Array Queue
│   ├── Linked Queue
│   └── Circular Queue
│
├── TWO STRUCTURES
│   ├── Queue → 2 Stacks
│   └── Stack → 2 Queues
│
└── DEQUE
    ├── Both ends
    └── Sliding Window Maximum


PRIORITY QUEUE / HEAP
│
├── Min Heap
│   ├── Smallest first
│   ├── Top K largest
│   ├── Merge K lists
│   └── Dijkstra
│
└── Max Heap
    ├── Largest first
    └── Top K smallest
```

---

# Interview Revision Rules

### Rule 1 — LIFO?

```text
STACK
```

### Rule 2 — FIFO?

```text
QUEUE
```

### Rule 3 — Need both ends?

```text
DEQUE
```

### Rule 4 — Need smallest/largest repeatedly?

```text
PRIORITY QUEUE / HEAP
```

### Rule 5 — Next greater/smaller?

```text
MONOTONIC STACK
```

### Rule 6 — Sliding window maximum?

```text
MONOTONIC DEQUE
```

### Rule 7 — Top K?

```text
HEAP OF SIZE K
```

### Rule 8 — Merge K sorted things?

```text
MIN HEAP
```

### Rule 9 — Shortest path with non-negative weights?

```text
DIJKSTRA + MIN HEAP
```
