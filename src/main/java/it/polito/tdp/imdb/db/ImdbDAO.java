package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public void listAllActors(Map<Integer,Actor>idMap){
		String sql = "SELECT * FROM actors";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getInt("id"))) {
				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				 idMap.put(actor.getId(), actor);
				}
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void listAllDirectors(Map<Integer,Director>idMap){
		String sql = "SELECT * FROM directors";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getInt("id"))) {
				    Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				    idMap.put(director.getId(), director);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> getAllYears(){
		String sql="SELECT YEAR AS a "
				+ "FROM movies "
				+ "WHERE YEAR <'2007' "
				+ "GROUP BY a "
				+ "ORDER BY a";
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				int a = res.getInt("year");
				result.add(a);
			}
			conn.close();
			return result;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> idDirectorsByYears(int anno){
		String sql="SELECT DISTINCT  a.director_id AS id "
				+ "FROM movies_directors a, movies b "
				+ "WHERE a.movie_id = b.id AND b.year = ? ";
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				int a = res.getInt("id");
				result.add(a);
			}
			conn.close();
			return result;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List <Adiacenza> getArchi(int anno, Map<Integer,Director>idMap) {
		String sql ="SELECT m1.director_id AS d1, m2.director_id AS d2 , COUNT(*) AS n "
				+ "FROM movies_directors m1, movies_directors m2, roles r1, roles r2, movies o1, movies o2 "
				+ "WHERE m1.movie_id = r1.movie_id AND  m2.movie_id = r2.movie_id AND m1.director_id<> m2.director_id AND r1.actor_id = r2.actor_id AND m1.director_id> m2.director_id AND m1.movie_id = o1.id AND m2.movie_id = o2.id AND o1.year =? AND o1.year = o2.year "
				+ "GROUP BY m1.director_id, m2.director_id";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				if(idMap.containsKey(res.getInt("d1")) && idMap.containsKey(res.getInt("d2"))) {
				   result.add(new Adiacenza(idMap.get(res.getInt("d1")),idMap.get(res.getInt("d2")),res.getInt("n")));	
				}
			}
			conn.close();
			return result;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		 
		
		
		
		
	}
	
	
	
	
	
	
	
}
