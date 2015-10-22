
package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Date;

public class Update extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setIntHeader("Refresh", 24 * 60 * 60);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Date date = new Date();
        out.println("Update Started at " + date.toString());
        System.out.println("Update Started at " + date.toString());


        try {
           /* System.setProperty("java.net.useSystemProxies", "true");
                  
            System.setProperty("http.proxyHost", "172.31.100.29");
            System.setProperty("http.proxyPort", "" + "3128");
            System.setProperty("http.proxyUser","edcguest");
            System.setProperty("http.proxyPassword","edcguest");
            */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Update</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Update at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            String key = "f2aadbac4691e264a737b37acf154d278612f54290ab85a56b5859513e1aeb91";
            String fileName = "online-valid.json";
            String url = "http://data.phishtank.com/data/";
            out.println(url + key + "/" + fileName);
            URL link = new URL("http://www.cnds.jhu.edu/courses/cs437/exercises/Ex1_2014.txt");//url + key + "/" + fileName); //The file that you want to download
            InputStream in = new BufferedInputStream(link.openStream());
            ByteArrayOutputStream outp = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;

            while (-1 != (n = in.read(buf))) {
                System.out.println("here");
                out.println("here");
                outp.write(buf, 0, n);
            }
            System.out.println("DONE");
            outp.close();
            in.close();
            byte[] res = outp.toByteArray();
            String p = getServletContext().getRealPath("/");
            File f = new File(p + "/" + fileName);

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(res);
            System.out.print(outp + "\n" + p + "/" + fileName);
            fos.close();
        } catch (Exception e) {
            out.println(e);
            e.printStackTrace();
        } finally {
            out.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
