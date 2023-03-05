/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siraeabmodel.clasesBase;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author coco
 */
public class HorarioEmpleado {
    private String idHorario;
    private String claveEmpelado;
    private char status;
    private String fechaAsignacion;
    //formato fecha es AAAA-DD-MM
    public HorarioEmpleado(String idHorario, String claveEmpleado, char status){
        this.idHorario = idHorario;
        this.claveEmpelado = claveEmpleado;
        this.status=status;
    }
    
    public HorarioEmpleado(String idHorario, String claveEmpleado, char status, String fechaAsignacion){
        this.fechaAsignacion = fechaAsignacion;
        this.idHorario = idHorario;
        this.claveEmpelado = claveEmpleado;
        this.status=status;
    }
    
    public String guarda(String idUsr) throws SQLException{
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spGuaradaHorarioEmpleado('"+claveEmpelado+"',"+idHorario+","+idUsr+",curdate(),'"+status+"');");
        r.next();
        String resultado = r.getString("mensaje");
        
        bd.cierraConexion();
        
        return resultado;
    }
    
    public String modifica(String idUsr) throws SQLException{
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spModificaHorarioEmpleado('"+claveEmpelado+"',"+idHorario+","+idUsr+",'"+status+"');");
        r.next();
        String resultado = r.getString("mensaje");
        
        bd.cierraConexion();
        
        return resultado;
    }
    
    public String borra() throws SQLException{
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spBorraHorarioEmpleado('"+claveEmpelado+"',"+idHorario+");");
        r.next();
        String resultado = r.getString("mensaje");
        
        bd.cierraConexion();
        return resultado;
    }
    
    
    public void setStatus(char status){
        this.status=status;
    }

    public String getIdHorario() {
        return idHorario;
    }

    public String getClaveEmpelado() {
        return claveEmpelado;
    }
    
    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public char getStatus() {
        return status;
    }
    
    
    
}
