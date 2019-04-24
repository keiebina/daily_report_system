package controllers.follow;

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
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsUpdateServlet
 */
@WebServlet("/follows/update")
public class FollowsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
            EntityManager em = DBUtil.createEntityManager();

            //セッションスコープからuser_id情報の取得 //リクエストパラメータからfollow_idの取得
            HttpSession session = ((HttpServletRequest)request).getSession();
            Employee e = (Employee)session.getAttribute("login_employee");
            String user_id = (String)e.getCode();
            String follow_id = (String)request.getParameter("follow_id");

            //user_idのフォローidをデータベースから取得
            List<String> ids = em.createNamedQuery("getFollow_ids", String.class)
                                .setParameter("user_id", user_id)
                                .getResultList();

            //フォローしているユーザーかどうか
            if(FollowDecision.isFollowing(follow_id, ids)){

                //フォローしているユーザーだった場合
                em.getTransaction().begin();

                em.createNamedQuery("deleteFollow_id")
                    .setParameter("user_id", user_id)
                    .setParameter("follow_id", follow_id)
                    .executeUpdate();

                em.close();
                request.getSession().setAttribute("flush", "フォローを解除しました。");
                response.sendRedirect(request.getContextPath() + "/employees/index");


            }else{      //フォローしていないユーザーの場合

                //followテーブルにuser_id,follow_idを保存
                Follow f = new Follow();
                f.setUser_id(user_id);
                f.setFollow_id(follow_id);

                em.getTransaction().begin();
                em.persist(f);
                em.getTransaction().commit();
                em.close();

                request.getSession().setAttribute("flush", "フォローが完了しました。");
                response.sendRedirect(request.getContextPath() + "/employees/index");
            }

    }

}
