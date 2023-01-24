/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Comanda;

/**
 * @author GuillermoRojoSantos
 * @author AlejandroMarínBermúd
 */
public interface ComandaDAO{
    
    public void newPedido (String currentDate);
    public void delPedido ();
    public void markPedido ();
    public void pedidoList (String currentDate);
    public void pedidoMarcadoList() ;
    public void getAllPedidos();
}
