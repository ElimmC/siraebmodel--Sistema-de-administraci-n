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
public class DetalleHorario {
    private Hora horaInicio;
    private Hora horaFin;
    private final int dia;
    private char estatus;
    private final String idHorario;

    public DetalleHorario(String horaInicio, String horaFin, int dia, char estatus,String idHorario) {
        this.horaInicio = new Hora(horaInicio);
        this.horaFin = new Hora(horaFin);
        this.dia = dia;
        this.estatus = estatus;
        this.idHorario = idHorario;
    }
    
    public int modificaDetalle(String idUsr) throws SQLException{
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spModificaDetalleHorario("+idHorario+", "+dia+",'"+horaInicio.toString()+"','"+horaFin.toString() +"',"+idUsr+",'" + estatus+"')");
        r.next();
        int resultado = Integer.parseInt(r.getString("resultado"));
        
        bd.cierraConexion();
        
        return resultado;
        
    }
    
    public int borraDetalle() throws SQLException{
        
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spBorraDetalleHorario("+idHorario+","+ dia+")");
        r.next();
        int resultado = Integer.parseInt(r.getString("resultado"));
        
        bd.cierraConexion();
        
        return resultado;
    }

    public int guardaDetalle(String idUsr) throws SQLException{
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spGuardaDetalleHorario("+idHorario+", "+dia+",'"+horaInicio.toString()+"', '"+horaFin.toString()+"'," +idUsr+", '"+estatus+"');");
        
        r.next();
        int resultado = Integer.parseInt(r.getString("resultado"));
        
        bd.cierraConexion();
        
        return resultado;
    }
    
    public char getEstatus() {
        return estatus;
    }

    public void setEstatus(char estatus) {
        this.estatus = estatus;
    }

    public Hora getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = new Hora( horaInicio);
    }

    public Hora getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = new Hora(horaFin);
    }
    
//    public void setDia(int dia) {
//        this.dia = dia;
//    }

    public int getDia() {
        return dia;
    }
    
    public String getIdHorario(){
        return idHorario;
    }
    
    
}
