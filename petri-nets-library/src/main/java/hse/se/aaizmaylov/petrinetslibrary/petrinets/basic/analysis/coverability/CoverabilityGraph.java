package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.coverability;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import hse.se.aaizmaylov.petrinetslibrary.utils.math.IntVector;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.getIndices;
import static java.util.stream.Collectors.toMap;

public final class CoverabilityGraph {
    private Map<Place, Integer> placesIndices;

    @Getter
    private MarkingNode initialMarking;

    public CoverabilityGraph(@NonNull PetriNet<Place, Transition> petriNet) {
        placesIndices = getIndices(petriNet.getPlaces());

        buildGraph(petriNet);
    }

    private void buildGraph(@NotNull PetriNet<Place, Transition> petriNet) {
        initialMarking = new MarkingNode(placesIndices, null);

        Queue<MarkingNode> newMarkings = new LinkedList<>();

        Map<MarkingNode, MarkingNode> allMarkings = new HashMap<>();

        allMarkings.put(initialMarking, initialMarking);

        newMarkings.add(initialMarking);

        while (!newMarkings.isEmpty()) {
            MarkingNode marking = newMarkings.poll();

            for (Transition t : petriNet.getTransitions()) {
                Map<Place, Long> newMarkingMap = marking.getMarkingMap(placesIndices);

                if (!t.enabled(newMarkingMap))
                    continue;

                t.fire(newMarkingMap);

                MarkingNode newNode = new MarkingNode(newMarkingMap, placesIndices, marking);

                MarkingNode same = allMarkings.get(newNode);
                if (same != null) {
                    marking.neighbours.put(t, same);
                    continue;
                }

                newNode.generalize();
                marking.neighbours.put(t, newNode);
                allMarkings.put(newNode, newNode);
                newMarkings.add(newNode);
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public Map<Place, Integer> getPlacesIndices() {
        return Collections.unmodifiableMap(placesIndices);
    }

    public void bfs(Consumer<MarkingNode> nodeVisitor, Consumer<Transition> transitionVisitor) {
        Queue<MarkingNode> q = new LinkedList<>();
        q.add(initialMarking);

        Set<MarkingNode> visited = new HashSet<>();

        while (!q.isEmpty()) {
            MarkingNode node = q.poll();
            visited.add(node);

            nodeVisitor.accept(node);

            for (Map.Entry<Transition, MarkingNode> edge : node.neighbours.entrySet()) {
                transitionVisitor.accept(edge.getKey());

                if (visited.contains(edge.getValue()))
                    continue;

                q.add(edge.getValue());
            }
        }
    }

    public static class MarkingNode implements Comparable<MarkingNode> {
        private IntVector elements;

        private MarkingNode parent;

        private Map<Transition, MarkingNode> neighbours = new HashMap<>();

        private int hash;

        private MarkingNode(@NotNull Map<Place, Integer> indices, MarkingNode parent) {
            elements = new IntVector(indices.size());
            this.parent = parent;

            indices.forEach(((place, index) -> elements.set(index, place.getMarks())));

            hash = Arrays.hashCode(elements.getElements());
        }

        private MarkingNode(@NotNull Map<Place, Long> marking, @NotNull Map<Place, Integer> indices,
                            MarkingNode parent) {

            elements = new IntVector(indices.size());
            this.parent = parent;

            indices.forEach(((place, index) -> elements.set(index, marking.get(place))));

            hash = Arrays.hashCode(elements.getElements());
        }

        public Map<Transition, MarkingNode> getNeighbours() {
            return Collections.unmodifiableMap(neighbours);
        }

        public IntVector getElements() {
            return elements;
        }

        @Nullable
        private MarkingNode findCovered() {
            MarkingNode current = parent;

            while (current != null) {
                if (compareTo(current) >= 0) {
                    return current;
                }

                current = current.parent;
            }

            return null;
        }

        // DO NOT USE HASHING BEFORE CALLING THIS METHOD
        private void generalize() {
            MarkingNode lesser = findCovered();

            if (lesser == null)
                return;

            for (int i = 0, size = elements.size(); i < size; i++) {
                if (lesser.elements.get(i) < elements.get(i))
                    elements.set(i, -1);
            }

            hash = Arrays.hashCode(elements.getElements());
        }

        @NotNull
        private Map<Place, Long> getMarkingMap(@NotNull Map<Place, Integer> indices) {
            return indices.entrySet()
                    .stream()
                    .collect(toMap(Map.Entry::getKey, e -> elements.get(e.getValue())));
        }

        @Contract(value = "null -> false", pure = true)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MarkingNode)) return false;
            MarkingNode that = (MarkingNode) o;
            return elements.equals(that.elements);
        }

        @Override
        public int hashCode() {
            return hash;
        }

        private static Comparator<Long> elementsComparator = (o1, o2) -> {
            if (o1.equals(o2))
                return 0;

            if (o1 == -1)
                return 1;

            if (o2 == -1)
                return -1;

            return Long.compare(o1, o2);
        };

        public long get(int index) {
            return elements.get(index);
        }


        @Override
        public int compareTo(@NotNull MarkingNode o) {
            boolean allGreaterOrEquals = true;

            for (int i = 0, size = elements.size(); i < size; i++) {
                allGreaterOrEquals = allGreaterOrEquals
                        && elementsComparator.compare(elements.get(i), o.elements.get(i)) >= 0;
            }

            return allGreaterOrEquals ? 1 : -1;
        }

        @Override
        public String toString() {
            return Arrays.stream(elements.getElements())
                    .mapToObj(l -> l == -1 ? "\u221E" : Long.toString(l))
                    .collect(Collectors.joining(",", "(", ")"));
        }
    }
}
