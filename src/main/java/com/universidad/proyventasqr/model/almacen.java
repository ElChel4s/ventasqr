package com.universidad.proyventasqr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "almacen")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID
    @Column(name = "id_alm", nullable = false) // Columna no nula
    private Long idAlm; // Identificador único del almacén

    @Column(name = "nombre", nullable = false, length = 100) // Nombre del almacén, no puede ser nulo
    private String nombre; // Nombre del almacén

    @Column(name = "ubicacion", nullable = false, length = 200) // Ubicación del almacén, no puede ser nulo
    private String ubicacion; // Dirección o ubicación del almacén

    @Column(name = "capacidad", nullable = false) // Capacidad máxima del almacén, no puede ser nula
    private Integer capacidad; // Capacidad del almacén (por ejemplo, en unidades o volumen)

    @Column(name = "estado", nullable = false, length = 50) // Estado del almacén (activo, inactivo)
    private String estado; // Estado actual del almacén (activo, inactivo, cerrado)

    // Puedes agregar otros métodos si los necesitas, por ejemplo, un método de
    // validación de capacidad
    public boolean puedeAlmacenar(int cantidad) {
        return cantidad > 0 && cantidad <= this.capacidad;
    }
}
