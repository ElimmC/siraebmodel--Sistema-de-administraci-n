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
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        System.out.println(Incidencia.procesaIncidencas("2020-05-01", "2020-05-05", "1"));
        ArrayList<Incidencia> incidencias=Incidencia.getIncidencias("2020-05-01", "2020-05-05");
        for (Incidencia incidencia : incidencias) {
            System.out.println(incidencia.getNombre()+" "+incidencia.getClave()+" "+incidencia.getFecha()+incidencia.getTipo());
        }

    }

}
