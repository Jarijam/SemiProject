package test;

import java.util.ArrayList;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.frame.Service;
import com.vo.Product;



public class ProductsDelete {

	public static void main(String[] args) {
		// Start Spring Container
		AbstractApplicationContext factory
		= new GenericXmlApplicationContext("myspring.xml");
		
		Service<Integer,Product> biz = 
			(Service)factory.getBean("pbiz");
		Product product = null;
		try {
			biz.remove(102);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		factory.close();
	}

}



