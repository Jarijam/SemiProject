package test;

import java.util.ArrayList;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.frame.Service;
import com.vo.Product;



public class ProductsSelectWhere {

	public static void main(String[] args) {
		// Start Spring Container
		AbstractApplicationContext factory
		= new GenericXmlApplicationContext("myspring.xml");
		
		Service<Integer,Product> biz = 
			(Service)factory.getBean("pbiz");
		Product product = null;
		try {
			product = biz.get(105);
			System.out.println(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		factory.close();
	}

}



