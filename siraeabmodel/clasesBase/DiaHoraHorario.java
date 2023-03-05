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
public class DiaHoraHorario {
    private Hora horaInicio;
    private Hora horaFin;
    private int dia;
    private char estatus;

    public DiaHoraHorario(String horaInicio, String horaFin, int dia, char estatus) {
        this.horaInicio = new Hora(horaInicio);
        this.horaFin = new Hora(horaFin);
        this.dia = dia;
        this.estatus = estatus;
    }
    
    public int guardaBase(String idHorario) throws SQLException{
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spGuardaDetalleHorario('"+idHorario+"', '"+dia+"','"+horaInicio.toString()+"', '21:00', 3, 'A');");
        
        
        
        bd.cierraConexion();
        
        return 0;
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

    public void setHoraInicio(Hora horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Hora getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Hora horaFin) {
        this.horaFin = horaFin;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
    
    
}
