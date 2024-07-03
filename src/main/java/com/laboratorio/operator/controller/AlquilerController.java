package com.laboratorio.operator.controller;

import com.laboratorio.operator.model.Alquiler;
import com.laboratorio.operator.service.AlquilerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alquileres")
@Tag(name = "Alquileres Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre alquiler de peliculas alojados en una base de datos en memoria.")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping
    @Operation(
            operationId = "Obtener alquileres",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todos los alquileres almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alquiler.class)))
    public List<Alquiler> getAllAlquileres() {
        return alquilerService.getAllAlquileres();
    }

    @GetMapping("/{id}")
    @Operation(
            operationId = "Obtener un alquiler",
            description = "Operacion de lectura",
            summary = "Se devuelve un alquiler a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alquiler.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el alquiler con el identificador indicado.")
    public ResponseEntity<Alquiler> getAlquilerById( @PathVariable Long id) {
        Optional<Alquiler> alquiler = alquilerService.getAlquilerById(id);
        return alquiler.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            operationId = "Insertar un alquiler",
            description = "Operacion de escritura",
            summary = "Se crea un alquiler a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del alquiler a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alquiler.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alquiler.class)))
    public Alquiler createAlquiler( @RequestBody Alquiler alquiler) {
        return alquilerService.createAlquiler(alquiler);
    }

    @PutMapping("/{id}")
    @Operation(
            operationId = "Modificar totalmente un alquiler",
            description = "Operacion de escritura",
            summary = "Se modifica totalmente un alquiler.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del alquiler a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = Alquiler.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alquiler.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Alquiler no encontrado.")
    public ResponseEntity<Alquiler> updateAlquiler( @PathVariable Long id,
                                                    @RequestBody Alquiler alquilerDetails) {
        Alquiler updatedAlquiler = alquilerService.updateAlquiler(id, alquilerDetails);
        return updatedAlquiler != null ? ResponseEntity.ok(updatedAlquiler) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    @Operation(
            operationId = "Modificar parcialmente un alquiler",
            description = "RFC 7386. Operacion de escritura",
            summary = "RFC 7386. Se modifica parcialmente un alquiler.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del alquiler a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alquiler.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Alquiler inválido o datos incorrectos introducidos.")
    public ResponseEntity<Alquiler> partialUpdateAlquiler(  @PathVariable Long id,
                                                           @RequestBody Alquiler alquilerDetails) {
        Alquiler existingAlquiler = alquilerService.getAlquilerById(id).orElse(null);
        if (existingAlquiler == null) {
            return ResponseEntity.notFound().build();
        }

        if (alquilerDetails.getFechaDesde() != null) {
            existingAlquiler.setFechaDesde(alquilerDetails.getFechaDesde());
        }
        if (alquilerDetails.getFechaHasta() != null) {
            existingAlquiler.setFechaHasta(alquilerDetails.getFechaHasta());
        }
        if (alquilerDetails.getPrecioFinal() != 0) {
            existingAlquiler.setPrecioFinal(alquilerDetails.getPrecioFinal());
        }
        if (alquilerDetails.getTipoRecibo() != null) {
            existingAlquiler.setTipoRecibo(alquilerDetails.getTipoRecibo());
        }

        Alquiler updatedAlquiler = alquilerService.updateAlquiler(id, existingAlquiler);
        return ResponseEntity.ok(updatedAlquiler);
    }

    @DeleteMapping("/{id}")
    @Operation(
            operationId = "Modificar parcialmente un alquiler",
            description = "RFC 7386. Operacion de escritura",
            summary = "RFC 7386. Se modifica parcialmente un alquiler.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del alquiler a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alquiler.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "alquiler inválido o datos incorrectos introducidos.")
    public ResponseEntity<Void> deleteAlquiler( @PathVariable Long id) {
        alquilerService.deleteAlquiler(id);
        return ResponseEntity.noContent().build();
    }
}