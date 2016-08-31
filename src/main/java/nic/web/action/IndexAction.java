/*
 * 文 件 名:  IndexAction.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年8月31日
 */
package nic.web.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年8月31日]
 * @see  [相关类/方法]
 * @since V1.00
 */
@Controller
@RequestMapping("/index")
public class IndexAction {

	@RequestMapping("/test")
	public String indexText(){
		return "hello";
	}
	
}
