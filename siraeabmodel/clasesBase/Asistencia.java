/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siraeabmodel.clasesBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author coco
 */
public class Asistencia {
    private String claveEmpleado;
    private String fecha;
    private String biometrico;
    private String mensaje;
    private int codigo ;

    public Asistencia(String claveEmpleado, String fecha, String biometrico) {
        this.claveEmpleado = claveEmpleado;
        this.fecha = fecha;
        this.biometrico = biometrico;
    }
    
    
    
    public Asistencia(String registro, String idUsr) throws SQLException{
        Pattern patron= Pattern.compile("^[a-zA-Z0-9]{6}[0-9]{15}$");
        if(patron.matcher(registro).matches()){
            claveEmpleado=registro.substring(0, 6);
            fecha=registro.substring(10,14)+"-"+registro.substring(8,10)+"-"+registro.substring(6,8)+" "+
                    registro.substring(14,16)+":"+registro.substring(16,18)+":"+registro.substring(18,20);
            biometrico=registro.charAt(20)+"";
            BaseDatos bd= new BaseDatos();
            bd.conectar();
            ResultSet r=bd.consulta("call spRegistroAsistencia('"+claveEmpleado+"','"+fecha+"',"+biometrico+",'A',"+idUsr+");");
            r.next();
            mensaje = r.getString("mensaje");
            codigo=r.getInt("resultado");
            bd.cierraConexion();
        }else{
            mensaje = "Registro no válido";
            codigo=0;
        }
    }
    
    public static ArrayList<Asistencia> buscar(String claveEmpleado, String fechaInicio, String fechaFin) throws SQLException{
        ArrayList<Asistencia> registros=new ArrayList<>();
        BaseDatos bd = new BaseDatos();
        bd.conectar();
        ResultSet r= bd.consulta("SELECT * from asistencias where  claveEmpleado='"+claveEmpleado+"' and DATE_FORMAT(fecha, '%Y-%m-%d') between '"+fechaInicio+"' and '"+fechaFin+"' ;");
        while(r.next()){
            registros.add(new Asistencia(r.getString("claveEmpleado"), r.getString("fecha"), r.getString("biometrico")));
        }
        bd.cierraConexion();
        
        return registros;
    }
    
    public String getClaveEmpleado() {
        return claveEmpleado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getBiometrico() {
        return biometrico;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getCodigo() {
        return codigo;
    }
    
}