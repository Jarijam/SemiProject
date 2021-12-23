package test;

import java.util.ArrayList;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.frame.Service;
import com.vo.Product;



public class ProductsInsert {

	public static void main(String[] args) {
		// Start Spring Container
		AbstractApplicationContext factory
		= new GenericXmlApplicationContext("myspring.xml");
		
		Service<Integer,Product> biz = 
			(Service)factory.getBean("pbiz");
		Product product = new Product("¼ÅÃ÷2",1999.9,"shirt02.jpg");
		try {
			biz.register(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		factory.close();
	}

}



