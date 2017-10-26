# Stable Matching Problem

## Terminology

A *node* refers to, abstractly, an object in a set; in our limited case, our
nodes are men or women and have names, but generically a node has priorities of
other nodes and identifying features, in our case a unique ID and a non-unique
name.

A *priority* refers to a preference one node has for being matched with another
node; a priority refers to the combination of the node a node would prefer to be
matched with, and how important that match is in comparison to matches with all
the other nodes in the set. Each node has a list of priorities, typically
consisting of a permutation of all the possible nodes in a set.

## Classes / code

Class listing (in `src/main/java`):

* `BiZip`
* `IsolatedNode`
* `Node`
* `NodeIterator`
* `NodePriority`
* `NodeSet`
* `NodeSetFactory` (client class for creating `NodeSet`s)
* `StableMarriage` (client program)

Code may be built and tested with [`gradle build`][1] (which has the advantage
of generating a `.jar` for the `StableMarriage` client program in `build/libs`)
or manually via `javac`.

Tests are stored in `src/test/java` and use JUnit 5. The easiest way to run the
tests is by installing Gradle.

## Program Flow

`NodeSet`s are created with the `NodeSetFactory` class; `NodeSetFactory.add`
creates a new node in the underlying set with an ID that starts at 0 and
increments by 1 each time `.add` is called. Then, `NodeSetFactory.addPriority`
adds a priority for a match for the last-added node of *lower* priority than the
last time `.addPriority` was called.

`.add` and `.addPriority` are called by the client until the underlying set is
fully populated. This process is then repeated with the other group of elements.
(The two sets are men and women in our limited case.)

Next, `NodeSetFactory.link` is called with the two factories as arguments,
promoting the internally index-based priority lists to actual lists of
`NodePriority` objects, creating a rich interface for objects and sets to
naturally interact with their parents, child nodes, priorities, and
matching sets by accessing the actual objects, not just their client-facing IDs
or names.

Then, `NodeSetFactory.getSet` may be called to retrieve the underlying, now
complete, `NodeSet` object from the factory.

Finally, `NodeSet.match` may be called with the two sets retrieved from the
factory as arguments in order to find a stable matching between the two sets.
This populates the `match` field of each `Node` in the `NodeSet`’s `nodes` list,
but an overview of the matching information may be obtained from
`NodeSet.getMatchStatus`, which generates a variable-width table displaying the
name, match, and match priority of every node in a `NodeSet`’s nodes.

Internally, `NodeSet.match` uses an iterator class `NodeIterator` which lazily
finds the next node in a set that satisfies a given predicate (in this case,
being unmatched with a non-empty priority list). When the nodes are all matched,
the iterator stops generating elements and the computation is complete.

[1]: https://gradle.org/
