package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entidades.Libro;

public class Test3 {

	public static void main(String[] args) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Persistencia" );
	    EntityManager em = emfactory.createEntityManager();
	    Libro l1=new Libro("100","autor1","titulo1",(byte) 0);	
		Libro l2=new Libro("200","autor2","titulo2",(byte) 0);	
	    Libro l3=new Libro("300","autor3","titulo3", (byte) 0);
		Libro l4=new Libro("400","autor4","titulo4", (byte) 0);
		em.getTransaction().begin();
		em.persist(l1);
		em.persist(l2);
		em.persist(l3);
		em.persist(l4);
		em.getTransaction().commit();
		System.out.println("Consultas:");
	    Query query1 = em.createQuery( "Select e from Libro e "  );
	    @SuppressWarnings("unchecked")
		List<Libro> list=(List<Libro>)query1.getResultList( );
	    System.out.println(list);
	    
	    Query query2 = em.createQuery("Select MAX(e.isbn) from Libro e");
	    Object result =  query2.getSingleResult();
	    System.out.println("El Mayor código de libro es :" + result);
	    
	    //llamada a consultas estáticas
	    
	    TypedQuery<Libro> consultaLibrosISBN = em.createNamedQuery("Libro.seleccionarIsbn", Libro.class);
	    consultaLibrosISBN.setParameter("id", "100");
	     Object resu = consultaLibrosISBN.getSingleResult();
	     System.out.println("El  libro buscado es :" + resu);		 
	     
	     System.out.println("*************Libros ordenados por titulo*********");
	     TypedQuery<Libro> consultaLibrosTitulo = em.createNamedQuery("Libro.seleccionarTitulo", Libro.class);
	     
	     List<Libro> lista = consultaLibrosTitulo.getResultList();
	    		 
	    System.out.println("*************Alumnos*********");
	     for (Libro a : lista) 
	    	System.out.println(a.getIsbn() + "," + a.getTitulo());
	    
	    em.close();

	}

}