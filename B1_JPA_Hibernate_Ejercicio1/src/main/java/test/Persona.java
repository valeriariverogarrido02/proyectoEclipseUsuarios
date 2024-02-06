package test;

import java.sql.Date;

public class Persona
{
    // Atributos de la clase Persona
    private String nombre;
    private int edad;
    private Date fechaNacimiento; 
    private String apellido;
    // Métodos de la clase Persona
    public String getNombre()
    {
        return nombre;
    }

    public int getEdad()
    {
        return edad;
    }

    public void setNombre(String n)
    {
        nombre = n;
    }

    public void setEdad(int e)
    {
        edad = e;
    }

    public static void main(String[] args)
    {
        Persona p1 = new Persona();
        System.out.println("Nombre: " + p1.getNombre());
        System.out.println("Edad: " + p1.getEdad());
    }
}