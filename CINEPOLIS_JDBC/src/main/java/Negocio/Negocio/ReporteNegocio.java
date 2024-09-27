/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.Negocio;

import Persistencia.DAOs.ReporteDAO;
import Persistencia.Entidades.Reporte;

/**
 *
 * @author stae
 */
public class ReporteNegocio implements IReporteNegocio {
    
    ReporteDAO reporteDAO;
    
     public ReporteNegocio() {
    }
    

    public ReporteNegocio(ReporteDAO reporteDAO) {
        this.reporteDAO = reporteDAO;
    }

    @Override
    public void generarReporte(Reporte reportes, int opcion) {
        
        reporteDAO.generarReporte(reportes, opcion);
        
    }
    
}
