/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siraeabmodel.clasesBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author coco
 */
public class Municipios {
    private ArrayList<String[]> municipios;
    
    public Municipios(String estado) throws SQLException{
        String municipio[]= new String[2];
        municipios= new ArrayList<String[]>();
        BaseDatos bd = new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("select * from municipios where idE='"+estado+"';");
        while(r.next()){
            municipio=new String[2];
            municipio[0]=r.getString("idE");
            municipio[1]=r.getString("municipio");
            municipios.add(municipio);
        }
        bd.cierraConexion();
    }
    
    public ArrayList getMunicipios(){
        return municipios;
    }
}
