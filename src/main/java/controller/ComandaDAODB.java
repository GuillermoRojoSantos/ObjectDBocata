/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Comanda;

/**
 * @author AlejandroMarínBermúd
 * @author GuillermoRojoSantos
 */
public class ComandaDAODB implements ComandaDAO {
    
    
     @Override
    public void newPedido(String currentDate) {
     
        var sc = new Scanner(System.in);
        var c = new Comanda();
        
        ProductoDAODB prod = new ProductoDAODB();
        prod.Menu();
        System.out.println();
        System.out.println("Introduce el nombre del alumno");
        c.setAlumno(sc.nextLine());
        System.out.println("Usar fecha actual?(y/n)");
        var answer = sc.next().toLowerCase();
        if (answer.equals("y")) {
            c.setFecha(currentDate);
        } else {
            System.out.println("Introduce la fecha");
            c.setFecha(sc.nextLine());
        }
        System.out.println("Introduzca el id del producto");
        int id = sc.nextInt();//this next is so the nextInt gets ''correctly closed''
        
        //problema aqui vvv
        c.setProducto(prod.getNombreProducto(id));
        c.setPrecio(prod.getPrecio(id));
        // Problema aqui ^^^
        
        c.setEstado("PENDIENTE");
        
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close(); 
    }

    @Override
    public void delPedido() {
        
        getAllPedidos();
        var sci = new Scanner(System.in);
        System.out.println("Introduzca la id del pedido que desea borrar");
        int idDel = sci.nextInt();
        System.out.println("Borrando Pedido...");
        
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Comanda com = em.find(Comanda.class, idDel);
        em.remove(com);
        em.getTransaction().commit();
        em.close(); 
    }

    @Override
    public void markPedido() {
        var sci = new Scanner(System.in);
        pedidoMarcadoList();
        System.out.println();
        System.out.println("Introduzca la id de la comanda a marcar como entregada:");
        int id = sci.nextInt();
        System.out.println("Marcando pedido...");
        
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();     
        em.getTransaction().begin();
        TypedQuery<Comanda> query = em.createQuery(
            "UPDATE Comanda SET Estado = :estado WHERE id = :id", Comanda.class);
        query.setParameter("estado", "ENTREGADO");
        query.setParameter("id", id);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        
    }

    @Override
    public void pedidoList(String currentDate) {

         var sci = new Scanner(System.in);
        System.out.println("Usar fecha actual? (y/n)");
        //here user can either use system's current date or a custom date
        var answer = sci.next().toLowerCase();
        if (answer.equals("n")) {
            System.out.println("Introduceel dia de hoy");
            String day = sci.next();
            System.out.println("Introduce el mes");
            String month = sci.next();
            System.out.println("Introduce el año");
            String year = sci.next();
         ArrayList<Comanda> salida;
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
             Query query = em.createQuery("select c from Comanda c where Fecha = :fecha", Comanda.class);
        query.setParameter("fecha", year + "-" + month + "-" + day );
        
        salida = (ArrayList<Comanda>) query.getResultList();       
        var df = new DecimalFormat("#.##");
            salida.forEach((co) -> {
                System.out.printf("Id: %d || Alumno: %s || Producto: %s || Fecha: %s || Precio: %s || Estado: %s%n",
                co.getId(), co.getAlumno(), co.getProducto(), co.getFecha(), co.getPrecio(),
                co.getEstado());
                    ;
                });
        em.close();    
        }
        else{
        ArrayList<Comanda> salida;
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
       Query query = em.createQuery("select c from Comanda c where Fecha = :fecha and Estado =:estado", Comanda.class);
         query.setParameter("fecha",currentDate);
         query.setParameter("estado", "PENDIENTE");
        salida = (ArrayList<Comanda>)query.getResultList();       
        var df = new DecimalFormat("#.##");
            salida.forEach((co) -> {
                System.out.printf("Id: %d || Alumno: %s || Producto: %s || Fecha: %s || Precio: %s || Estado: %s%n",
                        co.getId(), co.getAlumno(), co.getProducto(), co.getFecha(), co.getPrecio(),
                        co.getEstado());
                    ;
                });
        em.close();
        }
       
    }

    @Override
    public void pedidoMarcadoList() {
        ArrayList<Comanda> salida;
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        TypedQuery<Comanda> c = (TypedQuery<Comanda>) em.createQuery("select c from Comanda c where Estado= :estado", Comanda.class);
       
        c.setParameter("estado","PENDIENTE");
        
        salida = (ArrayList<Comanda>) c.getResultList();
        var df = new DecimalFormat("#.##");
           salida.forEach((co) -> {
                System.out.printf("Id: %d || Alumno: %s || Producto: %s || Fecha: %s || Precio: %s || Estado: %s%n",
                        co.getId(), co.getAlumno(), co.getProducto(), co.getFecha(), co.getPrecio(),
                        co.getEstado());
                ;
            });
        em.close();
        
    }

    @Override
    public void getAllPedidos() {
        
        ArrayList<Comanda> salida;
        var em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        TypedQuery<Comanda> c = (TypedQuery<Comanda>) em.createQuery("select c from Comanda c", Comanda.class);
        salida = (ArrayList<Comanda>) c.getResultList();
        var df = new DecimalFormat("#.##");
           salida.forEach((co) -> {
                System.out.printf("Id: %d || Alumno: %s || Producto: %s || Fecha: %s || Precio: %s || Estado: %s%n",
                        co.getId(), co.getAlumno(), co.getProducto(), co.getFecha(), co.getPrecio(),
                        co.getEstado());
                ;
            });
        em.close();
   
    }
    
}
