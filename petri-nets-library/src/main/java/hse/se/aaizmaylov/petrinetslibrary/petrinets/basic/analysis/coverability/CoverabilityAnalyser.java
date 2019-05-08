package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.coverability;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import lombok.NonNull;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.not;

public final class CoverabilityAnalyser {
    private final static Logger LOGGER = Logger.getLogger(CoverabilityAnalyser.class);

    private final CoverabilityGraph coverabilityGraph;
    private final PetriNet<Place, Transition> petriNet;

    private final Set<Place> unboundedPlaces = new HashSet<>();

    private final Set<Transition> deadTransitions = new HashSet<>();

    public CoverabilityAnalyser(@NonNull PetriNet<Place, Transition> petriNet) {
        coverabilityGraph = new CoverabilityGraph(petriNet);
        this.petriNet = petriNet;

        markingNodeConsumer = markingNode ->
                coverabilityGraph.getPlacesIndices().forEach(((place, index) -> {
                    if (markingNode.get(index) == -1) {
                        unboundedPlaces.add(place);
                    }
                }));

        analysis();
    }

    private void analysis() {
        LOGGER.info("analysis started");

        Set<Transition> notDeadTransitions = new HashSet<>();

        coverabilityGraph.bfs(markingNodeConsumer, notDeadTransitions::add);

        deadTransitions.addAll(
            petriNet.getTransitions()
                    .stream()
                    .filter(not(notDeadTransitions::contains))
                    .collect(Collectors.toList())
        );

        LOGGER.info("analysis finished");
    }

    private final Consumer<CoverabilityGraph.MarkingNode> markingNodeConsumer;

    public Set<Place> getUnboundedPlaces() {
        return Collections.unmodifiableSet(unboundedPlaces);
    }

    public Set<Transition> getDeadTransitions() {
        return Collections.unmodifiableSet(deadTransitions);
    }
}
