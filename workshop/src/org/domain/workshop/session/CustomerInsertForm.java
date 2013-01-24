package org.domain.workshop.session;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

//import javax.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;
import org.domain.workshop.entity.Customer;
import org.domain.workshop.entity.CustomerInsert;
import org.domain.workshop.entity.Item;
import org.domain.workshop.entity.Orderinfo;
import org.domain.workshop.entity.Orderline;
import org.domain.workshop.entity.OrderlineId;
import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.WorkingMemory;
import org.hibernate.validator.Length;



@Name("customerInsertForm")
@Scope(ScopeType.SESSION)
public class CustomerInsertForm implements Serializable
{
    
	private static final long serialVersionUID = 3434055586567983587L;

	@Logger private Log log;

	@In(create=true)
	private EntityManager entityManager;
   
    CustomerInsert customerInsert = new CustomerInsert();




	public CustomerInsert getCustomerInsert() {
		return customerInsert;
	}

	public void setCustomerInsert(CustomerInsert customerInsert) {
		this.customerInsert = customerInsert;
	}

	/*@In(create=true)
	private EntityManager entityManager;
    
	
	@DataModel
    private List<Customer> customerLista;
    
    
    @DataModel
    private List<LineaPedido> lineaPedidoLista;
    
    */
    
    
	private static KnowledgeBase createKnowledgeBase() 
	{
		try{
			 KnowledgeAgentConfiguration kaconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
			 kaconf.setProperty( "drools.agent.scanDirectories", "false" );
			 KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent( "test agent", kaconf );
			 kagent.applyChangeSet( ResourceFactory.newClassPathResource("changeset.xml"));
			 return kagent.getKnowledgeBase();			
		} catch (Exception ex){
			System.out.print(ex.getLocalizedMessage());
	          throw new RuntimeException(ex);
	       }

	}
	
public String insertarCliente(){
			
		log.info("Se llama al metodo de insertarCliente");
		int clienteIDtoinsert = customerInsert.getCustomerId();
		log.info("id del cliente nuevo a insertar = " + clienteIDtoinsert);
		log.info("rcivil seleccionado = " + customerInsert.getRcivil());
		
		boolean todoRiesgo = customerInsert.getTodoRiesgo();
		
		//customertoInsert.setCustomerId(customerId);
		customerInsert.setTitle("Mr");
		//customerInsert.setFname("Satanaz");
		//customerInsert.setLname("Dominicus");
		customerInsert.setAddressline("TheVarn");
		customerInsert.setTown("timbuctu");
		customerInsert.setZipcode("OA3");
		customerInsert.setPhone("4873871");
		customerInsert.setGold('1');
		customerInsert.setSilver('0');
		customerInsert.setEdad(15);
		//customerInsert.setGenero('M');
		//customerInsert.setTipo_Vehiculo("Liviano");
		//customerInsert.setCobertura("B");
		customerInsert.setMarca("Mazda");
		customerInsert.setModelo("RX-7");
		customerInsert.setZona_circulacion("Norte");
		
		
		
		try{
		entityManager.flush();
		entityManager.persist(customerInsert);
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
				
		return "EXITO";
		
	}
	
}
