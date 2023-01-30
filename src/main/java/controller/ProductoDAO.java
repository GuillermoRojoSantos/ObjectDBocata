/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

/**
 * @author AlejandroMarínBermúd
 * @author GuillermoRojoSantos
 */
public interface ProductoDAO {
    
    public void Menu ();
    public float getPrecio (int id);
    public String getNombreProducto (int id);
    public void newProducto();
}
