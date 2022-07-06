package it.polito.tdp.imdb.model;

public class Best implements Comparable<Best>{
   private  Director d ;
   private  int peso ;
    
	public Best(Director d, int peso) {
		super();
		this.d = d;
		this.peso = peso;
	}

	public Director getD() {
		return d;
	}

	public void setD(Director d) {
		this.d = d;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Best [d=" + d + ", peso=" + peso + "]";
	}

	@Override
	public int compareTo(Best o) {
		return o.getPeso()-this.getPeso();
	} 
	
	
	
	
    
    
    
}
