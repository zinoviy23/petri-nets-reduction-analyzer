package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.InitializedReduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.DefaultReductionInitializationData;
import org.junit.jupiter.api.Test;

import static hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.SomePetriNets.fromDiazWithRedundantPlace;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteRedundantPlaceTest {
    @Test
    void deletePlace() {
        PetriNet<Place, Transition> petriNet = fromDiazWithRedundantPlace();
        InitializedReduction<DefaultReductionInitializationData, Place, Transition> reduction =
                new DeleteRedundantPlace();
        TransformCallbackImpl callback = new TransformCallbackImpl();

        reduction.initialize(new DefaultReductionInitializationData(petriNet));

        assertTrue(reduction.reduceFrom(petriNet.getPlacesMap().get("p"), callback));

        assertTrue(petriNet.getTransitionsMap().get("t1").getOutputs()
                .stream()
                .noneMatch(arc -> arc.getToEndpoint().label().equals("p")));

        assertTrue(petriNet.getTransitionsMap().get("t3").getInputs()
                .stream()
                .noneMatch(arc -> arc.getFromEndpoint().label().equals("p")));
    }
}