package Ej2;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import models.Clientes;
import models.Productos;
import models.Ventas;
import persistance.SessionFactoryUtil;

public class Principal {
	
	public static void main(String[] args) {
		menu();
	}
	
	public static void menu() {
		Scanner entrada = new Scanner(System.in);
		
		boolean continuar = true;
		
		while (continuar) {
			System.out.println("// INSERTAR VENTAS //");
			System.out.println("1. Insertar Ventas");
			System.out.println("2. Salir");
			System.out.println("Escoge una opcion:");
			int opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				insertarVenta();
				break;
			case 2:
				continuar = false;
				break;
			default:
				break;
			}
		}
	}
	
	public static void insertarVenta() {
		Scanner entrada = new Scanner(System.in);
		
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		
		System.out.println("Inserta los siguientes datos");
		System.out.print("IDVenta: ");
		int idv = entrada.nextInt();
		System.out.print("IDCliente: ");
		int idc = entrada.nextInt();
		System.out.print("IDProducto: ");
		int idp = entrada.nextInt();
		System.out.print("Cantidad: ");
		int cantidad = entrada.nextInt();
		
		Ventas v = new Ventas();
		v.setIdventa(idv);
		v.setClientes(idc);
		v.setProductos(idp);
		v.setCantidad(cantidad);
		
		boolean idventa = false;
		boolean idprod = true;
		boolean idcli = true;
		
		String consultaV = "FROM Ventas WHERE IDVENTA = " + idv + "";
		Query<Ventas> qV = session.createQuery(consultaV);

		List<Ventas> resultV = qV.list();
		for (int j = 0; j < resultV.size(); j++) {
			Ventas v2 = (Ventas) resultV.get(j);
			if (v2.getIdventa() == idv) {
				idventa = true;
			}
		}
		
		String consultaC = "FROM Clientes WHERE IDCLIENTE = " + idc + "";
		Query<Clientes> qC = session.createQuery(consultaC);

		List<Clientes> resultC = qC.list();
		for (int j = 0; j < resultC.size(); j++) {
			Clientes c = (Clientes) resultC.get(j);
			if (c.getIdcliente() == idc) {
				idcli = false;
			}
		}
		
		String consultaP = "FROM Productos WHERE IDPRODUCTO = " + idp + "";
		Query<Productos> qP = session.createQuery(consultaP);

		List<Productos> resultP = qP.list();
		for (int j = 0; j < resultP.size(); j++) {
			Productos p = (Productos) resultP.get(j);
			if (p.getIdproducto() == idp) {
				idprod = false;
			}
		}
		
		// Si el ID de ventas no existe, y los ID producto y de cliente si, entra 
		if (idventa == false && idprod == true && idcli == true) {
			if (cantidad > 0) {
				session.save(v);
				transaction.commit();
			} else {
				System.err.println("ERROR: La cantidad debe ser mayor a 0.");
			}
		} else {
			System.err.println("ERROR: Uno de los ID no estan bien.");
		}
	}
	
}
