/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.persistence.TypedQuery;
import model.Comanda;
import model.Producto;

/**
 *
 * @author Alejandro
 */
public class ProductoDAODB implements ProductoDAO {

    @Override
    public void Menu() {
        ArrayList<Producto> salida;
        
         var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        TypedQuery<Producto> p = (TypedQuery<Producto>) em.createQuery("select p from Producto p", Producto.class);
        salida = (ArrayList<Producto>) p.getResultList();
        var df = new DecimalFormat("#.##");
        
        salida.forEach((co) -> {
                System.out.printf("Id: %d || Nombre: %s || Precio: %s€%n", co.getId(), co.getNombre(),
                        df.format(co.getPrecio()));
            });
    }

    @Override
    public float getPrecio(int id) {
        
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        TypedQuery<Producto> p = (TypedQuery<Producto>) em.createQuery("select p.Precio from Producto p where id = : id", Producto.class);
        
        p.setParameter("id", id);
        Producto prod = p.getSingleResult();
        
            
       
        
        return prod.getPrecio();
        
    }

    @Override
    public String getNombreProducto(int id) {
      
         var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        TypedQuery<Producto> p = (TypedQuery<Producto>) em.createQuery("select p.Nombre from Producto p where id = : id", Producto.class);
        
        p.setParameter("id", id);
        Producto prod = p.getSingleResult();
       
        
        return prod.getNombre();
      
    }

    @Override
    public void newProducto() {
         var sc = new Scanner(System.in);
         var p = new Producto();
         
         System.out.println();
        System.out.println("Introduce el nombre del producto");
        p.setNombre(sc.nextLine());
        System.out.println("Introduce el precio del producto");
        p.setPrecio(sc.nextFloat());

         var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();

    }
    
}
