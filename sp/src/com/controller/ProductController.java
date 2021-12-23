package com.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Service;
import com.vo.Product;

@Controller
public class ProductController {

	@Resource(name="pbiz")
	Service<Integer,Product> biz;
	
	@RequestMapping("/productadd.mc")
	public ModelAndView padd() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("center", "product/add");
		mv.setViewName("main");
		return mv;
	}
	
	@RequestMapping("/productlist.mc")
	public ModelAndView plist() {
		ModelAndView mv = new ModelAndView();
		ArrayList<Product> list = null;
		
		try {
			list = biz.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mv.addObject("plist", list);
		mv.addObject("center", "product/list");

		mv.setViewName("main");
		return mv;
	}
	
	@RequestMapping("/productaddimpl.mc")
	public ModelAndView paddimpl(ModelAndView mv,
			Product product) {
		
		String imgname = 
				product.getMf().getOriginalFilename();
		
		product.setImgname(imgname);
				
		try {
			biz.register(product);
			Util.saveFile(product.getMf());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mv.setViewName("main");
		return mv;
	}
	
}












