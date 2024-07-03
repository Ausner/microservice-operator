package com.laboratorio.operator.controller;

import com.laboratorio.operator.model.Compra;
import com.laboratorio.operator.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
@Tag(name = "Compras Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre compras de peliculas alojados en una base de datos en memoria.")

public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    @Operation(
            operationId = "Obtener compras",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todas las compras almacenadas en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Compra.class)))
    public List<Compra> getAllCompras() {
        return compraService.getAllCompras();
    }

    @GetMapping("/{id}")
    @Operation(
            operationId = "Obtener una compra",
            description = "Operacion de lectura",
            summary = "Se devuelve una compra a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Compra.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado la compra con el identificador indicado.")
    public ResponseEntity<Compra> getCompraById(@PathVariable Long id) {
        return compraService.getCompraById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            operationId = "Insertar una compra",
            description = "Operacion de escritura",
            summary = "Se crea una compra a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la compra a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Compra.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Compra.class)))
    public Compra createCompra(  @RequestBody Compra compra) {
        return compraService.createCompra(compra);
    }

    @PutMapping("/{id}")
    @Operation(
            operationId = "Modificar totalmente una compra",
            description = "Operacion de escritura",
            summary = "Se modifica totalmente una compra.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la compra a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = Compra.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Compra.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Compra no encontrada.")
    public ResponseEntity<Compra> updateCompra( @PathVariable Long id,
                                               @RequestBody Compra compraDetails) {
        return ResponseEntity.ok(compraService.updateCompra(id, compraDetails));
    }

    @PatchMapping("/{id}")
    @Operation(
            operationId = "Modificar parcialmente una compra",
            description = "RFC 7386. Operacion de escritura",
            summary = "RFC 7386. Se modifica parcialmente una compra.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la compra a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Compra.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Compra inválida o datos incorrectos introducidos.")
    public ResponseEntity<Compra> patchCompra( @PathVariable Long id,
                                               @RequestBody Compra compraDetails) {
        return ResponseEntity.ok(compraService.patchCompra(id, compraDetails));
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Compra.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "alquiler inválido o datos incorrectos introducidos.")
    public ResponseEntity<Void> deleteCompra(  @PathVariable Long id) {
        compraService.deleteCompra(id);
        return ResponseEntity.noContent().build();
    }
}