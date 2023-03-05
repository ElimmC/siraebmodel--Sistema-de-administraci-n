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
public class Horario {
    
    private String descripcion;
    private char status;
    private int id;
    private ArrayList<DetalleHorario>detalles;
    private int numD;
     
    public Horario(String descripcion, char status){
        this.status=status;
        this.descripcion=descripcion;
    }
    public Horario(int id){
        this.id=id;
    }
    
    public Horario(int id,String descripcion, char status){
        this.id=id;
        this.status=status;
        this.descripcion=descripcion;
    }
    public boolean buscarHorario() throws SQLException{
        boolean res;
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("select * from horarios where id="+id);
        res=r.next();
        if(res){
            descripcion = r.getString("descripcion");
            status = r.getString("estatus").charAt(0);
            if(Integer.parseInt(r.getString("numeroDetalles"))>0){
                detalles=new ArrayList<>();
                r=bd.consulta("select * from detalleHorario where idHorario="+id);
                DetalleHorario detalle;
                while(r.next()){
                    detalle= new DetalleHorario(r.getString("entrada"), r.getString("salida"),
                            Integer.parseInt(r.getString("dia")), r.getString("estatus").charAt(0), id+"");
                    detalles.add(detalle);
                }
            }
        }
        bd.cierraConexion();
        
        return res;
    }
    public boolean buscarDetalles() throws SQLException{
        boolean res;
        detalles=new ArrayList<>();
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("select * from detalleHorario where idHorario="+id);
        res=r.next();
        if(res){
                DetalleHorario detalle;
                detalle= new DetalleHorario(r.getString("entrada"), r.getString("salida"),
                            Integer.parseInt(r.getString("dia")), r.getString("estatus").charAt(0), id+"");
                    detalles.add(detalle);
                while(r.next()){
                    detalle= new DetalleHorario(r.getString("entrada"), r.getString("salida"),
                            Integer.parseInt(r.getString("dia")), r.getString("estatus").charAt(0), id+"");
                    detalles.add(detalle);
                }
        }
        return res;
    }
    
    public int modificaHorario(String idUsr) throws SQLException{
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spModificaHorario("+id+", '"+descripcion+"','"+status+"',"+idUsr+")");
        r.next();
        int resultado = Integer.parseInt(r.getString("resultado"));
        
        bd.cierraConexion();
        
        return resultado;
        
    }
    
    public int borraDetalle(String id) throws SQLException{
        
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spBorraHorario("+id+")");
        r.next();
        int resultado = Integer.parseInt(r.getString("resultado"));
        
        bd.cierraConexion();
        
        return resultado;
    }

    public int guardaHorario(String idUsr) throws SQLException{
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r=bd.consulta("call spGuardaHorario('"+descripcion+"', "+idUsr+",'"+status+"');");
        
        r.next();
        int resultado = Integer.parseInt(r.getString("resultado"));
        
        bd.cierraConexion();
        
        return resultado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public int getNumDetalles() {
        return numD;
    }

    public void setNumDetalles(int numD) {
        this.numD = numD;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
    
    public ArrayList<DetalleHorario> getDetalles(){
        return detalles;
    }
    
    public int agregarDetalle(String inicio, String fin, int dia, char status, String idUsr) throws SQLException{
        DetalleHorario detalle= new DetalleHorario(inicio, fin, dia, status, id+"");
        return detalle.guardaDetalle(idUsr);
    }
    
}
