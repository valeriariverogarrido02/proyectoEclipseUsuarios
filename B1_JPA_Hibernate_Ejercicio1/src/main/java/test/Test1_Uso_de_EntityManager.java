package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.persistence.TypedQuery;
import entidades.Libro;

public class Test1_Uso_de_EntityManager {

	public static void main(String[] args) {
		//conexión a traves de EntityManager sin usar patrón Facade
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Persistencia");
		EntityManager emanager = emfactory.createEntityManager();
		Libro l1 = new Libro("100", "autor1", "titulo1", (byte) 0);
		Libro l2 = new Libro("200", "autor2", "titulo2", (byte) 0);
		Libro l3 = new Libro("300", "autor3", "titulo3", (byte) 0);
		Libro l4 = new Libro("400", "autor4", "titulo4", (byte) 0);
		
		/*1. Cuando llamamos a emanager.persist(l) la entidad es persistida (sincronizada Y almacenada) en nuestra base de datos, 
		 *  y queda gestionada por el proveedor de persistencia en el contexto de persistencia.
		Por ello, cualquier cambio en su estado será sincronizado automáticamente y de forma transparente 
		para la aplicación*/
		
		/*2. En el ejemplo siguiente hemos modificado el estado de la entidad libro l1 después de haber sido persistida, 
		 * pero dentro de la misma transacción que realizó la persistencia, y por tanto la nueva información se sincronizará 
		 * con la base de datos en el commit de la transacción o antes si se realiza flush.*/
		/* ampliación línea 38 :Sin embargo, podemos forzar a que cualquier cambio pendiente se guarde en la base de datos 
		 * antes de terminar la transacción invocando el método flush() de EntityManager. 
		 * los cambios efectuados sobre la entidad l1  serán persistidos (si no lo
		 *  estuvieran ya) antes de finalizar la transacción. */
		    emanager.getTransaction().begin();
			emanager.persist(l1);
			l1.setTitulo("JPA e Hibernate");
			//emanager.flush();
			emanager.persist(l2);
			emanager.persist(l3);
			emanager.persist(l4);
		    emanager.getTransaction().commit();
		
		//comprobamos si la entidad está gestionada todavia o no.
		boolean gestionada = emanager.contains(l1);
		System.out.println("l1 está gestionada "+gestionada);
		
		//compruebo creando otra transacción si l1 sigue o no gestionada
		emanager.getTransaction().begin();
		l1.setTitulo("nuevo cambio");
		boolean gestionadaNueva = emanager.contains(l1);
		System.out.println("l1 está en una nueva transacción, ¿sigue gestionada? "+gestionadaNueva);
		
		emanager.getTransaction().commit();
		
		/*El contexto de persistencia

		El conjunto de los objetos entidad manejados por un EntityManager se llama contexto de persistencia.

		El EntityManager cada tanto sincroniza los cambios del contexto de persistencia a la base de datos, en un proceso llamado flush.

		La trampa del ejemplo anterior está en que cuando hacemos find (y también cuando hacemos consultas JPQL o Criteria) 
		el EntityManager nos devuelve un objeto de su contexto de persistencia ¡y es por eso que los cambios que hicimos 
		terminaron en la base de datos!

		Ahora sabemos que el EntityManager es el que persistió los cambios que hicimos. Pero ¿cuándo lo hizo?
		Momentos en los que ocurre el flush.

		La especificación de JPA menciona dos situaciones en las que el EntityManager está obligado a hacer flush:

    	Al llamar explícitamente a su método flush
    	Al ocurrir el commit de la transacción (lo que ocurre en el ejemplo del principio)

		Sin embargo, si el EntityManager tiene configurado el modo de flush FlushModeType.AUTO (que es el que viene por defecto)
 		también hará flush cuando hagamos una consulta y tengamos cambios pendientes en el contexto de persistencia 
 		que puedan afectar el resultado de esa consulta, como en este ejemplo:*/

		// Comienzo una transacción
//		Persona persona = entityManager.find(Persona.class, 1);
//		System.out.println(persona.getNombre());
//		// Mostrará "José"
//		persona.setNombre("Carlos");
//		entityManager.createQuery(“FROM Persona”).getResultList(); // Ocurre el flush, y la persona con id 1 tendrá el cambio que hicimos
//		// Commit de la transacción*/
		
		// 3.  buscar un libro
		emanager.getTransaction().begin();
		Libro libroBuscado =emanager.find(Libro.class, "200");
		System.out.println(libroBuscado);
		emanager.getTransaction().commit();
		
		libroBuscado.setTitulo("el árbol de la vida");
		libroBuscado =emanager.find(Libro.class, "200");
		System.out.println(libroBuscado);
		// 4. consulta
		TypedQuery<Libro> query = emanager.createQuery("Select e " + "from Libro e ", Libro.class);
		List<Libro> list = query.getResultList();
		System.out.println(list);
		
		
		// 5. Recuperar el estado de una entidad después de modificarla.
		/*Esto es útil cuando persistimos una entidad, y tras haber cambiado su estado deseamos recuperar
		 * el estado persistido (desechando así los últimos cambios realizados sobre la entidad, que como
		 * sabemos podrían haber sido sincronizados con la base de datos). Esto podemos llevarlo a cabo
		 * mediante el método refresh() de  EntityManager*/
		emanager.getTransaction().begin();
		emanager.persist(libroBuscado);
		libroBuscado.setTitulo("hoy es viernes");
		emanager.refresh(libroBuscado);
		emanager.getTransaction().commit();
		System.out.println("**Compruebo si ha modificado el titulo de l200 o ha revertido los cambios");
		System.out.println("**Gracias al refresh ha revertido los cambios");
		List<Libro> list2 = query.getResultList();
		System.out.println(list2);
				
		
		//6. actualizar un libro: si la entidad está persistida se actualiza sólo
		emanager.getTransaction().begin();
		libroBuscado.setAutor("pepito");
		//emanager.merge(libroBuscado);
		emanager.getTransaction().commit();
		
		//7. actualizar un libro: si la entidad está en modo Detached (separada)
		/*Podemos separar una o todas las entidades gestionadas actualmente por el contexto de persistencia
		 * mediante los métodos detach() y clear(), respectivamente. Una vez que una entidad se encuentra separada,
		 * ésta deja de estar gestionada, y por tanto los cambios en su estado dejan de ser
		 *  sincronizados con la base de datos.*/
		
		/**/
		emanager.getTransaction().begin();
		emanager.detach(l4);
		l4.setAutor("Juanito");
		System.out.println(query.getResultList());
		emanager.getTransaction().commit();
		
		emanager.getTransaction().begin();
		l4.setAutor("Luisita");
		emanager.merge(l4);
		System.out.println(query.getResultList());
		System.out.println( "l4 pasa a estado manage "+emanager.contains(l4));
		/*Separado - una instancia separada es un objeto que se ha hecho persistente, pero su Session ha sido cerrada.
		 *  La referencia al objeto todavía es válida, por supuesto, y la instancia separada podría incluso ser modificada 
		 *  en este estado. Una instancia separada puede ser re-unida a una nueva Session más tarde, haciéndola persistente 
		 *  de nuevo (con todas las modificaciones). */
		System.out.println(emanager.find(Libro.class, "400"));
		System.out.println( "l4 pasa a estado manage "+emanager.contains(l4));
		emanager.getTransaction().commit();
		
		//8. contains()		
		/*El método contains() devuelve true si el objeto Java que le pasamos como parámetro
		 *  se encuentra en estado gestionado por el proveedor de persistencia, y false en caso contrario.*/
		
		System.out.println( emanager.contains(libroBuscado));
		/*Cuando persistimos una entidad, automáticamente se convierte en una entidad gestionada.
		 *  Todos los cambios que efectuemos sobre ella dentro del contexto de una transacción se
		 *   verán reflejados también en la base de datos, de forma transparente para la aplicación. */
		
		
		
		
		
		emanager.close();
		emfactory.close();

	}

}
