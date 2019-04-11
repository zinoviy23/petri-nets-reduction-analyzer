package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Edge;

class EdgesPairIncidentWithVertex<TTokenContainer, TTarget, TNeighbour> {
    final Edge<TTokenContainer, TTarget, TNeighbour> edgeToVertex;

    final Edge<TTokenContainer, TNeighbour, TTarget> edgeFromVertex;

    EdgesPairIncidentWithVertex(Edge<TTokenContainer, TTarget, TNeighbour> edgeToVertex,
                                        Edge<TTokenContainer, TNeighbour, TTarget> edgeFromVertex) {
        this.edgeToVertex = edgeToVertex;
        this.edgeFromVertex = edgeFromVertex;
    }
}