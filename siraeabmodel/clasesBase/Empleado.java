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
public class Empleado {
    private String clave;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String curp;
    private String fechaInicio;
    private String calle;
    private String numeroEx;
    private String numeroIn;
    private String colonia;
    private String codigoPostal;
    private String estado;
    private String municipio;
    private char estatus;
    private boolean existe;

    public Empleado(String clave) throws SQLException {
        this.clave = clave;
        existe=consultaEmpleado(this.clave);
    }
    
    public Empleado(){
    clave="";
    nombre="";
    apellidoPaterno="";
    apellidoMaterno="";
    curp="";
    fechaInicio="";
    calle="";
    numeroEx="";
    numeroIn="";
    colonia="";
    codigoPostal="";
    estado="";
    municipio="";
    estatus=' ';
    existe=false;
    
    }
    
    public boolean getExiste(){
        return existe;
    }
    
    public int borraEmpleado(String id, String clave) throws SQLException{
        int resultado=0;
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r= bd.consulta("call spBorraEmpleado('"+clave+"', '"+id+"');");
        r.next();
        resultado = Integer.parseInt(r.getString("resultado"));
        bd.cierraConexion();
        return resultado;
    }
    
    public boolean consultaEmpleado(String clave) throws SQLException{
        
        BaseDatos db=new BaseDatos();
        db.conectar();
        ResultSet r=db.consulta("select * from empleadosComp where clave='"+clave+"';");
        if(r.next()){
            this.nombre=r.getString("nombre");
            this.apellidoPaterno=r.getString("apellidoPaterno");
            this.apellidoMaterno=r.getString("apellidoMaterno");
            this.curp=r.getString("curp");
            this.fechaInicio=r.getString("fechaInicio");
            this.calle=r.getString("calle");
            this.numeroEx=r.getString("numeroEx");
            this.numeroIn=r.getString("numeroIn");
            this.colonia=r.getString("colonia");
            this.codigoPostal=r.getString("codigoPostal");
            this.estado=r.getString("estado");
            this.municipio=r.getString("municipio");
            this.estatus=r.getString("estatus").charAt(0);
        }else{
            return false;
        }
        db.cierraConexion();
        return true;
    }
    
//    public boolean consultaEmpleado(String clave, String nombre, String paterno, String materno, String curp
//                                    ,String fecha, String calle, String exterior, String Interior) throws SQLException{
//        
//        BaseDatos db=new BaseDatos();
//        db.conectar();
//        ResultSet r=db.consulta("select * from empleadosComp where clave='"+clave+"';");
//        if(r.next()){
//            this.nombre=r.getString("nombre");
//            this.apellidoPaterno=r.getString("apellidoPaterno");
//            this.apellidoMaterno=r.getString("apellidoMaterno");
//            this.curp=r.getString("curp");
//            this.fechaInicio=r.getString("fechaInicio");
//            this.calle=r.getString("calle");
//            this.numeroEx=r.getString("numeroEx");
//            this.numeroIn=r.getString("numeroIn");
//            this.colonia=r.getString("colonia");
//            this.codigoPostal=r.getString("codigoPostal");
//            this.estado=r.getString("estado");
//            this.municipio=r.getString("municipio");
//            this.estatus=r.getString("estatus").charAt(0);
//        }else{
//            return false;
//        }
//        db.cierraConexion();
//        return true;
//    }
    
    public int guardaEmpleado(String id, String clave, String nombre, String apellidoPaterno, String apellidoMaterno,
            String curp, String fechaInicio, String calle, String numeroEx, String numeroIn, String colonia, String codigoPostal,
            String estado, String municipio, String estatus) throws SQLException{
        int resultado=0;
        BaseDatos bd= new BaseDatos();
        bd.conectar();
        ResultSet r= bd.consulta("call spRegistraEmpleado('"+clave+"', '"+nombre+"', '"+apellidoPaterno+"', '"+apellidoMaterno+"', '"+curp+"','"+fechaInicio+"','"+
        calle+"', '"+numeroEx+"', '"+numeroIn+"','"+colonia+"','"+codigoPostal+"','"+estado+"','"+municipio+"','"+estatus+"',"+id+");");
        r.next();
        resultado = Integer.parseInt(r.getString("resultado"));
        bd.cierraConexion();
        return resultado;
    }
    
    public int modificaEmpleado(String id,String clave, String nombre, String apellidoPaterno, String apellidoMaterno,
            String calle, String numeroEx, String numeroIn, String colonia, String codigoPostal,
            String estado, String municipio, String estatus, String curp) throws SQLException{
            int resultado=0;
            clave=clave.equals("")?this.clave:clave;
            curp=curp.equals("")?this.curp:curp;
            nombre=nombre.equals("")?this.nombre:nombre;
            apellidoPaterno=apellidoPaterno.equals("")?this.apellidoPaterno:apellidoPaterno;
            apellidoMaterno=apellidoMaterno.equals("")?this.apellidoMaterno:apellidoMaterno;
            numeroEx=numeroEx.equals("")?this.numeroEx:numeroEx;
            numeroIn=numeroIn.equals("")?this.numeroIn:numeroIn;
            colonia=colonia.equals("")?this.colonia:colonia;
            codigoPostal=codigoPostal.equals("")?this.codigoPostal:codigoPostal;
            estado=estado.equals("")?this.estado:estado;
            municipio=municipio.equals("")?this.municipio:municipio;
            estatus=estatus.equals("")?this.estatus+"":estatus;            
            
            BaseDatos bd= new BaseDatos();
            bd.conectar();
            ResultSet r= bd.consulta("call spModificaEmpleado('"+clave+"', '"+nombre+"', '"+apellidoPaterno+"','"+apellidoMaterno+"'," +
            "	'"+curp+"', '"+calle+"', '"+numeroEx+"','"+numeroIn+"'," +
            "    '"+colonia+"','"+codigoPostal+"', '"+estado+"','"+municipio+"','"+estatus+"', "+id+");");
            r.next();
            resultado = Integer.parseInt(r.getString("resultado"));
            bd.cierraConexion();
        
            
        return resultado;
    }
    
    public String getClave() {
        return clave;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getCurp() {
        return curp;
    }

    public String getFecha() {
        return fechaInicio;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumeroEx() {
        return numeroEx;
    }

    public String getNumeroIn() {
        return numeroIn;
    }

    public String getColonia() {
        return colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getEstado() {
        return estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public char getEstatus() {
        return estatus;
    }
    
    
    
    
    
}
