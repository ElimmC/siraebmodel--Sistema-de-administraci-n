/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siraeabmodel;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import siraeabmodel.clasesBase.Asistencia;
import siraeabmodel.clasesBase.BaseDatos;
import siraeabmodel.clasesBase.Empleado;
import siraeabmodel.clasesBase.Estados;
import siraeabmodel.clasesBase.Municipios;
import siraeabmodel.clasesBase.Horario;
import siraeabmodel.clasesBase.DetalleHorario;
import siraeabmodel.clasesBase.HorarioEmpleado;
import siraeabmodel.clasesBase.Incidencia;
/**
 *
 * @author Sonia
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    String id="";
    ArrayList<String[]> esta;
    
    
    public Principal(String id) throws SQLException {
        Estados es= new Estados();
        esta=es.getEstados();
        initComponents();
        mostrarTabla();
        mostrarTablaHorario();
        mostrarTablaAsignaciones();
        mostrarTablaArchivo0();
        this.id=id;
        for (int i = 0; i < esta.size(); i++) {
            cEstados.addItem(esta.get(i)[1]);
        }
        
    }
    
    void mostrarTabla() throws SQLException{
        DefaultTableModel modelo = new DefaultTableModel();    
        modelo.addColumn("Nombre");
        modelo.addColumn("Paterno");
        modelo.addColumn("Materno");
        modelo.addColumn("Curp");
        modelo.addColumn("Fecha de ingreso");
        modelo.addColumn("Calle");
        modelo.addColumn("Exterior");
        modelo.addColumn("Interior");
        modelo.addColumn("Colonia");
        modelo.addColumn("CP");
        modelo.addColumn("Estado");
        modelo.addColumn("Municipio");
        modelo.addColumn("Situacion");       
        modelo.addColumn("Clave");
        tabladatos.setModel(modelo);
        BaseDatos conex = new BaseDatos();
        String datos[] = new String[14];
        
        try{           
            conex.conectar();   
            ResultSet rsguarda = conex.consulta("select * from empleadosComp;");
                while(rsguarda.next()){
                    for(int i=0;i<14;i++){
                datos[i] = rsguarda.getString((i+1));                
                    }
                    modelo.addRow(datos);
            }
            tabladatos.setModel(modelo);
             
        }catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void mostrarTabla2(Empleado emp) throws SQLException{
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Paterno");
        modelo.addColumn("Materno");
        modelo.addColumn("Curp");
        modelo.addColumn("Fecha de ingreso");
        modelo.addColumn("Calle");
        modelo.addColumn("Exterior");
        modelo.addColumn("Interior");
        modelo.addColumn("Colonia");
        modelo.addColumn("CP");
        modelo.addColumn("Estado");
        modelo.addColumn("Municipio");
        modelo.addColumn("Situacion");       
        modelo.addColumn("Clave");
        tabladatos.setModel(modelo);

        String datos[] = new String[14];
        
        
        try{                         
            datos[0]=emp.getNombre();
            datos[1]=emp.getApellidoPaterno();
            datos[2]=emp.getApellidoMaterno();
            datos[3]=emp.getCurp();
            datos[4]=emp.getFecha();
            datos[5]=emp.getCalle();
            datos[6]=emp.getNumeroEx();
            datos[7]=emp.getNumeroIn();
            datos[8]=emp.getColonia();
            datos[9]=emp.getCodigoPostal();
            datos[10]=emp.getEstado();
            datos[11]=emp.getMunicipio();
            datos[12]=""+emp.getEstatus();
            datos[13]=emp.getClave();
            
            modelo.addRow(datos);
            tabladatos.setModel(modelo);
        }catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void mostrarTablaHorario() throws SQLException{
        DefaultTableModel modelo = new DefaultTableModel();    
        modelo.addColumn("Clave");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Dia");
        modelo.addColumn("Entrada");
        modelo.addColumn("Salida");
        modelo.addColumn("Estado");
        tablaHorarios.setModel(modelo);
        BaseDatos conex = new BaseDatos();
        String datos[] = new String[6];
        datos[0]="";
        datos[2]="";
        datos[3]="";
        datos[4]="";
        datos[5]="";  
        try{           
            ArrayList<Horario> horarios=new ArrayList<>();
            Horario horario;
            BaseDatos bd = new BaseDatos();
            bd.conectar();
            ResultSet r= bd.consulta("select * from horarios");
            while(r.next()){
                horario=new Horario(Integer.parseInt(r.getString("id")), r.getString("descripcion"), r.getString("estatus").charAt(0));
                horario.setNumDetalles(Integer.parseInt(r.getString("numeroDetalles")));
                horario.buscarDetalles();
                horarios.add(horario);
            }
            bd.cierraConexion();
            for (Horario horario1 : horarios) {
                if(horario1.getNumDetalles()>0){
                 ArrayList<DetalleHorario> detalle=horario1.getDetalles();
                 datos[1]=""+horario1.getDescripcion();
                for (DetalleHorario detalleHorario : detalle) {
                    datos[0]=""+detalleHorario.getIdHorario();
                    datos[2]=""+detalleHorario.getDia();
                    datos[3]=""+detalleHorario.getHoraInicio();
                    datos[4]=""+detalleHorario.getHoraFin();
                    datos[5]=""+detalleHorario.getEstatus();  
                    modelo.addRow(datos);
                }
            }
                else{
                    datos[0]=""+horario1.getId();
                    datos[1]=""+horario1.getDescripcion();
                    datos[5]=""+horario1.getStatus();
                    datos[3]="n/a";
                    datos[4]="n/a";
                    datos[2]="n/a";
                    modelo.addRow(datos);
                }
            }
            tablaHorarios.setModel(modelo);
             
        }catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void mostrarTablaHorario2( int idH) throws SQLException{
        DefaultTableModel modelo = new DefaultTableModel();    
        modelo.addColumn("Clave");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Dia");
        modelo.addColumn("Entrada");
        modelo.addColumn("Salida");
        modelo.addColumn("Estado");
        modelo.addColumn("Num Detalle");
        tablaHorarios.setModel(modelo);
        String datos[] = new String[7];
        datos[0]=""+idH;
        try{           
            int i=1;
            Horario horario =new Horario(idH);
            horario.buscarHorario();
            horario.buscarDetalles();
            ArrayList<DetalleHorario> detalle=horario.getDetalles(); 
            if(!detalle.isEmpty()){
            for (DetalleHorario detalleHorario : detalle) {
                datos[1]=""+horario.getDescripcion();
                datos[3]=""+detalleHorario.getHoraInicio();
                datos[4]=""+detalleHorario.getHoraFin();
                datos[2]=""+detalleHorario.getDia();
                datos[5]=""+detalleHorario.getEstatus();
                datos[6]=""+i;
                i++;
                modelo.addRow(datos);
            }
            tablaHorarios.setModel(modelo);
        }
            else{
            datos[1]=""+horario.getDescripcion();
                datos[3]="n/a";
                datos[4]="n/a";
                datos[2]="n/a";
                datos[5]=""+horario.getStatus();
                datos[6]=""+i;
                modelo.addRow(datos);           
               tablaHorarios.setModel(modelo);
            }
        }catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void mostrarTablaAsignaciones() throws SQLException{
        DefaultTableModel modelo = new DefaultTableModel();    
        modelo.addColumn("Clave Empleado");
        modelo.addColumn("Clave Horario");
        modelo.addColumn("Fecha de asignacion");
        modelo.addColumn("Estado");
        tablaAsignaciones1.setModel(modelo);
        String datos[] = new String[4];
        datos[0]="";
        datos[1]="";
        datos[2]="";
        datos[3]="";
        try{           
            BaseDatos bd = new BaseDatos();
            bd.conectar();
            ResultSet r= bd.consulta("select * from horarioEmpleado");
            while(r.next()){
                datos[0] = r.getString(2);                
                datos[1] = r.getString(1);
                datos[2] = r.getString(3);
                datos[3] = r.getString(4);
                    modelo.addRow(datos);
            }
            bd.cierraConexion();
            tablaAsignaciones1.setModel(modelo);
             
        }catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void mostrarAsignaciones2(String horEmp) throws SQLException{

        try{           
            BaseDatos bd = new BaseDatos();
            bd.conectar();
            ResultSet r= bd.consulta("select * from horarioEmpleado where claveEmpleado = '"+horEmp+"'");
            if(r.next()){
                jClaveHorario2.setText(r.getString(1));
            }
            else{
            JOptionPane.showMessageDialog(null, "No existe el empleado");
            }
            bd.cierraConexion();            
        }catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void mostrarTablaArchivo(String clave, String fechaInicial, String fechaFinal) throws SQLException{
        DefaultTableModel modelo = new DefaultTableModel();    
        modelo.addColumn("Clave");
        modelo.addColumn("Fecha y hora");
        tablaArchivos.setModel(modelo);
        String datos[] = new String[2];
        datos[0]="";
        datos[1]="";
        try{           
            ArrayList<Asistencia> as=Asistencia.buscar(clave, fechaInicial, fechaFinal);
          for (Asistencia a : as) {
            datos[0]=a.getClaveEmpleado();
            datos[1]=a.getFecha();
            modelo.addRow(datos);
          }
            tablaArchivos.setModel(modelo);             
        }catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void mostrarTablaArchivo0() throws SQLException{
        DefaultTableModel modelo = new DefaultTableModel();    
        modelo.addColumn("Clave");
        modelo.addColumn("Fecha y Hora");
        tablaArchivos.setModel(modelo);
        String datos[] = new String[2];
        datos[0]="";
        datos[1]="";
        tablaArchivos.setModel(modelo);             
    }
    
    void mostrarTablaIncidencia() throws SQLException{
        DefaultTableModel modelo = new DefaultTableModel();    
        modelo.addColumn("Nombre");
        modelo.addColumn("Clave");
        modelo.addColumn("Fecha");
        modelo.addColumn("Tipo");
        tablaInicidencias.setModel(modelo);
        int cont=0;
        String datos[] = new String[4];
        datos[0]="";
        datos[1]="";
        datos[2]="";
        datos[3]="";
        try{           
            String msg=Incidencia.procesaIncidencas(incidenciaFecha1.getText(), incidenciaFecha2.getText(), this.id);
            if(msg.equalsIgnoreCase("Procesado correctamente")){
                ArrayList<Incidencia> incidencias=Incidencia.getIncidencias(incidenciaFecha1.getText(), incidenciaFecha2.getText());
                for (Incidencia incidencia : incidencias) {
                    datos[0]=incidencia.getNombre();
                    datos[1]=incidencia.getClave();
                    datos[2]=incidencia.getFecha();
                    datos[3]=incidencia.getTipo();
                    cont++;
                    modelo.addRow(datos);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, msg);
                modelo.addRow(datos);
            }
            tablaInicidencias.setModel(modelo);
            incidenciaTotal.setText(""+cont);
        }catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void limpiarTablaIncidencia(){
        DefaultTableModel modelo = new DefaultTableModel();    
        modelo.addColumn("Nombre");
        modelo.addColumn("Clave");
        modelo.addColumn("Fecha");
        modelo.addColumn("Tipo");
        tablaInicidencias.setModel(modelo);
        int cont=0;
        String datos[] = new String[4];
        datos[0]="";
        datos[1]="";
        datos[2]="";
        datos[3]="";
        modelo.addRow(datos);
        tablaInicidencias.setModel(modelo);
    }
    
    void limpiar(){
        jClave.setText("");
        jNombre.setText("");
        jPaterno.setText("");
        jMaterno.setText("");
        jExterior.setText("");
        jInterior.setText("");
        jCalle.setText("");
        jColonia.setText("");
        jCP.setText("");
        cEstados.setSelectedIndex(0);
        jSituacion.setText("");
        jFecha.setText("");
        jCurp.setText("");
    }
    
    void limpiarHorario(){
        tClaveHorario.setText("");
        tDescripcionHorario.setText("");
        tHoraEntrada.setText("");
        tHoraSalida.setText("");
    }
    
    void limpiarAsignacion(){
        jClaveHorario2.setText("");
        jClaveEmpleado2.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        javax.swing.JTabbedPane jTabbedPane1 = new javax.swing.JTabbedPane();
        javax.swing.JInternalFrame jInternalFrame1 = new javax.swing.JInternalFrame();
        jNombre = new javax.swing.JTextField();
        jPaterno = new javax.swing.JTextField();
        jMaterno = new javax.swing.JTextField();
        jClave = new javax.swing.JTextField();
        jExterior = new javax.swing.JTextField();
        jCalle = new javax.swing.JTextField();
        jInterior = new javax.swing.JTextField();
        jColonia = new javax.swing.JTextField();
        jCP = new javax.swing.JTextField();
        jSituacion = new javax.swing.JTextField();
        jFecha = new javax.swing.JTextField();
        javax.swing.JButton bAgregar = new javax.swing.JButton();
        javax.swing.JButton bTraer = new javax.swing.JButton();
        javax.swing.JButton jButton3 = new javax.swing.JButton();
        javax.swing.JButton bModificar = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        tabladatos = new javax.swing.JTable();
        javax.swing.JButton bEliminar = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel9 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel10 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel11 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel12 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel13 = new javax.swing.JLabel();
        javax.swing.JButton bLimpiar = new javax.swing.JButton();
        jCurp = new javax.swing.JTextField();
        javax.swing.JLabel jLabel14 = new javax.swing.JLabel();
        javax.swing.JButton jBuscar = new javax.swing.JButton();
        javax.swing.JLabel jLabel15 = new javax.swing.JLabel();
        cMunicipio = new javax.swing.JComboBox<>();
        cEstados = new javax.swing.JComboBox<>();
        javax.swing.JInternalFrame jInternalFrame2 = new javax.swing.JInternalFrame();
        javax.swing.JButton bAgregarHorario = new javax.swing.JButton();
        javax.swing.JButton bBuscarHorario = new javax.swing.JButton();
        javax.swing.JButton bTraerHorario = new javax.swing.JButton();
        javax.swing.JButton bModificarDetalle = new javax.swing.JButton();
        javax.swing.JButton bLimpiarHorario = new javax.swing.JButton();
        javax.swing.JButton bEliminarDetalle = new javax.swing.JButton();
        javax.swing.JLabel jLabel16 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel17 = new javax.swing.JLabel();
        tHoraEntrada = new javax.swing.JTextField();
        javax.swing.JLabel jLabel18 = new javax.swing.JLabel();
        tHoraSalida = new javax.swing.JTextField();
        javax.swing.JLabel jLabel19 = new javax.swing.JLabel();
        tDescripcionHorario = new javax.swing.JTextField();
        javax.swing.JLabel jLabel22 = new javax.swing.JLabel();
        tClaveHorario = new javax.swing.JTextField();
        javax.swing.JLabel jLabel23 = new javax.swing.JLabel();
        cDia = new javax.swing.JComboBox<>();
        javax.swing.JLabel jLabel20 = new javax.swing.JLabel();
        cEstado = new javax.swing.JComboBox<>();
        javax.swing.JButton bAgregarDetalles = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        tablaHorarios = new javax.swing.JTable();
        javax.swing.JLabel jLabel21 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel24 = new javax.swing.JLabel();
        javax.swing.JButton bModificarHorario = new javax.swing.JButton();
        tNumeroDetalle = new javax.swing.JTextField();
        javax.swing.JLabel jLabel25 = new javax.swing.JLabel();
        javax.swing.JInternalFrame jInternalFrame3 = new javax.swing.JInternalFrame();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        tablaAsignaciones1 = new javax.swing.JTable();
        jClaveEmpleado2 = new javax.swing.JTextField();
        javax.swing.JLabel jLabel26 = new javax.swing.JLabel();
        jClaveHorario2 = new javax.swing.JTextField();
        javax.swing.JLabel jLabel27 = new javax.swing.JLabel();
        cAsignacionEstado = new javax.swing.JComboBox<>();
        javax.swing.JLabel jLabel28 = new javax.swing.JLabel();
        bAsignar2 = new javax.swing.JButton();
        javax.swing.JButton bDesasignar = new javax.swing.JButton();
        javax.swing.JButton bModificarAsig = new javax.swing.JButton();
        bBuscarAsignacion = new javax.swing.JButton();
        javax.swing.JButton bTraerAsignacion = new javax.swing.JButton();
        javax.swing.JButton bLimpiarAsignacion = new javax.swing.JButton();
        javax.swing.JInternalFrame jInternalFrame4 = new javax.swing.JInternalFrame();
        javax.swing.JScrollPane jScrollPane4 = new javax.swing.JScrollPane();
        archivoReporte = new javax.swing.JTextArea();
        regTotal = new javax.swing.JTextField();
        javax.swing.JLabel jLabel29 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel30 = new javax.swing.JLabel();
        regExito = new javax.swing.JTextField();
        javax.swing.JLabel jLabel31 = new javax.swing.JLabel();
        regFallo = new javax.swing.JTextField();
        javax.swing.JScrollPane jScrollPane5 = new javax.swing.JScrollPane();
        tablaArchivos = new javax.swing.JTable();
        archivoClave = new javax.swing.JTextField();
        javax.swing.JLabel jLabel32 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel33 = new javax.swing.JLabel();
        archivoFecha1 = new javax.swing.JTextField();
        javax.swing.JLabel jLabel34 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel35 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel36 = new javax.swing.JLabel();
        archivoFecha2 = new javax.swing.JTextField();
        javax.swing.JLabel jLabel37 = new javax.swing.JLabel();
        javax.swing.JButton archivoLimpiar = new javax.swing.JButton();
        javax.swing.JButton archivoBuscar1 = new javax.swing.JButton();
        javax.swing.JMenuBar jMenuBar1 = new javax.swing.JMenuBar();
        javax.swing.JMenu archivos = new javax.swing.JMenu();
        javax.swing.JMenuItem AbrirArchivo = new javax.swing.JMenuItem();
        javax.swing.JInternalFrame jInternalFrame5 = new javax.swing.JInternalFrame();
        javax.swing.JScrollPane jScrollPane6 = new javax.swing.JScrollPane();
        tablaInicidencias = new javax.swing.JTable();
        incidenciaFecha1 = new javax.swing.JTextField();
        incidenciaFecha2 = new javax.swing.JTextField();
        javax.swing.JLabel jLabel38 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel39 = new javax.swing.JLabel();
        buscarIncidencia = new javax.swing.JButton();
        limpiaIncidencia = new javax.swing.JToggleButton();
        javax.swing.JLabel jLabel40 = new javax.swing.JLabel();
        incidenciaTotal = new javax.swing.JTextField();

        fileChooser.setDialogTitle("Seleccionar Archivo");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jInternalFrame1.setVisible(true);

        jNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNombreActionPerformed(evt);
            }
        });

        jClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClaveActionPerformed(evt);
            }
        });

        bAgregar.setText("Agregar");
        bAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarActionPerformed(evt);
            }
        });

        bTraer.setText("Traer");
        bTraer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTraerActionPerformed(evt);
            }
        });

        jButton3.setText("jButton3");

        bModificar.setText("Modificar");
        bModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarActionPerformed(evt);
            }
        });

        tabladatos.setAutoCreateRowSorter(true);
        tabladatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabladatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tabladatos);

        bEliminar.setText("Eliminar");
        bEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarActionPerformed(evt);
            }
        });

        jLabel1.setText("Situacion");

        jLabel2.setText("Fecha de ingreso");

        jLabel3.setText("C. P.");

        jLabel4.setText("Estado");

        jLabel5.setText("Municipio");

        jLabel6.setText("Calle");

        jLabel7.setText("Numero Exterior");

        jLabel8.setText("Numero Interior");

        jLabel9.setText("Colonia");

        jLabel10.setText("Clave");

        jLabel11.setText("Nombre");

        jLabel12.setText("Paterno");

        jLabel13.setText("Materno");

        bLimpiar.setText("Limpiar");
        bLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLimpiarActionPerformed(evt);
            }
        });

        jLabel14.setText("CURP");

        jBuscar.setText("Buscar");
        jBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuscarActionPerformed(evt);
            }
        });

        jLabel15.setText("Solo con Clave");

        cMunicipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cMunicipioActionPerformed(evt);
            }
        });

        cEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cEstadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jClave, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jCurp, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(136, 136, 136))
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel11)
                                                    .addComponent(jNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel12))
                                                .addGap(31, 31, 31)
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel13)
                                                    .addComponent(jMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel6))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(jExterior, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(20, 20, 20)
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jInterior, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel9)))
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCP, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel3))
                                                .addGap(10, 10, 10)
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel4))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5)
                                                    .addComponent(cMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(bLimpiar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                            .addComponent(bTraer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(14, 14, 14)
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(bModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                            .addComponent(bEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addComponent(bAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15)
                                            .addComponent(jBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)))))
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 46, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel14)
                            .addComponent(bAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCurp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPaterno))
                .addGap(1, 1, 1)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(bTraer, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jExterior, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jInterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCalle))
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Empleados", jInternalFrame1);

        jInternalFrame2.setVisible(true);

        bAgregarHorario.setText("Agregar Horario");
        bAgregarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarHorarioActionPerformed(evt);
            }
        });

        bBuscarHorario.setText("Buscar");
        bBuscarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBuscarHorarioActionPerformed(evt);
            }
        });

        bTraerHorario.setText("Traer");
        bTraerHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTraerHorarioActionPerformed(evt);
            }
        });

        bModificarDetalle.setText("Modificar Detalle");
        bModificarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarDetalleActionPerformed(evt);
            }
        });

        bLimpiarHorario.setText("Limpiar");
        bLimpiarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLimpiarHorarioActionPerformed(evt);
            }
        });

        bEliminarDetalle.setText("Eliminar Detalle");
        bEliminarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEliminarDetalleActionPerformed(evt);
            }
        });

        jLabel16.setText("Solo con clave");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Estado");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Hora Entrada");

        tHoraSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tHoraSalidaActionPerformed(evt);
            }
        });

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Hora Salida");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Descripción");

        tClaveHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tClaveHorarioActionPerformed(evt);
            }
        });

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Clave Horario");

        cDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" }));
        cDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cDiaActionPerformed(evt);
            }
        });

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Día");

        cEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        cEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cEstadoActionPerformed(evt);
            }
        });

        bAgregarDetalles.setText("Agregar Detalles");
        bAgregarDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarDetallesActionPerformed(evt);
            }
        });

        tablaHorarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaHorarios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jScrollPane2.setViewportView(tablaHorarios);

        jLabel21.setText(" Detalles");

        jLabel24.setText("No necesita Clave");

        bModificarHorario.setText("Mod Horario");
        bModificarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarHorarioActionPerformed(evt);
            }
        });

        tNumeroDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tNumeroDetalleActionPerformed(evt);
            }
        });

        jLabel25.setText("Numero de Detalle a eliminar o modificar");

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tClaveHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                                .addComponent(tDescripcionHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 46, Short.MAX_VALUE))
                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tNumeroDetalle, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .addComponent(cDia, javax.swing.GroupLayout.Alignment.LEADING, 0, 83, Short.MAX_VALUE))
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel21))
                                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18)
                                            .addComponent(tHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tHoraSalida, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(45, 67, Short.MAX_VALUE)
                                .addComponent(bAgregarDetalles)
                                .addGap(38, 38, 38))
                            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bModificarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bAgregarHorario)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(bBuscarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bLimpiarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bTraerHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bModificarDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bEliminarDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bAgregarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bBuscarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tDescripcionHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tClaveHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)))
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addGap(10, 10, 10)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jInternalFrame2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(bModificarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(bTraerHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bModificarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bLimpiarHorario, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                    .addComponent(bEliminarDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame2Layout.createSequentialGroup()
                                .addComponent(bAgregarDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tNumeroDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(274, 274, 274))
        );

        jTabbedPane1.addTab("Horarios", jInternalFrame2);

        jInternalFrame3.setVisible(true);

        tablaAsignaciones1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaAsignaciones1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jScrollPane3.setViewportView(tablaAsignaciones1);

        jClaveEmpleado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClaveEmpleado2ActionPerformed(evt);
            }
        });

        jLabel26.setText("Clave Empleado");

        jClaveHorario2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClaveHorario2ActionPerformed(evt);
            }
        });

        jLabel27.setText("Clave Horario");

        cAsignacionEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        cAsignacionEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cAsignacionEstadoActionPerformed(evt);
            }
        });

        jLabel28.setText("Estado");

        bAsignar2.setText("Asignar");
        bAsignar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAsignar2ActionPerformed(evt);
            }
        });

        bDesasignar.setText("Desasignar");
        bDesasignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDesasignarActionPerformed(evt);
            }
        });

        bModificarAsig.setText("Modificar estado");
        bModificarAsig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModificarAsigActionPerformed(evt);
            }
        });

        bBuscarAsignacion.setText("Buscar");
        bBuscarAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBuscarAsignacionActionPerformed(evt);
            }
        });

        bTraerAsignacion.setText("Traer");
        bTraerAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTraerAsignacionActionPerformed(evt);
            }
        });

        bLimpiarAsignacion.setText("Limpiar");
        bLimpiarAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLimpiarAsignacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame3Layout = new javax.swing.GroupLayout(jInternalFrame3.getContentPane());
        jInternalFrame3.getContentPane().setLayout(jInternalFrame3Layout);
        jInternalFrame3Layout.setHorizontalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame3Layout.createSequentialGroup()
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                        .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jClaveEmpleado2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                        .addGap(49, 49, 49)
                                        .addComponent(jLabel26)))
                                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(jClaveHorario2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(52, 52, 52)
                                        .addComponent(cAsignacionEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(jLabel27)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bDesasignar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bAsignar2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bModificarAsig, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(87, 87, 87))
                                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                        .addGap(91, 91, 91)
                                        .addComponent(bBuscarAsignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(bTraerAsignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bLimpiarAsignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jInternalFrame3Layout.setVerticalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jClaveEmpleado2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jClaveHorario2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cAsignacionEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bAsignar2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bModificarAsig, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bTraerAsignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bLimpiarAsignacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bDesasignar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bBuscarAsignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        jTabbedPane1.addTab("Asignacion Horario", jInternalFrame3);

        jInternalFrame4.setVisible(true);

        archivoReporte.setEditable(false);
        archivoReporte.setColumns(20);
        archivoReporte.setRows(5);
        jScrollPane4.setViewportView(archivoReporte);

        regTotal.setEditable(false);
        regTotal.setText("0");
        regTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regTotalActionPerformed(evt);
            }
        });

        jLabel29.setText("Totales");

        jLabel30.setText("Exitosos");

        regExito.setEditable(false);
        regExito.setText("0");

        jLabel31.setText("Fallados");

        regFallo.setEditable(false);
        regFallo.setText("0");

        tablaArchivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaArchivos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jScrollPane5.setViewportView(tablaArchivos);

        jLabel32.setText("Clave Empleado");

        jLabel33.setText("Reporte del archivo");

        jLabel34.setText("Fecha inicio");

        jLabel35.setText("YYYY-MM-DD");

        jLabel36.setText("Fecha Final");

        jLabel37.setText("YYYY-MM-DD");

        archivoLimpiar.setText("Limpiar");
        archivoLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivoLimpiarActionPerformed(evt);
            }
        });

        archivoBuscar1.setText("Buscar");
        archivoBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivoBuscar1ActionPerformed(evt);
            }
        });

        archivos.setText("Archivo");

        AbrirArchivo.setText("Abrir");
        AbrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirArchivoActionPerformed(evt);
            }
        });
        archivos.add(AbrirArchivo);

        jMenuBar1.add(archivos);

        jInternalFrame4.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jInternalFrame4Layout = new javax.swing.GroupLayout(jInternalFrame4.getContentPane());
        jInternalFrame4.getContentPane().setLayout(jInternalFrame4Layout);
        jInternalFrame4Layout.setHorizontalGroup(
            jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame4Layout.createSequentialGroup()
                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame4Layout.createSequentialGroup()
                                .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(regTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                                    .addComponent(regExito)
                                    .addComponent(regFallo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel33)
                                .addGap(86, 86, 86)))
                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame4Layout.createSequentialGroup()
                                .addComponent(archivoBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(archivoLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61))
                            .addGroup(jInternalFrame4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(archivoClave))
                                .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jInternalFrame4Layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(archivoFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(archivoFecha2))
                                    .addGroup(jInternalFrame4Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel35)
                                            .addComponent(jLabel34))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel36)
                                            .addComponent(jLabel37))
                                        .addGap(14, 14, 14))))))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE))
                .addContainerGap())
        );
        jInternalFrame4Layout.setVerticalGroup(
            jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel33))
                    .addGroup(jInternalFrame4Layout.createSequentialGroup()
                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(regTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(regExito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(regFallo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jInternalFrame4Layout.createSequentialGroup()
                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(archivoClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(archivoFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(archivoFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jLabel34)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(jLabel37))
                        .addGap(18, 18, 18)
                        .addGroup(jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(archivoLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(archivoBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registros", jInternalFrame4);

        jInternalFrame5.setVisible(true);

        tablaInicidencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaInicidencias.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jScrollPane6.setViewportView(tablaInicidencias);

        incidenciaFecha1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incidenciaFecha1ActionPerformed(evt);
            }
        });

        jLabel38.setText("Fecha inicio   AAA-MM-DD");

        jLabel39.setText("Fecha Final AAAA-MM-DD");

        buscarIncidencia.setText("Buscar");
        buscarIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarIncidenciaActionPerformed(evt);
            }
        });

        limpiaIncidencia.setText("Limpiar");
        limpiaIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiaIncidenciaActionPerformed(evt);
            }
        });

        jLabel40.setText("Total de Incidencias:");

        javax.swing.GroupLayout jInternalFrame5Layout = new javax.swing.GroupLayout(jInternalFrame5.getContentPane());
        jInternalFrame5.getContentPane().setLayout(jInternalFrame5Layout);
        jInternalFrame5Layout.setHorizontalGroup(
            jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jInternalFrame5Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame5Layout.createSequentialGroup()
                        .addComponent(buscarIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(limpiaIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(162, 162, 162)
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(incidenciaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jInternalFrame5Layout.createSequentialGroup()
                        .addGroup(jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jInternalFrame5Layout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(125, 125, 125))
                            .addGroup(jInternalFrame5Layout.createSequentialGroup()
                                .addComponent(incidenciaFecha1)
                                .addGap(110, 110, 110)))
                        .addGroup(jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(incidenciaFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jInternalFrame5Layout.setVerticalGroup(
            jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(incidenciaFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(incidenciaFecha2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(limpiaIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(incidenciaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Incidencias", jInternalFrame5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_bLimpiarActionPerformed

    private void bEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarActionPerformed
        String nombre="",paterno="",materno="",clave="",calle="",interior="",
        exterior="",colonia="",cp="",estado="",municipio="",situacion="",fecha="",curp="";
        clave=jClave.getText();
        nombre=jNombre.getText();
        paterno=jPaterno.getText();
        materno=jMaterno.getText();
        calle=jExterior.getText();
        interior=jInterior.getText();
        exterior=jCalle.getText();
        colonia=jColonia.getText();
        cp=jCP.getText();
        estado=cEstados.getSelectedItem().toString();
        municipio=cMunicipio.getSelectedItem().toString();
        situacion=jSituacion.getText();
        fecha=jFecha.getText();
        curp=jCurp.getText();
        try {
            Empleado conex = new Empleado();
            
            if(conex.borraEmpleado(this.id, clave)==1){
                JOptionPane.showMessageDialog(null, "Datos eliminados");
                limpiar();
                mostrarTabla();
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo eliminar");
                mostrarTabla();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bEliminarActionPerformed

    private void bModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarActionPerformed
        String nombre="",paterno="",materno="",clave="",calle="",interior="",
        exterior="",colonia="",cp="",estado="",municipio="",situacion="",fecha="", curp="";
        clave=jClave.getText();
        nombre=jNombre.getText();
        paterno=jPaterno.getText();
        materno=jMaterno.getText();
        calle=jExterior.getText();
        interior=jInterior.getText();
        exterior=jCalle.getText();
        colonia=jColonia.getText();
        cp=jCP.getText();
        estado=cEstados.getSelectedItem().toString();
        municipio=cMunicipio.getSelectedItem().toString();
        situacion=jSituacion.getText();
        fecha=jFecha.getText();
        curp=jCurp.getText();
        try {
            Empleado conex = new Empleado();
            
            int noti=conex.modificaEmpleado(this.id, clave, nombre, paterno, materno, calle, exterior, interior, colonia, cp, estado, municipio, situacion, curp);
            if(noti==1){
            JOptionPane.showMessageDialog(null, "Datos Modificados");
            limpiar();
            mostrarTabla();
           }
           else{
            JOptionPane.showMessageDialog(null, "No se ha logrado modificar");
            mostrarTabla();
           }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Datos NO actualizados");
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bModificarActionPerformed

    private void bTraerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTraerActionPerformed
        int fila = tabladatos.getSelectedRow();
        if(fila >= 0){

            jNombre.setText(tabladatos.getValueAt(fila, 0).toString());
            jPaterno.setText(tabladatos.getValueAt(fila, 1).toString());
            jMaterno.setText(tabladatos.getValueAt(fila, 2).toString());
            jCurp.setText(tabladatos.getValueAt(fila, 3).toString());
            jFecha.setText(tabladatos.getValueAt(fila, 4).toString());
            jExterior.setText(tabladatos.getValueAt(fila, 5).toString());
            jCalle.setText(tabladatos.getValueAt(fila, 6).toString());
            jInterior.setText(tabladatos.getValueAt(fila, 7).toString());
            jColonia.setText(tabladatos.getValueAt(fila, 8).toString());
            jCP.setText(tabladatos.getValueAt(fila, 9).toString());
            cEstados.setSelectedItem(tabladatos.getValueAt(fila, 10).toString());
            cMunicipio.setSelectedItem(tabladatos.getValueAt(fila, 11).toString());
            jSituacion.setText(tabladatos.getValueAt(fila, 12).toString());
            jClave.setText(tabladatos.getValueAt(fila, 13).toString());

        }else{
            JOptionPane.showMessageDialog(null, "Fila no seleccionada");
        }
    }//GEN-LAST:event_bTraerActionPerformed

    private void bAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarActionPerformed
        String nombre="",paterno="",materno="",clave="",calle="",interior="",
        exterior="",colonia="",cp="",estado="",municipio="",situacion="",fecha="",curp="";
        clave=jClave.getText();
        nombre=jNombre.getText();
        paterno=jPaterno.getText();
        materno=jMaterno.getText();
        calle=jExterior.getText();
        interior=jInterior.getText();
        exterior=jCalle.getText();
        colonia=jColonia.getText();
        cp=jCP.getText();
        estado=cEstados.getSelectedItem().toString();
        municipio=cMunicipio.getSelectedItem().toString();
        situacion=jSituacion.getText();
        fecha=jFecha.getText();
        curp=jCurp.getText();
        try {
            Empleado conex = new Empleado();
           int noti=conex.guardaEmpleado(this.id, clave, nombre, paterno, materno, curp, fecha, calle, exterior, interior, colonia, cp, estado, municipio, situacion);
           if(noti==1){
            JOptionPane.showMessageDialog(null, "Datos guardados");
            limpiar();
            mostrarTabla();
           }
           else{
            JOptionPane.showMessageDialog(null, "La clave de el empleado ya existe");
            mostrarTabla();
           }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bAgregarActionPerformed

    private void jClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jClaveActionPerformed

    private void jNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jNombreActionPerformed

    private void jBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuscarActionPerformed
        Empleado busc = new Empleado();
        try {
            busc.consultaEmpleado(jClave.getText());
            mostrarTabla2(busc);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBuscarActionPerformed

    private void cMunicipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cMunicipioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cMunicipioActionPerformed

    private void cEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cEstadosActionPerformed
        // TODO add your handling code here:
        ArrayList<String[]> mun;
        Municipios mu;
        cMunicipio.removeAllItems();
        
        try {
            mu = new Municipios(esta.get(cEstados.getSelectedIndex())[0]);
            mun=mu.getMunicipios();
            
            for (int i = 0; i < mun.size(); i++) {
                cMunicipio.addItem(mun.get(i)[1]);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_cEstadosActionPerformed

    private void tHoraSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tHoraSalidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tHoraSalidaActionPerformed

    private void cDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cDiaActionPerformed

    private void tClaveHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tClaveHorarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tClaveHorarioActionPerformed

    private void bAgregarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarHorarioActionPerformed
     Horario horario2=new Horario(Integer.parseInt(tClaveHorario.getText()));
        try {
            if((!tDescripcionHorario.getText().equals("")) && !horario2.buscarHorario()){
                if(cEstado.getSelectedItem().equals("Activo")){
                    Horario horario=new Horario(tDescripcionHorario.getText(), 'A');
                    try {
                        int f=horario.guardaHorario(id);
                        if(f==1){
                            JOptionPane.showMessageDialog(null, "Guardado con exito");
                            mostrarTablaHorario();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "No se ha guardado");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    Horario horario=new Horario(tDescripcionHorario.getText(), 'I');
                    try {
                        int f=horario.guardaHorario(id);
                        if(f==1){
                            JOptionPane.showMessageDialog(null, "Guardado con exito");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "No se ha guardado");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
            else{
                if(!horario2.buscarHorario()){
                JOptionPane.showMessageDialog(null, "Ya existe la clave");
                }
                else{
                JOptionPane.showMessageDialog(null, "Complete los campos");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bAgregarHorarioActionPerformed

    private void bTraerHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTraerHorarioActionPerformed
        int fila = tablaHorarios.getSelectedRow();
        String dias=tablaHorarios.getValueAt(fila, 2).toString();
        if(fila >= 0){
            tClaveHorario.setText(tablaHorarios.getValueAt(fila, 0).toString());
            tDescripcionHorario.setText(tablaHorarios.getValueAt(fila, 1).toString());
            tHoraEntrada.setText(tablaHorarios.getValueAt(fila, 3).toString());
            tHoraSalida.setText(tablaHorarios.getValueAt(fila, 4).toString());
            switch(dias){
                case "1":cDia.setSelectedIndex(0);
                    break;
                case"2":cDia.setSelectedIndex(1);
                    break;
                case "3":cDia.setSelectedIndex(2);
                    break;
                case"4":cDia.setSelectedIndex(3);
                    break;
                case "5":cDia.setSelectedIndex(4);
                    break;
                case"6":cDia.setSelectedIndex(5);
                    break;
                case "7":cDia.setSelectedIndex(6);
                    break;
                default:cDia.setSelectedIndex(0);
                    break;
            }
            if(tablaHorarios.getValueAt(fila, 5).toString().equalsIgnoreCase("i")){
                cEstado.setSelectedItem("Inactivo");
            }
            else{
                cEstado.setSelectedItem("Activo");
            }
            if(tablaHorarios.getColumnCount()>=7)
            tNumeroDetalle.setText(tablaHorarios.getValueAt(fila, 6).toString()); //Maracara error si no se busca un horario, pero es a proposito
            if(tablaHorarios.getColumnCount()<7)
            tNumeroDetalle.setText(""); //Maracara error si no se busca un horario, pero es a proposito
        }else{
            JOptionPane.showMessageDialog(null, "Fila no seleccionada");
        }
    }//GEN-LAST:event_bTraerHorarioActionPerformed

    private void cEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cEstadoActionPerformed

    private void bBuscarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBuscarHorarioActionPerformed
        String idHorario= tClaveHorario.getText();
        if (idHorario.isEmpty()){
          JOptionPane.showMessageDialog(null, "Agregue el id del horario");
        }
        else{
            Horario horario=new Horario(Integer.parseInt(idHorario));
            try {
                if(horario.buscarHorario()){
                    mostrarTablaHorario2(Integer.parseInt(idHorario));
                }
                else{
                    JOptionPane.showMessageDialog(null, "No existe el horario");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }//GEN-LAST:event_bBuscarHorarioActionPerformed

    private void bLimpiarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLimpiarHorarioActionPerformed
        limpiarHorario();
        try {
            mostrarTablaHorario();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bLimpiarHorarioActionPerformed

    private void bModificarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarHorarioActionPerformed
        String desc=tDescripcionHorario.getText();
        String clave=tClaveHorario.getText();
        char estat;
        if(cEstado.getSelectedItem().equals("Activo")){
           estat='A';
        }
        else{
           estat='I';
        }
        Horario horario=new Horario(Integer.parseInt(clave));
        try {
            if(horario.buscarHorario()){
                horario.setDescripcion(desc);
                horario.setStatus(estat);
                if(horario.modificaHorario(id)==0){
                    JOptionPane.showMessageDialog(null, "Modificado exitosamente");
                    mostrarTablaHorario();
                }
            }
            else{
            JOptionPane.showMessageDialog(null, "no existe el horario");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bModificarHorarioActionPerformed

    private void bModificarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarDetalleActionPerformed
        String clave=tClaveHorario.getText();
        int modDetalle=Integer.parseInt(tNumeroDetalle.getText());
        String ini=tHoraEntrada.getText();
        String fi=tHoraSalida.getText();
        String dia=cDia.getSelectedItem().toString();
        int numDia=0;
        switch(dia){
            case "Lunes":numDia=1;
                break;
            case "Martes":numDia=2;
                break;
            case "Miercoles":numDia=3;
                break;
            case "Jueves":numDia=4;
                break;
            case "Viernes":numDia=5;
                break;
            case "Sabado":numDia=6;
                break;
            case "Domingo":numDia=7;
                break;
            default:numDia=0;
                break;
        }
        char estat;
        if(cEstado.getSelectedItem().equals("Activo")){
           estat='A';
        }
        else{
           estat='I';
        }
        try{
        Horario horario=new Horario(Integer.parseInt(clave));
         if(!horario.buscarHorario() || tNumeroDetalle.getText().isEmpty()){
             JOptionPane.showMessageDialog(null, "no existe el horario");
         }
            else{             
             horario.buscarDetalles();            
             ArrayList<DetalleHorario> detalle1=horario.getDetalles();
             detalle1.get(modDetalle-1).setEstatus(estat);
//             detalle1.get(detalle1.size()-1).setDia(numDia);
             detalle1.get(modDetalle-1).setHoraFin(fi);
             detalle1.get(modDetalle-1).setHoraInicio(ini);
             int agre=detalle1.get(modDetalle-1).modificaDetalle(id);
             if(agre==1){
                 JOptionPane.showMessageDialog(null, "Se modifico con exito");
                 mostrarTablaHorario();
             }
             else{
                 JOptionPane.showMessageDialog(null, "No se modifico con exito");
             }
         }
        }
        catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
    }//GEN-LAST:event_bModificarDetalleActionPerformed

    private void bAgregarDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarDetallesActionPerformed
        String clave=tClaveHorario.getText();
        String ini=tHoraEntrada.getText();
        String fi=tHoraSalida.getText();
        String dia=cDia.getSelectedItem().toString();
        int numDia;
        switch(dia){
            case "Lunes":numDia=1;
                break;
            case "Martes":numDia=2;
                break;
            case "Miercoles":numDia=3;
                break;
            case "Jueves":numDia=4;
                break;
            case "Viernes":numDia=5;
                break;
            case "Sabado":numDia=6;
                break;
            case "Domingo":numDia=7;
                break;
            default:numDia=0;
                break;
        }
        char estat;
        if(cEstado.getSelectedItem().equals("Activo")){
           estat='A';
        }
        else{
           estat='I';
        }
        Horario horario=new Horario(Integer.parseInt(clave));
        try {
            if(horario.buscarHorario()){
                int agre=horario.agregarDetalle(ini, fi, numDia, estat, id);
                if(agre==1){
                    JOptionPane.showMessageDialog(null, "Se agrego con exito");
                    mostrarTablaHorario();
                }
                else{
                    JOptionPane.showMessageDialog(null, "No se agrego con exito");
                }
            }
            else{
            JOptionPane.showMessageDialog(null, "no existe el horario");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bAgregarDetallesActionPerformed

    private void bEliminarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEliminarDetalleActionPerformed
        String clave=tClaveHorario.getText();
        String modDetalle=tNumeroDetalle.getText();
        try{
        Horario horario=new Horario(Integer.parseInt(clave));
         if(!horario.buscarHorario() || tNumeroDetalle.getText().isEmpty()){
             JOptionPane.showMessageDialog(null, "no existe el horario");
         }
            else{             
             horario.buscarDetalles();            
             ArrayList<DetalleHorario> detalle1=horario.getDetalles();
             int agre=detalle1.get(Integer.parseInt(modDetalle)-1).borraDetalle();
             if(agre==1){
                 JOptionPane.showMessageDialog(null, "Se elimino con exito");
                 mostrarTablaHorario();
             }
             else{
                 JOptionPane.showMessageDialog(null, "No se elimino con exito");
             }
         }
        }
        catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
    }//GEN-LAST:event_bEliminarDetalleActionPerformed

    private void tNumeroDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tNumeroDetalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tNumeroDetalleActionPerformed

    private void jClaveEmpleado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClaveEmpleado2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jClaveEmpleado2ActionPerformed

    private void jClaveHorario2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClaveHorario2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jClaveHorario2ActionPerformed

    private void cAsignacionEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cAsignacionEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cAsignacionEstadoActionPerformed

    private void bAsignar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAsignar2ActionPerformed
        String idEmpAsig = jClaveEmpleado2.getText();
        String idHrAsig = jClaveHorario2.getText();
        char estadoAsig;
        if(cAsignacionEstado.getSelectedItem().toString().equalsIgnoreCase("activo")){
            estadoAsig = 'A';
        }
        else{
            estadoAsig = 'I';
        }
        HorarioEmpleado horEmp=new HorarioEmpleado(idHrAsig, idEmpAsig, estadoAsig);
        try {
            String mensaje=horEmp.guarda(""+this.id);
            JOptionPane.showMessageDialog(null, mensaje);
            mostrarTablaAsignaciones();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_bAsignar2ActionPerformed

    private void bDesasignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDesasignarActionPerformed
        String idEmpAsig = jClaveEmpleado2.getText();
        String idHrAsig = jClaveHorario2.getText();
        char estadoAsig ;
        if(cAsignacionEstado.getSelectedItem().toString().equalsIgnoreCase("activo")){
            estadoAsig = 'A';
        }
        else{
            estadoAsig = 'I';
        }
        HorarioEmpleado horEmp=new HorarioEmpleado(idHrAsig, idEmpAsig, estadoAsig);
        try {
            String mensaje=horEmp.borra();
            JOptionPane.showMessageDialog(null, mensaje);
            mostrarTablaAsignaciones();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bDesasignarActionPerformed

    private void bModificarAsigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModificarAsigActionPerformed
        String idEmpAsig = jClaveEmpleado2.getText();
        String idHrAsig = jClaveHorario2.getText();
        char estadoAsig ;
        if(cAsignacionEstado.getSelectedItem().toString().equalsIgnoreCase("activo")){
            estadoAsig = 'A';
        }
        else{
            estadoAsig = 'I';
        }
        HorarioEmpleado horEmp=new HorarioEmpleado(idHrAsig, idEmpAsig, estadoAsig);
        try {
            String mensaje=horEmp.modifica(""+this.id);
            JOptionPane.showMessageDialog(null, mensaje);
            mostrarTablaAsignaciones();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bModificarAsigActionPerformed

    private void bBuscarAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBuscarAsignacionActionPerformed
        String idEmpAsig = jClaveEmpleado2.getText();
        try {
            mostrarAsignaciones2(idEmpAsig);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bBuscarAsignacionActionPerformed

    private void bTraerAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTraerAsignacionActionPerformed
        int fila = tablaAsignaciones1.getSelectedRow();
        jClaveHorario2.setText(tablaAsignaciones1.getValueAt(fila, 1).toString());
        jClaveEmpleado2.setText(tablaAsignaciones1.getValueAt(fila, 0).toString());
        if(tablaAsignaciones1.getValueAt(fila, 3).toString().equalsIgnoreCase("i")){
                cAsignacionEstado.setSelectedItem("Inactivo");
            }
            else{
                cEstado.setSelectedItem("Activo");
            }
    }//GEN-LAST:event_bTraerAsignacionActionPerformed

    private void bLimpiarAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLimpiarAsignacionActionPerformed
        try {
            mostrarTablaAsignaciones();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        limpiarAsignacion();
    }//GEN-LAST:event_bLimpiarAsignacionActionPerformed

    private void AbrirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirArchivoActionPerformed
        int total=0, exito=0, fallo=0;
        archivoReporte.setText("-----------Reporte-----------");
        regTotal.setText("");
        regExito.setText("");
        regFallo.setText("");
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        
        try {
          Scanner leeDoc = new Scanner(archivo);
          if(archivo.getName().endsWith(".txt")){
                while (leeDoc.hasNextLine()) {
                  total++;  
                  String linea = leeDoc.nextLine();
                  String codigo = linea.substring(0, 21);
                  Asistencia asis=new Asistencia(codigo, this.id);
                  archivoReporte.setText(archivoReporte.getText()+"\n"+asis.getMensaje());
                  if(asis.getCodigo()==1){
                    exito++;
                  }else{
                    fallo++;
                  }
            }
               leeDoc.close();
               regTotal.setText(""+total);
               regExito.setText(""+exito);
               regFallo.setText(""+fallo);
          }
          else{
              JOptionPane.showMessageDialog(null, "No es un archivo de texto");
          }

        } catch (IOException ex) {
          System.out.println("problema con el archivo "+archivo.getAbsolutePath());
        }  catch (SQLException ex) {
               Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
           }
    } else {
        System.out.println("Cancelado por el usuario.");
    }
    }//GEN-LAST:event_AbrirArchivoActionPerformed

    private void regTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regTotalActionPerformed

    private void archivoLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivoLimpiarActionPerformed
        regTotal.setText("");
        regExito.setText("");
        regFallo.setText("");
        archivoReporte.setText("");
        archivoFecha1.setText("");
        archivoFecha2.setText("");
        archivoClave.setText("");
    }//GEN-LAST:event_archivoLimpiarActionPerformed

    private void archivoBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivoBuscar1ActionPerformed
        try {
            mostrarTablaArchivo(archivoClave.getText(), archivoFecha1.getText(), archivoFecha2.getText());
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_archivoBuscar1ActionPerformed

    private void incidenciaFecha1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incidenciaFecha1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_incidenciaFecha1ActionPerformed

    private void limpiaIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiaIncidenciaActionPerformed
        incidenciaTotal.setText("");
        incidenciaFecha1.setText("");
        incidenciaFecha2.setText("");
            limpiarTablaIncidencia();

    }//GEN-LAST:event_limpiaIncidenciaActionPerformed

    private void buscarIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarIncidenciaActionPerformed
        int funciona=0,ano1=0,ano2=0,mes1=0,mes2=0,dia1=0,dia2=0;
        try {  
            ano1=Integer.parseInt(incidenciaFecha1.getText().substring(0,4));  
            mes1=Integer.parseInt(incidenciaFecha1.getText().substring(5,7));
            dia1=Integer.parseInt(incidenciaFecha1.getText().substring(8));
            ano2=Integer.parseInt(incidenciaFecha2.getText().substring(0,4));  
            mes2=Integer.parseInt(incidenciaFecha2.getText().substring(5,7));
            dia2=Integer.parseInt(incidenciaFecha2.getText().substring(8));
            funciona=1;
          } catch(NumberFormatException e){  
              JOptionPane.showMessageDialog(null, "Error al escribir las fechas");
              funciona=0;
          }
        if(funciona==1){
            if(mes1<1 || mes1>12 || mes2<1 || mes2>12){
                JOptionPane.showMessageDialog(null, "Error al escribir el mes");
            }
            else{
                if(dia1<1 || dia1>31 || dia2<1 || dia2>31){
                    JOptionPane.showMessageDialog(null, "Error al escribir el dia");
                }
                else{
                    try {
                        mostrarTablaIncidencia();
                    } catch (SQLException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_buscarIncidenciaActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JTextField archivoClave;
    javax.swing.JTextField archivoFecha1;
    javax.swing.JTextField archivoFecha2;
    javax.swing.JTextArea archivoReporte;
    javax.swing.JButton bAsignar2;
    javax.swing.JButton bBuscarAsignacion;
    javax.swing.JButton buscarIncidencia;
    javax.swing.JComboBox<String> cAsignacionEstado;
    javax.swing.JComboBox<String> cDia;
    javax.swing.JComboBox<String> cEstado;
    javax.swing.JComboBox<String> cEstados;
    javax.swing.JComboBox<String> cMunicipio;
    javax.swing.JFileChooser fileChooser;
    javax.swing.JTextField incidenciaFecha1;
    javax.swing.JTextField incidenciaFecha2;
    javax.swing.JTextField incidenciaTotal;
    javax.swing.JTextField jCP;
    javax.swing.JTextField jCalle;
    javax.swing.JTextField jClave;
    javax.swing.JTextField jClaveEmpleado2;
    javax.swing.JTextField jClaveHorario2;
    javax.swing.JTextField jColonia;
    javax.swing.JTextField jCurp;
    javax.swing.JTextField jExterior;
    javax.swing.JTextField jFecha;
    javax.swing.JTextField jInterior;
    javax.swing.JTextField jMaterno;
    javax.swing.JTextField jNombre;
    javax.swing.JTextField jPaterno;
    javax.swing.JTextField jSituacion;
    javax.swing.JToggleButton limpiaIncidencia;
    javax.swing.JTextField regExito;
    javax.swing.JTextField regFallo;
    javax.swing.JTextField regTotal;
    javax.swing.JTextField tClaveHorario;
    javax.swing.JTextField tDescripcionHorario;
    javax.swing.JTextField tHoraEntrada;
    javax.swing.JTextField tHoraSalida;
    javax.swing.JTextField tNumeroDetalle;
    javax.swing.JTable tablaArchivos;
    javax.swing.JTable tablaAsignaciones1;
    javax.swing.JTable tablaHorarios;
    javax.swing.JTable tablaInicidencias;
    javax.swing.JTable tabladatos;
    // End of variables declaration//GEN-END:variables
}
