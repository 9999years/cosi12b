    Rebecca Turner
    2017-10-01
    PA4

# Birds

The assignment requires the implementation of several species of bird objects
that all have shared traits (`Color getColor()`, `Point getPosition()`, and
`void fly()`), represented by the `BirdBehavior` interface. `BirdBehavior`
represents the assignment’s requirements; what the `Aviary` program requires
from a `Bird`.

`BirdBehavior` is implemented by `AbstractBird`, an abstract class that
outlines several getter and setter methods which can be used by all the
individual birds.

`AbstractBird` lays out the following properties:

* State:
  * Readable color
  * Readable position
  * Readable movement vector
* Behavior:
  * A `fly()` method, which by default calls `turn()` and `move()`
    * `turn()` provides a space for logic to change the movement vector
    * `move()` shifts the bird’s position by the movement vector

Sub-classes are meant to override `turn()` and `move()`; An alternative design
would have left them blank in `AbstractBird`, letting the compiler enforce
their implementation. (Because `AbstractBird` is, well, abstract, it’s not
required to implement the entire `BirdBehavior` interface; leaving `turn()` and
`move()` out of `AbstractBird` would make the subclasses implement the methods
before compiling.) This approach was abandoned because several types of birds
require only one type of movement; the cardinal, bluebird, and vultures only
ever turn and the hummingbird only moves without any consistent turning pattern.
To keep the sub-classes slim (`Cardinal.java` only contains five statements and one
branch), implementations of `turn()` and `move()` I thought would be maximally
useful to all sub-classes were provided.

The `AbstractBird` is extended by another abstract class, `Bird`, with the same
behavior and one major difference; a `Bird` is confined to an aviary, a
pre-defined world with coordinates it will never venture out of. Internally,
this means that the `Point position` field is replaced with a `WorldPoint
position` field which ensures its position will never venture past the aviary
and provides methods for determining which edges of the aviary, if any, the bird
is on (several birds required these to reverse course when approaching an edge).

Both `Bird` and `AbstractBird` are abstract because they miss key
functionalities; notably, the very things the assignment stipulates: a color and
the ability to fly. What these classes provide, however, is a rich framework
for building flying and coloring behavior into subclasses that represent real
birds. The rich framework allows subclasses to be quite short — the `Bluebird`’s
flying logic is six statements long, including the initialization setup.
However, a bird with an undefined color and undefined flying ability is
meaningless, and so `Bird` and `AbstractBird` are abstract classes to prevent
similarly meaningless instantiations.
