package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;

class EdgesPairIncidentWithVertex<TTokenContainer, TTarget, TNeighbour> {
    final Arc<TTokenContainer, TTarget, TNeighbour> arcToVertex;

    final Arc<TTokenContainer, TNeighbour, TTarget> arcFromVertex;

    EdgesPairIncidentWithVertex(Arc<TTokenContainer, TTarget, TNeighbour> arcToVertex,
                                Arc<TTokenContainer, TNeighbour, TTarget> arcFromVertex) {
        this.arcToVertex = arcToVertex;
        this.arcFromVertex = arcFromVertex;
    }
}