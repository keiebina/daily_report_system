package controllers.toppage;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

        //セッションスコープからuser_id情報の取得
        HttpSession session = ((HttpServletRequest)request).getSession();
        Employee e = (Employee)session.getAttribute("login_employee");
        Integer user_id = e.getId();

        //user_idがフォローしているidをデータベースから取得
        List<Integer> follow_ids = em.createNamedQuery("getFollow_ids", Integer.class)
                            .setParameter("user_id", user_id)
                            .getResultList();

//        //フォローしている社員数を取得
//        long follow_ids_count = (long)em.createNamedQuery("countFollow_ids", Long.class)
//                                    .setParameter("user_id", user_id)
//                                    .getSingleResult();

        List<Report> following_report = em.createNamedQuery("getAllFollowingReports", Report.class)
                                                .setParameter("follow_ids", follow_ids)
                                                .getResultList();
        System.out.println(follow_ids);

        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

}
