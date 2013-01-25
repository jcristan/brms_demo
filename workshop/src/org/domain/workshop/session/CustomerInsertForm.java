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
import org.domain.workshop.entity.CustomerInsert;
import org.domain.workshop.entity.Item;
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
			
	try{
		entityManager.flush();
		entityManager.persist(customerInsert);
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
				
		return "EXITO";
		
	}

public String calcularValorAsegurado(){
	
	log.info("Se llama al metodo de calcularValorAsegurado");
		
	
	
	//Valores por defecto para la insercion en la base de datos y prevenir excepciones
	customerInsert.setTitle("Mr");
	customerInsert.setAddressline("TheVarn");
	customerInsert.setTown("timbuctu");
	customerInsert.setZipcode("OA3");
	customerInsert.setPhone("4873871");
	customerInsert.setGold('1');
	customerInsert.setSilver('0');
	
	
	//Si todo riesgo es seleccionado
	boolean todoRiesgo = customerInsert.getTodoRiesgo();
	log.info("todoRiesgo seleccionado? = " + todoRiesgo);
	if(todoRiesgo){
		customerInsert.setRcivil(true);
		customerInsert.setHurto(true);
		customerInsert.setPerdida(true);
		customerInsert.setTerremoto(true);
		
		log.info("Todo riesgo seleccionado");
		log.info("rcivil = " + customerInsert.getRcivil());
		log.info("hurto = " + customerInsert.getHurto());
		log.info("perdida = " + customerInsert.getPerdida());
		log.info("terremoto = " + customerInsert.getTerremoto());
		
	}
		
	
	//Se calcula el valor asegurado de acuerdo a la marca
	BigDecimal valorCalculado = new BigDecimal("0.00");
	String marca = customerInsert.getMarca();
	
	if(!(marca == null) && marca.equals("chevrolet")){
		BigDecimal valorCalculadoChevrolet = new BigDecimal("10000000.00");
		valorCalculado = valorCalculadoChevrolet;
		customerInsert.setVasegurado(valorCalculado);
	}
	
	if(!(marca == null) && marca.equals("renault")){
		BigDecimal valorCalculadoRenault = new BigDecimal("15000000.00");
		valorCalculado = valorCalculadoRenault;
		customerInsert.setVasegurado(valorCalculado);
	}
	
	if(!(marca == null) && marca.equals("mazda")){
		BigDecimal valorCalculadoMazda = new BigDecimal("18000000.00");
		valorCalculado = valorCalculadoMazda;
		customerInsert.setVasegurado(valorCalculado);
	}
	
	if(!(marca == null) && marca.equals("dodge")){
		BigDecimal valorCalculadoDodge = new BigDecimal("21000000.00");
		valorCalculado = valorCalculadoDodge;
		customerInsert.setVasegurado(valorCalculado);
	}
	
	if(!(marca == null) && marca.equals("audi")){
		BigDecimal valorCalculadoAudi = new BigDecimal("25000000.00");
		valorCalculado = valorCalculadoAudi;
		customerInsert.setVasegurado(valorCalculado);
	}
	
	
	log.info("valorCalculado = " + customerInsert.getVasegurado());
	
	
	//Disparar BRM para calcular el porcentaje pprima
	KnowledgeBase knowledgeBase = createKnowledgeBase();
    
    StatefulKnowledgeSession ksession = knowledgeBase.newStatefulKnowledgeSession();
    ksession.insert(customerInsert);
    ksession.fireAllRules();
	
    BigDecimal pprimaRetornado = customerInsert.getPprima();
    log.info("pprimaRetornado = " + pprimaRetornado.toString());
	
	//Calcular valor de la poliza
	BigDecimal vpoliza = new BigDecimal("0.00");
	
	vpoliza = customerInsert.getVasegurado().multiply(pprimaRetornado);
	customerInsert.setVpoliza(vpoliza);
	log.info("vpoliza = " + customerInsert.getVpoliza());
	
	return "Calculado";
}
	
}
