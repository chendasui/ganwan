package com.das.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.das.process.MessageProcess;
//import com.das.util.SignUtil;

public class CoreServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public CoreServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 微信加密签名
//        String signature = request.getParameter("signature");
//        // 时间戮
//        String timestamp = request.getParameter("timestamp");
//        // 随机数
//        String nonce = request.getParameter("nonce");
        // 随机字符串
//        String echostr = request.getParameter("echostr"); 
         
        // 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败
        String result = null;
//        if(SignUtil.checkSignature(signature, timestamp, nonce)){
//    	if(echostr != null && echostr.length() > 1){
//        	result = echostr;
//        }else {
        	request.setCharacterEncoding("UTF-8");  
            response.setCharacterEncoding("UTF-8");  
      
            /** 读取接收到的xml消息 */  
            StringBuffer sb = new StringBuffer();  
            InputStream is = request.getInputStream();  
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
            BufferedReader br = new BufferedReader(isr);  
            String s = "";  
            while ((s = br.readLine()) != null) {  
                sb.append(s);  
            }  
            String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据 
            result = new MessageProcess().processWechatMsg(xml);
//        }
        try {  
            OutputStream os = response.getOutputStream();  
            os.write(result.getBytes("UTF-8"));  
            os.flush();  
            os.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	
}
