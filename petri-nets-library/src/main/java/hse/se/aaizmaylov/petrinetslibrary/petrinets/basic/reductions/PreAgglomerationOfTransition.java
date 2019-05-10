package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.ReductionHistory;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.TransformCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.FromPlaceToTransitionArc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import lombok.NonNull;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;

public class PreAgglomerationOfTransition implements Reduction<Place, Transition> {

    private final static Logger LOGGER = Logger.getLogger(PreAgglomerationOfTransition.class);

    @Override
    public boolean reduceFrom(@NonNull Place place,
                              @NonNull TransformCallback<Place, Transition> callback,
                              @NonNull ReductionHistory reductionHistory) {

        List<Arc<Long, Long, Place, Transition>> prePlacesArcs = checkAndGetAllArcsToPrePlaces(place);

        if (prePlacesArcs.isEmpty())
            return false;

        List<Transition> preTransitions = getAllPreTransitions(place);

        if (preTransitions.isEmpty())
            return false;

        final Transition h = first(place.getInputs()).getFromEndpoint();
        callback.onDeleteTarget(place);
        reductionHistory.delete(place);
        callback.onDeleteNeighbour(h);

        for (Arc<Long, Long, Place, Transition> prePlacesArc : prePlacesArcs) {
            for (Transition preTransition : preTransitions) {
                reductionHistory.merge(preTransition, h);

                preTransition.addInput(new FromPlaceToTransitionArc(
                        prePlacesArc.getFromEndpoint(),
                        preTransition,
                        prePlacesArc.weight()));
            }
        }

        reductionHistory.delete(h);

        new ArrayList<>(h.getInputs()).forEach(arc -> arc.getFromEndpoint().removeOutput(arc));
        new ArrayList<>(place.getOutputs()).forEach(arc -> arc.getToEndpoint().removeInput(arc));

        LOGGER.debug("Pre agglomeration of transitions around " + place + "!!");

        return true;
    }

    @NotNull
    private List<Transition> getAllPreTransitions(@NotNull Place place) {
        if (place.getOutputs().stream().anyMatch(arc -> arc.weight() != 1))
            return Collections.emptyList();

        return place.getOutputs()
                .stream()
                .map(Arc::getToEndpoint)
                .collect(Collectors.toList());
    }

    @NotNull
    private List<Arc<Long, Long, Place, Transition>> checkAndGetAllArcsToPrePlaces(@NotNull Place place) {
        if (place.getMarks() != 0 || place.getInputs().size() != 1)
            return Collections.emptyList();

        Transition h = first(place.getInputs()).getFromEndpoint();

        // if weight > 1, it can violate the bounded property invariant
        if (h.getOutputs().size() != 1 || first(h.getOutputs()).weight() != 1)
            return Collections.emptyList();

        List<Arc<Long, Long, Place, Transition>> prePlaces = new ArrayList<>(h.getInputs());

        if (!prePlaces
                .stream()
                .map(Arc::getFromEndpoint)
                .allMatch(p -> p.getOutputs().size() == 1)) {
            return Collections.emptyList();
        }

        if (prePlaces.size() == 0) {
            return Collections.emptyList();
        }

        if (prePlaces.stream().anyMatch(arc -> arc.getFromEndpoint() == place)) {
            return Collections.emptyList();
        }

        return prePlaces;
    }
}
