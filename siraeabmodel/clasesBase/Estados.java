/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siraeabmodel.clasesBase;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author coco
 */
public class Estados {
    private ArrayList<String[]> estados;
    
    public Estados() throws SQLException{
        String estado[]= new String[2];
        estados= new ArrayList<String[]>();
        BaseDatos bd = new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("select * from estados;");
        while(r.next()){
            estado=new String[2];
            estado[0]=r.getString("id");
            estado[1]=r.getString("estado");
            estados.add(estado);
        }
        bd.cierraConexion();
    }
    
    public ArrayList getEstados(){
        return estados;
    }
}
