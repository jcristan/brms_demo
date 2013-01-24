package org.domain.workshop.session;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

//import javax.context.SessionScoped;
import javax.persistence.EntityManager;

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
    /**
	 * 
	 */
	private static final long serialVersionUID = 3434055586567983587L;

	@Logger private Log log;

   /// @In org.drools.WorkingMemory policyPricingWorkingMemory;
       
    @In 
    StatusMessages statusMessages;

/*    @In private QueueSender myQueueSender;   
    
    @In(create = true)
    private QueueSession queueSession;*/

    @In(create = true)
    @Out
    Orderinfo orderInfo;
    
    
    Calendar currentDate = Calendar.getInstance();
    
    private String value;
    
   
    Customer customer = new Customer();




	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@In(create=true)
	private EntityManager entityManager;
    
	
	@DataModel
    private List<Customer> customerLista;
    
    
    @DataModel
    private List<LineaPedido> lineaPedidoLista;
    
    
    
    
    
    public void pedidoForm()
    {
    	try{
    		//insert en la base de datos
    		    		   		
			Iterator<LineaPedido> it = lineaPedidoLista.iterator();
			orderInfo.setCustomerId(customer.getCustomerId());
			orderInfo.setDatePlaced(currentDate.getTime());
			orderInfo.setDateShipped(currentDate.getTime());
			orderInfo.setArrivalDate(currentDate.getTime());
			orderInfo.setShipping(new BigDecimal(0.00));
			orderInfo.setDiscount(new BigDecimal(0.00));
			
			
			double total = 0.00;
			
		    while (it.hasNext()){
				LineaPedido lineaPedido = (LineaPedido)it.next();

				if (lineaPedido.getQuantity() > 0){
		    		//OrderlineId orderlineId = new OrderlineId(orderInfo.getOrderinfoId(), lineaPedido.getItem().getItemId());
		    		//Orderline orderline = new Orderline(orderlineId, lineaPedido.getQuantity());
					Orderline orderline = new Orderline();
					orderline.setItem(lineaPedido.getItem());
					orderline.setQuantity(lineaPedido.getQuantity());
		    		orderline.setOrderinfo(orderInfo);
		    		total = total + orderline.getItem().getSellPrice().doubleValue()*orderline.getQuantity() ; 		    		 
		    		orderInfo.getOrderlines().add(orderline);
		    		//policyPricingWorkingMemory.insert(lineaPedido.getItem());
				}
			}
			
			orderInfo.setTotal( new BigDecimal(total) );
			//orderInfo.setCustomer(this.geCustomerById(orderInfo.getCustomerId()));
			
			//entityManager.persist(orderInfo);
			
			//Llamando a Drools
/*		    policyPricingWorkingMemory.insert(orderInfo);
		    policyPricingWorkingMemory.insert(orderInfo.getCustomer());
		    policyPricingWorkingMemory.insert(orderInfo.getOrderlines());
		    policyPricingWorkingMemory.fireAllRules();
		    publish(orderInfo);
		    statusMessages.add("Orden Enviada !");*/
			
			KnowledgeBase knowledgeBase = createKnowledgeBase();
			if(knowledgeBase!=null){
				
				System.out.println("Knowledge NOO NULL");
			}
			else{
				System.out.println("NULL de KnowledgeBase");
						
			}
			
			StatefulKnowledgeSession ksession = knowledgeBase.newStatefulKnowledgeSession();
			ksession.insert(orderInfo);
			ksession.insert(orderInfo.getCustomer());
			ksession.insert(orderInfo.getOrderlines());
			ksession.fireAllRules();
			
			
			System.out.println("Silveriano ["+orderInfo.getCustomer().getSilver()+"]");
			System.out.println("Gold["+orderInfo.getCustomer().getGold()+"]");
			System.out.println("Descuento -->"+orderInfo.getDiscount());
			
		    
    	}catch (Exception e) {
    		statusMessages.add(e.getLocalizedMessage());
    		e.printStackTrace();
		}  
    }

    // add additional action methods
  
    @Length(max = 10)
    public String getValue()
    {
        return value;
    }
  
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public void publish(Orderinfo orderInfo) 
    {
       try
       {
    	   //myQueueSender.send(queueSession.createObjectMessage(orderInfo));
    	   log.info("Orden Enviada a la cola de Embarque");
       } 
       catch (Exception ex){
          throw new RuntimeException(ex);
        }
    }
    
    
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
	
	
	
}
