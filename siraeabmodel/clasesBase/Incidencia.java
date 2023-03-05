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
public class Incidencia {
    private String nombre;
    private String clave;
    private String tipo;
    private String fecha;

    public Incidencia(String nombre, String clave, String tipo, String fecha) {
        this.nombre = nombre;
        this.clave = clave;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFecha() {
        return fecha;
    }
    public static String procesaIncidencas(String fechaInicio, String fechaFin, String idUsr) throws SQLException{
        String mensaje;
        BaseDatos bd = new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spIncidencias('"+fechaInicio+"','"+fechaFin+"',"+idUsr+");");
        r.next();
        mensaje= r.getString("mensaje");
        bd.cierraConexion();        
        return mensaje;
    }
    public static ArrayList<Incidencia> getIncidencias(String fechaInicio, String fechaFin) throws SQLException{
        ArrayList<Incidencia> incidencias=new ArrayList<>();
        BaseDatos bd = new BaseDatos();
        bd.conectar();
        ResultSet r= bd.consulta("SELECT * from incidencias  where  fecha between '"+fechaInicio+"' and '"+fechaFin+"' ;");
        while(r.next()){
            incidencias.add(new Incidencia(r.getString("nombre"), r.getString("clave"), r.getString("tipo"), r.getString("fecha")));
        }
        bd.cierraConexion();
        
        return incidencias;
    }
    
}
