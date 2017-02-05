Comments and rational on the questions
================================================

### How to run

Code is located under `src/` directory, while `tests` are under `tests/` directory.
Two of the tasks(`OddEvenSorter` and `MyQueue`) have unit tests; they can be run via:
```bash
mvn -Dtest=no.interview.test.misc.\* clean compile test
``` 

The last task on fetching trading information can be run via:

```bash
mvn clean compile exec:java
```
and then by choosing corresponding action.

### Comments on the tasks and their solution

Below you can find additional comments on the tasks.

* On the first task of sorting/partitioning, it wasn't said about negative numbers,
so I assumed that they're acceptable, see unit test for more info.

* Second task is a bit ambigious. It says that it should allow `second` only after `first`, 
but doesn't say that it should be blocked or just return immediately with a special value, so caller can decide
what to do next. My solution is implemented as a blocking one. Also, in real code I would add `timeout`
parameter to the calls so the caller doesn't wait indefinitely:

```java
class Foo {
	void first(int timeout)
	...
}
```
Something along that lines.

* Third task is quite classical, and it was told to use stack, but not referred to concrete implementation of stack
like `java.util.Stack`.The problem with `java.util.Stack` is that it's old, slow and a bit inconsistent to compared to ArrayDeque.
For that reason, I used `Deque` interface as a Stack, so only methods operating on tail(method names `*Last`)
are used.

* The fourth task says implementation shoudn't persist the data for repeated runs, but it doesn't say 
anything about caching the results on repeated or related tasks. So I made it as a cached, hope it's fine.
Worth noting, that timestamps for 2016 can be tricky, because of the leap second added on 31/12/2016.
For instance, if the receiving system's clock is implemented by smearing technique, those transactions 
happened in 2017 will be marked as 2016 in the receving system for a smearing period.
Worth noting, this code doesn't have much of error-catching code, as it involves more time to make it more robust.

