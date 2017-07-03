/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet for market basket implementation
 * @author katharina
 */
@WebServlet(urlPatterns = {"/MarketBasket"})
public class MarketBasket extends HttpServlet {
    
    private Map<String, Float> prices = new HashMap<>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
             String product = request.getParameter("product");
             int quantity = Integer.parseInt(request.getParameter("quantity"));
             Map<String, Integer> map;
             
             HttpSession session = request.getSession();
            
             //check for new session
            if(session.isNew()){
                //init HashMap
                map = new HashMap<>();
                //assign HashMap to session
                session.setAttribute("mb", map);

            }else{
                //get map from existing session
                map = (Map) session.getAttribute("mb");
            }
            
            //check whether the product is stored or not
            if(map.containsKey(product)){
                int count = map.get(product)+quantity;
                map.put(product, count);
            }else{
                map.put(product, quantity);
            }
            
            String result = calcSum(map);  
            out.print(result);
              
        }
    }
    
    /**
     * Calculates the total price of all items in the market basket
     * @param basket    Map<String,Integer>     The specific market basket
     * @return A result String
     */
    private String calcSum(Map<String, Integer> basket){
        
        String result="";
        
        Set<String> entries = basket.keySet();
        
        for(String key : entries){
           int count = basket.get(key);
           float price = prices.get(key);
           
           float t_price = count * price;
           
           result+=key+": "+count+" x"+" - total: "+t_price+"   , ";
           
        }
        //discard the last commata
        return result.substring(0, result.length()-3);
    }

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        prices.put("Pizza", 10.00f);
        prices.put("Beer", 1.99f);
        prices.put("Rocket", 5.66f);
        prices.put("Hamster", 25.00f);
        prices.put("House Elf",100.00f);
        prices.put("Unicorn", 1000.99f);
        prices.put("The one ring", 0.01f);
        prices.put("Lightsaber",200.00f);
        
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
