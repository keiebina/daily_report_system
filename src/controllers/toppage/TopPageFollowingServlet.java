package controllers.toppage;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageFollowingServlet
 */
@WebServlet("/following")
public class TopPageFollowingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageFollowingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //セッションスコープからuser情報の取得
        Employee user = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch (Exception e) {
            page = 1;
        }

        //userがフォローしている従業員情報をデータベースから取得
        List<Employee> follows = em.createNamedQuery("getFollows", Employee.class)
                            .setParameter("user", user)
                            .getResultList();

        if(follows.size() > 0){
            //userにフォローされている従業員のreportテーブルを取得
            List<Report> followingReports = em.createNamedQuery("getAllFollowingReports", Report.class)
                    .setParameter("follows", follows)
                    .setFirstResult(15 * (page - 1))
                    .setMaxResults(15)
                    .getResultList();

            long followingCount = (long)em.createNamedQuery("getAllFollowingCount",Long.class)
                                                .setParameter("follows", follows)
                                                .getSingleResult();

            request.setAttribute("followingReports", followingReports);
            request.setAttribute("followingCount", followingCount);
        }else{
            request.setAttribute("followingReports", null);
        }


        em.close();


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/following.jsp");
        rd.forward(request, response);
    }

}
