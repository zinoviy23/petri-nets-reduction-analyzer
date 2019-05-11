package hse.se.aaizmaylov.petrinetscalculationserver.services;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions.*;
import lombok.NonNull;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReductionsInfoService {
    private final Map<String, Reduction<Place, Transition>> reductionsOnPlaces;
    private final Map<String, Reduction<Transition, Place>> reductionsOnTransition;

    public ReductionsInfoService() {
        Map<String, Reduction<Place, Transition>> reductionsOnPlaces = new HashMap<>();

        reductionsOnPlaces.put("PreA", new PreAgglomerationOfTransition());
        reductionsOnPlaces.put("PostA", new PostAgglomerationOfTransitions());
        reductionsOnPlaces.put("RedunP", new DeleteRedundantPlace());
        reductionsOnPlaces.put("FusParT", new FusionOfParallelTransitions());
        reductionsOnPlaces.put("FusLoopT", new FusionOfSelfLoopTransitions());

        Map<String, Reduction<Transition, Place>> reductionsOnTransitions = new HashMap<>();
        reductionsOnTransitions.put("FusParP", new FusionOfParallelPlaces());
        reductionsOnTransitions.put("FusLoopP", new FusionOfSelfLoopPlaces());

        this.reductionsOnPlaces = Collections.synchronizedMap(reductionsOnPlaces);
        this.reductionsOnTransition = Collections.synchronizedMap(reductionsOnTransitions);
    }

    public Pair<List<Reduction<Place, Transition>>, List<Reduction<Transition, Place>>>
    getReductions(@NonNull List<String> reductionsList) {

        List<Reduction<Place, Transition>> onPlaces = new ArrayList<>();
        List<Reduction<Transition, Place>> onTransition = new ArrayList<>();

        for (String reduction : reductionsList) {
            if (reductionsOnPlaces.containsKey(reduction)) {
                onPlaces.add(reductionsOnPlaces.get(reduction));
            } else if (reductionsOnTransition.containsKey(reduction)) {
                onTransition.add(reductionsOnTransition.get(reduction));
            } else {
                throw new UnknownReductionException("Unknown reduction " + reduction);
            }
        }

        return Pair.of(onPlaces, onTransition);
    }

    public List<String> toList(@NonNull String reductions) {
        return Arrays.stream(reductions.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
