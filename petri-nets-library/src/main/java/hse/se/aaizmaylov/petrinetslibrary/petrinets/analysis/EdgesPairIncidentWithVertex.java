package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import org.jetbrains.annotations.NotNull;

class EdgesPairIncidentWithVertex<TTokenContainer, TWeight, TTarget, TNeighbour> {
    final Arc<TTokenContainer, TWeight, TTarget, TNeighbour> arcToVertex;

    final Arc<TTokenContainer, TWeight, TNeighbour, TTarget> arcFromVertex;

    EdgesPairIncidentWithVertex(@NotNull Arc<TTokenContainer, TWeight, TTarget, TNeighbour> arcToVertex,
                                @NotNull Arc<TTokenContainer, TWeight, TNeighbour, TTarget> arcFromVertex) {
        this.arcToVertex = arcToVertex;
        this.arcFromVertex = arcFromVertex;
    }
}