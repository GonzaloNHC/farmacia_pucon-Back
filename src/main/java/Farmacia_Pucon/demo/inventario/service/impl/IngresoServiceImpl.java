package Farmacia_Pucon.demo.inventario.service.impl;

import Farmacia_Pucon.demo.inventario.dto.IngresoDTO;
import Farmacia_Pucon.demo.inventario.entity.Lote;
import Farmacia_Pucon.demo.inventario.entity.Producto;
import Farmacia_Pucon.demo.inventario.repository.LoteRepository;
import Farmacia_Pucon.demo.inventario.repository.ProductoRepository;
import Farmacia_Pucon.demo.inventario.service.IngresoService;

import org.springframework.stereotype.Service;

@Service
public class IngresoServiceImpl implements IngresoService {

    private final ProductoRepository productoRepository;
    private final LoteRepository loteRepository;

    public IngresoServiceImpl(ProductoRepository productoRepository, LoteRepository loteRepository) {
        this.productoRepository = productoRepository;
        this.loteRepository = loteRepository;
    }

    @Override
    public void ingresar(IngresoDTO dto) {

        Producto producto = productoRepository.findById(dto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Integer stockActual = producto.getStock();
        Integer cantNueva = dto.getCantidadNueva();

        Double pppActual = producto.getPppActual();
        Double precioNuevo = dto.getPrecioNuevo();

        Double nuevoPPP = (pppActual * stockActual + precioNuevo * cantNueva)
                / (stockActual + cantNueva);

        producto.setStock(stockActual + cantNueva);
        producto.setPppActual(nuevoPPP);

        Lote lote = new Lote();
        lote.setProducto(producto);
        lote.setCantidadInicial(cantNueva);
        lote.setCodigoLote(dto.getLote());
        lote.setFechaVencimiento(dto.getVencimiento());

        loteRepository.save(lote);
        productoRepository.save(producto);
    }
}
