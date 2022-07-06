package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private ImdbDAO dao;
	private Graph<Director, DefaultWeightedEdge>grafo;
	private Map<Integer,Director> mapD;
	private Map<Integer,Actor> mapA;
	
	
	
	public Model() {
		this.dao = new ImdbDAO();
		this.mapD = new HashMap<Integer,Director>();
		this.dao.listAllDirectors(mapD);
		this.mapA = new HashMap<Integer,Actor>();
		this.dao.listAllActors(mapA);
	}
	
	public List<Integer>anni(){
		return dao.getAllYears();
	}
	
	public void creaGrafo(int anno ) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungi vertici
		for(Integer a : this.dao.idDirectorsByYears(anno)) {
			for(Director d: mapD.values()) {
				if(a.equals(d.getId())) {
					this.grafo.addVertex(d);
				}
			}
		}
		
		//aggiungi archi 
		for(Adiacenza a : dao.getArchi(anno, mapD)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getd1(), a.getd2(), a.getPeso());
		}
		
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
    public int nArchi() {
	    return this.grafo.edgeSet().size();
	}
    
    public boolean grafoCreato() {
	    if(this.grafo == null) {
	    	return false;
	    }else {
	    	return true;
	    }
	}
    
    public List<Director> getVertici(){
    	List<Director> l1 = new ArrayList<>();
    	for(Director d1 : this.grafo.vertexSet()) {
    		l1.add(d1);
    	}
    	return l1;
    }
    
    public List<Best>getBest(Director a){
    	double pmin = 0;
    	List<Director> vicini = Graphs.neighborListOf(this.grafo, a);
    	List<Best> h = new ArrayList<>();
    	for(Director f : vicini) {
    		if(this.grafo.containsEdge(a,f)) {
				DefaultWeightedEdge e = this.grafo.getEdge(a, f);
				int peso = (int)this.grafo.getEdgeWeight(e);
				h.add(new Best(f,peso));
			}else if(this.grafo.containsEdge(f,a)) {
				DefaultWeightedEdge e = this.grafo.getEdge(f, a);
				int peso = (int)this.grafo.getEdgeWeight(e);
				h.add(new Best(f,peso));
			}
    	}
    	Collections.sort(h);
    	return h;
    }
    
    
}
