/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siraeabmodel.clasesBase;

import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author coco
 */
public class Usuario {
   private String id;
   private boolean existe;
   
   public Usuario(String usr, String psw) throws SQLException{
       id="";
       existe=false;
       BaseDatos bd = new BaseDatos();
       bd.conectar();
       ResultSet r= bd.consulta("call spLogin('"+usr+"', '"+psw+"');");
       if(r.next()){
           id=r.getString("id");
           existe=true;
       }else{
           existe=false;
       }
       bd.cierraConexion();
   }
   
   public boolean getExiste(){
       return existe;
   }
   
   public String getId(){
       return id;
   }
   
}
