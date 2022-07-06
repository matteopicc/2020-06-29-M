package it.polito.tdp.imdb.model;

public class Adiacenza implements Comparable<Adiacenza> {
   Director d1;
   Director d2 ;
   int peso;
   
public Adiacenza(Director d1, Director d2, int peso) {
	
	this.d1 = d1;
	this.d2 = d2;
	this.peso = peso;
}

public Director getd1() {
	return d1;
}

public void setd1(Director d1) {
	this.d1 = d1;
}

public Director getd2() {
	return d2;
}

public void setd2(Director d2) {
	this.d2 = d2;
}

public int getPeso() {
	return peso;
}

public void setPeso(int peso) {
	this.peso = peso;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((d1 == null) ? 0 : d1.hashCode());
	result = prime * result + ((d2 == null) ? 0 : d2.hashCode());
	result = prime * result + peso;
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Adiacenza other = (Adiacenza) obj;
	if (d1 == null) {
		if (other.d1 != null)
			return false;
	} else if (!d1.equals(other.d1))
		return false;
	if (d2 == null) {
		if (other.d2 != null)
			return false;
	} else if (!d2.equals(other.d2))
		return false;
	if (peso != other.peso)
		return false;
	return true;
}

@Override
public int compareTo(Adiacenza o) {
	return o.getPeso()-this.getPeso();
}

@Override
public String toString() {
	return "Adiacenza [d1=" + d1 + ", d2=" + d2 + ", peso=" + peso + "]";
}


   
   
}
