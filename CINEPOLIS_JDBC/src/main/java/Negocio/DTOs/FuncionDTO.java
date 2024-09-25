/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio.DTOs;

import java.util.Date;
import java.util.List;

/**
 *
 * @author stae
 */
public class FuncionDTO {
    
    private Long id;
    private Date fecha;
    private double horaInicio;
    private PeliculaDTO peliculaDTO;
    private double horaFin;
    private SalaDTO salaDTO;
    private SucursalDTO sucursalDTO;
    
    public FuncionDTO() {
    }

    public FuncionDTO(Long id, Date fecha, double horaInicio, List<PeliculaDTO> peliculas,  SalaDTO salaDTO, SucursalDTO sucursalDTO) {
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.peliculaDTO = peliculaDTO;
        this.salaDTO = salaDTO;
        this.sucursalDTO = sucursalDTO;
    }

    public double getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(double horaFin) {
        this.horaFin = horaFin;
    }

    public SalaDTO getSalaDTO() {
        return salaDTO;
    }

    public void setSalaDTO(SalaDTO salaDTO) {
        this.salaDTO = salaDTO;
    }

    public SucursalDTO getSucursalDTO() {
        return sucursalDTO;
    }

    public void setSucursalDTO(SucursalDTO sucursalDTO) {
        this.sucursalDTO = sucursalDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(double horaInicio) {
        this.horaInicio = horaInicio;
    }

    public PeliculaDTO getPeliculaDTO() {
        return peliculaDTO;
    }

    public void setPeliculaDTO(PeliculaDTO peliculaDTO) {
        this.peliculaDTO = peliculaDTO;
    }

    public String calcularHoraFin() {
        String horaFinStr = "";

        if (peliculaDTO != null) {
            double duracionPelicula = peliculaDTO.getDuracion();

            // converitr horas, mins
            //hora inicio + duracion
            //formatear a hora:mins
            int horas = (int) (duracionPelicula / 60);
            int minutos = (int) (duracionPelicula % 60);

            // 2
            double horaFinEnHoras = this.horaInicio + horas + (minutos / 60.0);

            int horasFin = (int) Math.floor(horaFinEnHoras);
            int minutosFin = (int) Math.round((horaFinEnHoras - horasFin) * 60);

            //3
            horaFinStr = String.format("%02d:%02d", horasFin, minutosFin);
        }

        return horaFinStr;
    }
    
    
}
