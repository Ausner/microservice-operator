package com.laboratorio.operator.service;

import com.laboratorio.operator.facade.ProductsFacade;
import com.laboratorio.operator.model.Alquiler;
import com.laboratorio.operator.model.MovieDto;
import com.laboratorio.operator.repository.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private ProductsFacade productsFacade;

    public List<Alquiler> getAllAlquileres() {
        return alquilerRepository.findAll();
    }

    public Optional<Alquiler> getAlquilerById(Long id) {
        return alquilerRepository.findById(id);
    }

    public Alquiler createAlquiler(Alquiler alquiler) {
        String movieId = String.valueOf(alquiler.getIdPelicula());
        // Verificar si la película existe usando ProductsFacade
        MovieDto movie = productsFacade.getMovie(movieId);

        if (movie == null) {
            throw new RuntimeException("La película no existe en el inventario");
        }

        // Si la película existe, crear el alquiler
        return alquilerRepository.save(alquiler);
    }

    public Alquiler updateAlquiler(Long id, Alquiler alquilerDetails) {
        Optional<Alquiler> optionalAlquiler = alquilerRepository.findById(id);

        if (optionalAlquiler.isPresent()) {
            Alquiler alquiler = optionalAlquiler.get();
            alquiler.setIdPelicula(alquilerDetails.getIdPelicula());
            alquiler.setFechaDesde(alquilerDetails.getFechaDesde());
            alquiler.setFechaHasta(alquilerDetails.getFechaHasta());
            alquiler.setPrecioFinal(alquilerDetails.getPrecioFinal());
            alquiler.setTipoRecibo(alquilerDetails.getTipoRecibo());
            return alquilerRepository.save(alquiler);
        }
        return null;
    }

    public void deleteAlquiler(Long id) {
        alquilerRepository.deleteById(id);
    }
}