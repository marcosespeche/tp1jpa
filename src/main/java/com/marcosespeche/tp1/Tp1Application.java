package com.marcosespeche.tp1;

import com.marcosespeche.tp1.repositorios.ClienteRepository;
import com.marcosespeche.tp1.repositorios.PedidoRepository;
import com.marcosespeche.tp1.repositorios.ProductoRepository;
import com.marcosespeche.tp1.repositorios.RubroRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.marcosespeche.tp1.entidades.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
public class Tp1Application {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private RubroRepository rubroRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(Tp1Application.class, args);
	}
	@Bean
	@Transactional
	CommandLineRunner init(EntityManager entityManager, RubroRepository rubroRepository, ClienteRepository clienteRepository, ProductoRepository productoRepository, PedidoRepository pedidoRepository) {
		return args -> {
			System.out.println("-----------------ESTOY FUNCIONANDO-----------------");

			//Creación de un nuevo rubro
			Rubro nuevoRubro = Rubro.builder()
					.denominacion("Papas fritas")
					.listaProductos(new ArrayList<Producto>())
					.build();

			//Creación de nuevos productos para el rubro
			Producto producto1 = Producto.builder()
					.denominacion("Papas con cheddar")
					.precioVenta(1500)
					.stockMinimo(40)
					.build();
			Producto producto2 = Producto.builder()
					.denominacion("Papas con huevo frito")
					.precioVenta(2000)
					.stockMinimo(25)
					.build();
			Producto producto3 = Producto.builder()
					.denominacion("Papas con bacon")
					.precioVenta(2000)
					.stockMinimo(25)
					.build();

			//Estableciendo la relación
			nuevoRubro.anadirProducto(producto1);
			nuevoRubro.anadirProducto(producto2);
			nuevoRubro.anadirProducto(producto3);

			//Persistiendo el rubro

			rubroRepository.save(nuevoRubro);

			//Creacion de clientes
			Cliente cliente1 = Cliente.builder()
					.nombre("Marcos")
					.apellido("Espeche")
					.listaDomicilios(new ArrayList<Domicilio>())
					.listaPedidos(new ArrayList<Pedido>())
					.build();
			Domicilio domicilio1 = Domicilio.builder()
					.calle("Belgrano")
					.numero("123")
					.localidad("Mendoza")
					.build();
			cliente1.anadirDomicilio(domicilio1);

			//Persistiendo el cliente
			clienteRepository.save(cliente1);

			//Creacion del pedido
			Pedido pedido1 = Pedido.builder()
					.estado("Iniciado")
					.fecha(new Date())
					.listaDetallePedidos(new ArrayList<DetallePedido>())
					.tipoEnvio("retira")
					.build();

			Producto productoRecuperado1 = productoRepository.findById(producto1.getId()).orElse(null);
			Producto productoRecuperado2 = productoRepository.findById(producto2.getId()).orElse(null);

			//Para que producto este en estado MANAGED y no en DETACHED
			productoRecuperado1 = entityManager.merge(productoRecuperado1);
			productoRecuperado2 = entityManager.merge(productoRecuperado2);

			DetallePedido detalle1 = DetallePedido.builder()
					.cantidad(2)
					.producto(productoRecuperado1)
					.build();
			DetallePedido detalle2 = DetallePedido.builder()
					.cantidad(1)
					.producto(productoRecuperado2)
					.build();
			detalle1.setSubtotal(productoRecuperado1.getPrecioVenta());
			detalle2.setSubtotal(productoRecuperado2.getPrecioVenta());
			pedido1.anadirDetalle(detalle1);
			pedido1.anadirDetalle(detalle2);
			pedido1.setTotal(detalle1.getSubtotal() + detalle2.getSubtotal());

			Factura factura = Factura.builder()
					.numero(12)
					.fecha(new Date())
					.total((int)pedido1.getTotal())
					.build();

			//Persistiendo el cliente
			pedidoRepository.save(pedido1);
		}; }
}
