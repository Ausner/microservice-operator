package com.laboratorio.operator.service;

import com.laboratorio.operator.facade.ProductsFacade;
import com.laboratorio.operator.model.Compra;
import com.laboratorio.operator.model.MovieDto;
import com.laboratorio.operator.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private ProductsFacade productsFacade;
    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    public Optional<Compra> getCompraById(Long id) {
        return compraRepository.findById(id);
    }

    public Compra createCompra(Compra compra) {

        String movieId = String.valueOf(compra.getIdPelicula());
        // Verificar si la película existe usando ProductsFacade
        MovieDto movie = productsFacade.getMovie(movieId);

        if (movie == null) {
            throw new RuntimeException("La película no existe en el inventario");
        }

        // Si la película existe, crear el alquiler
        return compraRepository.save(compra);
    }

    public Compra updateCompra(Long id, Compra compraDetails) {
        Compra compra = compraRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        compra.setIdPelicula(compraDetails.getIdPelicula());
        compra.setFechaCompra(compraDetails.getFechaCompra());
        compra.setPrecioTotal(compraDetails.getPrecioTotal());
        compra.setTipoPago(compraDetails.getTipoPago());
        return compraRepository.save(compra);
    }

    public Compra patchCompra(Long id, Compra compraDetails) {
        Compra compra = compraRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        if (compraDetails.getIdPelicula() != 0) {
            compra.setIdPelicula(compraDetails.getIdPelicula());
        }
        if (compraDetails.getFechaCompra() != null) {
            compra.setFechaCompra(compraDetails.getFechaCompra());
        }
        if (compraDetails.getPrecioTotal() != 0.0) {
            compra.setPrecioTotal(compraDetails.getPrecioTotal());
        }
        if (compraDetails.getTipoPago() != null) {
            compra.setTipoPago(compraDetails.getTipoPago());
        }
        return compraRepository.save(compra);
    }

    public void deleteCompra(Long id) {
        compraRepository.deleteById(id);
    }
}